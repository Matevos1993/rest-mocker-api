package restmocker.backend.domain;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.inject.Singleton;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import restmocker.backend.domain.dto.TokenBody;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

@Singleton
public class TokenService {

  @ConfigProperty(name = "jwt.secret")
  String secretKey;

  public String generateToken(TokenBody tokenBody) {
    byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
    SecretKey key = new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());

    return Jwts.builder()
        .claim("id", tokenBody.getId())
        .claim("email", tokenBody.getEmail())
        .claim("username", tokenBody.getUsername())
        .claim("expiresIn", tokenBody.getExpiresIn())
        .signWith(key, SignatureAlgorithm.HS256)
        .compact();
  }
}