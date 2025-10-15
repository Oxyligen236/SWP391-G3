package hrms.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import hrms.dao.DegreeDAO;
import hrms.dto.UserDTO;
import hrms.model.Account;
import hrms.model.Degree;
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
    private final DegreeDAO degreeDAO = new DegreeDAO();

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
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "User không tồn tại");
            return;
        }

        List<Degree> degreeList = degreeDAO.getAll();

        request.setAttribute("user", user);
        request.setAttribute("degreeList", degreeList);
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
    UserDTO user = userService.getUserById(userId);
    if (user == null) {
        response.sendError(HttpServletResponse.SC_NOT_FOUND, "User không tồn tại");
        return;
    }

  
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

    
    StringBuilder errorMsg = new StringBuilder();

    if (fullname == null || fullname.trim().isEmpty()) {
        errorMsg.append("Họ và tên không được để trống.<br>");
    }

    if (email == null || email.trim().isEmpty()) {
        errorMsg.append("Email không được để trống.<br>");
    } else if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
        errorMsg.append("Định dạng email không hợp lệ.<br>");
    }

    if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
        errorMsg.append("Số điện thoại không được để trống.<br>");
    } else if (!phoneNumber.matches("^(0|\\+84)[0-9]{9}$")) {
        errorMsg.append("Số điện thoại không hợp lệ (phải có 10 số).<br>");
    }

    if (birthDateStr == null || birthDateStr.isEmpty()) {
        errorMsg.append("Ngày sinh không được để trống.<br>");
    } else {
        try {
            Date birthDate = Date.valueOf(birthDateStr);
            Date today = new Date(System.currentTimeMillis());
            if (birthDate.after(today)) {
                errorMsg.append("Ngày sinh không hợp lệ (lớn hơn ngày hiện tại).<br>");
            }
        } catch (IllegalArgumentException e) {
            errorMsg.append("Định dạng ngày sinh không hợp lệ.<br>");
        }
    }

    if (gender == null || gender.trim().isEmpty()) {
        errorMsg.append("Vui lòng chọn giới tính.<br>");
    }

    if (cccd != null && !cccd.trim().isEmpty()) {
        if (!cccd.matches("^[0-9]{12}$")) {
            errorMsg.append("CCCD phải gồm 12 chữ số.<br>");
        }
    }

    if (address == null || address.trim().isEmpty()) {
        errorMsg.append("Địa chỉ không được để trống.<br>");
    }

    if (degreeIdStr == null || degreeIdStr.trim().isEmpty()) {
        errorMsg.append("Vui lòng chọn bằng cấp.<br>");
    }


    if (errorMsg.length() > 0) {
        request.setAttribute("errorMessage", errorMsg.toString());
        List<Degree> degreeList = degreeDAO.getAll();
        request.setAttribute("degreeList", degreeList);
        request.setAttribute("user", user);
        request.getRequestDispatcher("/view/profile/editProfile.jsp").forward(request, response);
        return;
    }


    user.setFullname(fullname);
    user.setEmail(email);
    user.setPhoneNumber(phoneNumber);
    user.setBirthDate(Date.valueOf(birthDateStr));
    user.setGender(gender);
    user.setCccd(cccd);
    user.setAddress(address);
    user.setNation(nation);
    user.setEthnicity(ethnicity);
    user.setDegreeId(Integer.parseInt(degreeIdStr));

    boolean updated = userService.updateUser(user);

    if (updated) {
        request.setAttribute("successMessage", "Cập nhật thông tin thành công!");
    } else {
        request.setAttribute("errorMessage", "Cập nhật thất bại hoặc không có trường nào thay đổi.");
    }

    List<Degree> degreeList = degreeDAO.getAll();
    request.setAttribute("degreeList", degreeList);
    request.setAttribute("user", user);
    request.getRequestDispatcher("/view/profile/editProfile.jsp").forward(request, response);
}}
