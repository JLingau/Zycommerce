package models;

import utils.DBManager;

import org.sql2o.*;
import java.util.*;

public class TransactionDAO {
    private DBManager dbManager = new DBManager();
    private Sql2o sql2o;

    public TransactionDAO() {
        this.sql2o = dbManager.getDb();
    }

    public void insertTransaction(TransactionModel transactionModel) {
        String sql = "INSERT INTO transactions(product_name, product_desc, product_price, product_quantity, product_image, product_buyer, product_seller) " +
                "values (:productName, :productDesc, :productPrice, :productQuantity, :productImage, :productBuyer, :productSeller)";

        try (Connection con = sql2o.open()) {
            con.createQuery(sql).addParameter("productName", transactionModel.getProductName())
                    .addParameter("productDesc", transactionModel.getProductDescription())
                    .addParameter("productPrice", transactionModel.getProductPrice())
                    .addParameter("productQuantity", transactionModel.getProductQuantity())
                    .addParameter("productImage", transactionModel.getProductImage())
                    .addParameter("productBuyer", transactionModel.getProductBuyer())
                    .addParameter("productSeller", transactionModel.getProductSeller())
                    .executeUpdate();
        }
    }

    public List<TransactionModel> getClientTransactions(UserModel user) {
        String sql = "SELECT * FROM transactions WHERE product_buyer = :buyer";
        Map<String, String> colMaps = new HashMap<>();
        colMaps.put("product_name", "productName");
        colMaps.put("product_desc", "productDescription");
        colMaps.put("product_price", "productPrice");
        colMaps.put("product_quantity", "productQuantity");
        colMaps.put("product_image", "productImage");
        colMaps.put("product_seller", "productSeller");
        colMaps.put("product_buyer", "productBuyer");
        sql2o.setDefaultColumnMappings(colMaps);
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql).addParameter("buyer", user.getFullname())
                    .executeAndFetch(TransactionModel.class);
        }
    }

    public List<TransactionModel> getSellerOrderList(UserModel userModel) {
        String sql = "SELECT * FROM transactions WHERE product_seller = :seller";
        Map<String, String> colMaps = new HashMap<>();
        colMaps.put("product_name", "productName");
        colMaps.put("product_desc", "productDescription");
        colMaps.put("product_price", "productPrice");
        colMaps.put("product_quantity", "productQuantity");
        colMaps.put("product_image", "productImage");
        colMaps.put("product_seller", "productSeller");
        colMaps.put("product_buyer", "productBuyer");
        sql2o.setDefaultColumnMappings(colMaps);
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql).addParameter("seller", userModel.getFullname())
                    .executeAndFetch(TransactionModel.class);
        }
    }
}
