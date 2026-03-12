package com.edu_assess.edu_assess.backend.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JWTTokenHelper {

    @Value("${jwt.auth.app}")
    private String appName;

    @Value("${jwt.auth.secret_key}")
    private String secretKey;

    @Value("${jwt.auth.expires_in}")
    private long expiresIn;

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        List<String> authorities = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        claims.put("authorities", authorities);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuer(appName)
                .setIssuedAt(new Date())
                .setExpiration(generateExpirationDate())
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return username != null &&
                username.equals(userDetails.getUsername()) &&
                !isTokenExpired(token);
    }

    public String getUsernameFromToken(String token) {
        try {
            return getAllClaimsFromToken(token).getSubject();
        } catch (Exception e) {
            return null;
        }
    }

    public List<String> getAuthoritiesFromToken(String token) {
        try {
            Claims claims = getAllClaimsFromToken(token);
            Object authoritiesObj = claims.get("authorities");
            if (authoritiesObj instanceof List<?>) {
                return ((List<?>) authoritiesObj).stream()
                        .map(Object::toString)
                        .collect(Collectors.toList());
            }
        } catch (Exception e) {
            // log
        }
        return Collections.emptyList();
    }

    private boolean isTokenExpired(String token) {
        Date expiration = getExpirationDate(token);
        return expiration != null && expiration.before(new Date());
    }

    private Date getExpirationDate(String token) {
        try {
            return getAllClaimsFromToken(token).getExpiration();
        } catch (Exception e) {
            return null;
        }
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Date generateExpirationDate() {
        // expiresIn is in seconds, so multiply by 1000 for milliseconds
        return new Date(System.currentTimeMillis() + expiresIn * 1000L);
    }

    public String getToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }
}