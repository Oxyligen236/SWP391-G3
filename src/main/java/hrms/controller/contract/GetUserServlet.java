package hrms.controller.contract;

import java.io.IOException;
import java.io.PrintWriter;

import hrms.dao.UserDAO;
import hrms.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/getUser")
public class GetUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userID = request.getParameter("userID");
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();

        if (userID == null || userID.trim().isEmpty()) {
            out.print("{\"found\":false,\"message\":\"User ID is required\"}");
            return;
        }

        try {
            
            User user = userDAO.getUserById(Integer.parseInt(userID));

            if (user != null) {
                // trả về JSON đơn giản
                String nameEscaped = user.getFullname() != null ? user.getFullname().replace("\"", "\\\"") : "";
                out.print("{\"found\":true,\"name\":\"" + nameEscaped + "\"}");
            } else {
                out.print("{\"found\":false,\"message\":\"User not found\"}");
            }
        } catch (Exception e) {
            response.setStatus(500);
            out.print("{\"found\":false,\"message\":\"Server error: " + e.getMessage().replace("\"","\\\"") + "\"}");
        }
    }
}
