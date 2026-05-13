package com.radi.demo7;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import redis.clients.jedis.Jedis;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    // GET — show the login page
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    // POST — handle login form submission
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Basic validation
        if (username == null || username.isBlank() ||
                password == null || password.isBlank()) {
            request.setAttribute("error", "All fields are required.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        // Check user in database
        User user = UserDAO.getUser(username, password);

        if (user == null) {
            request.setAttribute("error", "Invalid username or password.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        // 1 — Create JWT token
        String token = JWTHelper.generateToken(user.getUsername(), user.getRole());

        // 2 — Store session in Redis
        try (Jedis jedis = new Jedis("localhost", 6379)) {
            jedis.setex("session:" + user.getUsername(), 3600, token);
        }

        // 3 — Save token in cookie
        Cookie cookie = new Cookie("jwt", token);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(3600);
        cookie.setPath("/");
        response.addCookie(cookie);

        // 4 — Save user info in HTTP session
        HttpSession session = request.getSession();
        session.setAttribute("username", user.getUsername());
        session.setAttribute("role", user.getRole());
        session.setAttribute("email", user.getEmail());
        session.setAttribute("userId", user.getId());

        // Redirect to home page
        response.sendRedirect(request.getContextPath() + "/products");
    }
}