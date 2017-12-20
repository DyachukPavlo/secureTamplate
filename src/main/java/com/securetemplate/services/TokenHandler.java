package com.securetemplate.services;

import com.google.common.io.BaseEncoding;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class TokenHandler {

    private final SecretKey secretKey;

    public TokenHandler() {
        String jwtKey = "jwtkey1234567890";
        byte[] decodedKey = BaseEncoding.base64().decode(jwtKey);
        secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
    }

    public String extractUserName(String token) throws ExpiredJwtException {
        String name = "";
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            Claims body = claimsJws.getBody();
            name = body.getSubject();

        return name;
    }

    public Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            Claims body = claimsJws.getBody();
            expiration = body.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    public String generateAccessToken(String userName, LocalDateTime expires) {
        return Jwts.builder()
                .setSubject(userName)
                .setExpiration(Date.from(expires.atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }
}
