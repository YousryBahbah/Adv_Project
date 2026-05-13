package com.radi.demo7;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/addReview")
public class AddReviewServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String content = request.getParameter("content");
        String productIdStr = request.getParameter("productId");

        // Validation
        if (content == null || content.isBlank()) {
            response.sendRedirect(request.getContextPath() + "/products");
            return;
        }

        try {
            int userId = (int) session.getAttribute("userId");
            int productId = productIdStr != null ? Integer.parseInt(productIdStr) : 0;
            ReviewDAO.addReview(userId, productId, content);
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect(request.getContextPath() + "/products");
    }
}