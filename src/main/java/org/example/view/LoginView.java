package org.example.view;

import org.example.controller.LoginController;
import org.example.view.components.CustomInputPanel;
import org.example.view.components.LeftBackgroundPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoginView extends JFrame {

    private final CustomInputPanel inputUser;
    private final CustomInputPanel inputPass;

    public LoginView() {
        setTitle("Login - Book & Arsip Highlight : Library Information Log");
        setSize(1200, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(1, 2));
        
        // PANEL KIRI
        LeftBackgroundPanel leftPanel = new LeftBackgroundPanel();
        leftPanel.setLayout(new GridBagLayout());

        JPanel brandBox = new JPanel();
        brandBox.setBackground(new Color(0, 0, 0, 0));
        brandBox.setLayout(new BoxLayout(brandBox, BoxLayout.Y_AXIS));
        brandBox.setBorder(new EmptyBorder(30, 0, 30, 0));

        JLabel lblTitle = new JLabel("<html><center>Book & Arsip Highlight :</center></html>");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 42));
        lblTitle.setForeground(Color.WHITE);

        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setMaximumSize(new Dimension(Integer.MAX_VALUE, lblTitle.getPreferredSize().height + 50));

        JLabel lblSub = new JLabel("Library Information Log");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        lblSub.setForeground(Color.WHITE);

        lblSub.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblSub.setHorizontalAlignment(SwingConstants.CENTER);
        lblSub.setMaximumSize(new Dimension(Integer.MAX_VALUE, lblSub.getPreferredSize().height));

        brandBox.add(lblTitle);
        brandBox.add(Box.createVerticalStrut(15));
        brandBox.add(lblSub);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        leftPanel.add(brandBox, gbc);

        // PANEL KANAN
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(Color.WHITE);

        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(400, 450));
        card.setBackground(Color.WHITE);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        JLabel lblLogin = new JLabel("ADMIN LOGIN");
        lblLogin.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblLogin.setForeground(new Color(50, 50, 50));
        lblLogin.setAlignmentX(Component.CENTER_ALIGNMENT);

        inputUser = new CustomInputPanel("Username", "USER");
        inputPass = new CustomInputPanel("Password", "LOCK");

        JButton btnLogin = new JButton("LOGIN");
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLogin.setBackground(new Color(80,200,120));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnLogin.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));

        card.add(Box.createVerticalStrut(20));
        card.add(lblLogin);
        card.add(Box.createVerticalStrut(40));
        card.add(inputUser);
        card.add(Box.createVerticalStrut(20));
        card.add(inputPass);
        card.add(Box.createVerticalStrut(40));
        card.add(btnLogin);

        rightPanel.add(card);

        add(leftPanel);
        add(rightPanel);

        btnLogin.addActionListener(e -> prosesLogin());
        inputPass.getField().addActionListener(e -> prosesLogin());

        setVisible(true);
    }

    private void prosesLogin() {
        String username = inputUser.getText().trim();
        String password = inputPass.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Input tidak boleh kosong!", "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        LoginController loginCtrl = new LoginController();
        if (loginCtrl.login(username, password)) {
            JOptionPane.showMessageDialog(this, "Login Berhasil! Selamat Datang.", "Sukses", JOptionPane.INFORMATION_MESSAGE);

            this.dispose();
            new SideBarView().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Username/Password Salah!", "Gagal", JOptionPane.ERROR_MESSAGE);
        }
    }
}