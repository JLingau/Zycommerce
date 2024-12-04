package views;

import controllers.AdminController;
import models.TransactionModel;
import models.UserModel;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class OrderView extends JPanel {
    private AdminController adminController;
    private JPanel containerPanel;
    private UserModel userModel;

    public OrderView(UserModel userModel) {
        this.userModel = userModel;
        this.adminController = new AdminController(userModel);
        refreshOrder();
    }

    public void initLayout() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(new EmptyBorder(10, 40, 10, 40));

        containerPanel = new JPanel();
        containerPanel.setLayout(new BorderLayout());
        containerPanel.setMaximumSize(new Dimension(getToolkit().getScreenSize().width, 100));
        JLabel textLabel = new JLabel();
        textLabel.setHorizontalAlignment(SwingConstants.LEFT);
        textLabel.setFont(new Font("serif", Font.BOLD, 24));
        JButton addItemButton = new JButton("Add Item");
        addItemButton.setMargin(new Insets(10, 10, 10, 10));
        addItemButton.addActionListener(e -> new AddItem(userModel));
        if (userModel.getPrivilege().equalsIgnoreCase("admin") || userModel.getPrivilege().equalsIgnoreCase("seller")) {
            textLabel.setText("Your client order list : ");
            containerPanel.add(textLabel, BorderLayout.WEST);
            containerPanel.add(Box.createHorizontalGlue(), BorderLayout.CENTER);
            containerPanel.add(addItemButton, BorderLayout.EAST);
            this.add(containerPanel);
            for (TransactionModel transactionModel : adminController.getSellerOrderTransaction()) {
                this.add(new OrderGrid(transactionModel, userModel));
            }
        } else {
            textLabel.setText("Order History : ");
            this.add(textLabel);
            for (TransactionModel transactionModel : adminController.getClientTransactions()) {
                this.add(new OrderGrid(transactionModel, userModel));
            }
        }
    }

    public void refreshOrder() {
        this.revalidate();
        this.repaint();
        this.removeAll();
        initLayout();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Order History List");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        frame.add(new OrderView(new UserModel("tester", "asdsf","admin","asdadsf","admin","adfasdf","asdfadf")));

        frame.setVisible(true);
    }
}

class OrderGrid extends JPanel {
    private JLabel productName, productDescription, productActor, productImage, productQuantity, totalPrice;
    private TransactionModel transactionModel;
    private UserModel userModel;

    public OrderGrid(TransactionModel transactionModel, UserModel userModel) {
        this.transactionModel = transactionModel;
        this.userModel = userModel;
        initLayout();
    }

    public void initComponents() {
        Font heading = new Font("serif", Font.BOLD, 24);
        Font subHeading = new Font("serif", Font.BOLD, 18);
        try {
            ImageIcon image = new ImageIcon(getClass().getResource("/" + transactionModel.getProductSeller() + "/" + transactionModel.getProductImage()));
            Image resizedImage = image.getImage().getScaledInstance(125 , 125, Image.SCALE_SMOOTH);
            productImage = new JLabel(new ImageIcon(resizedImage));
        } catch (Exception e) {
            productImage = new JLabel("Image Not Found");
        }


        productName = new JLabel(transactionModel.getProductName());
        productName.setFont(heading);
        productDescription = new JLabel(transactionModel.getProductDescription());
        productDescription.setFont(subHeading);
        if (userModel.getPrivilege().equals("admin") || userModel.getPrivilege().equalsIgnoreCase("seller")) {
            productActor = new JLabel("Buyer : " + transactionModel.getProductBuyer());
        } else {
            productActor = new JLabel("Seller : " + transactionModel.getProductSeller());
        }
        productActor.setFont(subHeading);
        productQuantity = new JLabel("Quantity : " + transactionModel.getProductQuantity());
        productQuantity.setFont(heading);
        totalPrice = new JLabel(String.valueOf(transactionModel.getProductPrice() * transactionModel.getProductQuantity()));
        totalPrice.setFont(heading);
        totalPrice.setForeground(new Color(42, 173, 199));
    }

    public void initLayout() {
        initComponents();
        this.setLayout(new GridBagLayout());
        this.setBorder(new CompoundBorder(new CompoundBorder(new EmptyBorder(10, 0, 10, 0),
                new LineBorder(Color.BLACK, 2, true)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        this.setMaximumSize(new Dimension((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(), 150));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridheight = 3;
        constraints.insets = new Insets(0, 0, 0, 10);
        constraints.fill = GridBagConstraints.VERTICAL;
        this.add(productImage, constraints);

        constraints.gridx++;
        constraints.gridheight = 1;
        constraints.weightx = 1;
        constraints.anchor = GridBagConstraints.LINE_START;
        this.add(productName, constraints);

        constraints.gridy++;
        this.add(productDescription, constraints);

        constraints.gridy++;
        this.add(productActor, constraints);

        constraints.gridx++;
        constraints.gridy = 0;
        constraints.weightx = 0;
        constraints.weighty = 0.5;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.fill = GridBagConstraints.BOTH;
        this.add(totalPrice, constraints);

        constraints.gridy++;
        this.add(productQuantity, constraints);
    }
}
