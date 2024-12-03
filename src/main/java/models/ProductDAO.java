package models;

import utils.DBManager;

import org.sql2o.*;
import java.util.*;

public class ProductDAO {
    private DBManager dbManager = new DBManager();
    private Sql2o sql2o;

    public ProductDAO() {
        this.sql2o = dbManager.getDb();
    }

    public List<ProductModel> getAllProducts(UserModel userModel) {
        if (userModel.getPrivilege().equalsIgnoreCase("admin") ||
                userModel.getPrivilege().equalsIgnoreCase("seller")) {
            String sql = "SELECT * FROM products WHERE seller = :seller";

            try (Connection con = sql2o.open()) {
                return con.createQuery(sql)
                        .addParameter("seller", userModel.getFullname())
                        .executeAndFetch(ProductModel.class);
            }
        } else {
            String sql = "SELECT * FROM products";

            try (Connection con = sql2o.open()) {
                return con.createQuery(sql).executeAndFetch(ProductModel.class);
            }
        }
    }

    public void addProduct(ProductModel products) {
        String sql = "INSERT INTO products (name, description, price, seller, image) " +
                "VALUES (:name, :description, :price, :seller, :image)";

        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("name", products.getName())
                    .addParameter("description", products.getDescription())
                    .addParameter("price", products.getPrice())
                    .addParameter("seller", products.getSeller())
                    .addParameter("image", products.getImage())
                    .executeUpdate();
        }
    }

    public void updateProduct(ProductModel products, ProductModel updatedProducts) {
        String sql = "UPDATE products " +
                "SET name = :name, description = :description, price = :price, image = :image " +
                "WHERE id = :id";

        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("name", updatedProducts.getName())
                    .addParameter("description", updatedProducts.getDescription())
                    .addParameter("price", updatedProducts.getPrice())
                    .addParameter("image", updatedProducts.getImage())
                    .addParameter("id", products.getId())
                    .executeUpdate();
        }
    }

    public void removeProduct(ProductModel products) {
        String sql = "DELETE FROM products WHERE id = :id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", products.getId())
                    .executeUpdate();
        }
    }
}
