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
            request.setAttribute("error", "Ngày sinh không hợp lệ!");
            preserveFormData(request, fullname, email, phoneNumber, birthDate,
                    request.getParameter("gender"), request.getParameter("address"),
                    request.getParameter("nation"), request.getParameter("ethnicity"),
                    request.getParameter("cccd"), request.getParameter("departmentId"),
                    request.getParameter("positionId"), request.getParameter("degreeId"));
            loadDropdownData(request);
            request.getRequestDispatcher("/view/profile/createUser.jsp").forward(request, response);
            return;
        }

        String gender = request.getParameter("gender");
        String address = request.getParameter("address");
        String nation = request.getParameter("nation");
        String ethnicity = request.getParameter("ethnicity");
        String cccd = request.getParameter("cccd");

        Integer departmentId = parseInteger(request.getParameter("departmentId"));
        Integer positionId = parseInteger(request.getParameter("positionId"));
        Integer degreeId = parseInteger(request.getParameter("degreeId"));

        boolean hasError = false;
        StringBuilder errorMsg = new StringBuilder();

        if (fullname == null || fullname.trim().isEmpty()) {
            hasError = true;
            errorMsg.append("Họ & tên là bắt buộc!<br>");
        } else if (!fullname.matches("^[a-zA-ZÀ-ỹ\\s]{2,100}$")) {
            hasError = true;
            errorMsg.append("Họ & tên chỉ chứa chữ và khoảng trắng (2-100 ký tự)!<br>");
        }

        if (email == null || email.trim().isEmpty()) {
            hasError = true;
            errorMsg.append("Email là bắt buộc!<br>");
        } else if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[A-Za-z]{2,6}$")) {
            hasError = true;
            errorMsg.append("Email không hợp lệ!<br>");
        }

        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            if (!phoneNumber.matches("^0\\d{8,11}$")) {
                hasError = true;
                errorMsg.append("Số điện thoại không hợp lệ (bắt đầu bằng 0, 9-12 chữ số)!<br>");
            }
        }

        if (cccd == null || cccd.trim().isEmpty()) {
            hasError = true;
            errorMsg.append("CCCD/CMND là bắt buộc!<br>");
        } else if (!cccd.matches("^\\d{12}$")) {
            hasError = true;
            errorMsg.append("CCCD/CMND không hợp lệ (12 chữ số)!<br>");
        }

        if (birthDate != null) {
            java.sql.Date today = new java.sql.Date(System.currentTimeMillis());
            java.sql.Date minBirthDate = java.sql.Date.valueOf(today.toLocalDate().minusYears(18));
            if (birthDate.after(today)) {
                hasError = true;
                errorMsg.append("Ngày sinh không được lớn hơn ngày hiện tại!<br>");
            } else if (birthDate.after(minBirthDate)) {
                hasError = true;
                errorMsg.append("Người dùng phải từ 18 tuổi trở lên!<br>");
            }
        }

        if (gender == null || gender.trim().isEmpty()) {
            hasError = true;
            errorMsg.append("Giới tính là bắt buộc!<br>");
        }

        if (address != null && address.length() > 255) {
            hasError = true;
            errorMsg.append("Địa chỉ không được vượt quá 255 ký tự!<br>");
        }

        if (nation != null && !nation.isEmpty() && !nation.matches("^[a-zA-ZÀ-ỹ\\s]{1,50}$")) {
            hasError = true;
            errorMsg.append("Quốc tịch chỉ chứa chữ và tối đa 50 ký tự!<br>");
        }

        if (ethnicity != null && !ethnicity.isEmpty() && !ethnicity.matches("^[a-zA-ZÀ-ỹ\\s]{1,50}$")) {
            hasError = true;
            errorMsg.append("Dân tộc chỉ chứa chữ và tối đa 50 ký tự!<br>");
        }

        if (departmentId == null) {
            hasError = true;
            errorMsg.append("Phòng ban là bắt buộc!<br>");
        }
        if (positionId == null) {
            hasError = true;
            errorMsg.append("Chức vụ là bắt buộc!<br>");
        }
        if (degreeId == null) {
            hasError = true;
            errorMsg.append("Bằng cấp là bắt buộc!<br>");
        }

        UserDAO dao = new UserDAO();
        if (!hasError && dao.existsCccd(cccd)) {
            hasError = true;
            errorMsg.append("CCCD/CMND đã tồn tại!<br>");
        }
        if (!hasError && dao.existsEmail(email)) {
            hasError = true;
            errorMsg.append("Email đã tồn tại!<br>");
        }
        if (!hasError && phoneNumber != null && !phoneNumber.isEmpty() && dao.existsPhoneNumber(phoneNumber)) {
            hasError = true;
            errorMsg.append("Số điện thoại đã tồn tại!<br>");
        }

        if (hasError) {
            request.setAttribute("error", errorMsg.toString());
            preserveFormData(request, fullname, email, phoneNumber, birthDate, gender, address, nation, ethnicity,
                    cccd, request.getParameter("departmentId"), request.getParameter("positionId"),
                    request.getParameter("degreeId"));
            loadDropdownData(request);
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

        Integer userId = dao.createUserReturnId(user);

        if (userId != null) {
            request.setAttribute("success", "Tạo user thành công! User ID: " + userId);
        } else {
            request.setAttribute("error", "Tạo user thất bại!");
        }

        preserveFormData(request, fullname, email, phoneNumber, birthDate, gender, address, nation, ethnicity,
                cccd, request.getParameter("departmentId"), request.getParameter("positionId"),
                request.getParameter("degreeId"));
        loadDropdownData(request);
        request.getRequestDispatcher("/view/profile/createUser.jsp").forward(request, response);
    }

    private boolean hasHRPermission(HttpSession session) {
        Account account = (Account) session.getAttribute("account");
        if (account == null) return false;
        return account.getRole() == 2 || account.getRole() == 3;
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
    }
}
