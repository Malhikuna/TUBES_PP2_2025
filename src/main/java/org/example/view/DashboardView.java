package org.example.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class DashboardView extends JPanel {
    public DashboardView() {
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel pnlStats = new JPanel(new GridLayout(1, 3, 20, 0));
        pnlStats.add(createStatBox("Total Koleksi Buku", "Total", new Color(52, 152, 219)));
        pnlStats.add(createStatBox("Total Anggota Aktif", "45", new Color(46, 204, 113)));
        pnlStats.add(createStatBox("Total Buku Dipinjam", "12", new Color(231, 76, 60)));

        JPanel pnlHighlight = new JPanel(new BorderLayout(0, 10));
        JLabel lblTitle = new JLabel("Top 5 Buku Terpopuler");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));

        String[] columns = {"ID Buku", "Judul", "Kategori", "Total Dipinjam"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);

        model.addRow(new Object[]{"B001", "Bahlil Assassination", "Politik", "25"});

        pnlHighlight.add(lblTitle, BorderLayout.NORTH);
        pnlHighlight.add(new JScrollPane(table), BorderLayout.CENTER);

        add(pnlStats, BorderLayout.NORTH);
        add(pnlHighlight, BorderLayout.CENTER);
    }

    private JPanel createStatBox(String title, String value, Color color) {
        JPanel box = new JPanel(new GridLayout(2, 1));
        box.setBackground(color);
        JLabel lblTitle = new JLabel(title, SwingConstants.CENTER);
        lblTitle.setForeground(Color.WHITE);
        JLabel lblValue = new JLabel(value, SwingConstants.CENTER);
        lblValue.setFont(new Font("Arial", Font.BOLD, 24));
        lblValue.setForeground(Color.WHITE);
        box.add(lblTitle);
        box.add(lblValue);
        return box;
    }
}
