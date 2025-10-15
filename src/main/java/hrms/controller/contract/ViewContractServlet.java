package hrms.controller.contract;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hrms.dao.UserDAO;
import hrms.dao.contract.ContractDAO;
import hrms.model.Contract;
import hrms.model.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/viewContracts")
public class ViewContractServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");
        if ("detail".equalsIgnoreCase(action)) {
            String idStr = request.getParameter("id");
            if (idStr == null || idStr.trim().isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing contract id");
                return;
            }
            try {
                int id = Integer.parseInt(idStr);
                ContractDAO dao = new ContractDAO();
                Contract contract = dao.getContractById(id); 
                if (contract == null) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Contract not found");
                    return;
                }
                request.setAttribute("contract", contract);
                RequestDispatcher rd = request.getRequestDispatcher("/view/contract/viewContract.jsp");
                rd.forward(request, response);
                return;
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid contract id");
                return;
            } catch (Exception e) {
                throw new ServletException("Lỗi khi lấy chi tiết hợp đồng", e);
            }
        }
        String searchField = request.getParameter("searchField");
        String searchValue = request.getParameter("searchValue");
        String fromDate = request.getParameter("fromDate");
        String toDate = request.getParameter("toDate");

        String sortField = request.getParameter("sortField");
        String sortOrder = request.getParameter("sortOrder");

        if (sortOrder == null || (!sortOrder.equals("asc") && !sortOrder.equals("desc"))) {
            sortOrder = "asc"; 
        }

        int page = 1;
        int pageSize = 10; 
        String pageParam = request.getParameter("page");
        String sizeParam = request.getParameter("size");
        try {
            if (pageParam != null) page = Math.max(1, Integer.parseInt(pageParam));
        } catch (NumberFormatException ex) {
            page = 1;   
        }

        
        String showAllParam = request.getParameter("showAll");
        boolean showAll = "true".equalsIgnoreCase(showAllParam);
        if (showAll) {
     
            searchField = null;
            searchValue = null;
            fromDate = null;
            toDate = null;
            sortField = null;
            sortOrder = "asc";
            page = 1; 
        }
        request.setAttribute("showAll", showAll);

        try {
            ContractDAO dao = new ContractDAO();

            List<Contract> allContracts = dao.getContracts(searchField, searchValue, fromDate, toDate, sortField, sortOrder);

            int totalRecords = allContracts.size();
            int totalPages = (int) Math.ceil((double) totalRecords / pageSize);
            if (totalPages == 0) totalPages = 1;
            if (page > totalPages) page = totalPages;

            int fromIndex = (page - 1) * pageSize;
            int toIndex = Math.min(fromIndex + pageSize, totalRecords);
            List<Contract> contractsPage = (fromIndex < totalRecords) ? allContracts.subList(fromIndex, toIndex) : java.util.Collections.emptyList();

            // --- START: add user full name map ---
            UserDAO userDAO = new UserDAO();
            Map<Integer, String> userFullNames = new HashMap<>();
            for (Contract c : contractsPage) {
                int uid = c.getUserId();
                if (!userFullNames.containsKey(uid)) {
                    User u = userDAO.getUserById(uid); // nếu tên method khác, sửa cho phù hợp
                    userFullNames.put(uid, u != null ? u.getFullname() : String.valueOf(uid));
                }
            }
            request.setAttribute("userFullNames", userFullNames);
            // --- END ---
            
            request.setAttribute("contracts", contractsPage);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("pageSize", pageSize);
            request.setAttribute("totalRecords", totalRecords);


            request.setAttribute("searchField", searchField);
            request.setAttribute("searchValue", searchValue);
            request.setAttribute("fromDate", fromDate);
            request.setAttribute("toDate", toDate);
            request.setAttribute("sortField", sortField);
            request.setAttribute("sortOrder", sortOrder);

            RequestDispatcher rd = request.getRequestDispatcher("/view/contract/viewListContract.jsp");
            rd.forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Lỗi khi tìm kiếm và sắp xếp hợp đồng", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ContractDAO dao = new ContractDAO();
        try {
            Contract contract = new Contract();
            String userID = request.getParameter("userID");
            String startDate = request.getParameter("startDate");
            String endDate = request.getParameter("endDate");
            String signDate = request.getParameter("signDate");
            String duration = request.getParameter("duration");
            String baseSalary = request.getParameter("baseSalary");
            String typeID = request.getParameter("typeID");
            String positionID = request.getParameter("positionID");
            String signerID = request.getParameter("signerID"); 


            if (userID == null || startDate == null || signDate == null || duration == null || baseSalary == null || typeID == null
                    || userID.isEmpty() || startDate.isEmpty() || signDate.isEmpty() || duration.isEmpty() || baseSalary.isEmpty() || typeID.isEmpty()) {
                throw new IllegalArgumentException("Các trường bắt buộc bị thiếu");
            }

            contract.setUserId(Integer.parseInt(userID));
            contract.setStartDate(LocalDate.parse(startDate));
            contract.setEndDate(endDate != null && !endDate.isEmpty() ? LocalDate.parse(endDate) : null);
            contract.setSignDate(LocalDate.parse(signDate));
            contract.setDuration(Integer.parseInt(duration));
            contract.setBaseSalary(Double.parseDouble(baseSalary));
            contract.setNote(request.getParameter("note"));
            contract.setTypeID(Integer.parseInt(typeID));
            contract.setPositionId(Integer.parseInt(positionID));
            contract.setSignerId(Integer.parseInt(signerID));

            dao.addContract(contract);
            response.sendRedirect("viewContracts?action=list");
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/view/contract/createContract.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Đã xảy ra lỗi khi thêm hợp đồng");
            request.getRequestDispatcher("/view/contract/createContract.jsp").forward(request, response);
        }
    }
}
