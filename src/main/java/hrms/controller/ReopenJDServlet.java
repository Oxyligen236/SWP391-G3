package hrms.controller;

import hrms.dao.JobDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/reopenjd")
public class ReopenJDServlet extends HttpServlet {

    @Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    String jobIdStr = request.getParameter("id");
    if (jobIdStr != null) {
        int jobId = Integer.parseInt(jobIdStr);
        JobDAO dao = new JobDAO();
        boolean success = dao.updateStatus(jobId, "InProgress");
        System.out.println("Open JD " + jobId + " -> " + success);
    }
    response.sendRedirect("jdlist");
}

}
