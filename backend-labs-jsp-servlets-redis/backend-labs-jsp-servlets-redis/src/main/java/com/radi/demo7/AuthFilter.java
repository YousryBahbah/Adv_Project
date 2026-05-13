package com.radi.demo7;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * AuthFilter - Servlet Filter that intercepts ALL incoming HTTP requests.
 * Responsible for protecting secured endpoints by validating JWT tokens.
 * Public paths (login, signup) are whitelisted and allowed through.
 * All other paths require a valid JWT cookie to proceed.
 */
@WebFilter("/*")
public class AuthFilter implements Filter {

    /**
     * Core filter method — runs before every request reaches a servlet.
     * Validates JWT from cookie and allows or rejects the request.
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        // Extract path without context root (e.g. /login, /products)
        String path = request.getRequestURI()
                .substring(request.getContextPath().length());

        System.out.println("[AuthFilter] Incoming request: " + path);

        // Whitelist public paths — no authentication needed
        if (path.contains("login") || path.contains("signup") ||
                path.contains("error") ||
                path.endsWith(".css") || path.endsWith(".js")) {
            chain.doFilter(request, response);
            return;
        }

        String user = null;

        // Extract and validate JWT token from browser cookie
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if ("jwt".equals(c.getName())) {
                    try {
                        // Verify token signature and expiry using shared secret
                        DecodedJWT jwt = JWT.require(Algorithm.HMAC256("ecommerce-secret-key-2024"))
                                .build()
                                .verify(c.getValue());
                        user = jwt.getSubject(); // Extract username from token
                    } catch (Exception e) {
                        // Token is invalid or expired
                        System.out.println("[AuthFilter] Invalid token: " + e.getMessage());
                        user = null;
                    }
                    break;
                }
            }
        }

        // Reject unauthenticated requests — redirect to login
        if (user == null) {
            System.out.println("[AuthFilter] Unauthorized access blocked: " + path);
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Pass authenticated username to downstream servlets via request attribute
        request.setAttribute("user", user);
        chain.doFilter(request, response);
    }
}