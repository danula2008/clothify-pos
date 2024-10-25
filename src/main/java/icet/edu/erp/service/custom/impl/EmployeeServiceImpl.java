package icet.edu.erp.service.custom.impl;

import icet.edu.erp.dto.Employee;
import icet.edu.erp.service.custom.EmployeeService;
import javafx.collections.ObservableList;

import java.sql.SQLIntegrityConstraintViolationException;

public class EmployeeServiceImpl implements EmployeeService {
    private static EmployeeServiceImpl instance;
    private EmployeeServiceImpl(){}

    public static EmployeeServiceImpl getInstance() {
        return instance==null? instance = new EmployeeServiceImpl() : instance;
    }

    @Override
    public boolean deleteEmployee(Integer id) {
        return false;
    }

    @Override
    public ObservableList<Employee> getAll() {
        return null;
    }

    @Override
    public boolean addEmployee(Employee customer) throws SQLIntegrityConstraintViolationException {
        return false;
    }
}
