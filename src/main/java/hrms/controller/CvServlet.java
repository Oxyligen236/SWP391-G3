package hrms.controller;

import java.io.IOException;

import hrms.service.CvService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/cv")
public class CvServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        CvService cvService = new CvService();

        name = (name != null && !name.trim().isEmpty()) ? name.trim() : null;
        email = (email != null && !email.trim().isEmpty()) ? email.trim() : null;
        phone = (phone != null && !phone.trim().isEmpty()) ? phone.trim() : null;

        if (name != null || email != null || phone != null) {
            request.setAttribute("cvs", cvService.searchCVs(name, email, phone));
        } else {
            request.setAttribute("cvs", cvService.getAllCVJobTitle());
        }
        request.getRequestDispatcher("/view/cv/cv_List.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}
