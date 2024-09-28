package com.booklnad.bookland.security.jwt;

import com.booklnad.bookland.dto.JwtAuthenticationDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JWTService {
    private static final Logger logger = LoggerFactory.getLogger(JWTService.class);
    @Value("c8bb43b04e3c6e32c58e9ce7145feefb805bab622f19cade523b4468bc439b7d")
    private String jwtSecret;

    public JwtAuthenticationDto generateAuthToken(String login){
        JwtAuthenticationDto jwtDto = new JwtAuthenticationDto();
        jwtDto.setToken(generateJwtToken(login));
        jwtDto.setRefreshToken(generateRefreshToken(login));
        return jwtDto;
    }

    public JwtAuthenticationDto refreshBaseToken(String login, String refreshToken){
        JwtAuthenticationDto jwtDto = new JwtAuthenticationDto();
        jwtDto.setToken(generateJwtToken(login));
        jwtDto.setRefreshToken(refreshToken);
        return jwtDto;
    }

    public boolean validateJwtToken(String token){
        try{
            Jwts.parser()
                    .verifyWith(getSingInKey()).build()
                    .parseSignedClaims(token)
                    .getPayload();
            return true;
        } catch (ExpiredJwtException e){
            logger.error("Expired JwtException ", e);
        } catch (UnsupportedJwtException e){
            logger.error("Unsupported JwtException ", e);
        } catch (MalformedJwtException e){
            logger.error("Malformed JwtException ", e);
        } catch (SecurityException e){
            logger.error("Security Exception ", e);
        } catch (Exception e){
            logger.error("Exception ", e);
        }
        return false;
    }

    public String getLoginFromToken(String token){
        Claims claims = Jwts.parser().verifyWith(getSingInKey()).build()
                .parseSignedClaims(token).getPayload();
        return claims.getSubject();
    }

    private String generateJwtToken(String login){
        Date date = Date.from(LocalDateTime.now().plusHours(2).atZone(ZoneId.systemDefault()).toInstant());
        return Jwts.builder().subject(login).expiration(date).signWith(getSingInKey()).compact();
    }

    private String generateRefreshToken(String login){
        Date date = Date.from(LocalDateTime.now().plusDays(1).atZone(ZoneId.systemDefault()).toInstant());
        return Jwts.builder().subject(login).expiration(date).signWith(getSingInKey()).compact();
    }

    private SecretKey getSingInKey(){
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
