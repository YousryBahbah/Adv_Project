package com.radi.demo7;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import redis.clients.jedis.Jedis;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet("/addProduct")
public class AddProductServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Admin check
        String role = (String) request.getSession().getAttribute("role");
        if (!"admin".equals(role)) {
            response.sendRedirect(request.getContextPath() + "/products");
            return;
        }

        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String priceStr = request.getParameter("price");

        // Validation
        if (name == null || name.isBlank() ||
                description == null || description.isBlank() ||
                priceStr == null || priceStr.isBlank()) {
            request.setAttribute("error", "All fields are required.");
            response.sendRedirect(request.getContextPath() + "/products");
            return;
        }

        try {
            double price = Double.parseDouble(priceStr);
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO products (name, description, price) VALUES (?, ?, ?)"
            );
            stmt.setString(1, name);
            stmt.setString(2, description);
            stmt.setDouble(3, price);
            stmt.executeUpdate();

            // Clear Redis cache so new product appears
            try (Jedis jedis = new Jedis("localhost", 6379)) {
                jedis.del("products");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect(request.getContextPath() + "/products");
    }
}