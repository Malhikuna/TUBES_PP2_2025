package org.example;

import org.example.view.LoginView;
import org.example.view.SideBarView;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
//            LoginView frame = new LoginView();
            SideBarView frame = new SideBarView();
            frame.setVisible(true);
        });
    }
}