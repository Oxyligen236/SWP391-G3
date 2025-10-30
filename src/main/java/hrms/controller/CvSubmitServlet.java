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

        String name = request.getParameter("name");
        String gender = request.getParameter("gender");
        String address = request.getParameter("address");
        String nationality = request.getParameter("nationality");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String experience = request.getParameter("experience");
        String education = request.getParameter("education");
        String skills = request.getParameter("skills");
        String aboutMe = request.getParameter("aboutMe");
        String jdID = request.getParameter("jdID");
        CvService cvService = new CvService();
        try {
            int jdIDInt = Integer.parseInt(jdID);
            if (name == null
                    || name.trim().isEmpty()
                    || gender == null || gender.trim().isEmpty()
                    || address == null || address.trim().isEmpty()
                    || nationality == null || nationality.trim().isEmpty()
                    || email == null || email.trim().isEmpty()
                    || phone == null || phone.trim().isEmpty()
                    || experience == null || experience.trim().isEmpty()
                    || education == null || education.trim().isEmpty()
                    || skills == null || skills.trim().isEmpty()
                    || aboutMe == null || aboutMe.trim().isEmpty()
                    || jdID == null) {

                request.setAttribute("errorMessage", "Vui lòng điền đầy đủ thông tin!");
                request.getRequestDispatcher("/view/cv/cv_Submit.jsp").forward(request, response);
                return;
            }
            CVs newCV = new CVs(jdIDInt, name, gender, address, nationality, email, phone, experience, education, skills, aboutMe, "Pending");
            boolean isAdded = cvService.addCV(newCV);
            if (isAdded) {
                request.setAttribute("successMessage", "Nộp CV thành công!");
            } else {
                request.setAttribute("errorMessage", "Nộp CV không thành công. Vui lòng thử lại!");
            }
            request.getRequestDispatcher("/view/cv/cv_Submit.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "JD ID không hợp lệ!");
            request.getRequestDispatcher("/view/cv/cv_Submit.jsp").forward(request, response);
        }

    }

}
