package com.booklnad.bookland.security;

import com.booklnad.bookland.DB.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Slf4j
@Component
public class JwpProvider {
    private static final Logger logger = LoggerFactory.getLogger(JwpProvider.class);
    private final SecretKey jwtTokenSecret;

    private final SecretKey jwtRefreshSecret;

    public JwpProvider(
            @Value("${jwt.secret}") String jwtAccessSecret,
            @Value("${jwt.secret}") String jwtRefreshSecret
    ) {
        this.jwtTokenSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtAccessSecret));
        this.jwtRefreshSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtRefreshSecret));
    }

    public String generateAccessToken(@NonNull User user){
        final LocalDateTime now = LocalDateTime.now();
        final Instant accessExpirationInstant = now.plusHours(1).atZone(ZoneId.systemDefault()).toInstant();
        final Date accessExpiration = Date.from(accessExpirationInstant);
        return Jwts.builder()
                .subject(user.getLogin())
                .expiration(accessExpiration)
                .signWith(jwtTokenSecret)
                .claim("role", user.getRole())
                .claim("name", user.getName())
                .compact();
    }

    public String generateRefreshToken(@NonNull User user){
        final LocalDateTime now = LocalDateTime.now();
        final Instant accessExpirationInstant = now.plusDays(30).atZone(ZoneId.systemDefault()).toInstant();
        final Date accessExpiration = Date.from(accessExpirationInstant);
        return Jwts.builder()
                .subject(user.getLogin())
                .expiration(accessExpiration)
                .signWith(jwtTokenSecret)
                .compact();
    }

    public Boolean validateAccessToken(@NonNull String assessToken){
        return validateToken(assessToken, jwtTokenSecret);
    }

    public Boolean validateRefreshToken(@NonNull String refreshToken){
        return validateToken(refreshToken, jwtRefreshSecret);
    }

    private Boolean validateToken(String token, SecretKey secret) {
        try {
            Jwts.parser().verifyWith(secret).build().parseSignedClaims(token);
            return true;
        } catch (ExpiredJwtException e){
            logger.error("Срок действия токена истек " + e);
        } catch (UnsupportedJwtException e){
            logger.error("Неподдерживаемый jwt " + e);
        } catch (MalformedJwtException e){
            logger.error("Деформированный jwt " + e);
        } catch (SignatureException e){
            logger.error("Недействительная подпись " + e);
        } catch (Exception e){
            logger.error("неверный токен", e);
        }
        return false;
    }

    public Claims getAccessClaims(@NonNull String token){
        return getClaims(token, jwtTokenSecret);
    }

    public Claims getRefreshClaims(@NonNull String token){
        return getClaims(token, jwtRefreshSecret);
    }

    private Claims getClaims(String token, SecretKey secret) {
        return Jwts.parser().verifyWith(secret).build().parseSignedClaims(token).getPayload();
    }
}
