package hrms.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import hrms.dao.DegreeDAO;
import hrms.dto.UserDTO;
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

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/authenticate");
            return;
        }

        int userId = (Integer) session.getAttribute("userId");
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
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/authenticate");
            return;
        }

        int userId = (Integer) session.getAttribute("userId");
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

        if (fullname != null && !fullname.isEmpty()) {
            user.setFullname(fullname);
        }
        if (email != null && !email.isEmpty()) {
            user.setEmail(email);
        }
        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            user.setPhoneNumber(phoneNumber);
        }
        if (birthDateStr != null && !birthDateStr.isEmpty()) {
            user.setBirthDate(Date.valueOf(birthDateStr));
        }
        if (gender != null && !gender.isEmpty()) {
            user.setGender(gender);
        }
        if (cccd != null && !cccd.isEmpty()) {
            user.setCccd(cccd);
        }
        if (address != null && !address.isEmpty()) {
            user.setAddress(address);
        }
        if (nation != null && !nation.isEmpty()) {
            user.setNation(nation);
        }
        if (ethnicity != null && !ethnicity.isEmpty()) {
            user.setEthnicity(ethnicity);
        }
        if (degreeIdStr != null && !degreeIdStr.isEmpty()) {
            user.setDegreeId(Integer.parseInt(degreeIdStr));
        }

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
    }
}
