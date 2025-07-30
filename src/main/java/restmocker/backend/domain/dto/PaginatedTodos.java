package restmocker.backend.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PaginatedTodos {

  private List<Todo> todos;
  private int totalCount;
  private int offset;
  private int limit;
  private String sort;
  private String order;
  private int totalPages;
  private int currentPage;
}
