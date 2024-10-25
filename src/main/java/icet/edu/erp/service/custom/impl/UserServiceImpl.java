package icet.edu.erp.service.custom.impl;

import icet.edu.erp.dao.DaoFactory;
import icet.edu.erp.dao.custom.UserDao;
import icet.edu.erp.dto.User;
import icet.edu.erp.entity.UserEntity;
import icet.edu.erp.service.custom.UserService;
import icet.edu.erp.util.DaoType;
import javafx.collections.ObservableList;
import org.modelmapper.ModelMapper;

import java.sql.SQLIntegrityConstraintViolationException;

public class UserServiceImpl implements UserService {
    private static UserServiceImpl instance;
    private UserServiceImpl(){}

    public static UserServiceImpl getInstance(){
        return instance==null? instance = new UserServiceImpl() : instance;
    }

    private final UserDao repository = DaoFactory.getInstance().getDaoType(DaoType.USER);
    private final ModelMapper mapper = new ModelMapper();

    public boolean validateLogin(String email, String password){
        return repository.getItemByEmail(email).getPassword().equals(password);
    }

    @Override
    public String sendOTP(String email) {
        System.out.println("Email sent to" + email);
        return "123456";
    }

    @Override
    public boolean isEmailInSystem(String email) {
        return repository.getItemByEmail(email) != null;
    }

    @Override
    public Integer getUserId(String email) {
        return repository.getItemByEmail(email).getId();
    }

    @Override
    public String getEmail(Integer userId) {
        return repository.getItem(userId).getEmail();
    }

    @Override
    public boolean deleteUser(Integer id) {
        return repository.delete(id);
    }

    @Override
    public ObservableList<User> getAllCustomers() {
        return (ObservableList<User>) repository.findAll().stream().map(userEntity -> mapper.map(userEntity, User.class)).toList();
    }

    @Override
    public boolean addUser(User user) throws SQLIntegrityConstraintViolationException {
        return repository.save(mapper.map(user, UserEntity.class));
    }

    @Override
    public boolean updatePassword(Integer id, String newPwd) {
        return repository.updatePassword(id, newPwd);
    }
}
