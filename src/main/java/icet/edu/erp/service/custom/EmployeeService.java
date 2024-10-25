package icet.edu.erp.service.custom;

import icet.edu.erp.dto.Employee;
import icet.edu.erp.service.SuperService;
import javafx.collections.ObservableList;

import java.sql.SQLIntegrityConstraintViolationException;

public interface EmployeeService extends SuperService {
    boolean deleteEmployee(Integer id);
    ObservableList<Employee> getAllEmployees();
    boolean addEmployee(Employee customer) throws SQLIntegrityConstraintViolationException;
    boolean updateEmployee(Employee employee) throws SQLIntegrityConstraintViolationException;
    Employee getEmployeeByUserId(Integer userId);
}
