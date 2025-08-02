package restmocker.backend.application;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;
import java.util.UUID;
import restmocker.backend.domain.RandomResourceGenerator;

@Produces(MediaType.APPLICATION_JSON)
@Path("/random")
public class RandomResource {

  private final RandomResourceGenerator generator;
  private final RandomResourceMapper mapper;

  @Inject
  public RandomResource(RandomResourceGenerator generator, RandomResourceMapper mapper) {
    this.generator = generator;
    this.mapper = mapper;
  }

  @GET
  @Path("/colors")
  public Response getRandomColors(@QueryParam("count") @DefaultValue("10") int count) {
    return Response.ok(mapper.mapToColorDtoList(generator.generateColors(count))).build();
  }

  @GET
  @Path("/users")
  public Response getRandomUsers(@CookieParam("userId") String userId,
                                 @QueryParam("count") @DefaultValue("10") int count) {

    if (userId == null) {

      userId = UUID.randomUUID().toString();
      NewCookie cookie = new NewCookie.Builder("userId")
          .value(userId)
          .path("/")
          .maxAge(365 * 24 * 60 * 60)
          .build();
      generator.generateUsers(userId, count);

      return Response.ok(mapper.mapToUserDtoList(generator.getUsers(userId)))
          .cookie(cookie)
          .build();
    }

    generator.generateUsers(userId, count);
    return Response.ok(mapper.mapToUserDtoList(generator.getUsers(userId))).build();
  }
}