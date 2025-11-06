package hrms.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import hrms.dao.DegreeDAO;
import hrms.dao.DepartmentDAO;
import hrms.dao.PositionDAO;
import hrms.dao.UserDAO;
import hrms.dto.UserDTO;
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

@WebServlet("/user/create")
public class CreateUserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!hasHRPermission(request.getSession())) {
            response.sendRedirect(request.getContextPath() + "/view/profile/accessDenied.jsp");
            return;
        }

        // Nhận dữ liệu từ CV nếu có
        String fullname = request.getParameter("fullname");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phoneNumber");
        String birthDate = request.getParameter("birthDate");
        String gender = request.getParameter("gender");
        String address = request.getParameter("address");
        String nation = request.getParameter("nation");
        String cccd = request.getParameter("cccd");
        String degreeId = request.getParameter("degreeId");

        // Set vào request để hiển thị trên form
        if (fullname != null) {
            request.setAttribute("formFullname", fullname);
        }
        if (email != null) {
            request.setAttribute("formEmail", email);
        }
        if (phoneNumber != null) {
            request.setAttribute("formPhoneNumber", phoneNumber);
        }
        if (birthDate != null) {
            request.setAttribute("formBirthDate", birthDate);
        }
        if (gender != null) {
            request.setAttribute("formGender", gender);
        }
        if (address != null) {
            request.setAttribute("formAddress", address);
        }
        if (nation != null) {
            request.setAttribute("formNation", nation);
        }
        if (cccd != null) {
            request.setAttribute("formCccd", cccd);
        }
        if (degreeId != null) {
            request.setAttribute("formDegreeId", degreeId);
        }

        loadDropdownData(request);
        request.getRequestDispatcher("/view/profile/createUser.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        if (!hasHRPermission(request.getSession())) {
            response.sendRedirect(request.getContextPath() + "/view/profile/accessDenied.jsp");
            return;
        }

        // --- Get form data ---
        String fullname = request.getParameter("fullname");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phoneNumber");
        String birthStr = request.getParameter("birthDate");
        java.sql.Date birthDate = null;

        try {
            if (birthStr != null && !birthStr.isEmpty()) {
                birthDate = java.sql.Date.valueOf(birthStr);
            }
        } catch (IllegalArgumentException e) {
            birthDate = null;
        }

        String gender = request.getParameter("gender");
        String address = request.getParameter("address");
        String nation = request.getParameter("nation");
        String ethnicity = request.getParameter("ethnicity");
        String cccd = request.getParameter("cccd");

        String depParam = request.getParameter("departmentId");
        String posParam = request.getParameter("positionId");
        String degParam = request.getParameter("degreeId");

        Integer departmentId = parseInteger(depParam);
        Integer positionId = parseInteger(posParam);
        Integer degreeId = parseInteger(degParam);

        boolean hasError = false;
        StringBuilder errorMsg = new StringBuilder();

        // --- Validate basic information ---
        if (fullname == null || fullname.trim().isEmpty()) {
            hasError = true;
            errorMsg.append("Full name is required!<br>");
        } else if (!fullname.matches("^[a-zA-ZÀ-ỹ\\s]{2,100}$")) {
            hasError = true;
            errorMsg.append("Full name can only contain letters and spaces (2-100 characters)!<br>");
        }

        if (email == null || email.trim().isEmpty()) {
            hasError = true;
            errorMsg.append("Email is required!<br>");
        } else if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[A-Za-z]{2,6}$")) {
            hasError = true;
            errorMsg.append("Invalid email format!<br>");
        }

        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            if (!phoneNumber.matches("^0\\d{8,11}$")) {
                hasError = true;
                errorMsg.append("Invalid phone number (starts with 0, 9-12 digits)!<br>");
            }
        }

        if (cccd == null || cccd.trim().isEmpty()) {
            hasError = true;
            errorMsg.append("CCCD/ID card is required!<br>");
        } else if (!cccd.matches("^\\d{12}$")) {
            hasError = true;
            errorMsg.append("Invalid CCCD/ID card (12 digits)!<br>");
        }

        if (birthDate != null) {
            java.sql.Date today = new java.sql.Date(System.currentTimeMillis());
            java.sql.Date minBirthDate = java.sql.Date.valueOf(today.toLocalDate().minusYears(18));
            if (birthDate.after(today)) {
                hasError = true;
                errorMsg.append("Birth date cannot be in the future!<br>");
            } else if (birthDate.after(minBirthDate)) {
                hasError = true;
                errorMsg.append("User must be at least 18 years old!<br>");
            }
        }

        if (gender == null || gender.trim().isEmpty()) {
            hasError = true;
            errorMsg.append("Gender is required!<br>");
        }

        if (address != null && address.length() > 255) {
            hasError = true;
            errorMsg.append("Address cannot exceed 255 characters!<br>");
        }

        if (nation != null && !nation.isEmpty() && !nation.matches("^[a-zA-ZÀ-ỹ\\s]{1,50}$")) {
            hasError = true;
            errorMsg.append("Nation can only contain letters and max 50 characters!<br>");
        }

        if (ethnicity != null && !ethnicity.isEmpty() && !ethnicity.matches("^[a-zA-ZÀ-ỹ\\s]{1,50}$")) {
            hasError = true;
            errorMsg.append("Ethnicity can only contain letters and max 50 characters!<br>");
        }

        DepartmentDAO deptDao = new DepartmentDAO();
        PositionDAO posDao = new PositionDAO();
        DegreeDAO degDao = new DegreeDAO();
        UserDAO userDao = new UserDAO();

        // --- Validate selection ---
        if (departmentId == null) {
            hasError = true;
            errorMsg.append("Department is required!<br>");
        }
        if (positionId == null) {
            hasError = true;
            errorMsg.append("Position is required!<br>");
        }
        if (degreeId == null) {
            hasError = true;
            errorMsg.append("Degree is required!<br>");
        }

        // --- Validate duplicates ---
        if (!hasError && userDao.existsCccd(cccd)) {
            hasError = true;
            errorMsg.append("CCCD/ID card already exists!<br>");
        }
        if (!hasError && userDao.existsEmail(email)) {
            hasError = true;
            errorMsg.append("Email already exists!<br>");
        }
        if (!hasError && phoneNumber != null && !phoneNumber.isEmpty() && userDao.existsPhoneNumber(phoneNumber)) {
            hasError = true;
            errorMsg.append("Phone number already exists!<br>");
        }

        if (hasError) {
            request.setAttribute("error", errorMsg.toString());
            preserveFormData(request, fullname, email, phoneNumber, birthDate, gender, address,
                    nation, ethnicity, cccd, depParam, posParam, degParam);
            loadDropdownData(request);
            request.getRequestDispatcher("/view/profile/createUser.jsp").forward(request, response);
            return;
        }

        // --- Create User ---
        UserDTO user = new UserDTO();
        user.setFullname(fullname);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setBirthDate(birthDate);
        user.setGender(gender);
        user.setAddress(address);
        user.setNation(nation);
        user.setEthnicity(ethnicity);
        user.setCccd(cccd);
        user.setDepartmentId(departmentId);
        user.setPositionId(positionId);
        user.setDegreeId(degreeId);

        Integer userId = userDao.createUserReturnId(user);

        if (userId != null) {
            request.setAttribute("success", "User created successfully! User ID: " + userId);
        } else {
            request.setAttribute("error", "User creation failed!");
        }

        preserveFormData(request, fullname, email, phoneNumber, birthDate, gender, address,
                nation, ethnicity, cccd, depParam, posParam, degParam);
        loadDropdownData(request);
        request.getRequestDispatcher("/view/profile/createUser.jsp").forward(request, response);
    }

    private boolean hasHRPermission(HttpSession session) {
        Account account = (Account) session.getAttribute("account");
        if (account == null) {
            return false;
        }
        return account.getRole() == 1 || account.getRole() == 2; // HR / HR Manager
    }

    private Integer parseInteger(String param) {
        if (param == null || param.isEmpty()) {
            return null;
        }
        try {
            return Integer.parseInt(param);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private void preserveFormData(HttpServletRequest request, String fullname, String email, String phoneNumber,
            java.sql.Date birthDate, String gender, String address, String nation,
            String ethnicity, String cccd, String departmentId, String positionId,
            String degreeId) {
        request.setAttribute("formFullname", fullname);
        request.setAttribute("formEmail", email);
        request.setAttribute("formPhoneNumber", phoneNumber);
        request.setAttribute("formBirthDate", birthDate);
        request.setAttribute("formGender", gender);
        request.setAttribute("formAddress", address);
        request.setAttribute("formNation", nation);
        request.setAttribute("formEthnicity", ethnicity);
        request.setAttribute("formCccd", cccd);
        request.setAttribute("formDepartmentId", departmentId);
        request.setAttribute("formPositionId", positionId);
        request.setAttribute("formDegreeId", degreeId);
    }

    private void loadDropdownData(HttpServletRequest request) {
        DepartmentDAO deptDao = new DepartmentDAO();
        PositionDAO posDao = new PositionDAO();
        DegreeDAO degDao = new DegreeDAO();

        List<Department> departments = deptDao.getAll();
        request.setAttribute("departments", departments != null ? departments : new ArrayList<>());

        List<Position> positions = posDao.getAll();
        request.setAttribute("positions", positions != null ? positions : new ArrayList<>());

        List<Degree> degrees = degDao.getAll();
        request.setAttribute("degrees", degrees != null ? degrees : new ArrayList<>());

        List<String> genders = new ArrayList<>();
        genders.add("Male");
        genders.add("Female");
        genders.add("Other");
        request.setAttribute("genders", genders);
    }
}
