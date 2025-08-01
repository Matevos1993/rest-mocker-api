package restmocker.backend.domain;

import restmocker.backend.domain.dto.PaginatedQuote;
import restmocker.backend.domain.dto.Quote;

import java.util.List;

public interface QuoteRepository {

  PaginatedQuote getPaginatedQuotes(int page, int limit, String search);

  Quote getQuoteById(int id);

  Quote getRandomQuote();

  List<Quote> getRandomQuote(int limit);
}
