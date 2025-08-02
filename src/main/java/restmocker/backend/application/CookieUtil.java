package restmocker.backend.application;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.NewCookie;

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
}
