package restmocker.backend.application;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
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
  public void updateTodo(@PathParam("id") Long id, TodoDto todoDto) {

    todoRepository.updateTodo(id, todoMapper.mapToTodo(todoDto));
  }

  @DELETE
  public void deleteTodos(@QueryParam("ids") List<Long> ids) {

    todoRepository.deleteTodos(ids);
  }
}
