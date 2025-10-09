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
import jakarta.servlet.http.HttpSession;

@WebServlet("/profile/update")
public class UpdateProfileServlet extends HttpServlet {

    private final UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("account") == null) {
            req.setAttribute("error", "Bạn chưa đăng nhập.");
            req.getRequestDispatcher("/view/profile/error.jsp").forward(req, resp);
            return;
        }

        int userId = 1; // FIX: test với ID 1
        // userId = ((Account) session.getAttribute("account")).getUserID();

        String fullname = req.getParameter("fullname");
        String email = req.getParameter("email");
        String phoneNumber = req.getParameter("phoneNumber");
        String birthDateStr = req.getParameter("birthDate");
        String gender = req.getParameter("gender");
        String cccd = req.getParameter("cccd");
        String address = req.getParameter("address");
        String nation = req.getParameter("nation");
        String ethnicity = req.getParameter("ethnicity");

        UserDTO dto = new UserDTO();
        dto.setUserId(userId);
        dto.setFullname(fullname);
        dto.setEmail(email);
        dto.setPhoneNumber(phoneNumber);
        dto.setGender(gender);
        dto.setCccd(cccd);
        dto.setAddress(address);
        dto.setNation(nation);
        dto.setEthnicity(ethnicity);

        // Parse birth date
        if (birthDateStr != null && !birthDateStr.isEmpty()) {
            try {
                java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(birthDateStr);
                dto.setBirthDate(new java.sql.Date(utilDate.getTime()));
            } catch (ParseException e) {
                e.printStackTrace();
                req.setAttribute("error", "Định dạng ngày sinh không hợp lệ.");
                req.setAttribute("user", dto);
                req.getRequestDispatcher("/view/profile/editProfile.jsp").forward(req, resp);
                return;
            }
        }

        boolean success = userService.updateUser(dto);
        if (success) {
            session.setAttribute("successMessage", "Cập nhật thông tin thành công!");
            resp.sendRedirect(req.getContextPath() + "/view");
        } else {
            req.setAttribute("error", "Cập nhật thông tin thất bại!");
            req.setAttribute("user", dto);
            req.getRequestDispatcher("/view/profile/editProfile.jsp").forward(req, resp);
        }
    }
}
