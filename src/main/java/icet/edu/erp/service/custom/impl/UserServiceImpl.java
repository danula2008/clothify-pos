package icet.edu.erp.service.custom.impl;

import com.jfoenix.controls.JFXTextField;
import icet.edu.erp.service.custom.UserService;

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
}
