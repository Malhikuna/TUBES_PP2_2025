package org.example.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SideBarView extends JFrame {
    private JPanel contentPanel;
    private CardLayout cardLayout;
    private DashboardView dashboardView;
    private PeminjamanView peminjamanView;
    private AnggotaView anggotaView;
    private BukuView bukuView;

    public SideBarView() {
        setTitle("Book & Arsip Highlight : Library Information Log");
        setSize(1200, 750);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel sidebar = new JPanel();
        sidebar.setLayout(new GridLayout(10, 1, 0, 5));
        sidebar.setPreferredSize(new Dimension(220, 0));
        sidebar.setBackground(new Color(44, 62, 80));
        sidebar.setBorder(new EmptyBorder(20, 10, 20, 10));

        JLabel lblTitle = new JLabel("PERPUSTAKAAN", SwingConstants.CENTER);
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        sidebar.add(lblTitle);
        sidebar.add(Box.createRigidArea(new Dimension(0, 20)));

        JButton btnDash = createMenuButton("Dashboard");
        JButton btnBuku = createMenuButton("Master Buku");
        JButton btnAnggota = createMenuButton("Master Anggota");
        JButton btnTransaksi = createMenuButton("Transaksi");

        sidebar.add(btnDash);
        sidebar.add(btnBuku);
        sidebar.add(btnAnggota);
        sidebar.add(btnTransaksi);

        sidebar.add(Box.createVerticalGlue());

        JButton btnLogout = new JButton("Logout");
        btnLogout.setMaximumSize(new Dimension(180, 40));
        btnLogout.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLogout.setBackground(new Color(192, 57, 43));
        btnLogout.setForeground(Color.WHITE);

        btnLogout.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Yakin mau keluar?", "Logout", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                this.dispose();
                new LoginView().setVisible(true);
            }
        });

        sidebar.add(btnLogout);
        sidebar.add(Box.createRigidArea(new Dimension(0, 20)));

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        dashboardView = new DashboardView();
        peminjamanView = new PeminjamanView();
        anggotaView = new AnggotaView();
        bukuView = new BukuView();

        contentPanel.add(dashboardView, "Dashboard");
        contentPanel.add(bukuView, "Buku");
        contentPanel.add(anggotaView, "Anggota");
        contentPanel.add(peminjamanView, "Transaksi");

        btnDash.addActionListener(e -> {
            cardLayout.show(contentPanel, "Dashboard");
            dashboardView.loadTopBooks();
        });

        btnTransaksi.addActionListener(e -> {
            cardLayout.show(contentPanel, "Transaksi");
            peminjamanView.refreshTable();
        });

        btnAnggota.addActionListener(e -> {
            cardLayout.show(contentPanel, "Anggota");
        });

        btnBuku.addActionListener(e -> {
            cardLayout.show(contentPanel, "Buku");
        });

        add(sidebar, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);
    }

    private JButton createMenuButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(52, 73, 94));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
}