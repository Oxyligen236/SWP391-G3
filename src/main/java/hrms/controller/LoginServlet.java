package hrms.controller;

import java.io.IOException;

import hrms.dao.AccountDao;
import hrms.model.Account;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/login")
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
        request.getRequestDispatcher("/view/login/login.jsp").forward(request, response);
    }
    

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String remember = request.getParameter("remember");

        AccountDao accountDao = new AccountDao();
        Account account = accountDao.getAccountByUsername(username);


        if (account != null && account.isActive() && account.getPassword().equals(password)) {
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
            response.sendRedirect("home");
        } else {
            request.setAttribute("errorMessage", "Invalid username or password");
            request.getRequestDispatcher("/view/login/login.jsp").forward(request, response);
        }
    }
}
