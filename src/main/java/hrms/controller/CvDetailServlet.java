package hrms.controller;

import java.io.IOException;
import java.util.List;

import hrms.dto.CVJobDetailDTO;
import hrms.service.CvService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/cv/detail")
public class CvDetailServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");
        try {
            int cvId = Integer.parseInt(idParam);
            CvService cvService = new CvService();
            CVJobDetailDTO cvDetail = cvService.getCvWithJobTitle(cvId);

            if (cvDetail == null) {
                request.setAttribute("CV_ID_error", "CV with ID " + cvId + " not found.");
            } else {
                HttpSession session = request.getSession();
                Boolean isFiltering = (Boolean) session.getAttribute("isFiltering");

                List<CVJobDetailDTO> cvList;
                if (isFiltering) {
                    cvList = (List<CVJobDetailDTO>) session.getAttribute("filteredCVs");
                } else {
                    cvList = cvService.getAllCVJobTitle();
                }
                if (cvList != null && !cvList.isEmpty()) {
                    int index = -1;
                    for (int i = 0; i < cvList.size(); i++) {
                        if (cvList.get(i).getCvID() == cvId) {
                            index = i;
                            break;
                        }
                    }
                    if (index > 0) {
                        request.setAttribute("prevCV", cvList.get(index - 1));
                    }
                    if (index < cvList.size() - 1) {
                        request.setAttribute("nextCV", cvList.get(index + 1));
                    }
                }

                request.setAttribute("cvDetail", cvDetail);
            }

            request.getRequestDispatcher("/view/cv/cv_Detail.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            request.setAttribute("CV_ID_error", "Invalid CV ID format: " + idParam);
            request.getRequestDispatcher("/view/cv/cv_Detail.jsp").forward(request, response);
        }

    }

}
