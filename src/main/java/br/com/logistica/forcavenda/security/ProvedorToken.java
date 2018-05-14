package br.com.logistica.forcavenda.security;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class ProvedorToken {
  private static final Logger logger = LoggerFactory.getLogger(ProvedorToken.class);

  @Value("${app.jwtSecret}")
  private String jwtSecret;

  @Value("${app.jwtExpirationInMs}")
  private int jwtExpirationInMs;

  public String generateToken(Authentication authentication) {

    UsuarioPrincipal userDetails = (UsuarioPrincipal) authentication.getPrincipal();
    Date expiryDate = new Date(new Date().getTime() + jwtExpirationInMs);

    return Jwts.builder()
      .setSubject(userDetails.getId().toString())
      .setIssuedAt(new Date())
      .setExpiration(expiryDate)
      .signWith(SignatureAlgorithm.HS512, jwtSecret)
      .compact();
  }

  public String getUserIdFromJWT(String token) {
    Claims claims = Jwts.parser()
      .setSigningKey(jwtSecret)
      .parseClaimsJws(token)
      .getBody();

    return claims.getSubject();
  }

  public boolean validateToken(String authToken) {
    try {
      Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
      return true;
    } catch (SignatureException ex) {
      logger.error("Assinatura inválida do JWT");
    } catch (MalformedJwtException ex) {
      logger.error("Token JWT inválido");
    } catch (ExpiredJwtException ex) {
      logger.error("Token JWT expirado");
    } catch (UnsupportedJwtException ex) {
      logger.error("Token JWT não suportado");
    } catch (IllegalArgumentException ex) {
      logger.error("A string de declarações do JWT está vazia");
    }
    return false;
  }
}
