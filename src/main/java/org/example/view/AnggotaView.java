package org.example.view;

import org.example.controller.AnggotaController;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AnggotaView extends JPanel {
    public DefaultTableModel model;
    public JTable table;
    public JTextField txtId, txtNama, txtTelp, txtCari;
    public JRadioButton rbSemua, rbFilterAktif, rbFilterTidakAktif, rbAktif, rbTidakAktif;
    public JButton btnTambah, btnUbah, btnHapus, btnClear;
    public JCheckBox checkSort;

    public AnggotaView() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // --- PANEL ATAS 
        JPanel pnlAtas = new JPanel(new BorderLayout(10, 10));
        
        // Header (Filter & Search)
        JPanel pnlHeader = new JPanel(new BorderLayout());
        JPanel pnlFilter = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rbSemua = new JRadioButton("Semua", true);
        rbFilterAktif = new JRadioButton("Aktif");
        rbFilterTidakAktif = new JRadioButton("Tidak Aktif");
        ButtonGroup bg = new ButtonGroup();
        bg.add(rbSemua); bg.add(rbFilterAktif); bg.add(rbFilterTidakAktif);
        
        pnlFilter.add(new JLabel("Status: "));
        pnlFilter.add(rbSemua); pnlFilter.add(rbFilterAktif); pnlFilter.add(rbFilterTidakAktif);

        JPanel pnlSearch = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        txtCari = new JTextField(15);
        checkSort = new JCheckBox("Z-A");

        pnlSearch.add(new JLabel("Cari Nama/ID: "));
        pnlSearch.add(txtCari);
        pnlSearch.add(checkSort);
        
        pnlHeader.add(pnlFilter, BorderLayout.WEST);
        pnlHeader.add(pnlSearch, BorderLayout.EAST);

        //  Form Input
        JPanel pnlInput = new JPanel(new GridBagLayout());
        pnlInput.setBorder(BorderFactory.createTitledBorder("Form Input Anggota"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtId = new JTextField("Auto"); txtId.setEditable(false);
        txtNama = new JTextField(20);
        txtTelp = new JTextField(20);

        rbAktif = new JRadioButton("Aktif", true);
        rbTidakAktif = new JRadioButton("Tidak Aktif");

        ButtonGroup bgStatus = new ButtonGroup();
        bgStatus.add(rbAktif);
        bgStatus.add(rbTidakAktif);

        JPanel pnlStatus = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        pnlStatus.add(rbAktif);
        pnlStatus.add(rbTidakAktif);

        gbc.gridx = 0; gbc.gridy = 3;
        pnlInput.add(new JLabel("Status:"), gbc);

        gbc.gridx = 1;
        pnlInput.add(pnlStatus, gbc);


        gbc.gridx = 0; gbc.gridy = 0; pnlInput.add(new JLabel("ID Anggota:"), gbc);
        gbc.gridx = 1; pnlInput.add(txtId, gbc);
        gbc.gridx = 0; gbc.gridy = 1; pnlInput.add(new JLabel("Nama:"), gbc);
        gbc.gridx = 1; pnlInput.add(txtNama, gbc);
        gbc.gridx = 0; gbc.gridy = 2; pnlInput.add(new JLabel("No Telp:"), gbc);
        gbc.gridx = 1; pnlInput.add(txtTelp, gbc);

        pnlAtas.add(pnlHeader, BorderLayout.NORTH);
        pnlAtas.add(pnlInput, BorderLayout.CENTER);

        // PANEL TENGAH (Tabel) 
        model = new DefaultTableModel(new String[]{"ID", "Nama", "No Telp", "Status"}, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        // PANEL BAWAH (Tombol CRUD)
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnTambah = new JButton("Tambah");
        btnUbah = new JButton("Ubah");
        btnHapus = new JButton("Hapus");
        btnClear = new JButton("Clear");
        pnlButtons.add(btnTambah); pnlButtons.add(btnUbah); 
        pnlButtons.add(btnHapus); pnlButtons.add(btnClear);

        add(pnlAtas, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(pnlButtons, BorderLayout.SOUTH);

        // Inisialisasi controller 
        AnggotaController controller = new AnggotaController(this);
    }
}