package com.springsecuritytutorial.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;


@Service
public class JwtService {

    private String secretKey = "";

    public JwtService() {
        try {
            KeyGenerator hmacSHA256Gen = KeyGenerator.getInstance("HmacSHA256");

            SecretKey secret_key = hmacSHA256Gen.generateKey();

            secretKey = Base64.getEncoder().encodeToString(secret_key.getEncoded());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String generateToken(String userName) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userName", userName);


        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(userName)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 60 * 60 * 10))
                .and()
                .signWith(getKey())
                .compact();
    }

    private Key getKey() {

        // if uncomment this, then uncomment constructor
//        try {
//            KeyGenerator hmacSHA256Gen = KeyGenerator.getInstance("HmacSHA256");
//
//            SecretKey secret_key = hmacSHA256Gen.generateKey();
//
//            secretKey = Base64.getEncoder().encodeToString(secret_key.getEncoded());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        byte[] decode = Decoders.BASE64.decode(secretKey);

        return Keys.hmacShaKeyFor(decode);
    }

//    getting payload
    private Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith(decryptKey(secretKey)).build()
                .parseSignedClaims(token)
                .getPayload();
    }


    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private SecretKey decryptKey(String secretKey) {
        byte[] decode = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(decode);
    }

    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        String username = extractUserName(token);
        Boolean expired = isTokenExpired(token);
        return username.equals(userDetails.getUsername()) && !expired;
    }

    private Boolean isTokenExpired(String token) {
        Date expiredDate = extractExpiration(token);
        return expiredDate.before(new Date());
    }
}