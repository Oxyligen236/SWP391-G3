package hrms.controller;

import java.io.IOException;
import java.util.List;

import hrms.dao.JobDAO;
import hrms.model.Account;
import hrms.model.JobDescription;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/jdlist")
public class JdListServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        JobDAO dao = new JobDAO();

        HttpSession session = request.getSession();

        try {
            String search = request.getParameter("search");
            String departmentFilter = request.getParameter("department");
            String statusFilter = request.getParameter("status");

            List<JobDescription> jdList = dao.getFilteredJD(
                    search, departmentFilter, statusFilter, 1, 100);

            request.setAttribute("jdList", jdList);

            request.setAttribute("search", search);
            request.setAttribute("departmentFilter", departmentFilter);
            request.setAttribute("statusFilter", statusFilter);

            request.getRequestDispatcher("/view/jd/jdlist.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi hệ thống: " + e.getMessage());
        }

    }
    
}
