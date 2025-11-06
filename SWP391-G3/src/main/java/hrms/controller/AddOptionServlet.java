package hrms.controller;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

import hrms.dao.DegreeDAO;
import hrms.dao.DepartmentDAO;
import hrms.dao.PositionDAO;
import hrms.model.Account;
import hrms.model.Degree;
import hrms.model.Department;
import hrms.model.Position;
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

    private boolean hasHRPermission(HttpSession session) {
        Account account = (Account) session.getAttribute("account");
        if (account == null) return false;
        return account.getRole() == 1 || account.getRole() == 2; // 1=HR, 2=HR Manager
    }

    private boolean isValidType(String type) {
        return type != null && (type.equals("position") || type.equals("department") || type.equals("degree"));
    }

    private void loadOptions(HttpServletRequest request) {
        try {
            List<Position> positions = positionDAO.getAll();
            List<Department> departments = departmentDAO.getAll();
            List<Degree> degrees = degreeDAO.getAll();

            request.setAttribute("positions", positions);
            request.setAttribute("departments", departments);
            request.setAttribute("degrees", degrees);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "💥 Lỗi khi tải danh sách: " + e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!hasHRPermission(request.getSession())) {
            response.sendRedirect(request.getContextPath() + "/view/profile/accessDenied.jsp");
            return;
        }

        // Nếu chưa có type, để trống để dropdown JSP hiển thị
        String type = request.getParameter("type");
        if (!isValidType(type)) {
            type = "";
        }

        request.setAttribute("type", type);
        loadOptions(request);
        request.getRequestDispatcher("/view/profile/addOption.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!hasHRPermission(request.getSession())) {
            response.sendRedirect(request.getContextPath() + "/view/profile/accessDenied.jsp");
            return;
        }

        String type = request.getParameter("type");
        String name = request.getParameter("name");

        // Validate type và tên
        if (!isValidType(type) || name == null || name.trim().isEmpty()) {
            request.setAttribute("errorMessage", "⚠️ Vui lòng chọn type và nhập tên!");
            request.setAttribute("type", type);
            loadOptions(request);
            request.getRequestDispatcher("/view/profile/addOption.jsp").forward(request, response);
            return;
        }

        name = name.trim();
        if (!VALID_NAME.matcher(name).matches()) {
            request.setAttribute("errorMessage", "⚠️ Tên không hợp lệ (2-50 ký tự, chỉ chữ và khoảng trắng)!");
            request.setAttribute("type", type);
            loadOptions(request);
            request.getRequestDispatcher("/view/profile/addOption.jsp").forward(request, response);
            return;
        }

        boolean success = false;
        try {
            switch (type) {
                case "position":
                    if (!positionDAO.exists(name)) success = positionDAO.insertIfNotExists(name) > 0;
                    break;
                case "department":
                    if (!departmentDAO.exists(name)) success = departmentDAO.insertIfNotExists(name) > 0;
                    break;
                case "degree":
                    if (!degreeDAO.exists(name)) success = degreeDAO.insertIfNotExists(name) > 0;
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "💥 Lỗi cơ sở dữ liệu: " + e.getMessage());
            request.setAttribute("type", type);
            loadOptions(request);
            request.getRequestDispatcher("/view/profile/addOption.jsp").forward(request, response);
            return;
        }

        if (success) {
            request.setAttribute("successMessage", "✅ Thêm thành công " + type + ": " + name);
        } else {
            request.setAttribute("errorMessage", "⚠️ " + type + " đã tồn tại hoặc lỗi!");
        }

        request.setAttribute("type", type);
        loadOptions(request);
        request.getRequestDispatcher("/view/profile/addOption.jsp").forward(request, response);
    }
}
