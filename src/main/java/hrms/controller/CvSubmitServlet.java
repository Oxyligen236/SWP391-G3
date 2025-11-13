package hrms.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.regex.Pattern;

import hrms.dao.DegreeDAO;
import hrms.model.CVs;
import hrms.model.Degree;
import hrms.service.CvService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/cv/submit")
public class CvSubmitServlet extends HttpServlet {

    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-ZÀ-ỹ\\s]+$");
    private static final Pattern CCCD_PATTERN = Pattern.compile("^\\d{9}$|^\\d{12}$");
    private static final Pattern ADDRESS_PATTERN = Pattern.compile("^[a-zA-Z0-9À-ỹ\\s,.-]+$");
    private static final Pattern NATIONALITY_PATTERN = Pattern.compile("^[a-zA-ZÀ-ỹ\\s]+$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\d{10}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        DegreeDAO degreeDAO = new DegreeDAO();
        List<Degree> degrees = degreeDAO.getAll();
        request.setAttribute("degrees", degrees);
        request.getRequestDispatcher("/view/cv/cv_Submit.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String name = request.getParameter("name");
        String dobStr = request.getParameter("dob");
        String gender = request.getParameter("gender");
        String CCCD = request.getParameter("CCCD");
        String address = request.getParameter("address");
        String nationality = request.getParameter("nationality");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String degree = request.getParameter("degree");
        String experience = request.getParameter("experience");
        String education = request.getParameter("education");
        String skills = request.getParameter("skills");
        String aboutMe = request.getParameter("aboutMe");
        String jdID = request.getParameter("jdID");

        DegreeDAO degreeDAO = new DegreeDAO();
        List<Degree> degrees = degreeDAO.getAll();
        request.setAttribute("degrees", degrees);

        if (name == null || name.trim().isEmpty()
                || dobStr == null || dobStr.trim().isEmpty()
                || gender == null || gender.trim().isEmpty()
                || CCCD == null || CCCD.trim().isEmpty()
                || address == null || address.trim().isEmpty()
                || nationality == null || nationality.trim().isEmpty()
                || email == null || email.trim().isEmpty()
                || phone == null || phone.trim().isEmpty()
                || degree == null || degree.trim().isEmpty()
                || experience == null || experience.trim().isEmpty()
                || education == null || education.trim().isEmpty()
                || skills == null || skills.trim().isEmpty()
                || aboutMe == null || aboutMe.trim().isEmpty()
                || jdID == null) {

            request.setAttribute("errorMessage", "All fields are required.");
            request.getRequestDispatcher("/view/cv/cv_Submit.jsp").forward(request, response);
            return;
        }

        if (!NAME_PATTERN.matcher(name.trim()).matches()) {
            request.setAttribute("errorMessage", "Name cannot contain numbers or special characters.");
            request.getRequestDispatcher("/view/cv/cv_Submit.jsp").forward(request, response);
            return;
        }

        LocalDate dob;
        try {
            dob = LocalDate.parse(dobStr);
            LocalDate now = LocalDate.now();
            int age = Period.between(dob, now).getYears();

            if (dob.isAfter(now)) {
                request.setAttribute("errorMessage", "Date of birth cannot be in the future.");
                request.getRequestDispatcher("/view/cv/cv_Submit.jsp").forward(request, response);
                return;
            }

            if (age < 18) {
                request.setAttribute("errorMessage", "You must be at least 18 years old.");
                request.getRequestDispatcher("/view/cv/cv_Submit.jsp").forward(request, response);
                return;
            }

            if (age > 100) {
                request.setAttribute("errorMessage", "Invalid date of birth.");
                request.getRequestDispatcher("/view/cv/cv_Submit.jsp").forward(request, response);
                return;
            }
        } catch (IOException e) {
            request.setAttribute("errorMessage", "Invalid date format.");
            request.getRequestDispatcher("/view/cv/cv_Submit.jsp").forward(request, response);
            return;
        }

        if (!gender.equals("male") && !gender.equals("female") && !gender.equals("other")) {
            request.setAttribute("errorMessage", "Invalid gender selection.");
            request.getRequestDispatcher("/view/cv/cv_Submit.jsp").forward(request, response);
            return;
        }

        if (!CCCD_PATTERN.matcher(CCCD.trim()).matches()) {
            request.setAttribute("errorMessage", "CCCD must be exactly 9 or 12 digits.");
            request.getRequestDispatcher("/view/cv/cv_Submit.jsp").forward(request, response);
            return;
        }

        if (!ADDRESS_PATTERN.matcher(address.trim()).matches()) {
            request.setAttribute("errorMessage", "Address cannot contain special characters other than commas, periods, and hyphens.");
            request.getRequestDispatcher("/view/cv/cv_Submit.jsp").forward(request, response);
            return;
        }

        if (!NATIONALITY_PATTERN.matcher(nationality.trim()).matches()) {
            request.setAttribute("errorMessage", "Nationality cannot contain numbers or special characters.");
            request.getRequestDispatcher("/view/cv/cv_Submit.jsp").forward(request, response);
            return;
        }

        if (!EMAIL_PATTERN.matcher(email.trim()).matches()) {
            request.setAttribute("errorMessage", "Invalid email format.");
            request.getRequestDispatcher("/view/cv/cv_Submit.jsp").forward(request, response);
            return;
        }

        if (!PHONE_PATTERN.matcher(phone.trim()).matches()) {
            request.setAttribute("errorMessage", "Phone number must be exactly 10 digits.");
            request.getRequestDispatcher("/view/cv/cv_Submit.jsp").forward(request, response);
            return;
        }

        int jdIDInt;
        try {
            jdIDInt = Integer.parseInt(jdID);
            if (jdIDInt <= 0) {
                request.setAttribute("errorMessage", "Invalid Job ID.");
                request.getRequestDispatcher("/view/cv/cv_Submit.jsp").forward(request, response);
                return;
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "JD ID is invalid.");
            request.getRequestDispatcher("/view/cv/cv_Submit.jsp").forward(request, response);
            return;
        }

        CvService cvService = new CvService();
        CVs newCV = new CVs(jdIDInt, name.trim(), dob, gender, CCCD.trim(), address.trim(),
                nationality.trim(), email.trim(), phone.trim(), degree,
                experience.trim(), education.trim(), skills.trim(), aboutMe.trim(), "Pending");

        boolean isAdded = cvService.addCV(newCV);

        if (isAdded) {
            request.setAttribute("successMessage", "Submit CV successful!");
            request.setAttribute("redirectUrl", request.getContextPath() + "/view/home/homePage_guest.jsp");
        } else {
            request.setAttribute("errorMessage", "Submit CV failed. Please try again!");
        }
        request.getRequestDispatcher("/view/cv/cv_Submit.jsp").forward(request, response);
    }
}
