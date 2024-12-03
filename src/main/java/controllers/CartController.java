package controllers;

import models.*;

import javax.swing.*;
import java.util.*;

public class CartController {
    private List<CartModel> cartItems = new ArrayList<>();
    private TransactionDAO transactionDAO = new TransactionDAO();

    public void addItem(ProductModel product, int quantity) {
        for (CartModel cartItem : cartItems) {
            if (cartItem.getProduct().getId() == product.getId()) {
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
                return;
            }
        }

        cartItems.add(new CartModel(product, quantity));
    }

    public void removeItem(ProductModel product) {
        cartItems.removeIf(cartItem -> cartItem.getProduct().getId() == product.getId());
    }

    public int getTotalPrice() {
        return cartItems.stream()
                .mapToInt(cartItems -> (int) (cartItems.getProduct().getPrice() * cartItems.getQuantity()))
                .sum();
    }

    public List<CartModel> getCartItems() {
        return cartItems;
    }

    public void checkoutProduct(UserModel userModel) {
        for (CartModel cartItem : cartItems) {
            ProductModel product = cartItem.getProduct();
            transactionDAO.insertTransaction(
                    new TransactionModel(product.getName(), product.getDescription(), product.getPrice(),
                            cartItem.getQuantity(), product.getImage(), userModel.getFullname(), product.getSeller())
            );
        }
        JOptionPane.showMessageDialog(null, "Successfully checked out product");
        cartItems.clear();
    }
}
