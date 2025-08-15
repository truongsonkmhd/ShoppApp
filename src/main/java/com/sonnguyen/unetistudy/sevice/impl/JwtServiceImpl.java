package com.example.ShoppApp.sevice.impl;

import com.example.ShoppApp.common.TokenType;
import com.example.ShoppApp.exception.InvalidDataException;
import com.example.ShoppApp.sevice.JwtService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.*;
import java.util.function.Function;
import io.jsonwebtoken.*;
import org.springframework.security.access.AccessDeniedException;

@Service
@Slf4j(topic = "JWT-SERVICE")
public class JwtServiceImpl implements JwtService {
    @Value("${jwt.expiryMinutes}")
    private long expiryMinutes;

    @Value("${jwt.expiryDay}")
    private long expiryDay;

    @Value("${jwt.accessKey}")
    private String accessKey;

    @Value("${jwt.refreshKey}")
    private String refreshKey;
    @Override
    public String generateAccessToken(long userId, String username, Collection<? extends GrantedAuthority> authorities) {
        log.info("Generate access token for user {} with authorities{}" , userId, authorities);
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId",userId);
        claims.put("role", authorities);

        return generateToken(claims,username);
    }

    @Override
    public String generateRefreshToken(long userId, String username, Collection<? extends GrantedAuthority> authorities) {
        log.info("Generate refresh token for user {} with authorities" , userId, authorities);

        Map<String, Object> claims = new HashMap<>();
        claims.put("userId",userId);
        claims.put("role", authorities);
        return generateToken(claims,username);
    }

    // để giải nén token có đúng hay không , còn có hạn hay không , cũng như là xem hàm có hợp lệ hay không
    @Override
    public String extractUsername(String token, TokenType type) {
        log.info("Extract username from token {} with type {}" ,token, type);

        return extractClaims(type,token,Claims::getSubject);
    }

    private <T> T extractClaims(TokenType type , String token , Function<Claims, T> claimsTFunctor){
        final  Claims claims = extraAllClaim(token,type);
        return claimsTFunctor.apply(claims);
    }


    private Claims extraAllClaim(String token, TokenType type) {
        try {
            return Jwts.parser()
                    .setSigningKey(accessKey)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException | SignatureException | MalformedJwtException e) {
            throw new AccessDeniedException("Access denied, error: " + e.getMessage());
        }
    }


    private String generateToken(Map<String,Object> claims, String username){
        log.info("Generate access token for user {} with name {}", username , claims);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000* 60 * expiryMinutes))
                .signWith(getKey(TokenType.ACCESS_TOKEN), SignatureAlgorithm.ES256)
                .compact();
    }

    private String generateRefreshToken(Map<String,Object> claims, String username){
        log.info("Generate refresh token for user {} with name {}", username , claims);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000* 60 * 60 * 24 * expiryMinutes))
                .signWith(getKey(TokenType.REFRESH_TOKEN), SignatureAlgorithm.ES256)
                .compact();
    }

    private Key getKey(TokenType type){
        switch (type){
            case ACCESS_TOKEN -> {
                return Keys.hmacShaKeyFor(Decoders.BASE64.decode(accessKey));
            }
            case REFRESH_TOKEN -> {
                return Keys.hmacShaKeyFor(Decoders.BASE64.decode(refreshKey));
            }
            default -> throw new InvalidDataException("Invalid token type");
        }
    }
}
