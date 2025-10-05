package restmocker.backend.domain;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotAuthorizedException;
import restmocker.backend.domain.dto.AuthRequest;
import restmocker.backend.domain.dto.Token;
import restmocker.backend.domain.dto.TokenBody;
import restmocker.backend.domain.dto.User;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class AuthRepository {

  private final UserGenerator gen;
  private final TokenService tokenService;

  @Inject
  public AuthRepository(UserGenerator gen, TokenService tokenService) {
    this.tokenService = tokenService;
    this.gen = gen;
  }

  public Token authorizeUser(String userId, AuthRequest request) {

    User user = getUsers(userId).stream().filter(u -> Objects.equals(u.getUsername(), request.getUsername()) && Objects.equals(u.getPassword(), request.getPassword())).findFirst().orElse(null);

    if (user == null) throw new NotAuthorizedException("Username or password is invalid");
    LocalDateTime now = LocalDateTime.now();
    LocalDateTime todayMidnight = now.withHour(23).withMinute(59).withSecond(59).withNano(0);

    if (now.isAfter(todayMidnight)) {
      todayMidnight = todayMidnight.plusDays(1);
    }

    Date expiresAt = Date.from(todayMidnight.atZone(ZoneId.systemDefault()).toInstant());

    TokenBody tokenBody = new TokenBody();
    tokenBody.setId(user.getId());
    tokenBody.setEmail(user.getEmail());
    tokenBody.setUsername(user.getUsername());
    tokenBody.setExpiresIn(expiresAt);

    Token token = new Token();
    token.setToken("Bearer " + tokenService.generateToken(tokenBody));

    return token;
  }

  private List<User> getUsers(String userId) {
    return gen.getUsers(userId);
  }
}
