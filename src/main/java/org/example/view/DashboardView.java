package org.example.view;

import org.example.KoneksiDB;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class DashboardView extends JPanel {
    private JLabel lblTotalBuku, lblTotalAnggota, lblTotalPinjam;
    private JTable table;
    private DefaultTableModel tableModel;

    public DashboardView() {
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        lblTotalBuku = new JLabel("0", SwingConstants.CENTER);
        lblTotalAnggota = new JLabel("0", SwingConstants.CENTER);
        lblTotalPinjam = new JLabel("0", SwingConstants.CENTER);

        JPanel pnlStats = new JPanel(new GridLayout(1, 3, 20, 0));
        pnlStats.add(createStatBox("Total Koleksi Buku", lblTotalBuku, new Color(52, 152, 219)));
        pnlStats.add(createStatBox("Total Anggota Aktif", lblTotalAnggota, new Color(46, 204, 113)));
        pnlStats.add(createStatBox("Total Buku Dipinjam", lblTotalPinjam, new Color(231, 76, 60)));

        JPanel pnlHighlight = new JPanel(new BorderLayout(0, 10));
        JLabel lblTitle = new JLabel("Top 5 Buku Terpopuler");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));

        String[] columns = {"ID Buku", "Judul", "Kategori", "Total Dipinjam"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);

        pnlHighlight.add(lblTitle, BorderLayout.NORTH);
        pnlHighlight.add(new JScrollPane(table), BorderLayout.CENTER);

        add(pnlStats, BorderLayout.NORTH);
        add(pnlHighlight, BorderLayout.CENTER);

        ambilDataDariDatabase();
    }

    private void ambilDataDariDatabase() {
        try (Connection conn = KoneksiDB.configDB()) {

            ResultSet rsBuku = conn.createStatement().executeQuery("SELECT SUM(stok) FROM buku");
            if (rsBuku.next()) lblTotalBuku.setText(rsBuku.getString(1));

            ResultSet rsAnggota = conn.createStatement().executeQuery("SELECT COUNT(*) FROM anggota WHERE status_aktif = 'Aktif'");
            if (rsAnggota.next()) lblTotalAnggota.setText(rsAnggota.getString(1));

            ResultSet rsPinjam = conn.createStatement().executeQuery("SELECT COUNT(*) FROM peminjaman WHERE status = 'Dipinjam'");
            if (rsPinjam.next()) lblTotalPinjam.setText(rsPinjam.getString(1));

            String sqlTop = "SELECT b.id_buku, b.judul, b.kategori, COUNT(p.id_log) as total " +
                    "FROM buku b " +
                    "LEFT JOIN peminjaman p ON b.id_buku = p.id_buku " +
                    "GROUP BY b.id_buku, b.judul, b.kategori " +
                    "ORDER BY total DESC LIMIT 5";

            ResultSet rsTop = conn.createStatement().executeQuery(sqlTop);
            tableModel.setRowCount(0); // Bersihkan data manual sebelumnya
            while (rsTop.next()) {
                tableModel.addRow(new Object[]{
                        rsTop.getString("id_buku"),
                        rsTop.getString("judul"),
                        rsTop.getString("kategori"),
                        rsTop.getString("total")
                });
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat Dashboard: " + e.getMessage());
        }
    }

    private JPanel createStatBox(String title, JLabel valueLabel, Color color) {
        JPanel box = new JPanel(new GridLayout(2, 1));
        box.setBackground(color);

        JLabel lblTitle = new JLabel(title, SwingConstants.CENTER);
        lblTitle.setForeground(Color.WHITE);

        valueLabel.setFont(new Font("Arial", Font.BOLD, 24));
        valueLabel.setForeground(Color.WHITE);

        box.add(lblTitle);
        box.add(valueLabel);
        return box;
    }
}