package hrms.controller;

import java.io.IOException;

import hrms.dao.CVsDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/cv")
public class CVServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CVsDAO cvDao = new CVsDAO();
        request.setAttribute("cvs", cvDao.getAll());
        request.getRequestDispatcher("/view/cv/cv_List.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Handle form submission for CV
    }
}
