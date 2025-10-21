package hrms.controller;

import java.io.IOException;
import java.sql.Date;

import hrms.dao.DegreeDAO;
import hrms.dao.DepartmentDAO;
import hrms.dao.PositionDAO;
import hrms.dao.UserDAO;
import hrms.dto.UserDTO;
import hrms.model.Account;
import hrms.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/edit")
public class EditProfileServlet extends HttpServlet {

    private final UserService userService = new UserService();
    private final UserDAO userDAO = new UserDAO();
    private final DegreeDAO degreeDAO = new DegreeDAO();
    private final DepartmentDAO departmentDAO = new DepartmentDAO();
    private final PositionDAO positionDAO = new PositionDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");

        if (account == null) {
            response.sendRedirect(request.getContextPath() + "/authenticate");
            return;
        }

        int userId = account.getUserID();
        UserDTO user = userService.getUserById(userId);

        if (user == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy thông tin người dùng.");
            return;
        }

        setDropdowns(request);
        request.setAttribute("user", user);
        request.getRequestDispatcher("/view/profile/editProfile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");

        if (account == null) {
            response.sendRedirect(request.getContextPath() + "/authenticate");
            return;
        }

        int userId = account.getUserID();
        UserDTO currentUser = userService.getUserById(userId);
        if (currentUser == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "User không tồn tại");
            return;
        }

        // ✅ PHÂN QUYỀN THEO ROLE ID
        // Admin (1), HR Manager (2), HR (3) có quyền chỉnh sửa toàn bộ
        int roleId = userDAO.getRoleIdByUserId(userId);
        boolean canEditAll = ( roleId == 2 || roleId == 3);

        // --- Nhận dữ liệu từ form ---
        String fullname = request.getParameter("fullname");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phoneNumber");
        String birthDateStr = request.getParameter("birthDate");
        String gender = request.getParameter("gender");
        String cccd = request.getParameter("cccd");
        String address = request.getParameter("address");
        String nation = request.getParameter("nation");
        String ethnicity = request.getParameter("ethnicity");
        String degreeIdStr = request.getParameter("degreeId");
        String departmentIdStr = request.getParameter("departmentId");
        String positionIdStr = request.getParameter("positionId");

        StringBuilder errorMsg = new StringBuilder();

        // --- VALIDATE ---
        if (isEmpty(fullname)) errorMsg.append("Họ và tên không được để trống.<br>");
        if (isEmpty(email)) {
            errorMsg.append("Email không được để trống.<br>");
        } else if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            errorMsg.append("Email không hợp lệ.<br>");
        } else if (userDAO.existsEmail(email) && !email.equalsIgnoreCase(currentUser.getEmail())) {
            errorMsg.append("Email đã tồn tại.<br>");
        }

        if (isEmpty(phoneNumber)) {
            errorMsg.append("Số điện thoại không được để trống.<br>");
        } else if (!phoneNumber.matches("^(0|\\+84)[0-9]{9}$")) {
            errorMsg.append("Số điện thoại không hợp lệ.<br>");
        } else if (userDAO.existsPhoneNumber(phoneNumber)
                && !phoneNumber.equals(currentUser.getPhoneNumber())) {
            errorMsg.append("Số điện thoại đã tồn tại.<br>");
        }

        if (!isEmpty(cccd)) {
            if (!cccd.matches("^[0-9]{12}$")) {
                errorMsg.append("CCCD phải gồm 12 chữ số.<br>");
            } else if (userDAO.existsCccd(cccd)
                    && !cccd.equals(currentUser.getCccd())) {
                errorMsg.append("CCCD đã tồn tại.<br>");
            }
        }

        Date birthDate = null;
        if (!isEmpty(birthDateStr)) {
            try {
                birthDate = Date.valueOf(birthDateStr);
                Date today = new Date(System.currentTimeMillis());

                if (birthDate.after(today)) {
                    errorMsg.append("Ngày sinh không được lớn hơn ngày hiện tại.<br>");
                } else {
                    long ageInMillis = today.getTime() - birthDate.getTime();
                    long ageInYears = ageInMillis / (1000L * 60 * 60 * 24 * 365);
                    if (ageInYears < 18) {
                        errorMsg.append("Người dùng phải đủ 18 tuổi trở lên.<br>");
                    }
                }
            } catch (IllegalArgumentException e) {
                errorMsg.append("Định dạng ngày sinh không hợp lệ.<br>");
            }
        }

        if (isEmpty(gender)) errorMsg.append("Vui lòng chọn giới tính.<br>");
        if (isEmpty(address)) errorMsg.append("Địa chỉ không được để trống.<br>");

        // --- Nếu có lỗi ---
        if (errorMsg.length() > 0) {
            setDropdowns(request);
            request.setAttribute("user", currentUser);
            request.setAttribute("errorMessage", errorMsg.toString());
            request.getRequestDispatcher("/view/profile/editProfile.jsp").forward(request, response);
            return;
        }

        // --- Cập nhật thông tin ---
        currentUser.setFullname(fullname);
        currentUser.setEmail(email);
        currentUser.setPhoneNumber(phoneNumber);
        currentUser.setBirthDate(birthDate);
        currentUser.setGender(gender);
        currentUser.setCccd(cccd);
        currentUser.setAddress(address);
        currentUser.setNation(nation);
        currentUser.setEthnicity(ethnicity);
        currentUser.setDegreeId(parseOrNull(degreeIdStr));

        // ✅ Chỉ HR, HR Manager, Admin mới được chỉnh sửa Department, Position
        if (canEditAll) {
            currentUser.setDepartmentId(parseOrNull(departmentIdStr));
            currentUser.setPositionId(parseOrNull(positionIdStr));
        }

        boolean updated = userService.updateUser(currentUser);
        UserDTO refreshedUser = userService.getUserById(currentUser.getUserId());

        if (updated) {
            request.setAttribute("successMessage", "✅ Cập nhật thông tin thành công!");
        } else {
            request.setAttribute("errorMessage", "⚠️ Cập nhật thất bại hoặc không có thay đổi nào.");
        }

        setDropdowns(request);
        request.setAttribute("user", refreshedUser);
        request.getRequestDispatcher("/view/profile/editProfile.jsp").forward(request, response);
    }

    // --------------------- HÀM PHỤ TRỢ ---------------------

    private void setDropdowns(HttpServletRequest request) {
        request.setAttribute("degreeList", degreeDAO.getAll());
        request.setAttribute("departmentList", departmentDAO.getAll());
        request.setAttribute("positionList", positionDAO.getAll());
    }

    private Integer parseOrNull(String str) {
        try {
            return (str != null && !str.trim().isEmpty()) ? Integer.parseInt(str) : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
}
