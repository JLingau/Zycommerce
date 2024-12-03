package views;

import controllers.AuthController;
import models.UserModel;

import javax.swing.*;

public class Layout {
    private UserModel userModel;

    public Layout() {
        new LoginView();
    }
}
