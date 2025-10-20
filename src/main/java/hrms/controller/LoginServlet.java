package hrms.controller;

import java.io.IOException;

import hrms.dao.AccountDAO;
import hrms.model.Account;
import hrms.utils.PasswordUtil;
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
                if ("rememberMe".equals(cookie.getName())) {
                    request.setAttribute("remember", "checked");
                }
            }
        }
        request.getRequestDispatcher("/view/authenticate/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String remember = request.getParameter("remember");

        AccountDAO accountDao = new AccountDAO();
        Account account = accountDao.getAccountByUsername(username);

        if (account != null && account.isIsActive()) {
            boolean passwordMatch = false;

            if (PasswordUtil.isHashed(account.getPassword())) {
                passwordMatch = PasswordUtil.verifyPassword(password, account.getPassword());
            } else {
                passwordMatch = account.getPassword().equals(password);

                if (passwordMatch) {
                    String hashedPassword = PasswordUtil.hashPassword(password);
                    accountDao.updatePassword(account.getUserID(), hashedPassword);
                }
            }

            if (passwordMatch) {
                if ("on".equals(remember)) {
                    Cookie usernameCookie = new Cookie("username", username);
                    Cookie rememberCookie = new Cookie("rememberMe", "true");
                    usernameCookie.setMaxAge(7 * 24 * 60 * 60);
                    rememberCookie.setMaxAge(7 * 24 * 60 * 60);
                    response.addCookie(usernameCookie);
                    response.addCookie(rememberCookie);
                } else {
                    Cookie usernameCookie = new Cookie("username", "");
                    Cookie rememberCookie = new Cookie("rememberMe", "");
                    usernameCookie.setMaxAge(0);
                    rememberCookie.setMaxAge(0);
                    response.addCookie(usernameCookie);
                    response.addCookie(rememberCookie);
                }

                request.getSession().setAttribute("account", account);
                response.sendRedirect(request.getContextPath() + "/view/home/homepage_quest.jsp");
                return;
            }
        }

        request.setAttribute("errorMessage", "Invalid username or password");
        request.getRequestDispatcher("/view/authenticate/login.jsp").forward(request, response);
    }
}
