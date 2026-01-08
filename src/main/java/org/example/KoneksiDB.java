package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class KoneksiDB {
    private static Connection mysqlconfig;
    public static Connection configDB() {
        try {
            // URL Database sesuai proyek Library Information Log
            if (mysqlconfig == null || mysqlconfig.isClosed()) {
                String url = "jdbc:mysql://localhost:3306/pp2";
                String user = "root";
                String pass = "";
                DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
                mysqlconfig = DriverManager.getConnection(url, user, pass);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Koneksi Gagal: " + e.getMessage());
        }
        return mysqlconfig;
    }
}

