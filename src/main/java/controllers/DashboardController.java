package controllers;

import models.ProductDAO;
import models.ProductModel;

import java.util.List;

public class DashboardController {
    private ProductDAO productDAO;

    public DashboardController() {
        productDAO = new ProductDAO();
    }

    public List<ProductModel> getProductList() {
        return productDAO.getAllProducts();
    }
}
