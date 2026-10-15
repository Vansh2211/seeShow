
package com.seeshow.backend.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    private final SecretKey signingKey;
    private final long accessTokenExpirationMs;

    public JwtService(
            @Value("${app.jwt.secret}") String secret,
            @Value("${app.jwt.access-token-expiration-ms}") long expirationMs
    ) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);

        this.signingKey = Keys.hmacShaKeyFor(keyBytes);
        this.accessTokenExpirationMs = expirationMs;
    }

    // Generate a signed JWT access token
    public String generateAccessToken(String email) {

        Date now = new Date();

        Date expiration = new Date(
                now.getTime() + accessTokenExpirationMs
        );

        return Jwts.builder()
                .subject(email)
                .issuedAt(now)
                .expiration(expiration)
                .signWith(signingKey)
                .compact();
    }

    // Extract all claims after verifying the token signature
    public Claims extractAllClaims(String token) {

        return Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // Extract the email stored as the token subject
    public String extractEmail(String token) {
        return extractAllClaims(token).getSubject();
    }

    // Validate the token for a given email
    public boolean isTokenValid(String token, String email) {

        Claims claims = extractAllClaims(token);

        return email.equals(claims.getSubject())
                && claims.getExpiration().after(new Date());
    }
}
