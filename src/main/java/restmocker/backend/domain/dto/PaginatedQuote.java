package restmocker.backend.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PaginatedQuote {

  private List<Quote> quotes;
  private int totalCount;
  private int limit;
  private int totalPages;
  private int currentPage;
  private String search;
  private String searchBy;
}
