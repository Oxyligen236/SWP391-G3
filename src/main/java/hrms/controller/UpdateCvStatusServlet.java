package hrms.controller;

import java.io.IOException;
import java.util.List;

import hrms.dao.CVsDAO;
import hrms.dto.CVJobDetailDTO;
import hrms.service.CvService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/cv/updateCvStatus")
public class UpdateCvStatusServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String cvIDStr = request.getParameter("cvID");
        String newStatus = request.getParameter("status");

        if (cvIDStr == null || cvIDStr.isEmpty() || newStatus == null || newStatus.isEmpty()) {
            request.setAttribute("CV_ID_error", "CV ID and Status are required");
            request.getRequestDispatcher("/view/cv/cv_Detail.jsp").forward(request, response);
            return;
        }

        try {
            int cvID = Integer.parseInt(cvIDStr);
            CVsDAO cvDAO = new CVsDAO();
            CvService cvService = new CvService();

            boolean updated = cvDAO.updateCvStatus(cvID, newStatus);

            CVJobDetailDTO cvDetail = cvService.getCvWithJobTitle(cvID);

            HttpSession session = request.getSession();
            Boolean isFiltering = (Boolean) session.getAttribute("isFiltering");

            List<CVJobDetailDTO> cvList;
            if (isFiltering != null && isFiltering) {
                cvList = (List<CVJobDetailDTO>) session.getAttribute("filteredCVs");
            } else {
                cvList = cvService.getAllCVJobTitle();
            }

            CVJobDetailDTO prevCV = null;
            CVJobDetailDTO nextCV = null;

            if (cvList != null) {
                for (int i = 0; i < cvList.size(); i++) {
                    if (cvList.get(i).getCvID() == cvID) {
                        if (i > 0) {
                            prevCV = cvList.get(i - 1);
                        }
                        if (i < cvList.size() - 1) {
                            nextCV = cvList.get(i + 1);
                        }
                        break;
                    }
                }
            }

            request.setAttribute("cvDetail", cvDetail);
            request.setAttribute("prevCV", prevCV);
            request.setAttribute("nextCV", nextCV);

            if (updated) {
                request.setAttribute("successMessage", "CV status updated successfully!");
            } else {
                request.setAttribute("CV_ID_error", "Failed to update CV status");
            }

            request.getRequestDispatcher("/view/cv/cv_Detail.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            request.setAttribute("CV_ID_error", "Invalid CV ID format");
            request.getRequestDispatcher("/view/cv/cv_Detail.jsp").forward(request, response);
        }
    }
}
