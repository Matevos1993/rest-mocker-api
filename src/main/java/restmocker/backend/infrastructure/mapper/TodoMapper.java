package restmocker.backend.infrastructure.mapper;
import org.mapstruct.Mapper;
import restmocker.backend.domain.dto.PaginatedTodos;
import restmocker.backend.domain.dto.Todo;
import restmocker.backend.infrastructure.model.TodoModel;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface TodoMapper {

  Todo mapToTodo(TodoModel todo);

  List<Todo> mapToTodos(List<TodoModel> todos);

  TodoModel mapToModel(Todo todo);

  default PaginatedTodos mapToPaginatedTodos(List<TodoModel> todos, int totalCount, int offset, int limit, String sort, String order) {

    PaginatedTodos paginatedTodos = new PaginatedTodos();
    paginatedTodos.setTodos(mapToTodos(todos));
    paginatedTodos.setTotalCount(totalCount);
    paginatedTodos.setOffset(offset);
    paginatedTodos.setLimit(limit);
    paginatedTodos.setSort(sort);
    paginatedTodos.setOrder(order);
    paginatedTodos.setTotalPages((int) Math.ceil((double) totalCount / limit));
    paginatedTodos.setCurrentPage((offset / limit) + 1);
    return paginatedTodos;
  }
}
