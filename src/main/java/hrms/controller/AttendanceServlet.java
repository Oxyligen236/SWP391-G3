package hrms.controller;

import java.io.IOException;
import java.util.List;

import hrms.dao.DepartmentDAO;
import hrms.dao.PositionDAO;
import hrms.dto.AttendanceDTO;
import hrms.service.AttendanceService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/attendance")
public class AttendanceServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        DepartmentDAO departmentDAO = new DepartmentDAO();
        PositionDAO positionDAO = new PositionDAO();
        AttendanceService attendanceService = new AttendanceService();
        List<AttendanceDTO> attendanceList = attendanceService.getAllAttendances();
        req.setAttribute("departments", departmentDAO.getAll());
        req.setAttribute("positions", positionDAO.getAll());
        req.setAttribute("attendances", attendanceList);
        req.getRequestDispatcher("/view/attendance/companyAttendance.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String name = req.getParameter("userName");
        String department = req.getParameter("department");
        String position = req.getParameter("position");
        String hasCheckout3 = req.getParameter("hasCheckout3");
        String hasLate = req.getParameter("hasLate");
        String hasEarlyLeave = req.getParameter("hasEarlyLeave");
        String hasOT = req.getParameter("hasOT");

        boolean checkout3;
        boolean late;
        boolean earlyLeave;
        boolean ot;
        DepartmentDAO departmentDAO = new DepartmentDAO();
        PositionDAO positionDAO = new PositionDAO();
        req.setAttribute("departments", departmentDAO.getAll());
        req.setAttribute("positions", positionDAO.getAll());
        try {
            checkout3 = hasCheckout3 != null;
            late = hasLate != null;
            earlyLeave = hasEarlyLeave != null;
            ot = hasOT != null;
            AttendanceService attendanceService = new AttendanceService();
            List<AttendanceDTO> attendanceList = attendanceService.searchAttendances(
                    name, department, position, checkout3, late, earlyLeave, ot);
            req.setAttribute("attendances", attendanceList);
            req.getRequestDispatcher("/view/attendance/companyAttendance.jsp").forward(req, resp);
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing your request.");
        }

    }

}
