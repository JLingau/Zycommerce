package controllers;

import models.UserModel;
import org.apache.commons.codec.digest.DigestUtils;
import views.HomeFrame;
import views.Layout;
import views.LoginView;
import views.RegisterView;
import models.UserDAO;

import javax.swing.*;

public class AuthController {
    private UserDAO userDAO = new UserDAO();
    private JFrame frame;

    public AuthController(JFrame frame) {
        this.frame = frame;
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

    public void authenticateUser(UserModel user) {
        UserModel userContainer = userDAO.getUser(user);
        try {
            if (user.getUsername().equals(userContainer.getUsername())
                    && user.getPassword().equals(userContainer.getPassword())) {
                frame.dispose();
                new HomeFrame(userContainer);
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid username or password");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Username or Password is empty !");
        }
    }

    public void insertUser(UserModel user) {
        UserModel userContainer = userDAO.getUser(user);

        if (userContainer != null) {
            userDAO.addUser(user);
            moveToLogin();
        } else {
            JOptionPane.showMessageDialog(null, "Username sudah tersedia, Silahkan gunakan username lain !");
        }
    }
}
