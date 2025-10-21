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
        List<WorkHistory> list = dao.getAll();

        request.setAttribute("workHistoryList", list);
        request.getRequestDispatcher("/view/workhistory/workhistory.jsp").forward(request, response);
    }
}
