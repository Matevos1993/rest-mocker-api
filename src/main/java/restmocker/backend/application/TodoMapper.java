package restmocker.backend.application;

import org.mapstruct.Mapper;
import restmocker.backend.application.dto.PaginatedTodosDto;
import restmocker.backend.application.dto.TodoDto;
import restmocker.backend.domain.dto.PaginatedTodos;
import restmocker.backend.domain.dto.Todo;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface TodoMapper {

  TodoDto mapToTodoDto(Todo todo);

  List<TodoDto> mapToTodoDtos(List<Todo> todos);

  Todo mapToTodo(TodoDto todoDto);

  PaginatedTodosDto mapToPaginatedTodosDto(PaginatedTodos paginatedTodos);
}
