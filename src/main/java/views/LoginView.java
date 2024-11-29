package views;

import controllers.AuthController;
import models.UserModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class LoginView extends JFrame {
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JLabel welcomeText, usernameLabel, passwordLabel, logoLabel;
    private final JButton loginButton, signUpButton;
    private final JPanel leftPanel, rightPanel, formPanel;
    private final ImageIcon logoPicture;
    private AuthController authController;
    private Color accent = new Color(19, 113, 176);

    // Init JComponent Items
    public LoginView() {
        authController = new AuthController(this);
        usernameField = new JTextField(20);
        usernameField.setMargin(new Insets(5, 3, 5, 3));
        passwordField = new JPasswordField(20);
        passwordField.setMargin(new Insets(5, 3, 5, 3));
        welcomeText = new JLabel("Welcome To Zycommerce !");
        welcomeText.setFont(new Font("Serif", Font.BOLD, 18));
        welcomeText.setForeground(Color.WHITE);
        usernameLabel = new JLabel("Username");
        passwordLabel = new JLabel("Password");
        signUpButton = new JButton("SignUp Here");
        signUpButton.setHorizontalAlignment(SwingConstants.CENTER);
        loginButton = new JButton("Login");
        // Convert imageIcon to image to resize image size
        logoPicture = new ImageIcon(getClass().getResource("/images/logo.png"));
        Image resizedImage = logoPicture.getImage().getScaledInstance(250, 200, Image.SCALE_SMOOTH);
        logoLabel = new JLabel(new ImageIcon(resizedImage));
        leftPanel = new JPanel();
        rightPanel = new JPanel();
        formPanel = new JPanel();
        formPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        initView();

        signUpButton.addActionListener(e -> authController.moveToRegister());
        loginButton.addActionListener(e -> authController.authenticateUser(new UserModel(usernameField.getText(),
                authController.getHashedPassword(String.valueOf(passwordField.getPassword())))));
    }

    private void initView() {
        this.setLayout(new BorderLayout());
        this.setMinimumSize(new Dimension(500, 500));

        leftPanel.setBackground(accent);
        leftPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridy = 0;
        c.weighty = 0.3;
        c.anchor = GridBagConstraints.PAGE_END;
        leftPanel.add(logoLabel, c);

        c.gridy = 1;
        c.weighty = 0.6;
        c.insets = new Insets(10, 0, 0, 0);
        c.anchor = GridBagConstraints.PAGE_START;
        leftPanel.add(welcomeText, c);
        leftPanel.setBorder(new EmptyBorder(0, 10, 0, 10));

        formPanel.setLayout(new GridBagLayout());
        GridBagConstraints c2 = new GridBagConstraints();
        c2.gridx = 0;
        c2.gridy = 0;
        c2.insets = new Insets(0, 10, 10, 0);
        c2.anchor = GridBagConstraints.CENTER;
        formPanel.add(usernameLabel, c2);

        c2.gridx++;
        formPanel.add(usernameField, c2);

        c2.gridx = 0;
        c2.gridy++;
        formPanel.add(passwordLabel, c2);

        c2.gridx++;
        formPanel.add(passwordField, c2);

        c2.gridx = 0;
        c2.gridy++;
        c2.gridwidth = 2;
        c2.insets = new Insets(20, 0, 0, 0);
        c2.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(loginButton, c2);

        c2.gridy++;
        c2.insets = new Insets(10, 0, 0, 0);
        formPanel.add(signUpButton, c2);

        rightPanel.setLayout(new BorderLayout());
        rightPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
        JLabel loginLabel = new JLabel("Login");
        loginLabel.setFont(new Font("Serif", Font.BOLD, 40));
        loginLabel.setForeground(accent);
        loginLabel.setHorizontalAlignment(SwingConstants.CENTER);
        rightPanel.add(loginLabel, BorderLayout.NORTH);
        rightPanel.add(formPanel, BorderLayout.CENTER);
        rightPanel.add(Box.createVerticalGlue(), BorderLayout.SOUTH);

        this.add(leftPanel, BorderLayout.WEST);
        this.add(rightPanel, BorderLayout.CENTER);

        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.pack();
    }

    public void addRegisterListener(ActionListener listener) {
        signUpButton.addActionListener(listener);
    }
}
