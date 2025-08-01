package restmocker.backend.application;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Map;

@Produces(MediaType.APPLICATION_JSON)
@Path("/http")
public class ResponseResource {

  @GET
  @Path("/{status}")
  public Response getResponse(@PathParam("status") int status) {

    if (status < 100 || status > 599) return Response
        .status(Response.Status.BAD_REQUEST)
        .entity("{\"error\":\"Invalid HTTP status code.\"}")
        .build();

    String message;

    if (status == 200) {
      message = "ok";
    } else if (Response.Status.fromStatusCode(status) != null) {
      message = Response.Status.fromStatusCode(status).getReasonPhrase().toLowerCase();
    } else {
      message = "unknown";
    }

    return Response.status(status)
        .entity(Map.of("status", status, "message", message))
        .build();
  }

  @GET
  @Path("/{status}/{message}")
  public Response getYourOwnResponse(@PathParam("message") String message,
                                     @PathParam("status") int status) {

    if (status < 100 || status > 599) return Response
        .status(Response.Status.BAD_REQUEST)
        .entity("{\"error\":\"Invalid HTTP status code.\"}")
        .build();

    return Response.status(status)
        .entity(String.format("{\"status\": %d, \"message\": \"%s\"}", status, message))
        .type(MediaType.APPLICATION_JSON)
        .build();
  }
}
