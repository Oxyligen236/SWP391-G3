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

@WebServlet("/addContracts")
public class CreateContract extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ContractTypeDAO typeDAO = new ContractTypeDAO();
        List<ContractType> types = typeDAO.getAllTypes();
        request.setAttribute("types", types);
        request.getRequestDispatcher("/view/contract/createContract.jsp").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ContractDAO dao = new ContractDAO();
        Contract contract = new Contract();
                String userID = request.getParameter("userID");
                String startDate = request.getParameter("startDate");
                String endDate = request.getParameter("endDate");
                String signDate = request.getParameter("signDate");
                String duration = request.getParameter("duration");
                String baseSalary = request.getParameter("baseSalary");
                String typeID = request.getParameter("typeID");
                contract.setUserId(Integer.parseInt(userID));
                contract.setStartDate(java.time.LocalDate.parse(startDate));
                contract.setEndDate(endDate != null && !endDate.isEmpty() ? java.time.LocalDate.parse(endDate) : null);
                contract.setSignDate(java.time.LocalDate.parse(signDate));  
                contract.setDuration(Integer.parseInt(duration));
                contract.setBaseSalary(Double.parseDouble(baseSalary));
                contract.setNote(request.getParameter("note"));
                contract.setTypeID(Integer.parseInt(typeID));
                dao.addContract(contract);
                response.sendRedirect("contracts?action=list");
        }
    }

