package controllers;

import models.TransactionDAO;
import models.TransactionModel;
import models.UserDAO;
import models.UserModel;
import java.util.*;

public class AdminController {
    private UserModel userModel;
    private TransactionDAO transactionDAO = new TransactionDAO();
    private UserDAO userDAO = new UserDAO();

    public AdminController(UserModel userModel) {
        this.userModel = userModel;
    }

    public List<TransactionModel> getClientTransactions() {
        return transactionDAO.getClientTransactions(userModel);
    }

    public List<TransactionModel> getSellerOrderTransaction() {
        return transactionDAO.getSellerOrderList(userModel);
    }

    public List<UserModel> getPendingUsers() {
        return userDAO.getAllPendingUsers();
    }

    public void updateSellerStatus(UserModel user) {
        userDAO.updateSellerStatus(user);
    }

}
