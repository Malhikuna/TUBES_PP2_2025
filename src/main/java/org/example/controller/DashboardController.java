package org.example.controller;

import org.example.KoneksiDB;
import org.example.view.DashboardView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DashboardController {

    private final DashboardView view;

    public DashboardController(DashboardView view) {
        this.view = view;
    }

    public void refreshDashboard() {
        loadStats();
        loadTopBooks();
    }

    private void loadStats() {
        try (Connection conn = KoneksiDB.configDB()) {
            Statement st = conn.createStatement();

            ResultSet rs1 = st.executeQuery("SELECT COUNT(*) FROM buku");
            if (rs1.next()) {
                view.setTotalBuku(rs1.getString(1));
            }

            ResultSet rs2 = st.executeQuery("SELECT COUNT(*) FROM anggota WHERE status_aktif = 'Aktif'");
            if (rs2.next()) {
                view.setTotalAnggota(rs2.getString(1));
            }

            ResultSet rs3 = st.executeQuery("SELECT COUNT(*) FROM peminjaman WHERE status = 'Dipinjam'");
            if (rs3.next()) {
                view.setSedangDipinjam(rs3.getString(1));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadTopBooks() {
        view.clearTable();

        String sqlTop = "SELECT b.id_buku, b.judul, COUNT(p.id_buku) as jumlah " +
                "FROM peminjaman p " +
                "JOIN buku b ON p.id_buku = b.id_buku " +
                "GROUP BY b.id_buku, b.judul " +
                "ORDER BY jumlah DESC " +
                "LIMIT 5";

        try (Connection conn = KoneksiDB.configDB()) {
            Statement st = conn.createStatement();
            ResultSet rsTop = st.executeQuery(sqlTop);

            while (rsTop.next()) {
                Object[] rowData = new Object[]{
                        rsTop.getString("id_buku"),
                        rsTop.getString("judul"),
                        rsTop.getInt("jumlah") + " x Dipinjam"
                };
                view.addTableRow(rowData);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}