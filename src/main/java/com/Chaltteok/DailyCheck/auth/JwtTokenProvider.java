package com.Chaltteok.DailyCheck.auth;

import com.Chaltteok.DailyCheck.dto.LoginDTO;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.ZonedDateTime;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {
    private final Key key;
    private final long accessTokenExpTime = 86400000;

    public JwtTokenProvider(
//            @Value("${jwt.secret}") String secretKey,
//            @Value("${jwt.expiration_time}") long accessTokenExpTime
    ) {
            String secretKey = "64461f01e1af406da538b9c48d801ce59142452199ff112fb5404c8e7e98e3ff";
            byte[] keyBytes = Decoders.BASE64.decode(secretKey);
            this.key = Keys.hmacShaKeyFor(keyBytes);
            //this.accessTokenExpTime = accessTokenExpTime;
    }

    public String createAccessToken(LoginDTO loginDTO) {
        return createToken(loginDTO, accessTokenExpTime);
    }

    private String createToken(LoginDTO loginDTO, long expireTime) {
        Claims claims = Jwts.claims();
        claims.put("name", loginDTO.getName());

        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime tokenValidity = now.plusSeconds(expireTime);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(now.toInstant()))
                .setExpiration(Date.from(tokenValidity.toInstant()))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Long getUserId(String token){
        return parseClaims(token).get("userId", Long.class);
    }

    public String getUserName(String token){
        return parseClaims(token).get("userName", String.class);
    }

    public boolean validateToken(String token) {
        try{
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SignatureException | MalformedJwtException e){
            log.info("Invalid JWT token", e);
        } catch (ExpiredJwtException e){
            log.info("Expired JWT token", e);
        } catch (UnsupportedJwtException e){
            log.info("Unsupported JWT token", e);
        } catch (IllegalArgumentException e){
            log.info("JWT claims string is empty", e);
        }
        return false;
    }

    public Claims parseClaims(String accessToken){
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        }catch (ExpiredJwtException e){
            return e.getClaims();
        }
    }
}
