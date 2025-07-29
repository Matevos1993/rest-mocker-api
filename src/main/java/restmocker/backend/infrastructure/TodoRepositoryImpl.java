package restmocker.backend.infrastructure;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import restmocker.backend.domain.TodoRepository;
import restmocker.backend.domain.dto.Todo;
import restmocker.backend.infrastructure.mapper.TodoMapper;

import java.util.List;

@ApplicationScoped
public class TodoRepositoryImpl extends AbstractRepository implements TodoRepository {

  private final TodoMapper mapper;
  private List<restmocker.backend.infrastructure.model.Todo> t;

  @Inject
  public TodoRepositoryImpl(TodoMapper mapper) {
    this.mapper = mapper;
  }

  @Override
  public List<Todo> getTodos() {

    List<restmocker.backend.infrastructure.model.Todo> todos = entityManager.createQuery("""
            select todo
              from Todo todo
            """, restmocker.backend.infrastructure.model.Todo.class)
        .getResultList();

    todos.addAll(t);

    return mapper.mapToTodos(todos);
  }

  @Override
  public Todo getTodo(long id) {

    restmocker.backend.infrastructure.model.Todo todo = findTodoById(id);

    if (todo == null) throw new NotFoundException("Todo with id " + id + " does not exist");

    return mapper.mapToTodo(todo);
  }

  @Override
  @Transactional
  public Todo createTodo(Todo todo) {

    if (todo == null) throw new IllegalArgumentException("Todo cannot be null");

    return todo;
  }

  @Override
  @Transactional
  public void updateTodo(long id, Todo todo) {
    restmocker.backend.infrastructure.model.Todo t = findTodoById(id);

    if (t == null) throw new NotFoundException("Todo with id " + id + " does not exist");
  }

  @Override
  public void deleteTodos(List<Long> ids) {

    for (Long id : ids) {
      restmocker.backend.infrastructure.model.Todo todo = findTodoById(id);

      if (todo == null) throw new NotFoundException("Todo with id " + id + " does not exist");
    }
  }

  private restmocker.backend.infrastructure.model.Todo findTodoById(long id) {

    restmocker.backend.infrastructure.model.Todo todo = entityManager.createQuery("""
            select todo
              from Todo todo
             where todo.id = :id
            """, restmocker.backend.infrastructure.model.Todo.class)
        .setParameter("id", id)
        .getResultList()
        .stream()
        .findFirst()
        .orElse(null);

    if (todo == null && t != null && !t.isEmpty()) {
      todo = t.stream()
          .filter(x -> x.getId() == id)
          .findFirst()
          .orElse(null);
    }

    return todo;
  }
}
