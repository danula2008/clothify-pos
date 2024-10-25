package icet.edu.erp.service.custom.impl;

import com.jfoenix.controls.JFXTextField;
import icet.edu.erp.dto.Employee;
import icet.edu.erp.dto.User;
import icet.edu.erp.service.custom.UserService;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;

import java.sql.SQLIntegrityConstraintViolationException;

public class UserServiceImpl implements UserService {
    private static UserServiceImpl instance;
    private UserServiceImpl(){}

    public static UserServiceImpl getInstance(){
        return instance==null? instance = new UserServiceImpl() : instance;
    }

    public boolean validateLogin(String email, String password){
        return true;
    }

    @Override
    public String sendOTP(String email) {
        System.out.println("Email sent to" + email);
        return "123456";
    }

    @Override
    public boolean isEmailInSystem(String text) {
        return true;
    }

    @Override
    public Integer getId(JFXTextField txtUserEmail) {
        return null;
    }

    @Override
    public Employee getEmployee(JFXTextField txtUserEmail) {
        return null;
    }

    @Override
    public String getEmail(Integer userId) {
        return null;
    }

    @Override
    public boolean deleteUser(Integer id) {
        return false;
    }

    @Override
    public ObservableList<User> getAllCustomers() {
        return null;
    }

    @Override
    public boolean addUser(User user) throws SQLIntegrityConstraintViolationException {
        return false;
    }

    @Override
    public void updatePassword(Integer id, TextField textField) {
        return;
    }
}
