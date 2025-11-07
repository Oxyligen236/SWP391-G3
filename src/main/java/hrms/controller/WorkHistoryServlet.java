package hrms.controller;

import hrms.dao.UserDAO;
import hrms.dao.WorkHistoryDAO;
import hrms.model.User;
import hrms.model.WorkHistory;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/workhistory")
public class WorkHistoryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        WorkHistoryDAO dao = new WorkHistoryDAO();

        HttpSession session = request.getSession();

        try {
            String search = request.getParameter("search");
            UserDAO userDao = new UserDAO();
            User u= new User();
            List<WorkHistory> WorkHistoryList = dao.getFilteredHistory(search);

            for (WorkHistory wh : WorkHistoryList) {
                if (wh.getUserID() != 0) {
                    u.setFullname(userDao.getUserById(wh.getUserID()).getFullname());
                }
            }

            request.setAttribute("WorkHistoryList", WorkHistoryList);

Map<Integer, String> userNames = new HashMap<>();
for (WorkHistory wh : WorkHistoryList) {
    if (wh.getUserID() != 0) {
        User user = userDao.getUserById(wh.getUserID());
        if (user != null) {
            userNames.put(wh.getUserID(), user.getFullname());
        }
    }
}
request.setAttribute("UserNames", userNames);

            request.setAttribute("WorkHistoryList", WorkHistoryList);

            request.setAttribute("search", search);

            request.getRequestDispatcher("/view/workhistory/workhistory.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi hệ thống: " + e.getMessage());
        }
    }
}
