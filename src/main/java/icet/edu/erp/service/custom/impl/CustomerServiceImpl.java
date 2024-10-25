package icet.edu.erp.service.custom.impl;

import icet.edu.erp.dao.DaoFactory;
import icet.edu.erp.dao.custom.CustomerDao;
import icet.edu.erp.dto.Customer;
import icet.edu.erp.entity.CustomerEntity;
import icet.edu.erp.service.custom.CustomerService;
import icet.edu.erp.util.DaoType;
import javafx.collections.ObservableList;
import org.modelmapper.ModelMapper;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;

public class CustomerServiceImpl implements CustomerService {

    private static CustomerServiceImpl instance;

    private CustomerServiceImpl(){}

    public static CustomerServiceImpl getInstance() {
        return instance==null? instance = new CustomerServiceImpl() : instance;
    }

    private final CustomerDao repository = DaoFactory.getInstance().getDaoType(DaoType.CUSTOMER);

    private final ModelMapper mapper = new ModelMapper();

    @Override
    public boolean deleteCustomer(Integer id) {
        return repository.delete(id);
    }

    @Override
    public ObservableList<Customer> getAllCustomers() {
        return (ObservableList<Customer>) repository.findAll().stream().map(customerEntity -> mapper.map(customerEntity, Customer.class)).toList();
    }

    @Override
    public LocalDate getDate(Integer id) {
        return repository.getDate(id);
    }

    @Override
    public boolean addCustomer(Customer customer) throws SQLIntegrityConstraintViolationException {
        return repository.save(mapper.map(customer, CustomerEntity.class));
    }

    @Override
    public boolean updateCustomer(Customer customer) throws SQLIntegrityConstraintViolationException {
        return repository.update(mapper.map(customer, CustomerEntity.class));
    }

    @Override
    public Customer getCustomer(Integer id) {
        return mapper.map(repository.getItem(id), Customer.class);
    }
}
