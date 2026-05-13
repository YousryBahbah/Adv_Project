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

@WebServlet("/deleteProduct")
public class DeleteProductServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Admin check
        String role = (String) request.getSession().getAttribute("role");
        if (!"admin".equals(role)) {
            response.sendRedirect(request.getContextPath() + "/products");
            return;
        }

        String idStr = request.getParameter("id");

        try {
            int id = Integer.parseInt(idStr);
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                    "DELETE FROM products WHERE id = ?"
            );
            stmt.setInt(1, id);
            stmt.executeUpdate();

            // Clear Redis cache
            try (Jedis jedis = new Jedis("localhost", 6379)) {
                jedis.del("products");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect(request.getContextPath() + "/products");
    }
}