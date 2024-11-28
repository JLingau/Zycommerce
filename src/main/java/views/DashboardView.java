package views;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class DashboardView extends JPanel {
    private ImageIcon productImage;
    private JPanel itemGrid, quantityGrid;
    private JLabel productName, productDescription, productPrice, imageLabel, quantityLabel;
    private JButton addToCartButton, plusButton, minusButton;
    private ImageIcon logoPicture;
    private JSpinner quantitySpinner;

    public DashboardView() {
        Font heading = new Font("serif", Font.BOLD, 28);
        Font subHeading = new Font("serif", Font.BOLD, 20);
        // Inisialisasi seluruh komponen
        logoPicture = new ImageIcon(getClass().getResource("/images/baju.jpeg"));
        Image resizedImage = logoPicture.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        imageLabel = new JLabel(new ImageIcon(resizedImage));
        imageLabel.setBorder(new LineBorder(Color.BLACK));
        productName = new JLabel("Baju Kemerdekaan");
        productName.setFont(subHeading);
        productDescription = new JLabel("Baju Kemerdekaan Terbaru 2024 HUT RI");
        productPrice = new JLabel("125000");
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
        itemGrid = new JPanel();
        itemGrid.setBorder(BorderFactory.createCompoundBorder(new LineBorder(Color.BLACK, 2, true),
                new EmptyBorder(10, 10, 10, 10)));
        itemGrid.setLayout(new GridBagLayout());

        // Button ActionListener
        plusButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                incrementQuantity();
            }
        });

        minusButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                decrementQuantity();
            }
        });

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        initTemplate();
    }

    private void initTemplate() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.insets = new Insets(0, 0, 0, 10);
        itemGrid.add(imageLabel, gbc);

        gbc.gridx++;
        gbc.gridheight = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        itemGrid.add(productName, gbc);

        gbc.gridy++;
        itemGrid.add(productDescription, gbc);

        gbc.gridx++;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        itemGrid.add(productPrice, gbc);

        gbc.gridy++;
        itemGrid.add(quantityGrid, gbc);

        gbc.gridx++;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 0, 0);
        itemGrid.add(addToCartButton, gbc);

        this.add(itemGrid);
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
        productPrice.setText(String.valueOf(quantity * Integer.parseInt(productPrice.getText())));
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
