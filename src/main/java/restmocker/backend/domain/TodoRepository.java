package restmocker.backend.domain;

import restmocker.backend.domain.dto.Todo;

import java.util.List;

public interface TodoRepository {

  List<Todo> getTodos();

  Todo getTodo(long id);

  Todo createTodo(Todo todo);

  void updateTodo(long id, Todo todo);

  void deleteTodos(List<Long> ids);
}
