package org.example.controller;

import org.example.KoneksiDB;

import java.sql.*;

public class LoginController {

    public boolean login(String username, String password) {
        Connection conn = KoneksiDB.configDB();

        if (conn == null) {
            return false;
        }

        try {
            String sql = "SELECT * FROM admin WHERE username = ? AND password = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
