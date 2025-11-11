package hrms.controller;

import java.io.IOException;
import java.util.List;

import hrms.dao.DepartmentDAO;
import hrms.dao.JobDAO;
import hrms.model.Department;
import hrms.model.JobDescription;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/jdlist")
public class JdListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        JobDAO dao = new JobDAO();
        DepartmentDAO dDao = new DepartmentDAO();

        try {
            dao.autoCancelExpiredJobs();

            String search = request.getParameter("search");
            String departmentFilter = request.getParameter("department");
            String statusFilter = request.getParameter("status");


            int page = 1;
            int itemsPerPage = 5;

            String pageParam = request.getParameter("page");
            String itemsPerPageParam = request.getParameter("itemsPerPage");

            if (pageParam != null && !pageParam.isEmpty()) {
                try {
                    page = Integer.parseInt(pageParam);
                    if (page < 1) page = 1;
                } catch (NumberFormatException e) { page = 1; }
            }

            if (itemsPerPageParam != null && !itemsPerPageParam.isEmpty()) {
                try {
                    itemsPerPage = Integer.parseInt(itemsPerPageParam);
                    if (itemsPerPage < 1) itemsPerPage = 5;
                    if (itemsPerPage > 50) itemsPerPage = 50;
                } catch (NumberFormatException e) { itemsPerPage = 5; }
            }

            List<JobDescription> allJD = dao.getFilteredJD(search, departmentFilter, statusFilter, 1, 1000);

            int totalItems = allJD.size();
            int totalPages = (int) Math.ceil((double) totalItems / itemsPerPage);
            if (page > totalPages && totalPages > 0) page = totalPages;

            int startIndex = (page - 1) * itemsPerPage;
            int endIndex = Math.min(startIndex + itemsPerPage, totalItems);

            List<JobDescription> paginatedJD = allJD.subList(startIndex, endIndex);

            request.setAttribute("jdList", paginatedJD);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("itemsPerPage", itemsPerPage);
            request.setAttribute("totalItems", totalItems);

            request.setAttribute("search", search);
            request.setAttribute("departmentFilter", departmentFilter);
            request.setAttribute("statusFilter", statusFilter);

            List<Department> departments = dDao.getAll();
            request.setAttribute("departments", departments);

            request.getRequestDispatcher("/view/jd/jdlist.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi hệ thống: " + e.getMessage());
        }
    }
}
