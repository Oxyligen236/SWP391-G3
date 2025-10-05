package hrms.controller;

import java.io.IOException;

import hrms.dao.CVsDAO;
import hrms.model.CVs;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UpdateCVServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            request.setAttribute("CV_ID_error", "CV ID is required");
            request.getRequestDispatcher("/view/cv_Update.jsp").forward(request, response);
            return;
        }

        int cvId;
        try {
            cvId = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            request.setAttribute("CV_ID_error", "Invalid CV ID");
            request.getRequestDispatcher("/view/cv_Update.jsp").forward(request, response);
            return;
        }

        CVsDAO cvDao = new CVsDAO();
        CVs cv = cvDao.getCVById(cvId);
        if (cv == null) {
            request.setAttribute("CV_ID_error", "CV not found");
        } else {
            request.setAttribute("cv", cv);
        }
        request.getRequestDispatcher("/view/cv_Update.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}
