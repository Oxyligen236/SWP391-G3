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
import jakarta.servlet.http.HttpSession;

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

        CvService cvService = new CvService();
        JobDAO jobDAO = new JobDAO();

        jobID = (jobID != null && !jobID.trim().isEmpty()) ? jobID.trim() : null;
        name = (name != null && !name.trim().isEmpty()) ? name.trim() : null;
        email = (email != null && !email.trim().isEmpty()) ? email.trim() : null;
        phone = (phone != null && !phone.trim().isEmpty()) ? phone.trim() : null;
        gender = (gender != null && !gender.trim().isEmpty()) ? gender.trim() : null;
        status = (status != null && !status.trim().isEmpty()) ? status.trim() : null;

        // Lưu search params vào session
        HttpSession session = request.getSession();
        session.setAttribute("searchJobID", jobID);
        session.setAttribute("searchName", name);
        session.setAttribute("searchEmail", email);
        session.setAttribute("searchPhone", phone);
        session.setAttribute("searchGender", gender);
        session.setAttribute("searchStatus", status);

        // Phân trang
        int itemsPerPage = 5;
        if (request.getParameter("itemsPerPage") != null) {
            try {
                itemsPerPage = Integer.parseInt(request.getParameter("itemsPerPage"));
            } catch (NumberFormatException e) {
                itemsPerPage = 5;
            }
        }

        int currentPage = 1;
        if (request.getParameter("page") != null) {
            try {
                currentPage = Integer.parseInt(request.getParameter("page"));
            } catch (NumberFormatException e) {
                currentPage = 1;
            }
        }

        try {
            int jobIdInt = (jobID != null) ? Integer.parseInt(jobID) : 0;

            request.setAttribute("jobs", jobDAO.getAll());

            List<CVJobDetailDTO> cvList;
            if (jobIdInt > 0 || name != null || email != null || phone != null || gender != null || status != null) {
                cvList = cvService.searchCVs(jobIdInt, name, email, phone, gender, status);
                session.setAttribute("filteredCVs", cvList);
                session.setAttribute("isFiltering", true);
            } else {
                cvList = cvService.getAllCVJobTitle();
                session.setAttribute("isFiltering", false);
            }

            // Tính toán phân trang
            int totalItems = cvList.size();
            int totalPages = (int) Math.ceil((double) totalItems / itemsPerPage);

            if (currentPage > totalPages && totalPages > 0) {
                currentPage = totalPages;
            }

            int startIndex = (currentPage - 1) * itemsPerPage;
            int endIndex = Math.min(startIndex + itemsPerPage, totalItems);

            List<CVJobDetailDTO> paginatedCvs = cvList.subList(startIndex, endIndex);

            request.setAttribute("cvs", paginatedCvs);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("itemsPerPage", itemsPerPage);

            if (cvList.isEmpty()) {
                request.setAttribute("message", "No CVs found.");
            }

            request.getRequestDispatcher("/view/cv/cv_List.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("jobID_error", "Invalid Job_ID format");
            request.getRequestDispatcher("/view/cv/cv_List.jsp").forward(request, response);
        }
    }

}
