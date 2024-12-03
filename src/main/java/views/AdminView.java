package views;

import controllers.AdminController;
import controllers.AuthController;
import models.UserModel;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.List;

public class AdminView extends JPanel {
    private UserModel user;
    private AdminController adminController;

    public AdminView(UserModel user) {
        this.user = user;
        this.adminController = new AdminController(user);
    }

    private void initLayout() {
        this.setBorder(new EmptyBorder(10, 10, 10, 10));
        JLabel headingText = new JLabel("Pending Seller Invitation : ");
        headingText.setFont(new Font("serif", Font.BOLD, 24));
        List<UserModel> pendingList = adminController.getPendingUsers();
        if (pendingList.isEmpty()) {
            this.setLayout(new BorderLayout());
            JLabel emptyText = new JLabel("No pending seller !");
            emptyText.setFont(new Font("serif", Font.BOLD, 24));
            emptyText.setForeground(new Color(47, 213, 235));
            this.add(headingText, BorderLayout.NORTH);
            this.add(emptyText, BorderLayout.CENTER);
        } else {
            this.add(headingText);
            for (UserModel user : pendingList) {
                this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
                this.add(new AdminGrid(user, adminController, this));
            }
        }
    }

    public void refreshAdmin() {
        repaint();
        revalidate();
        removeAll();
        initLayout();
    }
}

class AdminGrid extends JPanel {
    private JLabel name, email, phone, status;
    private JButton actionButton;
    private UserModel userModel;
    private AdminController adminController;
    private AdminView adminView;

    public AdminGrid(UserModel userModel, AdminController adminController, AdminView adminView) {
        this.userModel = userModel;
        this.adminController = adminController;
        this.adminView = adminView;

        initLayout();

        actionButton.addActionListener(e -> {
            int option = JOptionPane.showConfirmDialog(null , "Activate the seller account ?");
            if (option == JOptionPane.YES_OPTION) {
                adminController.updateSellerStatus(userModel);
                adminView.refreshAdmin();
            }
        });
    }

    private void initComponents() {
        Font heading = new Font("serif", Font.BOLD, 20);
        ImageIcon checkImage = new ImageIcon(getClass().getResource("/images/system_essential/check.png"));
        Image resizedImage = checkImage.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);

        name = new JLabel(userModel.getUsername());
        name.setFont(heading);
        email = new JLabel(userModel.getEmail());
        email.setFont(heading);
        phone = new JLabel("0" + userModel.getPhone());
        phone.setFont(heading);
        status = new JLabel(userModel.getStatus());
        status.setFont(heading);
        actionButton = new JButton(new ImageIcon(resizedImage));
        actionButton.setSize(new Dimension(60, 60));
        actionButton.setMargin(new Insets(5, 5, 5, 5));
    }

    private void initLayout() {
        initComponents();
        this.setLayout(new GridLayout(0, 5, 20, 10));
        this.setBorder(new CompoundBorder(new CompoundBorder(new EmptyBorder(10, 0, 10, 0),
                new LineBorder(new Color(47, 213, 235), 2, true)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        this.setMaximumSize(new Dimension(getToolkit().getScreenSize().width, 100));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        this.add(name, gbc);

        gbc.gridx++;
        this.add(phone, gbc);

        gbc.gridx++;
        this.add(email, gbc);

        gbc.gridx++;
        this.add(status, gbc);

        gbc.gridx++;
        this.add(actionButton, gbc);
    }
}
