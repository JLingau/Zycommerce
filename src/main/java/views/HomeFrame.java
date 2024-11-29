package views;

import models.UserModel;

import javax.swing.*;

public class HomeFrame extends JFrame{
    private UserModel userModel;

    public HomeFrame(UserModel userModel) {
        this();
        this.userModel = userModel;
        System.out.println(userModel.getUsername());
        System.out.println(userModel.getPassword());
    }

    public HomeFrame() {
        super("Zycommerce");
        JScrollPane scrollPane = new JScrollPane(new DashboardView());
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane);

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
