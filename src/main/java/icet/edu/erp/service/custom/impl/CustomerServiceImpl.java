package icet.edu.erp.service.custom.impl;

import icet.edu.erp.dto.Customer;
import icet.edu.erp.service.custom.CustomerService;
import javafx.collections.ObservableList;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;

public class CustomerServiceImpl implements CustomerService {

    private static CustomerServiceImpl instance;

    private CustomerServiceImpl(){}

    public static CustomerServiceImpl getInstance() {
        return instance==null? instance = new CustomerServiceImpl() : instance;
    }

    @Override
    public boolean deleteCustomer(Integer id) {
        return false;
    }

    @Override
    public ObservableList<Customer> getAllCustomers() {
        return null;
    }

    @Override
    public LocalDate getDate(Integer id) {
        return null;
    }

    @Override
    public boolean addCustomer(Customer customer) throws SQLIntegrityConstraintViolationException {
        return false;
    }
}
