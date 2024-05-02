package com.multitenant.multitenant.architecture.config.web;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;
import java.util.List;
@Component
public class TokenProvider {

    @Value("${security.jwt.token.secret-key:secret}")
    private String secretKey = "secret";

    @Value("${security.jwt.token.expire-length}")
    private long validityInMilliseconds; // 1h


    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }
    public String createToken(String username,  String tenantName) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("tenantId" ,tenantName);
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);
        return Jwts.builder()//
                .setClaims(claims)//
                .setIssuedAt(now)//
                .setExpiration(validity)//
                .signWith(SignatureAlgorithm.HS256, secretKey)//
                .compact();
    }
}
