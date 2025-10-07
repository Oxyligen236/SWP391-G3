package hrms.controller;

import java.io.IOException;

import hrms.dao.CVsDAO;
import hrms.model.CVs;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/cv/update")
public class UpdateCVServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");
        String view = "/view/cv/cv_Update.jsp";

        if (idParam == null || idParam.isEmpty()) {
            request.setAttribute("CV_ID_error", "Không có CV_ID");
            request.getRequestDispatcher(view).forward(request, response);
            return;
        }

        try {
            int cvId = Integer.parseInt(idParam);
            CVsDAO cvDao = new CVsDAO();
            CVs cv = cvDao.getCvById(cvId);

            if (cv == null) {
                request.setAttribute("CV_ID_error", "CV không tồn tại");
            } else {
                request.setAttribute("cv", cv);
            }

        } catch (NumberFormatException e) {
            request.setAttribute("CV_ID_error", "Định dạng CV_ID không hợp lệ");
        }

        request.getRequestDispatcher(view).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}
