package hrms.controller;

import hrms.dao.WorkHistoryDAO;
import hrms.model.WorkHistory;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/workhistory")
public class WorkHistoryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        WorkHistoryDAO dao = new WorkHistoryDAO();

        HttpSession session = request.getSession();

        try {
            String search = request.getParameter("search");


            List<WorkHistory> WorkHistoryList = dao.getFilteredHistory(search);

            request.setAttribute("WorkHistoryList", WorkHistoryList);

            request.setAttribute("search", search);

 

            request.getRequestDispatcher("/view/workhistory/workhistory.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi hệ thống: " + e.getMessage());
        }
    }
}
