package restmocker.backend.infrastructure.mapper;
import org.mapstruct.Mapper;
import restmocker.backend.domain.dto.Todo;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface TodoMapper {

  Todo mapToTodo(restmocker.backend.infrastructure.model.Todo todo);

  List<Todo> mapToTodos(List<restmocker.backend.infrastructure.model.Todo> todos);
}
