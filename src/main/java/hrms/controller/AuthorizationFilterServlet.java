package hrms.controller;

import java.io.IOException;

import hrms.model.Account;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebFilter("/*")
public class AuthorizationFilterServlet implements Filter {
    
    @Override
    public void doFilter (ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        String requestURI = httpRequest.getRequestURI();
        String contextPath = httpRequest.getContextPath();

        if (requestURI.contains("/authenticate") ||
            requestURI.contains("/css/") ||
            requestURI.contains("/js/") ||
            requestURI.contains("/assets/") ||
            requestURI.contains("/picture/") ||
            requestURI.contains("/view/home/homePage_guest.jsp") ||
            requestURI.contains("/view/landing.jsp") ||
            requestURI.endsWith("/") ||
            requestURI.equals(contextPath)) {
            chain.doFilter(request, response);
            return;
        }
        
        Account account = (session != null) ? (Account) session.getAttribute("account") : null;

        if (account == null) {
            httpResponse.sendRedirect(contextPath + "/authenticate");
            return;
        }

        int roleID = account.getRole();
        if (!hasPermission(requestURI, roleID)) {
            httpResponse.sendRedirect(contextPath + "/view/error.jsp");
            return;
        }
        chain.doFilter(request, response);
    }

    private boolean hasPermission(String requestURI, int roleID) {
        if (roleID == 1) {
            return true;
        }
        if (roleID == 2) {
            return true;
        }
        if (roleID == 3) {
        return true;
        }

        if (roleID == 4) {
            return true;
        }

        if (roleID == 5) {
            return true;
        }

        return false;
    }
}
