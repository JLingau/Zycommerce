package views;

import controllers.AddItemController;
import controllers.CartController;
import controllers.DashboardController;
import models.CartModel;
import models.ProductModel;
import models.UserModel;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DashboardView extends JPanel {
    private List<ProductModel> productModel;
    private List<ProductGrid> productGrid;
    private DashboardController dashboardController;
    private CartController cartController;
    private UserModel userModel;

    public DashboardView(CartController cartController, UserModel userModel) {
        this.cartController = cartController;
        this.userModel = userModel;
        refreshDashboard();
    }

    private void initLayout() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        dashboardController = new DashboardController(userModel);
        productModel = dashboardController.getProductList();
        productGrid = new ArrayList<ProductGrid>();

        try {
            for (ProductModel product : productModel) {
                productGrid.add(new ProductGrid(product, this, cartController, userModel));
            }

            for (ProductGrid product : productGrid) {
                add(product);
            }
        } catch (Exception e) {
            System.out.println("Product Model is Empty !");
        }

    }

    public void refreshDashboard() {
        this.revalidate();
        this.repaint();
        this.removeAll();
        this.initLayout();
    }
}

class ProductGrid extends JPanel {
    private JPanel quantityGrid;
    private JLabel productName, productDescription, productPrice, imageLabel, quantityLabel, sellerLabel;
    private JButton actionButton, plusButton, minusButton, deleteButton;
    private ImageIcon logoPicture;
    private ProductModel product;
    private CartController cartController;
    private UserModel userModel;
    private DashboardView dashboardView;

    public ProductGrid(ProductModel product, DashboardView dashboardView, CartController cartController, UserModel userModel) {
        this.product = product;
        this.cartController = cartController;
        this.userModel = userModel;
        this.dashboardView = dashboardView;
        initComponents();
        initLayout();
        plusButton.addActionListener(e -> incrementQuantity());
        minusButton.addActionListener(e -> decrementQuantity());
        if (userModel.getPrivilege().equalsIgnoreCase("client")) {
            actionButton.addActionListener(e -> {
                cartController.addItem(product, Integer.parseInt(quantityLabel.getText()));
                JOptionPane.showMessageDialog(null, "Item added to the cart");
            });
        } else {
            // Edit Item Action Listener
            actionButton.addActionListener(e -> {
                new AddItem(userModel, product);
                dashboardView.refreshDashboard();
            });

            deleteButton.addActionListener(e -> {
                int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this item?");
                if (option == JOptionPane.YES_OPTION) {
                    AddItemController addItemController = new AddItemController(userModel);
                    addItemController.deleteProduct(product, new File("/" + userModel.getFullname() + "/" + product.getImage()));
                    dashboardView.refreshDashboard();
                }
            });
        }

    }

    public void initComponents() {
        Font heading = new Font("serif", Font.BOLD, 28);
        Font subHeading = new Font("serif", Font.BOLD, 20);
        // Inisialisasi seluruh komponen
        try {
            logoPicture = new ImageIcon(getClass().getResource("/" + product.getSeller() + "/" + product.getImage()));
            Image resizedImage = logoPicture.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            imageLabel = new JLabel(new ImageIcon(resizedImage));
        } catch (Exception e) {
            imageLabel = new JLabel("Uploading Image");
        }

        imageLabel.setBorder(new LineBorder(Color.BLACK));
        productName = new JLabel(product.getName());
        productName.setFont(subHeading);
        sellerLabel = new JLabel("Seller : " + product.getSeller());
        productName.setFont(subHeading);
        productDescription = new JLabel(product.getDescription());
        productPrice = new JLabel(String.valueOf(product.getPrice()));
        productPrice.setForeground(new Color(66, 135, 245));
        productPrice.setFont(heading);
        quantityLabel = new JLabel("1");
        quantityLabel.setHorizontalAlignment(SwingConstants.CENTER);
        ImageIcon binButton = new ImageIcon(getClass().getResource("/images/system_essential/bin.png"));
        Image resizedBinButton = binButton.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        deleteButton = new JButton(new ImageIcon(resizedBinButton));
        deleteButton.setMargin(new Insets(5, 5, 5, 5));
        if (userModel.getPrivilege().equalsIgnoreCase("client")) {
            actionButton = new JButton("Add To Cart");
        } else {
            actionButton = new JButton("Edit Items");
        }
        actionButton.setMargin(new Insets(10, 10, 10, 10));
        plusButton = new JButton("+");
        plusButton.setMargin(new Insets(5, 5, 5, 5));
        minusButton = new JButton("-");
        minusButton.setMargin(new Insets(5, 5, 5, 5));
        quantityGrid = new JPanel();
        quantityGrid.setLayout(new GridLayout(0, 3, 10, 0));
        quantityGrid.add(minusButton);
        quantityGrid.add(quantityLabel);
        quantityGrid.add(plusButton);
    }

    public void initLayout() {
        setBorder(new CompoundBorder(new CompoundBorder(new EmptyBorder(10, 0, 10, 0),
                new LineBorder(Color.BLACK, 2, true)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        setLayout(new GridBagLayout());
        setMaximumSize(new Dimension((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(), 150));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 3;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.insets = new Insets(0, 0, 0, 10);
        add(imageLabel, gbc);

        gbc.gridx++;
        gbc.gridheight = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(productName, gbc);

        gbc.gridy++;
        add(productDescription, gbc);

        gbc.gridy++;
        add(sellerLabel, gbc);

        if (userModel.getPrivilege().equalsIgnoreCase("client")) {
            gbc.gridx++;
            gbc.gridy = 0;
            gbc.weightx = 0;
            gbc.weighty = 0.5;
            gbc.fill = GridBagConstraints.NONE;
            gbc.anchor = GridBagConstraints.CENTER;
            add(productPrice, gbc);

            gbc.gridy++;
            add(quantityGrid, gbc);

            gbc.gridx++;
            gbc.gridy = 0;
            gbc.gridheight = 3;
            gbc.fill = GridBagConstraints.VERTICAL;
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.insets = new Insets(0, 0, 0, 0);
            add(actionButton, gbc);
        } else {
            gbc.gridx++;
            gbc.gridy = 0;
            gbc.gridwidth = 2;
            gbc.weightx = 0;
            gbc.weighty = 0.5;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.anchor = GridBagConstraints.CENTER;
            add(productPrice, gbc);

            gbc.gridy++;
            gbc.gridwidth = 1;
            add(actionButton, gbc);

            gbc.gridx++;
            add(deleteButton, gbc);
        }
    }

    private void incrementQuantity() {
        int quantity = Integer.parseInt(quantityLabel.getText());
        quantity++;
        quantityLabel.setText(String.valueOf(quantity));
        updatePriceTimesQuantity(quantity);
    }

    private void decrementQuantity() {
        int quantity = Integer.parseInt(quantityLabel.getText());
        if (quantity <= 1) {
            quantity = 1;
        } else {
            quantity--;
        }
        quantityLabel.setText(String.valueOf(quantity));
        updatePriceTimesQuantity(quantity);
    }

    private void updatePriceTimesQuantity(int quantity) {
        // Update product price based on quantity
        productPrice.setText(String.valueOf(quantity * product.getPrice()));
    }

}
