package restmocker.backend.infrastructure;

import jakarta.enterprise.context.ApplicationScoped;
import restmocker.backend.domain.QuoteRepository;
import restmocker.backend.domain.dto.PaginatedQuote;
import restmocker.backend.domain.dto.Quote;

@ApplicationScoped
public class QuoteRepositoryImpl extends AbstractRepository implements QuoteRepository {

  @Override
  public PaginatedQuote getPaginatedQuotes(int page, int limit, String search) {
    return null;
  }

  @Override
  public Quote getQuoteById(int id) {
    return null;
  }

  @Override
  public Quote getRandomQuote() {
    return null;
  }

  @Override
  public Quote getRandomQuote(int limit) {
    return null;
  }
}
