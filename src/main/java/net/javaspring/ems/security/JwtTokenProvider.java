package net.javaspring.ems.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.core.Local;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app.jwt-expiration-milliseconds}")
    private String jwtExpirationDate;

    //Generate jwt token
    public String generateToken(Authentication authentication){
        String usernameOrEmail = authentication.getName();

        LocalDateTime currentDateTime = LocalDateTime.now();

// Calculate expiration date/time based on current date/time and expiration duration
        LocalDateTime expireDateTime = currentDateTime.plus(Long.parseLong(jwtExpirationDate), ChronoUnit.MILLIS);

// Convert LocalDateTime to Date using the system default time zone
        Date currentDate = Date.from(currentDateTime.atZone(ZoneId.systemDefault()).toInstant());
        Date expireDate = Date.from(expireDateTime.atZone(ZoneId.systemDefault()).toInstant());

        String token = Jwts.builder()
                .setSubject(usernameOrEmail)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(key())
                .compact();

        return token;
    }

    private Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String getUsername(String token){
        Claims claims  = Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody();

        String usernameOrEmail = claims.getSubject();

        return usernameOrEmail;
    }

    public boolean validateToken(String token){
        Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parse(token);
        return true;
    }

    public String getTokenFromRequest(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");

        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

}
