package restmocker.backend.infrastructure;

import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import restmocker.backend.domain.TodoRepository;
import restmocker.backend.domain.dto.PaginatedTodos;
import restmocker.backend.domain.dto.Todo;
import restmocker.backend.infrastructure.mapper.TodoMapper;
import restmocker.backend.infrastructure.model.TodoModel;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@ApplicationScoped
public class TodoRepositoryImpl extends AbstractRepository implements TodoRepository {

  private int nextId;
  private final TodoMapper mapper;
  private final List<TodoModel> todos = new CopyOnWriteArrayList<>();

  @Inject
  public TodoRepositoryImpl(TodoMapper mapper) {
    this.mapper = mapper;
  }

  @Scheduled(every = "30m")
  public void clearTodos() {

    todos.clear();
    nextId = 0;
  }

  @Override
  public PaginatedTodos getPaginatedTodos(int page, int limit, String sort, String order) {

    loadTodosIfEmpty();
    int totalCount = todos.size();

    if (totalCount == 0) return mapper.mapToPaginatedTodos(new ArrayList<>(), 0, page, limit, sort, order);

    List<TodoModel> sortedTodos = new ArrayList<>(todos);
    sortList(sort, order, sortedTodos);

    if (totalCount <= limit) page = 1;

    int fromIndex = (page - 1) * limit;
    int toIndex = Math.min(fromIndex + limit, totalCount);

    List<TodoModel> paginatedTodos = (fromIndex >= toIndex)
        ? new ArrayList<>()
        : sortedTodos.subList(fromIndex, toIndex);

    return mapper.mapToPaginatedTodos(paginatedTodos, totalCount, page, limit, sort, order);
  }

  @Override
  public List<Todo> getTodos() {

    loadTodosIfEmpty();

    return mapper.mapToTodos(todos);
  }

  @Override
  public Todo getTodo(long id) {

    TodoModel todo = findTodoById(id);

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

    TodoModel t = findTodoById(id);

    if (t == null) throw new NotFoundException("Todo with id " + id + " does not exist");

    t.setTodo(todo.getTodo() == null ? t.getTodo() : todo.getTodo());
    t.setCompleted(todo.getCompleted() == null ? t.getCompleted() : todo.getCompleted());
    t.setUpdatedAt(new Date());

    return mapper.mapToTodo(t);
  }

  @Override
  public void deleteTodos(List<Long> ids) {

    for (Long id : ids) {
      TodoModel todo = findTodoById(id);
      todos.remove(todo);
    }
  }

  private TodoModel findTodoById(long id) {

    if (todos.isEmpty()) loadTodosIfEmpty();

    return todos.stream()
        .filter(todo -> todo.getId() == id)
        .findFirst()
        .orElse(null);
  }

  private void loadTodosIfEmpty() {

    if (todos.isEmpty()) todos.addAll(entityManager.createQuery("""
            select todo
              from TodoModel todo
            """, TodoModel.class)
        .getResultList());

    nextId = todos.size();
  }

  private static void sortList(String sort, String order, List<TodoModel> sortedTodos) {

    Comparator<TodoModel> comparator = switch (sort.toLowerCase()) {
      case "todo" -> Comparator.comparing(TodoModel::getTodo);
      case "completed" -> Comparator.comparing(TodoModel::getCompleted);
      case "createdat" -> Comparator.comparing(TodoModel::getCreatedAt);
      case "updatedat" -> Comparator.comparing(TodoModel::getUpdatedAt);
      default -> Comparator.comparingLong(TodoModel::getId);
    };

    if ("desc".equalsIgnoreCase(order)) comparator = comparator.reversed();

    sortedTodos.sort(comparator);
  }
}
