package org.example.view;

import org.example.controller.AnggotaController;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AnggotaView extends JPanel {
    public DefaultTableModel model;
    public JTable table;
    public JTextField txtId, txtNama, txtTelp, txtCari;
    public JRadioButton rbSemua, rbAktif, rbTidakAktif;
    public JButton btnTambah, btnUbah, btnHapus, btnClear;
    private AnggotaController controller;

    public AnggotaView() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        initComponents();
        // Inisialisasi controller (Pedoman: controller akan memanggil loadData)
        this.controller = new AnggotaController(this);
    }

    private void initComponents() {
        // --- NORTH: Header & Filter ---
        JPanel pnlHeader = new JPanel(new BorderLayout());
        JPanel pnlFilter = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        rbSemua = new JRadioButton("Semua", true);
        rbAktif = new JRadioButton("Aktif");
        rbTidakAktif = new JRadioButton("Tidak Aktif");
        ButtonGroup bg = new ButtonGroup();
        bg.add(rbSemua); bg.add(rbAktif); bg.add(rbTidakAktif);

        pnlFilter.add(new JLabel("Status: "));
        pnlFilter.add(rbSemua); pnlFilter.add(rbAktif); pnlFilter.add(rbTidakAktif);

        JPanel pnlSearch = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        txtCari = new JTextField(15);
        pnlSearch.add(new JLabel("Cari Nama/ID: "));
        pnlSearch.add(txtCari);

        pnlHeader.add(pnlFilter, BorderLayout.WEST);
        pnlHeader.add(pnlSearch, BorderLayout.EAST);

        // --- CENTER: Tabel ---
        model = new DefaultTableModel(new String[]{"ID", "Nama", "No Telp", "Status"}, 0);
        table = new JTable(model);
        
        // --- WEST: Form Input ---
        JPanel pnlInput = new JPanel(new GridLayout(4, 2, 5, 5));
        pnlInput.setPreferredSize(new Dimension(300, 150));
        txtId = new JTextField("Auto"); txtId.setEditable(false);
        txtNama = new JTextField();
        txtTelp = new JTextField();

        pnlInput.add(new JLabel("ID Anggota:")); pnlInput.add(txtId);
        pnlInput.add(new JLabel("Nama:")); pnlInput.add(txtNama);
        pnlInput.add(new JLabel("No Telp:")); pnlInput.add(txtTelp);

        // --- SOUTH: Buttons ---
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnTambah = new JButton("Tambah");
        btnUbah = new JButton("Ubah");
        btnHapus = new JButton("Hapus");
        btnClear = new JButton("Clear");
        
        pnlButtons.add(btnTambah); pnlButtons.add(btnUbah); 
        pnlButtons.add(btnHapus); pnlButtons.add(btnClear);

        // Assemble
        add(pnlHeader, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(pnlInput, BorderLayout.WEST);
        add(pnlButtons, BorderLayout.SOUTH);
    }
}