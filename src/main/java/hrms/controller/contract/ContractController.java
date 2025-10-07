package hrms.controller.contract;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import hrms.dao.contract.ContractDAO;
import hrms.model.Contract;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/contracts")
public class ContractController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ContractDAO dao = new ContractDAO();
        List<Contract> contracts = dao.getAllContracts();
        request.setAttribute("contracts", contracts);
        request.getRequestDispatcher("/view/contract/viewListContract.jsp").forward(request, response);

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

                // Xác thực đầu vào
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

                dao.addContract(contract);
                response.sendRedirect("contracts?action=list");
            } catch (IllegalArgumentException e) {
                request.setAttribute("error", e.getMessage());
                request.getRequestDispatcher("/createContract.jsp").forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("error", "Đã xảy ra lỗi khi thêm hợp đồng");
                request.getRequestDispatcher("/createContract.jsp").forward(request, response);
            }
        }
    }

