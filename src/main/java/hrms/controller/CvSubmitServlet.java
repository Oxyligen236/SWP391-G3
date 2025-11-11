package hrms.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

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
        CvService cvService = new CvService();

        DegreeDAO degreeDAO = new DegreeDAO();

        try {
            int jdIDInt = Integer.parseInt(jdID);
            LocalDate dob = LocalDate.parse(dobStr);
            List<Degree> degrees = degreeDAO.getAll();
            request.setAttribute("degrees", degrees);

            if (name == null
                    || name.trim().isEmpty()
                    || dob == null
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

            CVs newCV = new CVs(jdIDInt, name, dob, gender, CCCD, address, nationality, email, phone, degree, experience, education, skills, aboutMe, "Pending");
            boolean isAdded = cvService.addCV(newCV);

            if (isAdded) {
                request.setAttribute("successMessage", "Submit CV successful!");
                request.setAttribute("redirectUrl", request.getContextPath() + "/view/home/homePage_guest.jsp");
            } else {
                request.setAttribute("errorMessage", "Submit CV failed. Please try again!");
            }
            request.getRequestDispatcher("/view/cv/cv_Submit.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "JD ID is invalid.");
            request.getRequestDispatcher("/view/cv/cv_Submit.jsp").forward(request, response);
        }
    }
}
