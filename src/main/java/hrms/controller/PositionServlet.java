package hrms.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

import hrms.dao.PositionDAO;
import hrms.model.Account;
import hrms.model.Position;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/option/position")
public class PositionServlet extends HttpServlet {

    private final PositionDAO positionDAO = new PositionDAO();
    private static final Pattern VALID_NAME = Pattern.compile("^[A-Za-zÀ-ỹ\\s]{2,100}$");

    private boolean hasHRPermission(HttpSession session) {
        Account account = (Account) session.getAttribute("account");
        return account != null && (account.getRole() == 1 || account.getRole() == 2);
    }

    private void loadPositions(HttpServletRequest request) {
        try {
            List<Position> positions = positionDAO.getAll();
            request.setAttribute("positions", positions);
            System.out.println("Loaded " + positions.size() + " positions");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error loading positions: " + e.getMessage());
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
            int result = positionDAO.insertIfNotExists(name);
            
            if (result > 0) {
                session.setAttribute("successMessage", "Position '" + name + "' added successfully!");
                System.out.println("Added position: " + name);
            } else {
                session.setAttribute("errorMessage", "Position '" + name + "' already exists!");
                System.out.println("Position already exists: " + name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            session.setAttribute("errorMessage", "Database error: " + e.getMessage());
        }

        response.sendRedirect(request.getContextPath() + "/option/position");
    }
}
