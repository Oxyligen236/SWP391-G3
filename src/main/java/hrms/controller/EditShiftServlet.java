package hrms.controller;

import hrms.dao.ShiftDAO;
import hrms.model.Shift;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.time.LocalTime;

@WebServlet("/EditShiftServlet")
public class EditShiftServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String shiftIdStr = request.getParameter("shiftID");
        if (shiftIdStr != null) {
            int shiftID = Integer.parseInt(shiftIdStr);

            ShiftDAO dao = new ShiftDAO();
            Shift shift = dao.getShiftById(shiftID);
            dao.closeConnection();


            request.setAttribute("shift", shift);
            RequestDispatcher rd = request.getRequestDispatcher("/view/shift/editShift.jsp");
            rd.forward(request, response);
        } else {
            response.sendRedirect("/shift");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        try {
            int shiftID = Integer.parseInt(request.getParameter("shiftID"));
            String name = request.getParameter("name");
            LocalTime checkin1 = LocalTime.parse(request.getParameter("checkin1"));
            LocalTime checkout1 = LocalTime.parse(request.getParameter("checkout1"));
            LocalTime checkin2 = LocalTime.parse(request.getParameter("checkin2"));
            LocalTime checkout2 = LocalTime.parse(request.getParameter("checkout2"));

            Shift s = new Shift();
            s.setShiftID(shiftID);
            s.setName(name);
            s.setCheckin1(checkin1);
            s.setCheckout1(checkout1);
            s.setCheckin2(checkin2);
            s.setCheckout2(checkout2);

            ShiftDAO dao = new ShiftDAO();
            boolean updated = dao.updateShift(s);
            dao.closeConnection();

            if (updated) {
                response.sendRedirect("/shift"); 
            } else {
                request.setAttribute("error", "Cập nhật thất bại!");
                request.getRequestDispatcher("/view/shift/editShift.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi xử lý dữ liệu!");
            request.getRequestDispatcher("/shift").forward(request, response);
        }
    }
}
