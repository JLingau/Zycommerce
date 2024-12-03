package controllers;

import models.UserModel;
import org.apache.commons.codec.digest.DigestUtils;
import views.HomeFrame;
import views.Layout;
import views.LoginView;
import views.RegisterView;
import models.UserDAO;

import javax.swing.*;
import java.util.regex.Pattern;

public class AuthController {
    private UserDAO userDAO = new UserDAO();
    private JFrame frame;

    public AuthController(JFrame frame) {
        this.frame = frame;
    }

    public boolean isNullOrEmpty(String value) {
        return value == null || value.isEmpty();
    }

    public boolean checkValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        Pattern p = Pattern.compile(emailRegex);

        return email != null && p.matcher(email).matches();
    }

    public void moveToRegister() {
        frame.dispose();
        new RegisterView();
    }

    public void moveToLogin() {
        frame.dispose();
        new LoginView();
    }

    public String getHashedPassword(String password) {
        return DigestUtils.sha256Hex(password);
    }

    public void validateAndInsertUser(UserModel user) {
        // Check form empty or not and validate other form requirements
        if (isNullOrEmpty(user.getUsername().trim()) || isNullOrEmpty(user.getPassword().trim())
                || isNullOrEmpty(user.getEmail().trim()) || isNullOrEmpty(user.getPhone().trim())
                || isNullOrEmpty(user.getFullname().trim()) || isNullOrEmpty(user.getPrivilege().trim())
                || isNullOrEmpty(user.getStatus().trim())) {
            JOptionPane.showMessageDialog(null, "Please input all form fields");
        } else if (user.getPassword().length() < 6) {
            JOptionPane.showMessageDialog(null, "Password must be at least 6 characters");
        } else if (!checkValidEmail(user.getEmail().trim())) {
            JOptionPane.showMessageDialog(null, "Email is not valid !");
        } else {
            insertUser(user);
        }
    }

    public void authenticateUser(UserModel user) {
        UserModel userContainer = userDAO.getUser(user);
        if ((user.getUsername().equals(userContainer.getUsername())
                && user.getPassword().equals(userContainer.getPassword()))) {
            if (userContainer.getStatus().equalsIgnoreCase("active")) {
                frame.dispose();
                new HomeFrame(userContainer, new CartController());
            } else {
                JOptionPane.showMessageDialog(frame, "Wait for your account confirmation !");
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Invalid username or password !");
        }
    }

    public void insertUser(UserModel user) {
        UserModel userContainer = userDAO.getUser(user);

        if (userContainer == null) {
            if (userDAO.getUserFullName(user) == null) {
                userDAO.addUser(user);
                moveToLogin();
            } else {
                JOptionPane.showMessageDialog(null, "Fullname already exists !");
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Username already exists !");
        }

    }
}
