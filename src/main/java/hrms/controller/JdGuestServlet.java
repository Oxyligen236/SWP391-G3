package hrms.controller;

import hrms.dao.DepartmentDAO;
import hrms.dao.JobDAO;
import hrms.model.Department;
import hrms.model.JobDescription;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/jd_guest")
public class JdGuestServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        JobDAO dao = new JobDAO();
        HttpSession session = request.getSession();

        try {
            String search = request.getParameter("search");
            String departmentFilter = request.getParameter("department");
            String statusFilter = "InProgress"; // guest chỉ thấy InProgress

            int currentPage = 1;
            int itemsPerPage = 5;

            String pageParam = request.getParameter("page");
            String itemsParam = request.getParameter("itemsPerPage");

            if (pageParam != null) {
                try { currentPage = Integer.parseInt(pageParam); if(currentPage < 1) currentPage=1; } catch (NumberFormatException e){ currentPage=1; }
            }
            if (itemsParam != null) {
                try { itemsPerPage = Integer.parseInt(itemsParam); if(itemsPerPage < 1) itemsPerPage=5; if(itemsPerPage>50) itemsPerPage=50; } catch (NumberFormatException e){ itemsPerPage=5; }
            }

            List<JobDescription> allJDs = dao.getFilteredJD(search, departmentFilter, statusFilter, 1, 1000); // lấy tất cả để tính tổng
            int totalItems = allJDs.size();
            int totalPages = (int)Math.ceil((double)totalItems / itemsPerPage);

            if(currentPage > totalPages && totalPages>0) currentPage = totalPages;

            int startIndex = (currentPage - 1) * itemsPerPage;
            int endIndex = Math.min(startIndex + itemsPerPage, totalItems);

            List<JobDescription> jdList = allJDs.subList(startIndex, endIndex);

            request.setAttribute("jdList", jdList);
            request.setAttribute("search", search);
            request.setAttribute("departmentFilter", departmentFilter);
            request.setAttribute("statusFilter", statusFilter);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("itemsPerPage", itemsPerPage);
            request.setAttribute("totalPages", totalPages);

            DepartmentDAO d = new DepartmentDAO();
            List<Department> departments = d.getAll();
            request.setAttribute("departments", departments);

            request.getRequestDispatcher("/view/jd/jd_guest.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi hệ thống: " + e.getMessage());
        }
    }
}
