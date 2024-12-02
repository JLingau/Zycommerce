package views;

import controllers.AuthController;
import models.UserModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class RegisterView extends JFrame {
    private final JPanel formPanel, containerPanel;
    private final JLabel usernameLabel, passwordLabel, fullnameLabel, emailLabel, phoneLabel, privilegeLabel, logoLabel;
    private final JRadioButton clientButton, sellerButton;
    private final JButton registerButton, loginButton;
    private final JTextField usernameTextField, fullnameTextField, emailTextField, phoneTextField;
    private final JPasswordField passwordTextField;
    private final ButtonGroup buttonGroup;
    private final ImageIcon logoPicture;
    private AuthController authController;

    public RegisterView() {
        Color primaryFont = Color.WHITE;
        Insets fieldInset = new Insets(5, 3, 5, 3);

        // Initialize all properties and set their display UI
        formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(34, 179, 181));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        containerPanel = new JPanel(new BorderLayout());
        containerPanel.setBackground(new Color(34, 179, 181));
        usernameLabel = new JLabel("Username");
        passwordLabel = new JLabel("Password");
        fullnameLabel = new JLabel("Full Name");
        emailLabel = new JLabel("Email");
        phoneLabel = new JLabel("Phone");
        privilegeLabel = new JLabel("Privilege");
        logoPicture = new ImageIcon(getClass().getResource("/images/logo.png"));
        Image resizedImage = logoPicture.getImage().getScaledInstance(150, 100, Image.SCALE_SMOOTH);
        logoLabel = new JLabel(new ImageIcon(resizedImage));
        logoLabel.setBackground(new Color(34, 179, 181));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        usernameTextField = new JTextField(25);
        usernameTextField.setMargin(fieldInset);
        fullnameTextField = new JTextField(25);
        fullnameTextField.setMargin(fieldInset);
        emailTextField = new JTextField(25);
        emailTextField.setMargin(fieldInset);
        phoneTextField = new JTextField(25);
        phoneTextField.setMargin(fieldInset);
        passwordTextField = new JPasswordField(25);
        passwordTextField.setMargin(fieldInset);
        registerButton = new JButton("Register");
        loginButton = new JButton("Login");
        clientButton = new JRadioButton("Client");
        clientButton.setBackground(new Color(34, 179, 181));
        sellerButton = new JRadioButton("Seller");
        sellerButton.setBackground(new Color(34, 179, 181));
        buttonGroup = new ButtonGroup();
        buttonGroup.add(clientButton);
        buttonGroup.add(sellerButton);
        authController = new AuthController(this);

        registerButton.addActionListener(e -> authController.validateAndInsertUser(new UserModel(
                usernameTextField.getText().trim(), authController.getHashedPassword(String.valueOf(passwordTextField.getPassword()).trim()),
                "client", "active", fullnameTextField.getText().trim(), emailTextField.getText().trim(),
                phoneTextField.getText().trim())));

        loginButton.addActionListener(e -> authController.moveToLogin());

        initView();
    }

    private void initView() {
        this.setBackground(new Color(34, 179, 181));
        this.getRootPane().setBorder(new EmptyBorder(20, 20, 20, 20));

        Insets leftInset = new Insets(0, 0, 10, 15);
        Insets rightInset = new Insets(0, 15, 10, 0);

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.CENTER;
        c.insets = leftInset;
        formPanel.add(usernameLabel, c);

        c.gridx++;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = rightInset;
        formPanel.add(usernameTextField, c);

        c.gridx = 0;
        c.gridy++;
        c.gridwidth = 1;
        c.insets = leftInset;
        formPanel.add(passwordLabel, c);

        c.gridx++;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = rightInset;
        formPanel.add(passwordTextField, c);

        c.gridx = 0;
        c.gridy++;
        c.gridwidth = 1;
        c.insets = leftInset;
        formPanel.add(fullnameLabel, c);

        c.gridx++;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = rightInset;
        formPanel.add(fullnameTextField, c);

        c.gridx = 0;
        c.gridy++;
        c.gridwidth = 1;
        c.insets = leftInset;
        formPanel.add(emailLabel, c);

        c.gridx++;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = rightInset;
        formPanel.add(emailTextField, c);

        c.gridx = 0;
        c.gridy++;
        c.gridwidth = 1;
        c.insets = leftInset;
        formPanel.add(phoneLabel, c);

        c.gridx++;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = rightInset;
        formPanel.add(phoneTextField, c);

        c.gridx = 0;
        c.gridy++;
        c.gridwidth = 1;
        c.insets = leftInset;
        formPanel.add(privilegeLabel, c);

        c.gridx++;
        c.insets = new Insets(0, 10, 10, 0);
        formPanel.add(clientButton, c);

        c.gridx++;
        formPanel.add(sellerButton, c);

        c.gridx = 0;
        c.gridy++;
        c.gridwidth = 3;
        c.insets = new Insets(20, 0, 10, 0);
        formPanel.add(registerButton, c);

        c.gridy++;
        c.insets = new Insets(0, 0, 10, 0);
        formPanel.add(loginButton, c);

        containerPanel.add(logoLabel, BorderLayout.NORTH);
        containerPanel.add(formPanel, BorderLayout.CENTER);
        containerPanel.add(Box.createHorizontalGlue(), BorderLayout.SOUTH);

        this.add(containerPanel);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocation(540, 270);
        this.setVisible(true);
        this.pack();
    }

    public static void main(String[] args) {
        new RegisterView();
    }
}
