package restmocker.backend.infrastructure;

import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import restmocker.backend.domain.TodoRepository;
import restmocker.backend.domain.dto.Todo;
import restmocker.backend.infrastructure.mapper.TodoMapper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@ApplicationScoped
public class TodoRepositoryImpl extends AbstractRepository implements TodoRepository {

  private final TodoMapper mapper;
  private final List<restmocker.backend.infrastructure.model.Todo> todos = new CopyOnWriteArrayList<>();
  private int nextId;

  @Inject
  public TodoRepositoryImpl(TodoMapper mapper) {
    this.mapper = mapper;
  }

  @Scheduled(every = "30m")
  public void clearTodos() {

    todos.clear();
    nextId = 0;
  }

  public List<Todo> getTodos(int offset, int limit, String sort, String order) {

    loadTodosIfEmpty();

    List<restmocker.backend.infrastructure.model.Todo> sortedTodos = new ArrayList<>(todos);

    sortList(sort, order, sortedTodos);

    if (offset >= sortedTodos.size()) {
      return new ArrayList<>();
    }

    List<restmocker.backend.infrastructure.model.Todo> paginatedTodos = sortedTodos.subList(offset, Math.min(offset + limit, sortedTodos.size()));

    return mapper.mapToTodos(paginatedTodos);
  }

  private static void sortList(String sort, String order, List<restmocker.backend.infrastructure.model.Todo> sortedTodos) {
    Comparator<restmocker.backend.infrastructure.model.Todo> comparator = switch (sort.toLowerCase()) {
      case "todo" -> Comparator.comparing(restmocker.backend.infrastructure.model.Todo::getTodo);
      case "completed" -> Comparator.comparing(restmocker.backend.infrastructure.model.Todo::getCompleted);
      case "createdat" -> Comparator.comparing(restmocker.backend.infrastructure.model.Todo::getCreatedAt);
      case "updatedat" -> Comparator.comparing(restmocker.backend.infrastructure.model.Todo::getUpdatedAt);
      default -> Comparator.comparingLong(restmocker.backend.infrastructure.model.Todo::getId);
    };

    if ("desc".equalsIgnoreCase(order)) {
      comparator = comparator.reversed();
    }

    sortedTodos.sort(comparator);
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

    loadTodosIfEmpty();

    if (todo == null) throw new IllegalArgumentException("Todo cannot be null");

    todo.setId(++nextId);
    todo.setCreatedAt(new Date());
    todo.setCompleted(todo.getCompleted() != null && todo.getCompleted());

    todos.add(mapper.mapToModel(todo));

    return todo;
  }

  @Override
  @Transactional
  public Todo updateTodo(long id, Todo todo) {

    restmocker.backend.infrastructure.model.Todo t = findTodoById(id);

    if (t == null) throw new NotFoundException("Todo with id " + id + " does not exist");

    t.setTodo(todo.getTodo() == null ? t.getTodo() : todo.getTodo());
    t.setCompleted(todo.getCompleted() == null ? t.getCompleted() : todo.getCompleted());
    t.setUpdatedAt(new Date());

    return mapper.mapToTodo(t);
  }

  @Override
  public void deleteTodos(List<Long> ids) {

    for (Long id : ids) {
      restmocker.backend.infrastructure.model.Todo todo = findTodoById(id);

      todos.remove(todo);
    }
  }

  private restmocker.backend.infrastructure.model.Todo findTodoById(long id) {

    if (todos.isEmpty()) loadTodosIfEmpty();

    return todos.stream()
        .filter(todo -> todo.getId() == id)
        .findFirst()
        .orElse(null);
  }

  private void loadTodosIfEmpty() {

    if (todos.isEmpty()) todos.addAll(entityManager.createQuery("""
            select todo
              from Todo todo
            """, restmocker.backend.infrastructure.model.Todo.class)
        .getResultList());

    nextId = todos.size();
  }
}
