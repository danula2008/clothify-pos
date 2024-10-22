package icet.edu.erp.service.custom;

import com.jfoenix.controls.JFXTextField;
import icet.edu.erp.service.SuperService;

public interface UserService extends SuperService {
    boolean validateLogin(String email, String password);
    String sendOTP(String email);
    boolean isEmailInSystem(String text);
}
