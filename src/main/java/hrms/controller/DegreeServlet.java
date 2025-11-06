package hrms.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

import hrms.dao.DegreeDAO;
import hrms.model.Account;
import hrms.model.Degree;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/option/degree")
public class DegreeServlet extends HttpServlet {

    private final DegreeDAO degreeDAO = new DegreeDAO();
    private static final Pattern VALID_NAME = Pattern.compile("^[A-Za-zÀ-ỹ\\s]{2,100}$");

    private boolean hasHRPermission(HttpSession session) {
        Account account = (Account) session.getAttribute("account");
        return account != null && (account.getRole() == 1 || account.getRole() == 2);
    }

    private void loadDegrees(HttpServletRequest request) {
        try {
            List<Degree> degrees = degreeDAO.getAll();
            request.setAttribute("degrees", degrees);
            System.out.println("Loaded " + degrees.size() + " degrees successfully");
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error loading degree list: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!hasHRPermission(request.getSession())) {
            response.sendRedirect(request.getContextPath() + "/view/profile/accessDenied.jsp");
            return;
        }

        loadDegrees(request);
        request.getRequestDispatcher("/view/profile/degree.jsp").forward(request, response);
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
            session.setAttribute("errorMessage", "Please enter the degree name!");
            response.sendRedirect(request.getContextPath() + "/option/degree");
            return;
        }

        name = name.trim();

        if (!VALID_NAME.matcher(name).matches()) {
            session.setAttribute("errorMessage", "Degree name must contain only letters and spaces (2-100 characters)!");
            response.sendRedirect(request.getContextPath() + "/option/degree");
            return;
        }

        try {
            int result = degreeDAO.insertIfNotExists(name);

            if (result > 0) {
                session.setAttribute("successMessage", "Degree '" + name + "' added successfully!");
                System.out.println("Added degree: " + name);
            } else {
                session.setAttribute("errorMessage", "Degree '" + name + "' already exists!");
                System.out.println("Degree already exists: " + name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            session.setAttribute("errorMessage", "Database error: " + e.getMessage());
        }

        response.sendRedirect(request.getContextPath() + "/option/degree");
    }
}
