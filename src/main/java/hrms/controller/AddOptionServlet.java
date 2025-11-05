package hrms.controller;

import java.io.IOException;
import java.util.regex.Pattern;

import hrms.dao.DegreeDAO;
import hrms.dao.DepartmentDAO;
import hrms.dao.PositionDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/option/add")
public class AddOptionServlet extends HttpServlet {
    private final PositionDAO positionDAO = new PositionDAO();
    private final DepartmentDAO departmentDAO = new DepartmentDAO();
    private final DegreeDAO degreeDAO = new DegreeDAO();

    private static final Pattern VALID_NAME = Pattern.compile("^[A-Za-zÀ-ỹ\\s]{2,50}$");

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        String type = request.getParameter("type");
        String name = request.getParameter("name");

        // 🧩 Validate cơ bản
        if (type == null || name == null || name.trim().isEmpty()) {
            session.setAttribute("errorMessage", "⚠️ Please enter all required fields!");
            response.sendRedirect(request.getContextPath() + "/view/option/add_option.jsp");
            return;
        }

        name = name.trim();

        // 🧩 Validate độ dài
        if (name.length() < 2) {
            session.setAttribute("errorMessage", "⚠️ Name is too short (minimum 2 characters).");
            response.sendRedirect(request.getContextPath() + "/view/option/add_option.jsp");
            return;
        }

        // 🧩 Validate ký tự
        if (!VALID_NAME.matcher(name).matches()) {
            session.setAttribute("errorMessage", "⚠️ Name contains invalid characters!");
            response.sendRedirect(request.getContextPath() + "/view/option/add_option.jsp");
            return;
        }

        boolean success = false;
        try {
            switch (type) {
                case "position":
                    if (positionDAO.exists(name)) {
                        session.setAttribute("errorMessage", "❌ Position '" + name + "' already exists!");
                    } else {
                        success = positionDAO.insertIfNotExists(name) > 0;
                    }
                    break;

                case "department":
                    if (departmentDAO.exists(name)) {
                        session.setAttribute("errorMessage", "❌ Department '" + name + "' already exists!");
                    } else {
                        success = departmentDAO.insertIfNotExists(name) > 0;
                    }
                    break;

                case "degree":
                    if (degreeDAO.exists(name)) {
                        session.setAttribute("errorMessage", "❌ Degree '" + name + "' already exists!");
                    } else {
                        success = degreeDAO.insertIfNotExists(name) > 0;
                    }
                    break;

                default:
                    session.setAttribute("errorMessage", "❌ Invalid type!");
                    response.sendRedirect(request.getContextPath() + "/view/option/add_option.jsp");
                    return;
            }

            if (success) {
                session.setAttribute("successMessage", "✅ Successfully added new " + type + ": " + name);
            } else if (session.getAttribute("errorMessage") == null) {
                session.setAttribute("errorMessage", "❌ Failed to add new " + type + ".");
            }

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("errorMessage", "💥 Database error: " + e.getMessage());
        }

        response.sendRedirect(request.getContextPath() + "/view/option/add_option.jsp");
    }
}
