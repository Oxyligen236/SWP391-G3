package hrms.controller;

import hrms.dao.AttendanceDAO;
import hrms.model.Attendance;
import hrms.model.Account;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/myattendance")
public class MyAttendanceServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Account account = (Account) (session != null ? session.getAttribute("account") : null);

        if (account == null) {
            response.sendRedirect(request.getContextPath() + "/view/authenticate/login.jsp");
            return;
        }

        int userID = account.getUserID(); 

        try {
            AttendanceDAO dao = new AttendanceDAO();
            List<Attendance> list = dao.getByUser(userID);

            request.setAttribute("attendanceList", list);
            request.getRequestDispatcher("/view/attendance/myattendance.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi tải dữ liệu chấm công");
        }
    }
}
