package icet.edu.erp.service.custom;

import com.jfoenix.controls.JFXTextField;
import icet.edu.erp.dto.Employee;
import icet.edu.erp.dto.User;
import icet.edu.erp.service.SuperService;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;

import java.sql.SQLIntegrityConstraintViolationException;

public interface UserService extends SuperService {
    boolean validateLogin(String email, String password);
    String sendOTP(String email);
    boolean isEmailInSystem(String text);
    Integer getId(JFXTextField txtUserEmail);
    Employee getEmployee(JFXTextField txtUserEmail);
    String getEmail(Integer userId);
    boolean deleteUser(Integer id);
    ObservableList<User> getAllCustomers();
    boolean addUser(User user) throws SQLIntegrityConstraintViolationException;
    void updatePassword(Integer id, TextField textField);
}
