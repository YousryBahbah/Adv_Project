package com.radi.demo7;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.Date;

public class JWTHelper {

    private static final String SECRET = "ecommerce-secret-key-2024";
    private static final long EXPIRY = 1000 * 60 * 60; // 1 hour

    public static String generateToken(String username, String role) {
        return JWT.create()
                .withSubject(username)
                .withClaim("role", role)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRY))
                .sign(Algorithm.HMAC256(SECRET));
    }

    public static DecodedJWT validateToken(String token) {
        return JWT.require(Algorithm.HMAC256(SECRET))
                .build()
                .verify(token);
    }
}