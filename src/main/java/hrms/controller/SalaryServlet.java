package hrms.controller;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import hrms.dao.AccountDAO;
import hrms.dao.PayrollDAO;
import hrms.dao.UserDAO;
import hrms.dao.contract.ContractDAO;
import hrms.dto.AccountDTO;
import hrms.dto.ContractDTO;
import hrms.dto.UserDTO;
import hrms.model.Account;
import hrms.model.Payroll;
import hrms.service.PayrollService;
import hrms.service.SalaryProcessor;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/salary")
public class SalaryServlet extends HttpServlet {

    private SalaryProcessor salaryProcessor;
    private PayrollDAO payrollDAO;
    private UserDAO userDAO;
    private ContractDAO contractDAO;
    private PayrollService payrollService;

    @Override
    public void init() throws ServletException {
        this.salaryProcessor = new SalaryProcessor();
        this.payrollDAO = new PayrollDAO();
        this.userDAO = new UserDAO();
        this.contractDAO = new ContractDAO();
        this.payrollService = new PayrollService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
            LocalDate now = LocalDate.now();
    int currentMonth = now.getMonthValue();
    int currentYear = now.getYear();
    LocalDate payDate = LocalDate.of(currentYear, currentMonth, 26);
Date payDateSql = Date.valueOf(payDate);
    request.setAttribute("payDate", payDateSql);

        if (action == null) {
            showSalaryPage(request, response);
        } else if ("generate".equals(action)) {
            generatePayrolls(request, response);
        } else if ("calculate".equals(action)) {
            calculateSalaries(request, response);
        } else {
            showSalaryPage(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    private void showSalaryPage(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    LocalDate now = LocalDate.now();
    int currentMonth = now.getMonthValue();
    int currentYear = now.getYear();

    LocalDate payDate = LocalDate.of(currentYear, currentMonth, 26);
Date payDateSql = Date.valueOf(payDate);
    List<UserDTO> employeesWithoutPayroll = payrollService.findEmployeesWithoutPayroll(currentMonth, currentYear);

    request.setAttribute("currentMonth", currentMonth);
    request.setAttribute("currentYear", currentYear);
    request.setAttribute("employeesWithoutPayroll", employeesWithoutPayroll);

    request.getRequestDispatcher("/view/payroll/salaryProcessor.jsp").forward(request, response);
}

private void generatePayrolls(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    LocalDate now = LocalDate.now();
    int currentMonth = now.getMonthValue();
    int currentYear = now.getYear();

    List<UserDTO> allUsers = userDAO.getAllWithJoin();
    AccountDAO accountDAO = new AccountDAO();
    int successCount = 0;
    int failCount = 0;

    for (UserDTO user : allUsers) {
        Account account = accountDAO.getAccountByUserID(user.getUserId());
        AccountDTO accountDTO = accountDAO.getAccountDTOByID(account.getAccountID());
        if (accountDTO != null && accountDTO.getRoleName().equals("Admin")) {
            continue;
        }

        List<Payroll> existingPayrolls = payrollDAO.searchPayroll(
                user.getUserId(), currentMonth, currentYear, null);

        if (existingPayrolls.isEmpty()) {
            double baseSalary = 0.0;
            List<ContractDTO> contracts = contractDAO.getContractsByUserId(user.getUserId());

            for (ContractDTO contract : contracts) {
                if ("Active".equals(contract.getStatus())) {
                    baseSalary = contract.getBaseSalary();
                    break;
                }
            }

            if (baseSalary > 0) {
                Payroll payroll = salaryProcessor.createPayroll(
                        user.getUserId(),
                        baseSalary,
                        currentMonth,
                        currentYear
                );

                String workHoursStr = "00:00";

                if (payrollDAO.insertPayroll(payroll, workHoursStr)) {
                    successCount++;
                } else {
                    failCount++;
                }
            }
        }
    }

    List<UserDTO> employeesWithoutPayroll = payrollService.findEmployeesWithoutPayroll(currentMonth, currentYear);

    request.setAttribute("currentMonth", currentMonth);
    request.setAttribute("currentYear", currentYear);
    request.setAttribute("employeesWithoutPayroll", employeesWithoutPayroll);
    request.setAttribute("successCount", successCount);
    request.setAttribute("failCount", failCount);

    if (successCount > 0) {
        request.setAttribute("successMessage", "Created " + successCount + " payrolls successfully!");
    }
    if (failCount > 0) {
        request.setAttribute("errorMessage", "There were " + failCount + " payroll creation failures!");
    }

    request.getRequestDispatcher("/view/payroll/salaryProcessor.jsp").forward(request, response);
}
    private void calculateSalaries(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        LocalDate now = LocalDate.now();
        int currentMonth = now.getMonthValue();
        int currentYear = now.getYear();

        List<Payroll> payrolls = payrollDAO.searchPayroll(0, currentMonth, currentYear, null);
        int successCount = 0;
        int failCount = 0;

        for (Payroll payroll : payrolls) {
            try {
                Map<String, Object> salaryDetails = salaryProcessor.calculateSalaryDetails(
                        payroll.getUserID(),
                        payroll.getPayrollID(),
                        payroll.getBaseSalary(),
                        currentMonth,
                        currentYear
                );

                double totalSalary = (double) salaryDetails.get("totalSalary");
                double actualWorkHours = (double) salaryDetails.get("actualWorkHours");

                long hours = (long) actualWorkHours;
                long minutes = (long) ((actualWorkHours - hours) * 60);
                String workHoursStr = String.format("%02d:%02d", hours, minutes);

                if (payrollDAO.updatePayrollSalary(payroll.getPayrollID(), totalSalary, workHoursStr, Date.valueOf(now).toString())) {
                    successCount++;
                } else {
                    failCount++;
                }

            } catch (Exception e) {
                System.err.println("Error calculating salary for payroll " + payroll.getPayrollID() + ": " + e.getMessage());
                failCount++;
            }
        }

        List<UserDTO> employeesWithoutPayroll = payrollService.findEmployeesWithoutPayroll(currentMonth, currentYear);

        request.setAttribute("currentMonth", currentMonth);
        request.setAttribute("currentYear", currentYear);
        request.setAttribute("employeesWithoutPayroll", employeesWithoutPayroll);
        request.setAttribute("successCount", successCount);
        request.setAttribute("failCount", failCount);

        if (successCount > 0) {
            request.setAttribute("successMessage", "Calculated " + successCount + " payrolls successfully!");
        }
        if (failCount > 0) {
            request.setAttribute("errorMessage", "There were " + failCount + " payroll calculation failures!");
        }

        request.getRequestDispatcher("/view/payroll/salaryProcessor.jsp").forward(request, response);
    }
}
