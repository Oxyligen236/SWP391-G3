package hrms.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

import hrms.dao.DepartmentDAO;
import hrms.dao.PositionDAO;
import hrms.dto.PositionDTO;
import hrms.model.Account;
import hrms.model.Department;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/option/position") // đổi URL
public class PositionServlet extends HttpServlet {

    private final PositionDAO positionDAO = new PositionDAO();
    private final DepartmentDAO departmentDAO = new DepartmentDAO();
    private static final Pattern VALID_NAME = Pattern.compile("^[A-Za-zÀ-ỹ\\s]{2,100}$");

    private boolean hasHRPermission(HttpSession session) {
        Account account = (Account) session.getAttribute("account");
        return account != null && (account.getRole() == 1 || account.getRole() == 2);
    }

    private void loadPositions(HttpServletRequest request) {
        try {
            List<PositionDTO> positions = positionDAO.getAllWithDepartmentName();
            request.setAttribute("positions", positions);

            List<Department> departments = departmentDAO.getAll();
            request.setAttribute("departments", departments);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error loading positions or departments: " + e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!hasHRPermission(request.getSession())) {
            response.sendRedirect(request.getContextPath() + "/view/profile/accessDenied.jsp");
            return;
        }

        loadPositions(request);
        request.getRequestDispatcher("/view/profile/position.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();

        if (!hasHRPermission(session)) {
            response.sendRedirect(request.getContextPath() + "/view/profile/accessDenied.jsp");
            return;
        }

        String name = request.getParameter("name");
        String departmentIdStr = request.getParameter("departmentId");
        int departmentId = 0;

        if (departmentIdStr != null && !departmentIdStr.isEmpty()) {
            try {
                departmentId = Integer.parseInt(departmentIdStr);
            } catch (NumberFormatException e) {
                session.setAttribute("errorMessage", "Invalid Department selected!");
                response.sendRedirect(request.getContextPath() + "/option/position");
                return;
            }
        }

        if (departmentId == 0) {
            session.setAttribute("errorMessage", "Please select a Department!");
            response.sendRedirect(request.getContextPath() + "/option/position");
            return;
        }

        if (name == null || name.trim().isEmpty()) {
            session.setAttribute("errorMessage", "Please enter a position name!");
            response.sendRedirect(request.getContextPath() + "/option/position");
            return;
        }

        name = name.trim();

        if (!VALID_NAME.matcher(name).matches()) {
            session.setAttribute("errorMessage", "Position name must contain only letters and spaces (2-100 characters)!");
            response.sendRedirect(request.getContextPath() + "/option/position");
            return;
        }

        try {
            int result = positionDAO.insertIfNotExists(name, departmentId);

            if (result > 0) {
                session.setAttribute("successMessage", "Position '" + name + "' added successfully to the department!");
            } else {
                session.setAttribute("errorMessage", "Position '" + name + "' already exists in this department!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            session.setAttribute("errorMessage", "Database error: " + e.getMessage());
        }

        response.sendRedirect(request.getContextPath() + "/option/position");
    }
}
