package restmocker.backend.application;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
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
}