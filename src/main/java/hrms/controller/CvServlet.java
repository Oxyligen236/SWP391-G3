package hrms.controller;

import java.io.IOException;

import hrms.dao.JobDAO;
import hrms.service.CvService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/cv")
public class CvServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String jobID = request.getParameter("jobID");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String gender = request.getParameter("gender");
        String status = request.getParameter("status");
        CvService cvService = new CvService();
        JobDAO jobDAO = new JobDAO();

        jobID = (jobID != null && !jobID.trim().isEmpty()) ? jobID.trim() : null;
        name = (name != null && !name.trim().isEmpty()) ? name.trim() : null;
        email = (email != null && !email.trim().isEmpty()) ? email.trim() : null;
        phone = (phone != null && !phone.trim().isEmpty()) ? phone.trim() : null;
        gender = (gender != null && !gender.trim().isEmpty()) ? gender.trim() : null;
        status = (status != null && !status.trim().isEmpty()) ? status.trim() : null;
        try {
            int jobIdInt = (jobID != null) ? Integer.parseInt(jobID) : 0;
            request.setAttribute("jobs", jobDAO.getAll());
            if (jobIdInt > 0 || name != null || email != null || phone != null || gender != null || status != null) {
                request.setAttribute("cvs", cvService.searchCVs(jobIdInt, name, email, phone, gender, status));
            } else {
                request.setAttribute("cvs", cvService.getAllCVJobTitle());
            }
            request.getRequestDispatcher("/view/cv/cv_List.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("jobID_error", "Định dạng Job_ID không hợp lệ");
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}
