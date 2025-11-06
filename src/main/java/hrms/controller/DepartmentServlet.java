package hrms.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

import hrms.dao.DepartmentDAO;
import hrms.model.Account;
import hrms.model.Department;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/option/department")
public class DepartmentServlet extends HttpServlet {

    private final DepartmentDAO departmentDAO = new DepartmentDAO();
    private static final Pattern VALID_NAME = Pattern.compile("^[A-Za-zÀ-ỹ\\s]{2,100}$");

    private boolean hasHRPermission(HttpSession session) {
        Account account = (Account) session.getAttribute("account");
        return account != null && (account.getRole() == 1 || account.getRole() == 2);
    }

    private void loadDepartments(HttpServletRequest request) {
        try {
            List<Department> departments = departmentDAO.getAll();
            request.setAttribute("departments", departments);
            System.out.println("Loaded " + departments.size() + " departments");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error loading departments: " + e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!hasHRPermission(request.getSession())) {
            response.sendRedirect(request.getContextPath() + "/view/profile/accessDenied.jsp");
            return;
        }

        loadDepartments(request);

        request.getRequestDispatcher("/view/profile/department.jsp").forward(request, response);
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
            session.setAttribute("errorMessage", "Please enter a department name!");
            response.sendRedirect(request.getContextPath() + "/option/department");
            return;
        }

        name = name.trim();

        if (!VALID_NAME.matcher(name).matches()) {
            session.setAttribute("errorMessage", "Department name must contain only letters and spaces (2-100 characters)!");
            response.sendRedirect(request.getContextPath() + "/option/department");
            return;
        }

        try {
            int result = departmentDAO.insertIfNotExists(name);

            if (result > 0) {
                session.setAttribute("successMessage", "Department '" + name + "' added successfully!");
                System.out.println("Added department: " + name);
            } else {
                session.setAttribute("errorMessage", "Department '" + name + "' already exists!");
                System.out.println("Department already exists: " + name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            session.setAttribute("errorMessage", "Database error: " + e.getMessage());
        }

        response.sendRedirect(request.getContextPath() + "/option/department");
    }
}
