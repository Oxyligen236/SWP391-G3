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

        String depParam = request.getParameter("departmentId");
        String posParam = request.getParameter("positionId");
        request.setAttribute("formDepartmentId", depParam);
        request.setAttribute("formPositionId", posParam);

        String[] fields = {"fullname","email","phoneNumber","birthDate","gender","address","nation","ethnicity","cccd","degreeId"};
        for (String f : fields) {
            String val = request.getParameter(f);
            if (val != null) request.setAttribute("form" + capitalize(f), val);
        }

        Integer selectedDeptId = parseInteger(depParam);
        loadDropdownData(request, selectedDeptId);

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

        String fullname = request.getParameter("fullname");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phoneNumber");
        String birthStr = request.getParameter("birthDate");
        String gender = request.getParameter("gender");
        String address = request.getParameter("address");
        String nation = request.getParameter("nation");
        String ethnicity = request.getParameter("ethnicity");
        String cccd = request.getParameter("cccd");

        String depParam = request.getParameter("departmentId");
        String posParam = request.getParameter("positionId");
        String degParam = request.getParameter("degreeId");

        java.sql.Date birthDate = null;
        if (birthStr != null && !birthStr.isEmpty()) {
            try {
                birthDate = java.sql.Date.valueOf(birthStr);
            } catch (IllegalArgumentException e) {
                birthDate = null;
            }
        }

        Integer departmentId = parseInteger(depParam);
        Integer positionId = parseInteger(posParam);
        Integer degreeId = parseInteger(degParam);

        boolean hasError = false;
        StringBuilder errorMsg = new StringBuilder();

        if (fullname == null || fullname.trim().isEmpty()) { hasError = true; errorMsg.append("Full name is required!<br>"); }
        if (email == null || email.trim().isEmpty()) { hasError = true; errorMsg.append("Email is required!<br>"); }
        if (cccd == null || cccd.trim().isEmpty()) { hasError = true; errorMsg.append("CCCD/ID card is required!<br>"); }
        if (departmentId == null) { hasError = true; errorMsg.append("Department is required!<br>"); }
        if (positionId == null) { hasError = true; errorMsg.append("Position is required!<br>"); }
        if (degreeId == null) { hasError = true; errorMsg.append("Degree is required!<br>"); }
        if (gender == null || gender.trim().isEmpty()) { hasError = true; errorMsg.append("Gender is required!<br>"); }

        UserDAO userDao = new UserDAO();
        if (!hasError && userDao.existsCccd(cccd)) { hasError = true; errorMsg.append("CCCD/ID card already exists!<br>"); }
        if (!hasError && userDao.existsEmail(email)) { hasError = true; errorMsg.append("Email already exists!<br>"); }
        if (!hasError && phoneNumber != null && !phoneNumber.isEmpty() && userDao.existsPhoneNumber(phoneNumber)) {
            hasError = true; errorMsg.append("Phone number already exists!<br>");
        }

        if (hasError) {
            request.setAttribute("error", errorMsg.toString());
            preserveFormData(request, fullname, email, phoneNumber, birthDate, gender,
                    address, nation, ethnicity, cccd, depParam, posParam, degParam);
            loadDropdownData(request, departmentId);
            request.getRequestDispatcher("/view/profile/createUser.jsp").forward(request, response);
            return;
        }

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
            request.setAttribute("success", "User created successfully!");
            request.setAttribute("newUserId", userId);
            request.setAttribute("newUserPositionId", positionId);
            System.out.println("User created: " + userId);
        } else {
            request.setAttribute("error", "User creation failed!");
            System.out.println("User creation failed");
            preserveFormData(request, fullname, email, phoneNumber, birthDate, gender,
                    address, nation, ethnicity, cccd, depParam, posParam, degParam);
            loadDropdownData(request, departmentId);
            request.getRequestDispatcher("/view/profile/createUser.jsp").forward(request, response);
            return;
        }

        loadDropdownData(request, departmentId);
        request.getRequestDispatcher("/view/profile/createUser.jsp").forward(request, response);
    }

    private boolean hasHRPermission(HttpSession session) {
        Account account = (Account) session.getAttribute("account");
        return account != null && (account.getRole() == 1 || account.getRole() == 2);
    }

    private Integer parseInteger(String param) {
        if (param == null || param.isEmpty()) return null;
        try {
            return Integer.parseInt(param);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private void preserveFormData(HttpServletRequest request, String fullname, String email, String phoneNumber,
                                  java.sql.Date birthDate, String gender, String address, String nation,
                                  String ethnicity, String cccd, String departmentId, String positionId, String degreeId) {
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

  private void loadDropdownData(HttpServletRequest request, Integer departmentId) {
    DepartmentDAO deptDao = new DepartmentDAO();
    PositionDAO posDao = new PositionDAO();
    DegreeDAO degDao = new DegreeDAO();

    // Load all departments
    List<Department> departments = deptDao.getAll();
    request.setAttribute("departments", departments != null ? departments : new ArrayList<>());

    // Load ALL positions (không filter)
    List<Position> positions = posDao.getAll(); // Thay đổi từ getByDepartmentId
    request.setAttribute("positions", positions != null ? positions : new ArrayList<>());

    // Load all degrees
    List<Degree> degrees = degDao.getAll();
    request.setAttribute("degrees", degrees != null ? degrees : new ArrayList<>());

    // Load genders
    List<String> genders = List.of("Male", "Female", "Other");
    request.setAttribute("genders", genders);
}
    private String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }
}