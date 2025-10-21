package hrms.controller;

import java.io.IOException;

import hrms.model.CVs;
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
        request.getRequestDispatcher("/view/cv/cv_Submit.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String title = request.getParameter("title");
        String name = request.getParameter("name");
        String gender = request.getParameter("gender");
        String address = request.getParameter("address");
        String nationality = request.getParameter("nationality");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String description = request.getParameter("cv_Description");
        String jdID = request.getParameter("jdID");
        CvService cvService = new CvService();

        request.setAttribute("title", title);
        request.setAttribute("jdID", jdID);

        try {
            int jdIDInt = Integer.parseInt(jdID);

            if (name == null || name.trim().isEmpty()
                    || gender == null || gender.trim().isEmpty()
                    || address == null || address.trim().isEmpty()
                    || nationality == null || nationality.trim().isEmpty()
                    || email == null || email.trim().isEmpty()
                    || phone == null || phone.trim().isEmpty()
                    || description == null || description.trim().isEmpty()
                    || jdID == null) {

                request.setAttribute("errorMessage", "Please fill in all required information!");
                request.getRequestDispatcher("/view/cv/cv_Submit.jsp").forward(request, response);
                return;
            }

            CVs newCV = new CVs(jdIDInt, name, gender, address, nationality, email, phone, description, "Pending");
            boolean isAdded = cvService.addCV(newCV);

            if (isAdded) {
                request.setAttribute("successMessage", "CV submitted successfully!");
            } else {
                request.setAttribute("errorMessage", "Failed to submit CV. Please try again!");
            }

            request.getRequestDispatcher("/view/cv/cv_Submit.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid JD ID!");
            request.getRequestDispatcher("/view/cv/cv_Submit.jsp").forward(request, response);
        }
    }
}
