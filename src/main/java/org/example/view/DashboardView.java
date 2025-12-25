package org.example.view;

import org.example.KoneksiDB;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DashboardView extends JPanel {

    private JLabel lblTotalBuku;
    private JLabel lblTotalAnggota;
    private JLabel lblSedangDipinjam;
    private JTable tableTopBuku;
    private DefaultTableModel tableModel;

    public DashboardView() {
        setLayout(new BorderLayout(20, 20));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        initStatCards();

        initTopTable();

        loadDataDashboard();
    }

    private void initStatCards() {
        JPanel pnlStats = new JPanel(new GridLayout(1, 3, 20, 0));

        lblTotalBuku = new JLabel("0", SwingConstants.CENTER);
        lblTotalAnggota = new JLabel("0", SwingConstants.CENTER);
        lblSedangDipinjam = new JLabel("0", SwingConstants.CENTER);

        pnlStats.add(createCard("Total Koleksi Buku", lblTotalBuku, new Color(52, 152, 219)));
        pnlStats.add(createCard("Total Anggota Aktif", lblTotalAnggota, new Color(46, 204, 113)));
        pnlStats.add(createCard("Sedang Dipinjam", lblSedangDipinjam, new Color(231, 76, 60)));

        add(pnlStats, BorderLayout.NORTH);
    }

    private void initTopTable() {
        JPanel pnlTable = new JPanel(new BorderLayout(0, 10));

        JLabel lblTitle = new JLabel("Top 5 Buku Terpopuler");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 16));

        String[] columns = {"ID Buku", "Judul Buku", "Total Peminjaman"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableTopBuku = new JTable(tableModel);
        tableTopBuku.setRowHeight(25);
        tableTopBuku.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));

        pnlTable.add(lblTitle, BorderLayout.NORTH);
        pnlTable.add(new JScrollPane(tableTopBuku), BorderLayout.CENTER);

        add(pnlTable, BorderLayout.CENTER);
    }

    private JPanel createCard(String title, JLabel valueLabel, Color bgColor) {
        JPanel card = new JPanel(new GridLayout(2, 1));
        card.setBackground(bgColor);
        card.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel lblTitle = new JLabel(title, SwingConstants.CENTER);
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Arial", Font.PLAIN, 14));

        valueLabel.setForeground(Color.WHITE);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 36));

        card.add(lblTitle);
        card.add(valueLabel);
        return card;
    }

    public void loadDataDashboard() {
        try (Connection conn = KoneksiDB.configDB()) {
            Statement st = conn.createStatement();

            ResultSet rs1 = st.executeQuery("SELECT COUNT(*) FROM buku");
            if (rs1.next()) {
                lblTotalBuku.setText(rs1.getString(1));
            }

            ResultSet rs2 = st.executeQuery("SELECT COUNT(*) FROM anggota WHERE status_aktif = 'Aktif'");
            if (rs2.next()) {
                lblTotalAnggota.setText(rs2.getString(1));
            }

            ResultSet rs3 = st.executeQuery("SELECT COUNT(*) FROM peminjaman WHERE status = 'Dipinjam'");
            if (rs3.next()) {
                lblSedangDipinjam.setText(rs3.getString(1));
            }

            String sqlTop = "SELECT b.id_buku, b.judul, COUNT(p.id_buku) as jumlah " +
                    "FROM peminjaman p " +
                    "JOIN buku b ON p.id_buku = b.id_buku " +
                    "GROUP BY b.id_buku, b.judul " +
                    "ORDER BY jumlah DESC " +
                    "LIMIT 5";

            ResultSet rsTop = st.executeQuery(sqlTop);

            tableModel.setRowCount(0);
            while (rsTop.next()) {
                tableModel.addRow(new Object[]{
                        rsTop.getString("id_buku"),
                        rsTop.getString("judul"),
                        rsTop.getInt("jumlah") + " x Dipinjam"
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}