package restmocker.backend.application.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PaginatedTodosDto {

  private List<TodoDto> todos;
  private int totalCount;
  private int offset;
  private int limit;
  private String sort;
  private String order;
  private int totalPages;
  private int currentPage;
}
