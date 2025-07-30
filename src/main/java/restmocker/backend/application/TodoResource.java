package restmocker.backend.application;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import restmocker.backend.application.dto.PaginatedTodosDto;
import restmocker.backend.application.dto.TodoDto;
import restmocker.backend.domain.TodoRepository;

import java.util.List;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/todos")
public class TodoResource {

  private final TodoRepository todoRepository;
  private final TodoMapper todoMapper;

  public TodoResource(TodoRepository todoRepository, TodoMapper todoMapper) {
    this.todoRepository = todoRepository;
    this.todoMapper = todoMapper;
  }

  @GET
  public PaginatedTodosDto getTodos(@QueryParam("offset") @DefaultValue("0") int offset,
                                    @QueryParam("limit") @DefaultValue("100") int limit,
                                    @QueryParam("sort") @DefaultValue("id") String sort,
                                    @QueryParam("order") @DefaultValue("asc") String order) {

    if (offset < 0 || limit <= 0) {
      offset = 0;
      limit = 100;
    }

    if (!List.of("id", "todo", "completed", "createdAt", "updatedAt").contains(sort.toLowerCase())) {
      sort = "id";
    }

    if (!List.of("asc", "desc").contains(order.toLowerCase())) {
      order = "asc";
    }

    return todoMapper.mapToPaginatedTodosDto(todoRepository.getPaginatedTodos(offset, limit, sort, order));
  }

  @GET
  public List<TodoDto> getTodos() {

    return todoMapper.mapToTodoDtos(todoRepository.getTodos());
  }

  @GET
  @Path("/{id}")
  public TodoDto getTodoById(@PathParam("id") Long id) {

    return todoMapper.mapToTodoDto(todoRepository.getTodo(id));
  }

  @POST
  public TodoDto createTodo(TodoDto todoDto) {

    return todoMapper.mapToTodoDto(todoRepository.createTodo(todoMapper.mapToTodo(todoDto)));
  }

  @PUT
  @Path("/{id}")
  public TodoDto updateTodo(@PathParam("id") Long id, TodoDto todoDto) {

    return todoMapper.mapToTodoDto(todoRepository.updateTodo(id, todoMapper.mapToTodo(todoDto)));
  }

  @DELETE
  public void deleteTodos(@QueryParam("id") List<Long> ids) {

    todoRepository.deleteTodos(ids);
  }
}
