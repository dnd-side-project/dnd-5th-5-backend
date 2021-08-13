package com.meme.ala.core.auth.jwt;

import com.meme.ala.core.error.ErrorCode;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class JwtProvider {
    private final UserDetailsService userDetailsService;
    private final long tokenValidTime = 30 * 60 * 1000L;
    @Value("${jwt.secret}")
    private String secretKey;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String email) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + tokenValidTime);
        Claims claims = Jwts.claims().setSubject("alajwt");
        claims.put("email", email);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserEmail(token));
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    public String getUserEmail(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .get("email", String.class);
    }

    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("X-AUTH_TOKEN");
    }

    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e){
            return false;
        }
    }

    /**
     * Throws:
     * UnsupportedJwtException – if the claimsJws argument does not represent an Claims JWS
     * MalformedJwtException – if the claimsJws string is not a valid JWS
     * SignatureException – if the claimsJws JWS signature validation fails
     * ExpiredJwtException – if the specified JWT is a Claims JWT and the Claims has an expiration time before the time this method is invoked.
     * IllegalArgumentException – if the claimsJws string is null or empty or only whitespace
     */
    public String setInvalidAuthenticationMessage(String jwt){
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwt);
            return "Logic Error : Should Tell to Backend";
        } catch (UnsupportedJwtException | MalformedJwtException e) {
            return ErrorCode.UNSUPPORTED_JWT.getMessage();
        } catch (ExpiredJwtException e) {
            return ErrorCode.EXPIRED_JWT.getMessage();
        } catch (SignatureException e) {
            return ErrorCode.SIGNATURE_INVALID_JWT.getMessage();
        } catch (IllegalArgumentException e) {
            return ErrorCode.JWT_NOT_FOUND.getMessage();
        }
    }

}
