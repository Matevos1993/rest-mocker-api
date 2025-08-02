package restmocker.backend.application;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.NewCookie;
import lombok.AllArgsConstructor;

import java.util.UUID;

@ApplicationScoped
public class CookieUtil {

  private CookieUtil() {}

  public static NewCookie createUserIdCookie(String userId) {

    return new NewCookie.Builder("userId")
        .value(userId)
        .path("/")
        .maxAge(365 * 24 * 60 * 60)
        .build();
  }

  public static UserIdCookie generateNewUserIdAndCookie() {

    String userId = UUID.randomUUID().toString();
    NewCookie cookie = createUserIdCookie(userId);
    return new UserIdCookie(userId, cookie);
  }

  @AllArgsConstructor
  public static class UserIdCookie {

    public final String userId;
    public final NewCookie cookie;
  }
}
