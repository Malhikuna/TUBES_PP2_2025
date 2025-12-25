package org.example.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AnggotaView extends JFrame {
    // Komponen Header
    public DefaultTableModel model;
    private JRadioButton rbSemua, rbAktif, rbTidakAktif;
    private ButtonGroup bgStatus;
    private JTextField txtSearch;

    // Komponen Form Input
    private JTextField txtIdAnggota, txtNama, txtNoTelp;

    // Komponen Tombol
    private JButton btnTambah, btnUbah, btnHapus, btnClear;

    // Komponen Tabel
    private JTable tabelAnggota;
    private DefaultTableModel tableModel;

    public AnggotaView() {
        setTitle("Halaman Anggota");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // HEADER 
        JPanel panelHeader = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rbSemua = new JRadioButton("Semua", true);
        rbAktif = new JRadioButton("Aktif");
        rbTidakAktif = new JRadioButton("Tidak Aktif");
        bgStatus = new ButtonGroup();
        bgStatus.add(rbSemua); bgStatus.add(rbAktif); bgStatus.add(rbTidakAktif);
        
        txtSearch = new JTextField(20);
        
        panelHeader.add(new JLabel("Filter Status: "));
        panelHeader.add(rbSemua); panelHeader.add(rbAktif); panelHeader.add(rbTidakAktif);
        panelHeader.add(new JLabel(" | Search: "));
        panelHeader.add(txtSearch);

        // FORM INPUT ANGGOTA & TOMBOL
        JPanel panelKiri = new JPanel(new BorderLayout());
        
        // Panel Form Input
        JPanel panelInput = new JPanel(new GridLayout(3, 2, 5, 5));
        txtIdAnggota = new JTextField("Auto");
        txtIdAnggota.setEditable(false); // Read Only
        txtNama = new JTextField();
        txtNoTelp = new JTextField();

        panelInput.add(new JLabel("Id Anggota:")); panelInput.add(txtIdAnggota);
        panelInput.add(new JLabel("Nama:"));       panelInput.add(txtNama);
        panelInput.add(new JLabel("No Telp:"));   panelInput.add(txtNoTelp);

        // Panel Tombol
        JPanel panelTombol = new JPanel(new FlowLayout());
        btnTambah = new JButton("Tambah");
        btnUbah = new JButton("Ubah");
        btnHapus = new JButton("Hapus");
        btnClear = new JButton("Clear");

        panelTombol.add(btnTambah);
        panelTombol.add(btnUbah);
        panelTombol.add(btnHapus);
        panelTombol.add(btnClear);

        panelKiri.add(panelInput, BorderLayout.NORTH);
        panelKiri.add(panelTombol, BorderLayout.CENTER);

        // TABEL (ID | Nama | No Telp | Status)
        String[] kolom = {"ID", "Nama", "No Telp", "Status"};
        tableModel = new DefaultTableModel(kolom, 0);
        tabelAnggota = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tabelAnggota);

        add(panelHeader, BorderLayout.NORTH);
        add(panelKiri, BorderLayout.WEST);
        add(scrollPane, BorderLayout.CENTER);

        // --- LOGIKA TOMBOL ---

        // Tombol Tambah
        btnTambah.addActionListener(e -> {
            String status = rbAktif.isSelected() ? "Aktif" : (rbTidakAktif.isSelected() ? "Tidak Aktif" : "Aktif");
            tableModel.addRow(new Object[]{
                tableModel.getRowCount() + 1, 
                txtNama.getText(), 
                txtNoTelp.getText(), 
                status
            });
        });

        // Tombol Ubah
        btnUbah.addActionListener(e -> {
            int row = tabelAnggota.getSelectedRow();
            if (row != -1) {
                tableModel.setValueAt(txtNama.getText(), row, 1);
                tableModel.setValueAt(txtNoTelp.getText(), row, 2);
            }
        });

        // Tombol Hapus
        btnHapus.addActionListener(e -> {
            int row = tabelAnggota.getSelectedRow();
            if (row != -1) {
                tableModel.removeRow(row);
            }
        });

        // Tombol Clear (Kosongkan Form Input)
        btnClear.addActionListener(e -> {
            txtIdAnggota.setText("Auto");
            txtNama.setText("");
            txtNoTelp.setText("");
            rbSemua.setSelected(true);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AnggotaView().setVisible(true);
        });
    }
}