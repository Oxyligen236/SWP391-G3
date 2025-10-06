package hrms.controller.contract;

import java.io.IOException;
import java.util.List;

import hrms.dao.contract.ContractDAO;
import hrms.dao.contract.ContractTypeDAO;
import hrms.model.Contract;
import hrms.model.ContractType;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/contracts")
public class ContractController extends HttpServlet {
    private ContractDAO dao;

    @Override
    public void init() {
        dao = new ContractDAO();
    }
    
     protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if(action == null) action = "list";

        switch (action) {
            case "list":
                List<Contract> list = dao.getAllContracts();
                request.setAttribute("contracts", list);
                request.getRequestDispatcher("/contracts/list.jsp").forward(request, response);
                break;
            case "add":
                List<ContractType> types = new ContractTypeDAO().getAllTypes();
                request.setAttribute("types", types);
                request.getRequestDispatcher("/contracts/add.jsp").forward(request, response);
                break;
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("insert".equals(action)) {
            Contract contract = new Contract();
            contract.setUserId(Integer.parseInt(request.getParameter("userID")));
            contract.setStartDate(java.sql.Date.valueOf(request.getParameter("startDate")));
            contract.setEndDate(java.sql.Date.valueOf(request.getParameter("endDate")));
            contract.setSignDate(java.sql.Date.valueOf(request.getParameter("signDate")));
            contract.setDuration(Integer.parseInt(request.getParameter("duration")));
            contract.setBaseSalary(Double.parseDouble(request.getParameter("baseSalary")));
            contract.setNote(request.getParameter("note"));
            contract.setTypeID(Integer.parseInt(request.getParameter("typeID")));

            dao.addContract(contract);
            response.sendRedirect("contracts?action=list");
        }
    }
}
