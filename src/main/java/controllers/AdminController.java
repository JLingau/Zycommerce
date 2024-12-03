package controllers;

import models.UserDAO;
import models.UserModel;
import java.util.*;

public class AdminController {
    private UserModel userModel;
    private UserDAO userDAO = new UserDAO();

    public AdminController(UserModel userModel) {
        this.userModel = userModel;
    }

    public List<UserModel> getPendingUsers() {
        return userDAO.getAllPendingUsers();
    }

    public void updateSellerStatus(UserModel user) {
        userDAO.updateSellerStatus(user);
    }

}
