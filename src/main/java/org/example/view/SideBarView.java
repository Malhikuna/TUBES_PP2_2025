package org.example.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SideBarView extends JFrame {
    private JPanel contentPanel;
    private CardLayout cardLayout;
    private DashboardView dashboardView;
    private PeminjamanView peminjamanView;
    private PeminjamanView AnggotaView;
    private PeminjamanView BukuView;

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

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        dashboardView = new DashboardView();
        peminjamanView = new PeminjamanView();
//        anggotaView = new AnggotaView();
//        bukuView = new BukuView();

        contentPanel.add(dashboardView, "Dashboard");
//        contentPanel.add(new bukuView, "Buku");
//        contentPanel.add(new anggotaView, "Anggota");
        contentPanel.add(peminjamanView, "Transaksi");


        btnDash.addActionListener(e -> {
            cardLayout.show(contentPanel, "Dashboard");
            dashboardView.loadDataDashboard();
        });

        btnTransaksi.addActionListener(e -> {
            cardLayout.show(contentPanel, "Transaksi");
            peminjamanView.refreshTable();
        });

        btnBuku.addActionListener(e -> JOptionPane.showMessageDialog(this, "Fitur Master Buku belum dibuat!"));
        btnAnggota.addActionListener(e -> JOptionPane.showMessageDialog(this, "Fitur Master Anggota belum dibuat!"));

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