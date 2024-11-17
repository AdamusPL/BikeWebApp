package com.bikeparadise.bikewebapp.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;


@Component
public class JwtTokenGenerator {
    private SecretKey getSignInKey() {
        SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_SECRET_KEY.getBytes(StandardCharsets.UTF_8));

        byte[] bytes = key.getEncoded();

        return new SecretKeySpec(bytes, "HmacSHA256");
    }

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expirationDate = new Date(currentDate.getTime() + SecurityConstants.JWT_EXPIRATION_DATE);

        SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_SECRET_KEY.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .subject(username)
                .claim("roles", authentication.getAuthorities())
                .issuedAt(currentDate)
                .expiration(expirationDate)
                .signWith(key)
                .compact();
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public List<String> extractRoles(Claims claims){
        return claims.get("roles", List.class);
    }

    public void verifyToken(String token, HttpServletResponse response) throws IOException {
        try {
            Claims claims = extractAllClaims(token);
            List<String> roles = extractRoles(claims);
            response.setStatus(HttpServletResponse.SC_OK);

            for (Object roleEntry : roles) {
                if (roleEntry instanceof Map) {
                    Map<String, Object> roleMap = (Map<String, Object>) roleEntry;
                    for (Map.Entry<String, Object> entry : roleMap.entrySet()) {
                        if(entry.getKey().equals("authority")){
                            if(entry.getValue().equals("ROLE_ADMIN")){
                                response.setContentType("text/plain");
                                response.getWriter().write("ROLE_ADMIN");
                                response.getWriter().flush();
                                response.getWriter().close();
                                return;
                            }
                            else{
                                response.setContentType("text/plain");
                                response.getWriter().write("ROLE_USER");
                                response.getWriter().flush();
                                response.getWriter().close();
                                return;
                            }
                        }
                    }
                }
            }

        } catch (ExpiredJwtException ex) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token expired");
        } catch (AuthenticationCredentialsNotFoundException ex) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("Token is not valid");
        }
    }
}
