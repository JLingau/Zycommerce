package views;

import controllers.DashboardController;
import models.ProductModel;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DashboardView extends JPanel {
    private List<ProductModel> productModel;
    private List<ProductGrid> productGrid;
    private DashboardController dashboardController;

    public DashboardView() {
        dashboardController = new DashboardController();
        productModel = dashboardController.getProductList();
        productGrid = new ArrayList<ProductGrid>();

        for (ProductModel product : productModel) {
            productGrid.add(new ProductGrid(product));
        }

        for (ProductGrid product : productGrid) {
            add(product);
        }

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.add(new DashboardView());
        frame.pack();

        frame.setVisible(true);
    }
}

class ProductGrid extends JPanel {
    private JPanel quantityGrid;
    private JLabel productName, productDescription, productPrice, imageLabel, quantityLabel, sellerLabel;
    private JButton addToCartButton, plusButton, minusButton;
    private ImageIcon logoPicture;
    private ProductModel product;

    public ProductGrid(ProductModel product) {
        this.product = product;
        initComponents();
        initLayout();
        plusButton.addActionListener(e -> incrementQuantity());
        minusButton.addActionListener(e -> decrementQuantity());
    }

    public ProductGrid() {
        initComponents();
        initLayout();
        plusButton.addActionListener(e -> incrementQuantity());
        minusButton.addActionListener(e -> decrementQuantity());
    }

    public void initComponents() {
        Font heading = new Font("serif", Font.BOLD, 28);
        Font subHeading = new Font("serif", Font.BOLD, 20);
        // Inisialisasi seluruh komponen
        logoPicture = new ImageIcon(getClass().getResource("/images/" + product.getImage()));
        Image resizedImage = logoPicture.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        imageLabel = new JLabel(new ImageIcon(resizedImage));
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
        addToCartButton = new JButton("Add To Cart");
        addToCartButton.setMargin(new Insets(10, 10, 10, 10));
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
        add(addToCartButton, gbc);
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

    private void updatePriceTimesQuantity(double quantity) {
        // Update product price based on quantity
        productPrice.setText(String.valueOf(quantity * product.getPrice()));
    }

}
