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

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1 — Get username before invalidating session
        HttpSession session = request.getSession(false);
        if (session != null) {
            String username = (String) session.getAttribute("username");

            // 2 — Delete Redis session
            if (username != null) {
                try (Jedis jedis = new Jedis("localhost", 6379)) {
                    jedis.del("session:" + username);
                }
            }

            // 3 — Invalidate HTTP session
            session.invalidate();
        }

        // 4 — Clear JWT cookie
        Cookie cookie = new Cookie("jwt", "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);

        response.sendRedirect(request.getContextPath() + "/login");
    }
}