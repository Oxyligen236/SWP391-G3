package hrms.controller;

import java.io.IOException;

import hrms.config.Iconstant;
import hrms.dao.AccountDAO;
import hrms.model.Account;
import hrms.model.User;
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

        String code = request.getParameter("code");
        String error = request.getParameter("error");

        if (code != null) {
            try {
                String accessToken = GoogleLogin.getToken(code);
                User googleUser = GoogleLogin.getUserInfo(accessToken);

                String googleEmail = googleUser.getEmail();

                AccountDAO accountDao = new AccountDAO();
                Account account = accountDao.getAccountByGoogleEmail(googleEmail);

                if (account == null) {
                    request.setAttribute("errorMessage",
                            "Email is not registered!");
                    request.getRequestDispatcher("/view/authenticate/login.jsp").forward(request, response);
                    return;
                }

                request.getSession().setAttribute("account", account);
                response.sendRedirect(request.getContextPath() + "/home");
                return;

            } catch (ServletException | IOException e) {
                request.setAttribute("errorMessage", "Google login failed: " + e.getMessage());
                request.getRequestDispatcher("/view/authenticate/login.jsp").forward(request, response);
            }
        }

        if (error != null) {
            request.setAttribute("errorMessage", "You cancelled Google login");
            request.getRequestDispatcher("/view/authenticate/login.jsp").forward(request, response);
            return;
        }

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

        username = username != null ? username.trim() : null;
        password = password != null ? password.trim() : null;

        if (username == null || username.length() < Iconstant.MIN_USERNAME_LENGTH) {
            request.setAttribute("errorMessage", "Username must be at least " + Iconstant.MIN_USERNAME_LENGTH + " characters long.");
            request.getRequestDispatcher("/view/authenticate/login.jsp").forward(request, response);
            return;
        }

        if (password == null || password.length() < Iconstant.MIN_PASSWORD_LENGTH) {
            request.setAttribute("errorMessage", "Password must be at least " + Iconstant.MIN_PASSWORD_LENGTH + " characters long.");
            request.getRequestDispatcher("/view/authenticate/login.jsp").forward(request, response);
            return;
        }

        AccountDAO accountDao = new AccountDAO();
        Account account = accountDao.getAccountByUsername(username);

        if (account == null) {
            request.setAttribute("errorMessage", "Invalid username or password");
            request.getRequestDispatcher("/view/authenticate/login.jsp").forward(request, response);
            return;
        }

        if (!account.isIsActive()) {
            request.setAttribute("errorMessage", "Your account has been deactivated. Please contact administrator.");
            request.getRequestDispatcher("/view/authenticate/login.jsp").forward(request, response);
            return;
        }
        boolean passwordMatch = PasswordUtil.verifyPassword(password, account.getPassword());
        if (account.getPassword().equals(password) || passwordMatch) {
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
            response.sendRedirect(request.getContextPath() + "/landing");
        } else {
            request.setAttribute("errorMessage", "Invalid username or password");
            request.getRequestDispatcher("/view/authenticate/login.jsp").forward(request, response);
        }
    }
}
