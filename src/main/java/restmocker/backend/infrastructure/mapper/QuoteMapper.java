package restmocker.backend.infrastructure.mapper;

import org.mapstruct.Mapper;
import restmocker.backend.domain.dto.PaginatedQuote;
import restmocker.backend.domain.dto.Quote;
import restmocker.backend.infrastructure.model.QuoteModel;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface QuoteMapper {

  Quote mapToQuote(QuoteModel quoteModel);

  List<Quote> mapToQuoteList(List<QuoteModel> quoteModels);

  default PaginatedQuote mapToPaginatedQuote(List<QuoteModel> quotes, int totalCount, int page, int limit, String search, String searchBy) {

    PaginatedQuote paginatedQuote = new PaginatedQuote();
    paginatedQuote.setQuotes(mapToQuoteList(quotes));
    paginatedQuote.setTotalCount(totalCount);
    paginatedQuote.setLimit(limit);
    paginatedQuote.setTotalPages((int) Math.ceil((double) totalCount / limit));
    paginatedQuote.setCurrentPage(page);
    paginatedQuote.setSearch(search);
    paginatedQuote.setSearchBy(searchBy);
    return paginatedQuote;
  }
}
