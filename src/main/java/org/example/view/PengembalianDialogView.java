package org.example.view;

import org.example.controller.TransaksiController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

public class PengembalianDialogView extends JDialog {

    public PengembalianDialogView(Frame owner, TransaksiController controller, int idLog, String nama, String judul, String tglWajibStr) {
        super(owner, "Konfirmasi Pengembalian", true);
        setSize(400, 400);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        JPanel pnlInfo = new JPanel(new GridLayout(6, 1, 5, 5));
        pnlInfo.setBorder(new EmptyBorder(20, 20, 20, 20));

        pnlInfo.add(new JLabel("Nama: " + nama));
        pnlInfo.add(new JLabel("Buku: " + judul));
        pnlInfo.add(new JLabel("Tgl Wajib Kembali: " + tglWajibStr));

        LocalDate hariIni = LocalDate.now();
        pnlInfo.add(new JLabel("Tanggal Hari Ini: " + hariIni.toString()));

        long terlambat = 0;
        double totalDenda = 0;

        try {
            LocalDate tglWajib = LocalDate.parse(tglWajibStr);

            terlambat = ChronoUnit.DAYS.between(tglWajib, hariIni);

            if (terlambat < 0) terlambat = 0;

            totalDenda = terlambat * 2000;

        } catch (Exception e) {
            System.out.println("Error parsing date: " + e.getMessage());
        }

        JLabel lblTelat = new JLabel("Keterlambatan: " + terlambat + " Hari");
        lblTelat.setFont(new Font("Arial", Font.BOLD, 16));
        lblTelat.setForeground(new Color(231, 76, 60));

        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        JLabel lblDenda = new JLabel("Total Denda: " + nf.format(totalDenda));
        lblDenda.setFont(new Font("Arial", Font.BOLD, 18));
        lblDenda.setForeground(new Color(192, 57, 43));

        pnlInfo.add(lblTelat);
        pnlInfo.add(lblDenda);

        add(pnlInfo, BorderLayout.CENTER);

        JPanel pnlButton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnProses = new JButton("Proses Pengembalian (Lunas)");
        JButton btnBatal = new JButton("Batal");

        btnProses.setBackground(new Color(46, 204, 113));
        btnProses.setForeground(Color.WHITE);

        btnBatal.addActionListener(e -> dispose());

        btnProses.addActionListener(e -> {
            controller.kembalikanBuku(idLog);
            dispose();
        });

        pnlButton.add(btnProses);
        pnlButton.add(btnBatal);
        add(pnlButton, BorderLayout.SOUTH);
    }
}