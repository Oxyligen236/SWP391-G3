package hrms.controller;

import java.io.IOException;

import hrms.service.CvService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/cv/updateCvStatus")
public class CvUpdateServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/view/cv/cv_Update.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String cvIdRaw = request.getParameter("cvID");
        String status = request.getParameter("status");
        CvService cvService = new CvService();

        try {
            int cvId = Integer.parseInt(cvIdRaw);
            boolean isUpdated = cvService.updateCvStatus(cvId, status);
            if (isUpdated) {
                response.sendRedirect(request.getContextPath() + "/cv/detail?id=" + cvId);
            } else {
                request.setAttribute("errorMessage", "Failed to update CV status.");
                request.getRequestDispatcher("/view/cv/cv_Detail.jsp").forward(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid CV ID.");
            request.getRequestDispatcher("/view/cv/cv_Detail.jsp").forward(request, response);
        }

    }

}
