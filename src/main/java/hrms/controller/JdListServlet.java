package hrms.controller;

import java.io.IOException;
import java.util.List;

import hrms.dao.JobDAO;
import hrms.model.JobDescription;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/jdlist")
public class JdListServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        JobDAO dao = new JobDAO();
        List<JobDescription> jdList = dao.getAll();

        request.setAttribute("jdList", jdList);
        request.getRequestDispatcher("/view/jd/jdlist.jsp").forward(request, response);
    }
}
