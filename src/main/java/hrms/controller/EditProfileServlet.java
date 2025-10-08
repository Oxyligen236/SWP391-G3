package hrms.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import hrms.dto.UserDTO;
import hrms.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/edit")
public class EditProfileServlet extends HttpServlet {

    private final UserService userService = new UserService();

   @Override
protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
    // Lấy user từ session
    UserDTO sessionUser = (UserDTO) req.getSession().getAttribute("currentUser");
    if (sessionUser == null) {
        resp.sendRedirect(req.getContextPath() + "/login"); // chưa đăng nhập
        return;
    }

    int userId = sessionUser.getUserId(); // lấy ID từ session
    UserDTO user = userService.getUserById(userId);

    if (user != null) {
        req.setAttribute("user", user);
        req.getRequestDispatcher("/view/profile/editProfile.jsp").forward(req, resp);
    } else {
        req.setAttribute("error", "Không tìm thấy thông tin người dùng.");
        req.getRequestDispatcher("/view/profile/error.jsp").forward(req, resp);
    }
}

@Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
    // Lấy userId từ session
    UserDTO sessionUser = (UserDTO) req.getSession().getAttribute("currentUser");
    if (sessionUser == null) {
        resp.sendRedirect(req.getContextPath() + "/login"); // chưa đăng nhập
        return;
    }

    int userId = sessionUser.getUserId();

    String fullname = req.getParameter("fullname");
    String email = req.getParameter("email");
    String phone = req.getParameter("phoneNumber");
    String birthDateStr = req.getParameter("birthDate");
    String gender = req.getParameter("gender");
    String address = req.getParameter("address");
    String nation = req.getParameter("nation");
    String ethnicity = req.getParameter("ethnicity");

    UserDTO dto = new UserDTO();
    dto.setUserId(userId);
    dto.setFullname(fullname);
    dto.setEmail(email);
    dto.setPhoneNumber(phone);
    dto.setGender(gender);
    dto.setAddress(address);
    dto.setNation(nation);
    dto.setEthnicity(ethnicity);

    if (birthDateStr != null && !birthDateStr.isEmpty()) {
        try {
            java.util.Date utilDate = new java.text.SimpleDateFormat("yyyy-MM-dd").parse(birthDateStr);
            dto.setBirthDate(new java.sql.Date(utilDate.getTime()));
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
    }

    boolean success = userService.updateUser(dto);
    if (success) {
        // Cập nhật luôn session user để giao diện hiển thị đúng
        sessionUser.setFullname(fullname);
        sessionUser.setEmail(email);
        sessionUser.setPhoneNumber(phone);
        sessionUser.setBirthDate(dto.getBirthDate());
        sessionUser.setGender(gender);
        sessionUser.setAddress(address);
        sessionUser.setNation(nation);
        sessionUser.setEthnicity(ethnicity);
        req.getSession().setAttribute("currentUser", sessionUser);

        req.setAttribute("successMessage", "Cập nhật thông tin thành công!");
        req.getRequestDispatcher("/view/profile/editProfile.jsp").forward(req, resp);
    } else {
        req.setAttribute("error", "Cập nhật thông tin thất bại!");
        req.getRequestDispatcher("/view/profile/editProfile.jsp").forward(req, resp);
    }
}
}