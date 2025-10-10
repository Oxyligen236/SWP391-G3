package hrms.controller;

import hrms.dao.JobDAO;
import hrms.model.JobDescription;
import java.io.IOException;
import java.time.LocalDate;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/createjd")
public class CreateJdServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/view/jd/createJd.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        try {
            int ticketID = Integer.parseInt(request.getParameter("ticketID"));
            String jobTitle = request.getParameter("jobTitle");
            LocalDate startDate = LocalDate.parse(request.getParameter("startDate"));
            LocalDate endDate = LocalDate.parse(request.getParameter("endDate"));
            String department = request.getParameter("department");
            int vacancies = Integer.parseInt(request.getParameter("vacancies"));
            String responsibilities = request.getParameter("responsibilities");
            String requirements = request.getParameter("requirements");
            String compensation = request.getParameter("compensation");
            String officeAddress = request.getParameter("officeAddress");
            String workingConditions = request.getParameter("workingConditions");

            JobDescription jd = new JobDescription(0, ticketID, jobTitle, startDate, endDate, department,
                    vacancies, responsibilities, requirements, compensation, officeAddress, workingConditions);

            JobDAO dao = new JobDAO();
            dao.insertJobDescription(jd);

            request.getSession().setAttribute("message", "Create JD Successfully!");
            response.sendRedirect(request.getContextPath() + "/jdlist");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
