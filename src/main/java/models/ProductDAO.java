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

    public List<ProductModel> getAllProducts() {
        String sql = "SELECT * FROM products";

        try (Connection con = sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(ProductModel.class);
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
}
