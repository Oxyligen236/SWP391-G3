package hrms.controller;

import java.io.IOException;
import java.util.List;

import hrms.dao.JobDAO;
import hrms.dto.CVJobDetailDTO;
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
        String jobID = request.getParameter("jobID");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String gender = request.getParameter("gender");
        String status = request.getParameter("status");
        String itemsPerPageStr = request.getParameter("itemsPerPage");
        String pageStr = request.getParameter("page");

        int totalCVs;
        int totalPages;
        int itemsPerPage = 5;
        int currentPage = 1;

        try {
            if (itemsPerPageStr != null && !itemsPerPageStr.trim().isEmpty()) {
                itemsPerPage = Integer.parseInt(itemsPerPageStr);
            }
        } catch (NumberFormatException e) {
            itemsPerPage = 5;
        }

        try {
            if (pageStr != null && !pageStr.trim().isEmpty()) {
                currentPage = Integer.parseInt(pageStr);
            }
        } catch (NumberFormatException e) {
            currentPage = 1;
        }

        CvService cvService = new CvService();
        JobDAO jobDAO = new JobDAO();

        jobID = (jobID != null && !jobID.trim().isEmpty()) ? jobID.trim() : null;
        name = (name != null && !name.trim().isEmpty()) ? name.trim() : null;
        email = (email != null && !email.trim().isEmpty()) ? email.trim() : null;
        phone = (phone != null && !phone.trim().isEmpty()) ? phone.trim() : null;
        gender = (gender != null && !gender.trim().isEmpty()) ? gender.trim() : null;
        status = (status != null && !status.trim().isEmpty()) ? status.trim() : null;

        try {
            int jobIdInt = (jobID != null) ? Integer.parseInt(jobID) : 0;

            List<CVJobDetailDTO> cvs;
            if (jobIdInt > 0 || name != null || email != null || phone != null || gender != null || status != null) {
                cvs = cvService.searchCVs(jobIdInt, name, email, phone, gender, status);
                request.getSession().setAttribute("isFiltering", true);
                request.getSession().setAttribute("filteredCVs", cvs);
            } else {
                cvs = cvService.getAllCVJobTitle();
                request.getSession().setAttribute("isFiltering", false);
            }

            if (currentPage < 1) {
                currentPage = 1;
            }
            if (itemsPerPage <= 0) {
                itemsPerPage = 5;
            }

            totalCVs = cvs.size();
            totalPages = (int) Math.ceil((double) totalCVs / itemsPerPage);

            if (currentPage > totalPages && totalPages > 0) {
                currentPage = totalPages;
            }

            int startIndex = (currentPage - 1) * itemsPerPage;

            if (startIndex < 0) {
                startIndex = 0;
            }

            int endIndex = Math.min(startIndex + itemsPerPage, totalCVs);

            List<CVJobDetailDTO> paginatedCVs = cvs.subList(startIndex, endIndex);

            request.setAttribute("jobs", jobDAO.getAll());
            request.setAttribute("cvs", paginatedCVs);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("itemsPerPage", itemsPerPage);
            request.setAttribute("totalCVs", totalCVs);
            request.setAttribute("name", name);
            request.setAttribute("email", email);
            request.setAttribute("phone", phone);
            request.setAttribute("gender", gender);
            request.setAttribute("status", status);
            request.setAttribute("jobID", jobID);

            request.getRequestDispatcher("/view/cv/cv_List.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            request.setAttribute("jobID_error", "Invalid Job ID format");
            request.getRequestDispatcher("/view/cv/cv_List.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Implementation if needed
    }
}
