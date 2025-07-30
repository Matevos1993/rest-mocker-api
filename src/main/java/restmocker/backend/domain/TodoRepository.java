package restmocker.backend.domain;

import restmocker.backend.domain.dto.Todo;

import java.util.List;

public interface TodoRepository {

  List<Todo> getTodos(int offset, int limit, String sort, String order);

  Todo getTodo(long id);

  Todo createTodo(Todo todo);

  Todo updateTodo(long id, Todo todo);

  void deleteTodos(List<Long> ids);
}
