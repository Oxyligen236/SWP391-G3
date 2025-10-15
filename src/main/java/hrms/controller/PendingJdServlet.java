package hrms.controller;

import hrms.dao.JobDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/pendingjd")
public class PendingJdServlet extends HttpServlet {

    @Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    String jobIdStr = request.getParameter("id");
    if (jobIdStr != null) {
        int jobId = Integer.parseInt(jobIdStr);
        JobDAO dao = new JobDAO();
        boolean success = dao.updateStatus(jobId, "Pending");
        System.out.println("Suspend JD " + jobId + " -> " + success);
    }
    response.sendRedirect("jdlist");
}

    
    
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int jobID = Integer.parseInt(request.getParameter("jobID"));
            JobDAO dao = new JobDAO();
            boolean result = dao.updateStatus(jobID, "Cancelled");
            System.out.println("Cancel JD result: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect(request.getContextPath() + "/jdlist");
    }
}
