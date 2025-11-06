package hrms.controller.contract;

import java.io.IOException;
import java.util.List;

import hrms.dao.contract.ContractDAO;
import hrms.dto.ContractDTO;
import hrms.model.Account;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/myContracts")
public class MyContractServlet extends HttpServlet{
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, ServletException {
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        
        if(account == null) {
            response.sendRedirect(request.getContextPath() + "/authenticate");
            return;
        }
        
        try {
            int userId = account.getUserID();
            
            ContractDAO contractDAO = new ContractDAO();
            
            // Auto-update contract status based on dates
            contractDAO.autoUpdateContractStatus();
            
            List<ContractDTO> contracts = contractDAO.getContractsByUserId(userId);
            
            request.setAttribute("contracts", contracts);
            
            request.getRequestDispatcher("/view/contract/myContract.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Cannot load contract list: " + e.getMessage());
            request.getRequestDispatcher("/view/profile/error.jsp").forward(request, response);
        }
    }
}
