package hrms.controller;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import hrms.dao.UserDAO;
import hrms.model.Account;
import hrms.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/profile/update")
public class UpdateProfileServlet extends HttpServlet {
    private UserDAO userDAO = new UserDAO();

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

        Account account = (Account) session.getAttribute("account");
        int userId = account.getUserID(); // Lấy userId từ session

        String fullname = req.getParameter("fullname");
        String email = req.getParameter("email");
        String phoneNumber = req.getParameter("phoneNumber");
        String birthDateStr = req.getParameter("birthDate");
        String gender = req.getParameter("gender");
        String cccd = req.getParameter("cccd");
        String address = req.getParameter("address");
        String nation = req.getParameter("nation");
        String ethnicity = req.getParameter("ethnicity");

        User user = new User();
        user.setUserId(userId);
        user.setFullname(fullname);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setGender(gender);
    
        user.setAddress(address);
        user.setNation(nation);
        user.setEthnicity(ethnicity);

        if (birthDateStr != null && !birthDateStr.isEmpty()) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date parsedDate = sdf.parse(birthDateStr);
                user.setBirthDate(new Date(parsedDate.getTime()));
            } catch (ParseException e) {
                e.printStackTrace();
                req.setAttribute("error", "Định dạng ngày sinh không hợp lệ.");
                req.setAttribute("user", user);
                req.getRequestDispatcher("/view/profile/editProfile.jsp").forward(req, resp);
                return;
            }
        }

        boolean success = userDAO.updateUser(user);

        if (success) {
            session.setAttribute("successMessage", "Cập nhật thông tin thành công!");
            resp.sendRedirect(req.getContextPath() + "/profile/view");
        } else {
            req.setAttribute("error", "Có lỗi xảy ra khi cập nhật thông tin. Vui lòng thử lại.");
            req.setAttribute("user", user);
            if (user.getBirthDate() != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                req.setAttribute("birthDateStr", sdf.format(user.getBirthDate()));
            }
            req.getRequestDispatcher("/view/profile/editProfile.jsp").forward(req, resp);
        }
    }
}
