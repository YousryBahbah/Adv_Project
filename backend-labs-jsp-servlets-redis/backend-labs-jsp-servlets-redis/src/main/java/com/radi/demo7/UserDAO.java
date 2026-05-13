package com.radi.demo7;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

    // Find user by username and password (for login)
    public static User getUser(String username, String password) {
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT * FROM users WHERE username = ? AND password = ?"
            );
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("role")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Insert new user (for signup)
    public static boolean registerUser(String username, String email, String password) {
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO users (username, email, password, role) VALUES (?, ?, ?, 'user')"
            );
            stmt.setString(1, username);
            stmt.setString(2, email);
            stmt.setString(3, password);
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete user by username
    public static boolean deleteUser(String username) {
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                    "DELETE FROM users WHERE username = ?"
            );
            stmt.setString(1, username);
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}