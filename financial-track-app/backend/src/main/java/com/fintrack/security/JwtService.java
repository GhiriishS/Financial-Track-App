package com.fintrack.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

  private final Key key;
  private final int expirationMinutes;

  public JwtService(
      @Value("${app.jwt.secret}") String secret,
      @Value("${app.jwt.expirationMinutes}") int expirationMinutes
  ) {
    this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    this.expirationMinutes = expirationMinutes;
  }

  public String generateToken(String subjectEmail) {
    Instant now = Instant.now();
    Instant exp = now.plusSeconds((long) expirationMinutes * 60L);

    return Jwts.builder()
        .setSubject(subjectEmail)
        .setIssuedAt(Date.from(now))
        .setExpiration(Date.from(exp))
        .signWith(key, SignatureAlgorithm.HS256)
        .compact();
  }

  public String parseSubject(String token) {
    return parser().parseClaimsJws(token).getBody().getSubject();
  }

  public boolean isValid(String token) {
    try {
      parser().parseClaimsJws(token);
      return true;
    } catch (JwtException | IllegalArgumentException e) {
      return false;
    }
  }

  private JwtParser parser() {
    return Jwts.parserBuilder().setSigningKey(key).build();
  }
}
