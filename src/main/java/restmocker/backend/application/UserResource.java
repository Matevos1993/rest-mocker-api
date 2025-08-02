package restmocker.backend.application;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;
import restmocker.backend.application.dto.UserDto;
import restmocker.backend.domain.RandomResourceGenerator;

import java.util.UUID;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/users")
public class UserResource {

  private final RandomResourceGenerator generator;
  private final RandomResourceMapper mapper;

  @Inject
  public UserResource(RandomResourceGenerator generator, RandomResourceMapper mapper) {
    this.generator = generator;
    this.mapper = mapper;
  }

  @GET
  public Response getRandomUsers(@CookieParam("userId") String userId,
                                 @QueryParam("count") @DefaultValue("10") int count) {

    if (userId == null) {

      userId = UUID.randomUUID().toString();
      NewCookie cookie = CookieUtil.createUserIdCookie(userId);
      generator.generateUsers(userId, count);

      return Response.ok(mapper.mapToUserDtoList(generator.getUsers(userId)))
          .cookie(cookie)
          .build();
    }

    generator.generateUsers(userId, count);

    return Response.ok(mapper.mapToUserDtoList(generator.getUsers(userId))).build();
  }

  @GET
  @Path("/{id}")
  public Response getUserById(@PathParam("id") int id,
                              @CookieParam("userId") String userId) {

    NewCookie cookie = null;

    if (userId == null) {
      userId = UUID.randomUUID().toString();
      cookie = CookieUtil.createUserIdCookie(userId);
      generator.generateUsers(userId, id);
    }

    if (generator.getUsers(userId) == null || generator.getUsers(userId).isEmpty()) {
      generator.generateUsers(userId, id);
    }

    UserDto userDto = mapper.mapToUserDto(generator.getUserById(id, userId));
    Response.ResponseBuilder responseBuilder = Response.ok(userDto);
    if (cookie != null) {
      responseBuilder.cookie(cookie);
    }
    return responseBuilder.build();
  }
}
