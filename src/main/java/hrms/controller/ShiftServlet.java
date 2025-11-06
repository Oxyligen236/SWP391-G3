package hrms.controller;

import hrms.dao.ShiftDAO;
import hrms.model.Shift;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/shift")
public class ShiftServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ShiftDAO dao = new ShiftDAO();
        List<Shift> shifts = dao.getAllShifts();


        request.setAttribute("shifts", shifts);
        request.getRequestDispatcher("/view/shift/shift.jsp").forward(request, response);
    }
}
