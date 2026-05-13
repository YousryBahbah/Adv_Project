package com.radi.demo7;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/signup")
public class SignUpServlet extends HttpServlet {

    // GET — show the signup page
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/signup.jsp").forward(request, response);
    }

    // POST — handle signup form submission
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Basic validation
        if (username == null || username.isBlank() ||
                email == null || email.isBlank() ||
                password == null || password.isBlank()) {

            request.setAttribute("error", "All fields are required.");
            request.getRequestDispatcher("/signup.jsp").forward(request, response);
            return;
        }

        boolean success = UserDAO.registerUser(username, email, password);

        if (success) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        } else {
            request.setAttribute("error", "Username or email already exists.");
            request.getRequestDispatcher("/signup.jsp").forward(request, response);
        }
    }
}