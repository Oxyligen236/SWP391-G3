package hrms.controller;

import hrms.dao.JobDAO;
import hrms.model.JobDescription;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/jd_guestDetail")
public class JdGuestDetailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int jobID = Integer.parseInt(request.getParameter("id"));
            JobDAO dao = new JobDAO();
            JobDescription jd = dao.getJobByJobId(jobID);

            if (jd == null) {
                request.setAttribute("error", "Job Description not found!");
                request.getRequestDispatcher("/view/jd/error.jsp").forward(request, response);
                return;
            }

            request.setAttribute("jd", jd);
            request.getRequestDispatcher("/view/jd/jd_guestDetail.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error loading Job Description details!");
            request.getRequestDispatcher("/view/error.jsp").forward(request, response);
        }
    }
}
