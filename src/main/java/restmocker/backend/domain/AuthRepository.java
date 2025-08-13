package restmocker.backend.domain;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotAuthorizedException;
import restmocker.backend.domain.dto.AuthRequest;
import restmocker.backend.domain.dto.User;

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

  public String authorizeUser(String userId, AuthRequest request) {

    User user = getUsers(userId).stream().filter(u -> Objects.equals(u.getUsername(), request.getUsername()) && Objects.equals(u.getPassword(), request.getPassword())).findFirst().orElse(null);

    if (user == null) throw new NotAuthorizedException("Username or password is invalid");

    return "Bearer " + tokenService.generateToken(request.getUsername(), user.getEmail(), user.getId());
  }

  private List<User> getUsers(String userId) {
    return gen.getUsers(userId);
  }
}
