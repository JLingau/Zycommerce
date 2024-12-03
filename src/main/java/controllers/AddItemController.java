package controllers;

import models.ProductDAO;
import models.ProductModel;
import models.UserModel;
import views.AddItem;

import javax.swing.*;
import java.io.*;
import java.nio.file.*;

public class AddItemController {
    private ProductDAO productDAO;
    private UserModel userModel;

    public AddItemController(UserModel userModel) {
        productDAO = new ProductDAO();
        this.userModel = userModel;
    }

    private void uploadImage(File imageFile) {
        if (imageFile != null) {
            try {
                Path currentPath = Paths.get("").toAbsolutePath();
                // Failsafe bila absolute path tidak ditemukan
                if (currentPath == null || !currentPath.toFile().exists()) {
                    currentPath = Paths.get(System.getProperty("user.dir"));
                }

                Path imageResourcePath = currentPath.resolve("src/main/resources/" + userModel.getFullname());
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

    private void removeImage(File imageFile) {
        if (imageFile != null) {
            try {
                Path currentPath = Paths.get("").toAbsolutePath();
                Path imageResourcePath = currentPath.resolve("src/main/resources/" + userModel.getFullname());
                Files.createDirectories(imageResourcePath);

                Path uploadPath = imageResourcePath.resolve(imageFile.getName());
                Files.delete(uploadPath);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error deleting image ! " + e.getMessage());
            }
        }
    }

    private void reuploadImage(File imageFile) {
        removeImage(imageFile);
        uploadImage(imageFile);
    }

    public void addProductToDB(ProductModel product, File imageFile) {
        productDAO.addProduct(product);
        uploadImage(imageFile);
    }

    public void updateProduct(ProductModel product, ProductModel updatedProduct, File imageFile) {
        productDAO.updateProduct(product, updatedProduct);
        reuploadImage(imageFile);
    }

    public void deleteProduct(ProductModel product, File imageFile) {
        productDAO.removeProduct(product);
        removeImage(imageFile);
    }
}
