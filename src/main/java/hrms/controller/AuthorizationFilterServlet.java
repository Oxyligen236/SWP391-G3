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
            requestURI.contains("/picture/")) {
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
            return !requestURI.contains("/updateRole");
        }


        if (roleID == 3) {
            return requestURI.contains("/cv") || 
                   requestURI.contains("/jd-list") || 
                   requestURI.contains("/home") ||
                   requestURI.contains("/cv-submit");
        }


        if (roleID == 4) {
            return !requestURI.contains("/account/view") &&
                   !requestURI.contains("/updateRole") &&
                   !requestURI.contains("/payroll/company");
        }


        if (roleID == 5) {
            return requestURI.contains("/my-") || 
                   requestURI.contains("/profile") || 
                   requestURI.contains("/cv-submit") ||
                   requestURI.contains("/home") ||
                   requestURI.contains("/payroll/personal") ||
                   requestURI.contains("/ticket");
        }

        return false;
    }
}
