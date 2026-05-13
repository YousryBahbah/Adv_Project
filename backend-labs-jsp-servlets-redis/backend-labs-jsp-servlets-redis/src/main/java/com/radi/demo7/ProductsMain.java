package com.radi.demo7;

import java.io.*;
import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/products")
public class ProductsMain extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        long start = System.currentTimeMillis();

        // Get products
        List<Product> products = null;
        try {
            String user = (String) request.getAttribute("user");
            products = ProductDb.getProductList(user);
        } catch (RuntimeException e) {
            if (e.getMessage() != null && e.getMessage().equals("Too many requests")) {
                request.setAttribute("error", "Too many requests. Please wait 10 seconds.");
                products = new java.util.ArrayList<>();
            } else {
                throw e;
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

        long end = System.currentTimeMillis();
        System.out.println("DB CALL TIME: " + (end - start) + " ms");

        // Get reviews
        List<Review> reviews = ReviewDAO.getAllReviews();

        // Pass both to JSP
        request.setAttribute("data", products);
        request.setAttribute("reviews", reviews);

        RequestDispatcher rs = request.getRequestDispatcher("display-products.jsp");
        rs.forward(request, response);
    }
}