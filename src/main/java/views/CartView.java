package views;

import controllers.CartController;
import models.CartModel;
import models.ProductModel;
import models.TransactionDAO;
import models.UserModel;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.List;

public class CartView extends JPanel {
    private CartController cartController;
    private UserModel userModel;
    private JPanel checkoutGrid, topPanel;
    private JButton checkoutButton;
    private JLabel emptyCartLabel;

    public CartView(CartController cartController, UserModel userModel) {
        this.cartController = cartController;
        this.userModel = userModel;
        if (cartController.getCartItems().isEmpty()) {
            emptyCartLabel = new JLabel("Cart is empty");
            emptyCartLabel.setHorizontalAlignment(SwingConstants.CENTER);
            emptyCartLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
            add(emptyCartLabel);
        } else {
            refreshCartView();
        }
    }

    public void initLayout(CartController cartController) {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(20, 20, 20, 20));

        checkoutButton = new JButton("Checkout");
        checkoutButton.setFont(new Font("serif", Font.BOLD, 28));
        checkoutButton.setMargin(new Insets(10, 20, 10, 20));

        checkoutButton.addActionListener(e -> {
            cartController.checkoutProduct(userModel);
            refreshCartView();
        });

        if (cartController.getCartItems().isEmpty()) {
            checkoutButton.setEnabled(false);
        } else {
            checkoutButton.setEnabled(true);
        }

        checkoutGrid = new JPanel();
        checkoutGrid.setLayout(new BoxLayout(checkoutGrid, BoxLayout.LINE_AXIS));
        checkoutGrid.add(Box.createHorizontalGlue());
        checkoutGrid.add(checkoutButton);

        topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(0, 1));

        for (CartModel cartItem : cartController.getCartItems()) {
            topPanel.add(new cartProductGrid(cartController, cartItem, this, userModel));
        }

        add(topPanel, BorderLayout.NORTH);
        add(checkoutGrid, BorderLayout.SOUTH);
    }

    public void refreshCartView() {
        this.revalidate();
        this.repaint();
        this.removeAll();
        initLayout(cartController);
    }
}

class cartProductGrid extends JPanel {
    private JLabel productName, productDescription, productPrice, productQuantity, productImage, productSeller;
    private JButton plusButton, minusButton, deleteButton;
    private JPanel quantityGrid;
    private CartController cartController;
    private CartModel cartModel;
    private CartView cartView;
    private UserModel userModel;

    public cartProductGrid(CartController cartController, CartModel cartModel, CartView cartView, UserModel userModel) {
        this.cartController = cartController;
        this.cartModel = cartModel;
        initComponent();
        initLayout();
        plusButton.addActionListener(e -> {incrementQuantity();});
        minusButton.addActionListener(e -> {decrementQuantity();});
        deleteButton.addActionListener(e -> {
            cartController.removeItem(cartModel.getProduct());
            this.cartView = new CartView(cartController, userModel);
            cartView.refreshCartView();
        });
    }

    public void initComponent() {
        Font Heading = new Font("serif", Font.BOLD, 24);
        Font Subheading = new Font("serif", Font.BOLD, 16);
        try {
            ImageIcon image = new ImageIcon(getClass().getResource("/" + cartModel.getProduct().getSeller() + "/" + cartModel.getProduct().getImage()));
            Image resizedImage = image.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            productImage = new JLabel(new ImageIcon(resizedImage));
        } catch (Exception e) {
            productImage = new JLabel("Image Not Found");
        }

        productName = new JLabel(cartModel.getProduct().getName());
        productName.setFont(Heading);
        productDescription = new JLabel(cartModel.getProduct().getDescription());
        productDescription.setFont(Subheading);
        productQuantity = new JLabel(String.valueOf(cartModel.getQuantity()));
        productQuantity.setHorizontalAlignment(SwingConstants.CENTER);
        productQuantity.setFont(Subheading);
        productPrice = new JLabel(String.valueOf(cartModel.getProduct().getPrice() * Integer.parseInt(productQuantity.getText())));
        productPrice.setFont(new Font("serif", Font.BOLD, 28));
        productPrice.setForeground(new Color(66, 135, 245));
        productSeller = new JLabel(cartModel.getProduct().getSeller());
        productSeller.setFont(Subheading);

        plusButton = new JButton("+");
        plusButton.setSize(25, 25);
        plusButton.setMargin(new Insets(5, 5, 5, 5));
        minusButton = new JButton("-");
        minusButton.setSize(25, 25);
        minusButton.setMargin(new Insets(5, 5, 5, 5));
        ImageIcon binButton = new ImageIcon(getClass().getResource("/images/system_essential/bin.png"));
        Image resizedBinButton = binButton.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        deleteButton = new JButton(new ImageIcon(resizedBinButton));
        deleteButton.setSize(25, 25);
        deleteButton.setMargin(new Insets(5, 5, 5, 5));

        quantityGrid = new JPanel();
        quantityGrid.setLayout(new GridLayout(0, 4, 10, 0));
        quantityGrid.add(minusButton);
        quantityGrid.add(productQuantity);
        quantityGrid.add(plusButton);
        quantityGrid.add(deleteButton);
    }

    public void initLayout() {
        setBorder(new CompoundBorder(new CompoundBorder(new EmptyBorder(10, 0, 10, 0),
                new LineBorder(Color.BLACK, 2, true)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        setMaximumSize(new Dimension((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(), 150));
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridheight = 3;
        constraints.fill = GridBagConstraints.VERTICAL;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(0, 0, 0, 10);
        add(productImage, constraints);

        constraints.gridx = 1;
        constraints.gridheight = 1;
        constraints.weightx = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(0, 0, 0, 0);
        add(productName, constraints);

        constraints.gridy++;
        add(productDescription, constraints);

        constraints.gridy++;
        add(productSeller, constraints);

        constraints.gridx++;
        constraints.gridy = 0;
        constraints.weightx = 0;
        constraints.weighty = 0.5;
        constraints.fill = GridBagConstraints.VERTICAL;
        constraints.anchor = GridBagConstraints.LINE_END;
        add(productPrice, constraints);

        constraints.gridy++;
        add(quantityGrid, constraints);
    }

    public void incrementQuantity() {
        int quantity = Integer.parseInt(productQuantity.getText());
        quantity++;
        productQuantity.setText(String.valueOf(quantity));
        updatePriceTimesQuantity(quantity);
    }

    public void decrementQuantity() {
        int quantity = Integer.parseInt(productQuantity.getText());
        if (quantity <= 1) {
            quantity = 1;
        } else {
            quantity--;
        }
        productQuantity.setText(String.valueOf(quantity));
        updatePriceTimesQuantity(quantity);
    }

    public void updatePriceTimesQuantity(int quantity) {
        productPrice.setText(String.valueOf(quantity * cartModel.getProduct().getPrice()));
    }
}
