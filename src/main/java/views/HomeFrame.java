package views;

import controllers.CartController;
import models.CartModel;
import models.UserModel;

import javax.swing.*;

public class HomeFrame extends JFrame{
    private UserModel userModel;
    private CartController cartController;
    private DashboardView dashboardView;
    private CartView cartView;
    private OrderView orderView;
    private AdminView adminView;
    private AddItem addItem;

    public HomeFrame(UserModel userModel, CartController cartController) {
        super("Zycommerce");
        this.userModel = userModel;
        this.cartController = cartController;
        dashboardView = new DashboardView(cartController, userModel);
        cartView = new CartView(cartController, userModel);
        orderView = new OrderView(userModel);
        adminView = new AdminView(userModel);

        JScrollPane scrollPane = new JScrollPane(dashboardView);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        JScrollPane cartScrollPane = new JScrollPane(cartView);
        cartScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        cartScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        JScrollPane orderViewPane = new JScrollPane(orderView);
        orderViewPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        orderViewPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        JScrollPane adminScrollPane = new JScrollPane(adminView);
        adminScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        adminScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JMenuBar menuBar = new JMenuBar();
        JMenu systemMenu = new JMenu("System");
        JMenuItem logoutMenuItem = new JMenuItem("Logout");

        systemMenu.add(logoutMenuItem);
        menuBar.add(systemMenu);

        logoutMenuItem.addActionListener(e -> {
            this.dispose();
            new Layout();
        });

        JTabbedPane tabbedPane = new JTabbedPane();
        if (userModel.getPrivilege().equalsIgnoreCase("client")) {
            tabbedPane.add("Dashboard", scrollPane);
            tabbedPane.add("Cart", cartScrollPane);
            tabbedPane.add("Order", orderViewPane);
            tabbedPane.addChangeListener(e -> {
                switch (tabbedPane.getSelectedIndex()) {
                    case 0:
                        dashboardView.refreshDashboard();
                        break;
                    case 1:
                        cartView.refreshCartView();
                        break;
                    case 2:
                        orderView.refreshOrder();
                        break;
                }
            });
        } else {
            tabbedPane.add("Dashboard", scrollPane);
            tabbedPane.add("History & Add Product", orderViewPane);
            if (userModel.getPrivilege().equalsIgnoreCase("admin")) {
                tabbedPane.add("Admin", adminScrollPane);
            }
            tabbedPane.addChangeListener(e -> {
                switch (tabbedPane.getSelectedIndex()) {
                    case 0:
                        dashboardView.refreshDashboard();
                        break;
                    case 1:
                        orderView.refreshOrder();
                        break;
                    case 2:
                        adminView.refreshAdmin();
                        break;
                }
            });
        }

        add(tabbedPane);
        setJMenuBar(menuBar);

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
