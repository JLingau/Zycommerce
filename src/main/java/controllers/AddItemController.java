package controllers;

import models.ProductDAO;
import models.ProductModel;
import views.AddItem;

import javax.swing.*;
import java.io.*;
import java.nio.file.*;

public class AddItemController {
    private ProductDAO productDAO;

    public AddItemController() {
        productDAO = new ProductDAO();
    }

    public void uploadImage(File imageFile) {
        if (imageFile != null) {
            try {
                Path currentPath = Paths.get("").toAbsolutePath();
                // Failsafe bila absolute path tidak ditemukan
                if (currentPath == null || !currentPath.toFile().exists()) {
                    currentPath = Paths.get(System.getProperty("user.dir"));
                }

                Path imageResourcePath = currentPath.resolve("src/main/resources/images");
                Files.createDirectories(imageResourcePath);

                Path uploadPath = imageResourcePath.resolve(imageFile.getName());
                if (uploadPath.toFile().exists()) {
                    JOptionPane.showMessageDialog(null, "Image already exists");
                } else {
                    Files.copy(imageFile.toPath(), uploadPath);
                    JOptionPane.showMessageDialog(null, "Image uploaded successfully!");
                }

            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error uploading image ! " + e.getMessage());
            }
        }
    }

    public void addProductToDB(ProductModel product, File imageFile) {
        productDAO.addProduct(product);
        uploadImage(imageFile);
    }
}
