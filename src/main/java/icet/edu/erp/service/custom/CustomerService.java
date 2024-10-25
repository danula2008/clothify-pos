package icet.edu.erp.service.custom;

import icet.edu.erp.dto.Customer;
import icet.edu.erp.service.SuperService;
import javafx.collections.ObservableList;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;

public interface CustomerService extends SuperService {
    boolean deleteCustomer(Integer id);
    ObservableList<Customer> getAllCustomers();
    LocalDate getDate(Integer id);
    boolean addCustomer(Customer customer) throws SQLIntegrityConstraintViolationException;
    boolean updateCustomer(Customer customer) throws SQLIntegrityConstraintViolationException;
    Customer getCustomer(Integer id);
}

