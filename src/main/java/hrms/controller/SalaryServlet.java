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

    private final LocalDate now = LocalDate.now();
    private final int currentMonth = now.getMonthValue();
    private final int currentYear = now.getYear();

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

        int itemsPerPage = 5;
        if (request.getParameter("itemsPerPage") != null) {
            try {
                itemsPerPage = Integer.parseInt(request.getParameter("itemsPerPage"));
            } catch (NumberFormatException e) {
                itemsPerPage = 5;
            }
        }

        int currentPage = 1;
        if (request.getParameter("page") != null) {
            try {
                currentPage = Integer.parseInt(request.getParameter("page"));
            } catch (NumberFormatException e) {
                currentPage = 1;
            }
        }

        List<UserDTO> employeesWithoutPayroll = payrollService.findEmployeesWithoutPayroll(currentMonth, currentYear);
        LocalDate payDate = LocalDate.of(currentYear, currentMonth, 26);
        Date payDateSql = Date.valueOf(payDate);

        request.setAttribute("payDate", payDateSql);

        if (null == action) {
            showSalaryPage(request, response, employeesWithoutPayroll, currentPage, itemsPerPage);
        } else {
            switch (action) {
                case "generate" ->
                    generatePayrolls(request, response, currentPage, itemsPerPage);
                case "calculate" ->
                    calculateSalaries(request, response, currentPage, itemsPerPage);
                default ->
                    showSalaryPage(request, response, employeesWithoutPayroll, currentPage, itemsPerPage);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    private void showSalaryPage(HttpServletRequest request, HttpServletResponse response,
            List<UserDTO> employeesWithoutPayroll, int currentPage, int itemsPerPage)
            throws ServletException, IOException {

        // Phân trang
        int totalItems = employeesWithoutPayroll.size();
        int totalPages = totalItems > 0 ? (int) Math.ceil((double) totalItems / itemsPerPage) : 0;

        if (currentPage > totalPages && totalPages > 0) {
            currentPage = totalPages;
        }

        int startIndex = (currentPage - 1) * itemsPerPage;
        int endIndex = Math.min(startIndex + itemsPerPage, totalItems);

        List<UserDTO> paginatedEmployees = totalItems > 0
                ? employeesWithoutPayroll.subList(startIndex, endIndex)
                : employeesWithoutPayroll;

        request.setAttribute("employeesWithoutPayroll", paginatedEmployees);
        request.setAttribute("totalEmployees", totalItems);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("itemsPerPage", itemsPerPage);
        request.setAttribute("currentMonth", currentMonth);
        request.setAttribute("currentYear", currentYear);

        request.getRequestDispatcher("/view/payroll/salaryProcessor.jsp").forward(request, response);
    }

    private void generatePayrolls(HttpServletRequest request, HttpServletResponse response,
            int currentPage, int itemsPerPage)
            throws ServletException, IOException {
        List<UserDTO> allUsers = userDAO.getAllWithJoin();
        AccountDAO accountDAO = new AccountDAO();
        int successCount = 0;
        int failCount = 0;

        for (UserDTO user : allUsers) {
            Account account = accountDAO.getAccountByUserID(user.getUserId());
            if (account == null) {
                continue;
            }

            AccountDTO accountDTO = accountDAO.getAccountDTOByID(account.getAccountID());
            if (accountDTO == null || accountDTO.getRoleName().equals("Admin")) {
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

        int totalItems = employeesWithoutPayroll.size();
        int totalPages = totalItems > 0 ? (int) Math.ceil((double) totalItems / itemsPerPage) : 0;

        if (currentPage > totalPages && totalPages > 0) {
            currentPage = totalPages;
        }

        int startIndex = (currentPage - 1) * itemsPerPage;
        int endIndex = Math.min(startIndex + itemsPerPage, totalItems);

        List<UserDTO> paginatedEmployees = totalItems > 0
                ? employeesWithoutPayroll.subList(startIndex, endIndex)
                : employeesWithoutPayroll;

        request.setAttribute("employeesWithoutPayroll", paginatedEmployees);
        request.setAttribute("totalEmployees", totalItems);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("itemsPerPage", itemsPerPage);
        request.setAttribute("currentMonth", currentMonth);
        request.setAttribute("currentYear", currentYear);
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

    private void calculateSalaries(HttpServletRequest request, HttpServletResponse response,
            int currentPage, int itemsPerPage)
            throws ServletException, IOException {
        List<Payroll> payrolls = payrollDAO.searchPayroll(0, currentMonth, currentYear, null);
        if (payrolls.isEmpty()) {
            request.setAttribute("currentMonth", currentMonth);
            request.setAttribute("currentYear", currentYear);
            request.setAttribute("errorMessage", "No payrolls found for the current month to calculate, please generate payrolls first.");
            request.getRequestDispatcher("/view/payroll/salaryProcessor.jsp").forward(request, response);
            return;
        }
        int successCount = 0;
        int failCount = 0;

        if (payrollDAO.isPayrollExistForMonth(currentMonth, currentYear)) {
            List<UserDTO> employeesWithoutPayroll = payrollService.findEmployeesWithoutPayroll(currentMonth, currentYear);
            if (!employeesWithoutPayroll.isEmpty()) {
                request.setAttribute("currentMonth", currentMonth);
                request.setAttribute("currentYear", currentYear);
                request.setAttribute("employeesWithoutPayroll", employeesWithoutPayroll);
                request.setAttribute("errorMessage", "Some employees do not have payroll records for the current month.");
                request.getRequestDispatcher("/view/payroll/salaryProcessor.jsp").forward(request, response);
                return;
            } else {
                request.setAttribute("currentMonth", currentMonth);
                request.setAttribute("currentYear", currentYear);
                request.setAttribute("employeesWithoutPayroll", employeesWithoutPayroll);
                request.setAttribute("errorMessage", "Payrolls for the current month have already been calculated.");
                request.getRequestDispatcher("/view/payroll/salaryProcessor.jsp").forward(request, response);
                return;
            }
        }

        for (Payroll payroll : payrolls) {
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
        }

        List<UserDTO> employeesWithoutPayroll = payrollService.findEmployeesWithoutPayroll(currentMonth, currentYear);

        int totalItems = employeesWithoutPayroll.size();
        int totalPages = totalItems > 0 ? (int) Math.ceil((double) totalItems / itemsPerPage) : 0;

        if (currentPage > totalPages && totalPages > 0) {
            currentPage = totalPages;
        }

        int startIndex = (currentPage - 1) * itemsPerPage;
        int endIndex = Math.min(startIndex + itemsPerPage, totalItems);

        List<UserDTO> paginatedEmployees = totalItems > 0
                ? employeesWithoutPayroll.subList(startIndex, endIndex)
                : employeesWithoutPayroll;

        request.setAttribute("employeesWithoutPayroll", paginatedEmployees);
        request.setAttribute("totalEmployees", totalItems);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("itemsPerPage", itemsPerPage);
        request.setAttribute("currentMonth", currentMonth);
        request.setAttribute("currentYear", currentYear);
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
