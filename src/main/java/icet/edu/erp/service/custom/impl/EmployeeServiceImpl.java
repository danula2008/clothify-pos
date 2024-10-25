package icet.edu.erp.service.custom.impl;

import icet.edu.erp.dao.DaoFactory;
import icet.edu.erp.dao.custom.EmployeeDao;
import icet.edu.erp.dto.Employee;
import icet.edu.erp.entity.EmployeeEntity;
import icet.edu.erp.service.custom.EmployeeService;
import icet.edu.erp.util.DaoType;
import javafx.collections.ObservableList;
import org.modelmapper.ModelMapper;
import java.sql.SQLIntegrityConstraintViolationException;

public class EmployeeServiceImpl implements EmployeeService {
    private static EmployeeServiceImpl instance;
    private EmployeeServiceImpl(){}

    public static EmployeeServiceImpl getInstance() {
        return instance==null? instance = new EmployeeServiceImpl() : instance;
    }

    private final EmployeeDao repository = DaoFactory.getInstance().getDaoType(DaoType.EMPLOYEE);

    private final ModelMapper mapper = new ModelMapper();

    @Override
    public boolean deleteEmployee(Integer id) {
        return repository.delete(id);
    }

    @Override
    public ObservableList<Employee> getAllEmployees() {
        return (ObservableList<Employee>) repository.findAll().stream().map(employeeEntity -> mapper.map(employeeEntity, Employee.class)).toList();
    }

    @Override
    public boolean addEmployee(Employee employee) throws SQLIntegrityConstraintViolationException {
        return repository.save(mapper.map(employee, EmployeeEntity.class));
    }

    @Override
    public boolean updateEmployee(Employee employee) throws SQLIntegrityConstraintViolationException {
        return repository.update(mapper.map(employee, EmployeeEntity.class));
    }

    @Override
    public Employee getEmployeeByUserId(Integer userId) {
        return mapper.map(repository.getItemByUserId(userId), Employee.class);
    }
}
