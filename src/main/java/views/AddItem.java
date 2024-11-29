package views;

import controllers.AddItemController;
import models.ProductModel;
import utils.NumberInputField;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class AddItem extends JFrame {
    private JLabel logoLabel, productNameLabel, productDescriptionLabel, productPriceLabel, productImageLabel, imageLabel;
    private JTextField productNameTextField;
    private NumberInputField productPriceField;
    private JTextArea productDescriptionTextArea;
    private File imageFile;
    private JFileChooser fileChooser;
    private JButton addImageButton, submitButton;
    private final JPanel formPanel, containerPanel;
    private AddItemController addItemController = new AddItemController();

    public AddItem() {
        ImageIcon image = new ImageIcon(getClass().getResource("/images/logo.png"));
        Image resizedImage = image.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        Insets fieldInset = new Insets(5, 5, 5, 5);
        Insets buttonInset = new Insets(10, 10, 10, 10);

        logoLabel = new JLabel(new ImageIcon(resizedImage));
        logoLabel.setBorder(new EmptyBorder(0, 0, 20, 0));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        imageLabel = new JLabel("No Image Selected");
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        productNameLabel = new JLabel("Product Name");
        productDescriptionLabel = new JLabel("Product Description");
        productPriceLabel = new JLabel("Product Price");
        productImageLabel = new JLabel("Image File");
        productNameTextField = new JTextField(25);
        productNameTextField.setMargin(fieldInset);
        productPriceField = new NumberInputField(25);
        productPriceField.setMargin(fieldInset);
        productDescriptionTextArea = new JTextArea(5, 25);
        productDescriptionTextArea.setLineWrap(true);
        productDescriptionTextArea.setMargin(fieldInset);
        addImageButton = new JButton("Choose Image");
        addImageButton.setMargin(buttonInset);
        submitButton = new JButton("Add Item");
        submitButton.setMargin(buttonInset);
        containerPanel = new JPanel();
        containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.Y_AXIS));
        containerPanel.setBackground(new Color(23, 183, 189));
        formPanel = new JPanel();
        formPanel.setBackground(new Color(23, 183, 189));
        formPanel.setLayout(new GridBagLayout());

        addImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                uploadImage();
            }
        });

        submitButton.addActionListener(e -> {
            addItemController.addProductToDB(new ProductModel(productNameTextField.getText(), "admin",
                    productDescriptionTextArea.getText(), Double.parseDouble(productPriceField.getText()),
                    imageFile.getName()), imageFile);
        });

        initView();
    }

    private void initView() {
        getContentPane().removeAll();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(0 ,0 ,10 , 10);
        formPanel.add(productNameLabel, gbc);

        gbc.gridy++;
        formPanel.add(productDescriptionLabel, gbc);

        gbc.gridy++;
        formPanel.add(productPriceLabel, gbc);

        gbc.gridy++;
        formPanel.add(productImageLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.insets = new Insets(0 ,0 ,10 , 0);
        formPanel.add(productNameTextField, gbc);

        gbc.gridy++;
        formPanel.add(productDescriptionTextArea, gbc);

        gbc.gridy++;
        formPanel.add(productPriceField, gbc);

        gbc.gridy++;
        formPanel.add(addImageButton, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(imageLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 0, 0, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(submitButton, gbc);

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setBackground(new Color(23, 183, 189));
        getRootPane().setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        containerPanel.add(logoLabel);
        containerPanel.add(formPanel);
        add(containerPanel);
        setVisible(true);
        // Disable button if all field is not filled
        disableButton();
        pack();
    }

    private void disableButton() {
        if (productNameTextField.getText().isEmpty() || productDescriptionTextArea.getText().isEmpty()
                || productPriceField.getText().isEmpty() || imageFile == null) {
            submitButton.setEnabled(false);
        } else {
            submitButton.setEnabled(true);
        }
    }

    private void uploadImage() {
        fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Product Image");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Image files", "jpg", "jpeg", "png"));

        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            imageFile = fileChooser.getSelectedFile();
            displayImage(imageFile);
            initView();
        }
    }

    private void displayImage(File imageFile) {
        try {
            BufferedImage image = ImageIO.read(imageFile);
            ImageIcon imageIcon = new ImageIcon(image.getScaledInstance(200, 200, Image.SCALE_SMOOTH));
            imageLabel.setIcon(imageIcon);
            imageLabel.setText("");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(AddItem.this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new AddItem();
    }
}
