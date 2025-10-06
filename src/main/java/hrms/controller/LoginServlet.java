package hrms.controller;

import java.io.IOException;

<<<<<<< HEAD
import hrms.dao.AccountDAO;
import hrms.dto.AccountDTO;
=======
import hrms.dao.AccountDao;
import hrms.model.Account;
>>>>>>> 10fe2e1d1659060b47d0e575b95baeb50b1f6f37
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/authenticate")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("username".equals(cookie.getName())) {
                    request.setAttribute("username", cookie.getValue());
                }
                if ("password".equals(cookie.getName())) {
                    request.setAttribute("password", cookie.getValue());
                }
            }
        }
        if (request.getAttribute("username") != null && request.getAttribute("password") != null) {
            request.setAttribute("remember", "checked");
        }
        request.getRequestDispatcher("/view/authenticate/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String remember = request.getParameter("remember");

<<<<<<< HEAD
        AccountDAO accountDao = new AccountDAO();
        AccountDTO account = accountDao.getAccountByUsername(username);
=======
        AccountDao accountDao = new AccountDao();
        Account account = accountDao.getAccountByUsername(username);
>>>>>>> 10fe2e1d1659060b47d0e575b95baeb50b1f6f37
        // Account account = new Account(username, password, "admin", true);
        if (account != null && account.isIsActive() && account.getPassword().equals(password)) {
            if ("on".equals(remember)) {
                Cookie usernameCookie = new Cookie("username", username);
                Cookie passwordCookie = new Cookie("password", password);
                usernameCookie.setMaxAge(7 * 24 * 60 * 60);
                passwordCookie.setMaxAge(7 * 24 * 60 * 60);
                response.addCookie(usernameCookie);
                response.addCookie(passwordCookie);
            } else {
                Cookie usernameCookie = new Cookie("username", "");
                Cookie passwordCookie = new Cookie("password", "");
                usernameCookie.setMaxAge(0);
                passwordCookie.setMaxAge(0);
                response.addCookie(usernameCookie);
                response.addCookie(passwordCookie);
            }
            request.getSession().setAttribute("account", account);
            response.sendRedirect(request.getContextPath() + "/view/home/home.jsp");
        } else {
            request.setAttribute("errorMessage", "Invalid username or password");
            request.getRequestDispatcher("/view/authenticate/login.jsp").forward(request, response);
        }
    }
}
