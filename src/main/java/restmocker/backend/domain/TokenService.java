package restmocker.backend.domain;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.inject.Singleton;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

@Singleton
public class TokenService {

  @ConfigProperty(name = "jwt.secret")
  String secretKey;

  public String generateToken(String username, String email, int id) {
    byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
    SecretKey key = new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());

    return Jwts.builder()
        .claim("email", email)
        .claim("username", username)
        .claim("id", id)
        .signWith(key, SignatureAlgorithm.HS256)
        .compact();
  }
}