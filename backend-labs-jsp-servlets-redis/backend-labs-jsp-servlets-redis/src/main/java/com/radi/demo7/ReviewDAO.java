package com.radi.demo7;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAO {

    // Get all reviews with username joined
    public static List<Review> getAllReviews() {
        List<Review> reviews = new ArrayList<>();
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT r.id, r.user_id, r.product_id, r.content, r.created_at, u.username " +
                            "FROM reviews r JOIN users u ON r.user_id = u.id " +
                            "ORDER BY r.created_at DESC"
            );
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                reviews.add(new Review(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("product_id"),
                        rs.getString("content"),
                        rs.getString("username"),
                        rs.getString("created_at")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reviews;
    }

    // Add a new review
    public static boolean addReview(int userId, int productId, String content) {
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt;

            if (productId == 0) {
                // General feedback — no product linked
                stmt = conn.prepareStatement(
                        "INSERT INTO reviews (user_id, product_id, content) VALUES (?, NULL, ?)"
                );
                stmt.setInt(1, userId);
                stmt.setString(2, content);
            } else {
                // Product-specific review
                stmt = conn.prepareStatement(
                        "INSERT INTO reviews (user_id, product_id, content) VALUES (?, ?, ?)"
                );
                stmt.setInt(1, userId);
                stmt.setInt(2, productId);
                stmt.setString(3, content);
            }

            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}