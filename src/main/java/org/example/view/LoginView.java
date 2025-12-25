package org.example.view;

import org.example.controller.AdminController;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView extends JFrame {

    JLabel lblUser = new JLabel("Username:");
    JLabel lblPass = new JLabel("Password:");
    JTextField txtUser = new JTextField();
    JPasswordField txtPass = new JPasswordField();
    JButton btnLogin = new JButton("LOGIN");

    public LoginView() {
        setTitle("Login - BAHLIL System");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        lblUser.setBounds(30, 30, 80, 25);
        txtUser.setBounds(110, 30, 180, 25);

        lblPass.setBounds(30, 70, 80, 25);
        txtPass.setBounds(110, 70, 180, 25);

        btnLogin.setBounds(110, 110, 100, 30);

        add(lblUser);
        add(txtUser);
        add(lblPass);
        add(txtPass);
        add(btnLogin);

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prosesLogin();
            }
        });

        txtPass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prosesLogin();
            }
        });

        setVisible(true);
    }

    private void prosesLogin() {
        String username = txtUser.getText();
        String password = new String(txtPass.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username dan Password tidak boleh kosong!");
            return;
        }

        AdminController adminCtrl = new AdminController();
        boolean isSukses = adminCtrl.login(username, password);

        if (isSukses) {
            JOptionPane.showMessageDialog(this, "Login Berhasil! Selamat Datang.");

            this.dispose();

            new SideBarView().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Username atau Password Salah!", "Login Gagal", JOptionPane.ERROR_MESSAGE);
            txtPass.setText("");
            txtPass.requestFocus();
        }
    }
}