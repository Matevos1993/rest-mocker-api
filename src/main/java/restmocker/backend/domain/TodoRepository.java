package restmocker.backend.domain;

import restmocker.backend.domain.dto.PaginatedTodos;
import restmocker.backend.domain.dto.Todo;

import java.util.List;

public interface TodoRepository {

  PaginatedTodos getPaginatedTodos(int page, int limit, String sort, String order);

  List<Todo> getTodos();

  Todo getTodo(long id);

  Todo createTodo(Todo todo);

  Todo updateTodo(long id, Todo todo);

  void deleteTodos(List<Long> ids);

  List<Todo> getTodosByUserId(int userId);
}
