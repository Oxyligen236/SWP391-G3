package hrms.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

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
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "User information not found.");
            return;
        }

        setDropdowns(request);
        request.setAttribute("user", user);
        request.setAttribute("roleId", account.getRole()); // pass roleID to JSP
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
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "User does not exist.");
            return;
        }

        // Only HR and HR Manager can edit Department and Position
        boolean canEditAll = (account.getRole() == 2 || account.getRole() == 3);

        // --- Get form data ---
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

        // --- VALIDATION ---
        if (isEmpty(fullname))
            errorMsg.append("Full name cannot be empty.<br>");
        if (isEmpty(email)) {
            errorMsg.append("Email cannot be empty.<br>");
        } else if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            errorMsg.append("Invalid email format.<br>");
        } else if (userDAO.existsEmail(email) && !email.equalsIgnoreCase(currentUser.getEmail())) {
            errorMsg.append("Email already exists.<br>");
        }

        if (isEmpty(phoneNumber)) {
            errorMsg.append("Phone number cannot be empty.<br>");
        } else if (!phoneNumber.matches("^(0|\\+84)[0-9]{9}$")) {
            errorMsg.append("Invalid phone number.<br>");
        } else if (userDAO.existsPhoneNumber(phoneNumber)
                && !phoneNumber.equals(currentUser.getPhoneNumber())) {
            errorMsg.append("Phone number already exists.<br>");
        }

        if (!isEmpty(cccd)) {
            if (!cccd.matches("^[0-9]{12}$")) {
                errorMsg.append("CCCD must be 12 digits.<br>");
            } else if (userDAO.existsCccd(cccd)
                    && !cccd.equals(currentUser.getCccd())) {
                errorMsg.append("CCCD already exists.<br>");
            }
        }

        Date birthDate = null;
        if (!isEmpty(birthDateStr)) {
            try {
                birthDate = Date.valueOf(birthDateStr);
                Date today = new Date(System.currentTimeMillis());

                if (birthDate.after(today)) {
                    errorMsg.append("Birth date cannot be in the future.<br>");
                } else {
                    long ageInMillis = today.getTime() - birthDate.getTime();
                    long ageInYears = ageInMillis / (1000L * 60 * 60 * 24 * 365);
                    if (ageInYears < 18) {
                        errorMsg.append("User must be at least 18 years old.<br>");
                    }
                }
            } catch (IllegalArgumentException e) {
                errorMsg.append("Invalid birth date format.<br>");
            }
        }

        if (isEmpty(gender))
            errorMsg.append("Please select gender.<br>");
        if (isEmpty(address))
            errorMsg.append("Address cannot be empty.<br>");

        // --- If validation fails ---
        if (errorMsg.length() > 0) {
            setDropdowns(request);
            request.setAttribute("user", currentUser);
            request.setAttribute("roleId", account.getRole());
            request.setAttribute("errorMessage", errorMsg.toString());
            request.getRequestDispatcher("/view/profile/editProfile.jsp").forward(request, response);
            return;
        }

        // --- Update user information ---
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

        // Only HR and HR Manager can edit Department and Position
        if (canEditAll) {
            currentUser.setDepartmentId(parseOrNull(departmentIdStr));
            currentUser.setPositionId(parseOrNull(positionIdStr));
        }

        boolean updated = userService.updateUser(currentUser);
        UserDTO refreshedUser = userService.getUserById(currentUser.getUserId());

        if (updated) {
            request.setAttribute("successMessage", "Profile updated successfully!");
        } else {
            request.setAttribute("errorMessage", "Update failed or no changes detected.");
        }

        setDropdowns(request);
        request.setAttribute("user", refreshedUser);
        request.setAttribute("roleId", account.getRole());
        request.getRequestDispatcher("/view/profile/editProfile.jsp").forward(request, response);
    }

    private void setDropdowns(HttpServletRequest request) {
        request.setAttribute("degreeList", degreeDAO.getAll());
        request.setAttribute("departmentList", departmentDAO.getAll());
        request.setAttribute("positionList", positionDAO.getAll());

        List<String> genders = new ArrayList<>();
        genders.add("Male");
        genders.add("Female");
        genders.add("Other");
        request.setAttribute("genders", genders);
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
