package com.cms.cmsapp.application.Security.Utils;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.cms.cmsapp.user.entity.User;
import com.cms.cmsapp.user.service.UserService;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {

    @Autowired
    private UserService userDetailsService;

    private static final String SECRET_KEY = "3zKq8P@x7LrDgYV9Cw#UmS$eXpV!HbNmWx!A7VsLpPZg3nQq";
    private static final long EXPIRATION_TIME = 86400000;

    private SecretKey getSigningKey() {
        // byte[] keyBytes = Decoders.BASE64.decode(JwtUtils.SECRET_KEY);
        byte[] keyBytes = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateJwtToken(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        String role =userDetails.getAuthorities().stream().findFirst().get().getAuthority();

        Key key = getSigningKey();

        return Jwts.builder()
                .subject(username)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }

    public String generateJwtToken(User userDetails) {

        Key key = getSigningKey();
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim("role","ROLE_"+ userDetails.getRole().name())
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }

    String getUserNameFromJwtToken(String token) {
        SecretKey key = getSigningKey();
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean validateToken(String token) {
        SecretKey key = getSigningKey();
        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public Authentication getAuthentication(String token) {
        String username = getUserNameFromJwtToken(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
}
