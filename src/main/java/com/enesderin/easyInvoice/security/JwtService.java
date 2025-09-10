package com.enesderin.easyInvoice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Service
public class JwtService {

    private String KEY="N3xxRzCmavMQTxfo9BmAezvCWHNMV/6i2chEiqHJB2w=";

    public String createToken(UserDetails user) {
        return Jwts.builder()
                .claim("authorities", user.getAuthorities())
                .setSubject(user.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 2))
                .setIssuedAt(new Date())
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token).getBody();
    }

    public String getUsernameByToken(String token) {
        return parseToken(token).getSubject();
    }

    public Boolean validateToken(String token) {
        Claims claims = parseToken(token);
        Date expiration = claims.getExpiration();
        return !expiration.before(new Date());
    }


    public Key getKey() {
        return Keys.hmacShaKeyFor(KEY.getBytes());
    }
}
