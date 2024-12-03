package controllers;

import models.ProductDAO;
import models.ProductModel;
import models.UserModel;

import java.util.List;

public class DashboardController {
    private ProductDAO productDAO;
    private UserModel userModel;

    public DashboardController(UserModel userModel) {
        this.userModel = userModel;
        productDAO = new ProductDAO();
    }

    public List<ProductModel> getProductList() {
        return productDAO.getAllProducts(userModel);
    }
}
