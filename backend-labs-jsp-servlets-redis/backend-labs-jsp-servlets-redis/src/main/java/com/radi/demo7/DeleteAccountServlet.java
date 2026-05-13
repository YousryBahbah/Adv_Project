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

@WebServlet("/deleteAccount")
public class DeleteAccountServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String username = (String) session.getAttribute("username");

        try {
            // 1 — Delete from database
            UserDAO.deleteUser(username);

            // 2 — Delete Redis session
            try (Jedis jedis = new Jedis("localhost", 6379)) {
                jedis.del("session:" + username);
            }

            // 3 — Invalidate HTTP session
            session.invalidate();

            // 4 — Clear JWT cookie
            Cookie cookie = new Cookie("jwt", "");
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);

        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect(request.getContextPath() + "/login");
    }
}