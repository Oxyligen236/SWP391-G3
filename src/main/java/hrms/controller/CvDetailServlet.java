package hrms.controller;

import java.io.IOException;
import java.util.List;

import hrms.dao.CVsDAO;
import hrms.model.CVs;
import hrms.service.CvService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/cv/detail")
public class CvDetailServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");
        try {
            int cvId = Integer.parseInt(idParam);
            CVsDAO cv = new CVsDAO();
            CvService cvService = new CvService();

            List<CVs> cvDetails = cv.getAll();
            cvService.getCvWithJobDetail(cvId);
            if (cvDetails == null) {
                request.setAttribute("CV_ID_error", "CV không tồn tại");
            } else {
                request.setAttribute("cvDetails", cvDetails);
                request.getRequestDispatcher("/view/cv/cv_Detail.jsp").forward(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("CV_ID_error", "Định dạng CV_ID không hợp lệ");
        }

    }

}
