package restmocker.backend.application.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PaginatedQuoteDto {

  private List<QuoteDto> quotes;
  private int totalCount;
  private int limit;
  private int totalPages;
  private int currentPage;
  private String search;
  private String searchBy;
}
