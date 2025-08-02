package restmocker.backend.application;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;
import restmocker.backend.application.dto.UserDto;
import restmocker.backend.domain.UserGenerator;

@Produces(MediaType.APPLICATION_JSON)
@Path("/users")
public class UserResource {

  private final UserGenerator generator;
  private final UserResourceMapper mapper;

  @Inject
  public UserResource(UserGenerator generator, UserResourceMapper mapper) {
    this.generator = generator;
    this.mapper = mapper;
  }

  @GET
  public Response getRandomUsers(@CookieParam("userId") String userId,
                                 @QueryParam("count") @DefaultValue("10") int count) {

    NewCookie cookie = null;

    if (userId == null) {
      CookieUtil.UserIdCookie userIdCookie = CookieUtil.generateNewUserIdAndCookie();
      userId = userIdCookie.userId;
      cookie = userIdCookie.cookie;
      generator.generateUsers(userId, count);
    } else {
      generator.generateUsers(userId, count);
    }

    Response.ResponseBuilder responseBuilder = Response.ok(mapper.mapToUserDtoList(generator.getUsers(userId)));

    if (cookie != null) responseBuilder.cookie(cookie);

    return responseBuilder.build();
  }

  @GET
  @Path("/{id}")
  public Response getUserById(@PathParam("id") int id,
                              @CookieParam("userId") String userId) {

    Response validation = validateRequest(userId, id);

    if (validation != null) return validation;

    return Response.ok(mapper.mapToUserDto(generator.getUserById(id, userId))).build();
  }

  @PUT
  @Path("/{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response updateUser(@PathParam("id") int id,
                             @CookieParam("userId") String userId,
                             UserDto user) {

    Response validation = validateRequest(userId, id);

    if (validation != null) return validation;

    return Response.ok(mapper.mapToUserDto(generator.updateUser(userId, id, mapper.mapToUser(user)))).build();
  }

  private Response validateRequest(String userId, int id) {
    if (!generator.isUserListValid(userId)) {

      return Response.status(Response.Status.BAD_REQUEST)
          .entity("{\"error\": \"You must generate users first by calling /users.\"}")
          .build();
    } else if (!generator.isUserIdValid(userId, id)) {

      return Response.status(Response.Status.NOT_FOUND)
          .entity(String.format("{\"error\": \"User with id  %d not found.\"}", id))
          .build();
    }

    return null;
  }
}
