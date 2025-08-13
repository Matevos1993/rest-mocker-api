package restmocker.backend.application;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import restmocker.backend.application.dto.AuthRequest;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/auth")
public class AuthResource {

  private final AuthService authService;

  @Inject
  public AuthResource(AuthService authService) {

    this.authService = authService;
  }

  @POST
  @Path("/login")
  public Response login(@CookieParam("userId") String userId, AuthRequest request) {
    return Response.ok(authService.login(userId, request)).build();
  }
}
