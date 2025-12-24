package org.example.view;

import javax.swing.*;
import java.awt.*;

public class SideBarView extends JFrame {
    private JPanel contentPanel;
    private CardLayout cardLayout;

    public SideBarView() {
        setTitle("BAHLIL - Library System");
        setSize(1000, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(200, 0));
        sidebar.setBackground(new Color(44, 62, 80));

        String[] menus = {"Dashboard", "Master Buku", "Master Anggota", "Transaksi"};
        for (String menu : menus) {
            JButton btn = new JButton(menu);
            btn.setMaximumSize(new Dimension(200, 40));
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            sidebar.add(btn);
            sidebar.add(Box.createRigidArea(new Dimension(0, 5)));

            btn.addActionListener(e -> cardLayout.show(contentPanel, menu));
        }

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.add(new DashboardView(), "Dashboard");
//        contentPanel.add(new BukuView(), "Master Buku");
//        contentPanel.add(new AnggotaView(), "Master Anggota");
        add(sidebar, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);
    }
}
