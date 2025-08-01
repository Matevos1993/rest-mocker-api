package restmocker.backend.infrastructure;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.TypedQuery;
import jakarta.ws.rs.NotFoundException;
import restmocker.backend.domain.QuoteRepository;
import restmocker.backend.domain.dto.PaginatedQuote;
import restmocker.backend.domain.dto.Quote;
import restmocker.backend.infrastructure.mapper.QuoteMapper;
import restmocker.backend.infrastructure.model.QuoteModel;

import java.util.List;
import java.util.Set;

@ApplicationScoped
public class QuoteRepositoryImpl extends AbstractRepository implements QuoteRepository {

  private String searchField;
  private String searchValue;

  private final QuoteMapper quoteMapper;

  @Inject
  public QuoteRepositoryImpl(QuoteMapper quoteMapper) {

    this.quoteMapper = quoteMapper;
  }

  @Override
  public PaginatedQuote getPaginatedQuotes(int page, int limit, String search) {

    int count = Math.toIntExact(getQuoteQuery(true, page, limit, search, Long.class).getSingleResult());
    List<QuoteModel> quotes = getQuoteQuery(false, page, limit, search, QuoteModel.class).getResultList();

    return quoteMapper.mapToPaginatedQuote(quotes, count, page, limit, searchField, searchValue);
  }

  @Override
  public Quote getQuoteById(int id) {

    try {
      return quoteMapper.mapToQuote(entityManager.createQuery("""
            select quote
              from QuoteModel quote
             where quote.id = :id
            """, QuoteModel.class)
          .setParameter("id", id)
          .getSingleResult()
      );
    } catch (Exception e) {
      throw new NotFoundException("Quote with id " + id + " not found");
    }
  }

  @Override
  public Quote getRandomQuote() {

    List<QuoteModel> results = entityManager.createQuery("""
                select quote
                  from QuoteModel quote
                 order by function('RANDOM')
            """, QuoteModel.class)
        .setMaxResults(1)
        .getResultList();

    return results.isEmpty() ? null : quoteMapper.mapToQuote(results.getFirst());
  }

  @Override
  public List<Quote> getRandomQuote(int limit) {

    List<QuoteModel> results = entityManager.createQuery("""
                select quote
                  from QuoteModel quote
                 order by function('RANDOM')
            """, QuoteModel.class)
        .setMaxResults(limit)
        .getResultList();

    return results.stream().map(quoteMapper::mapToQuote).toList();
  }

  private <T> TypedQuery<T> getQuoteQuery(boolean isCountQuery, int page, int limit, String search, Class<T> resultClass) {

    TypedQuery<T> query = entityManager.createQuery("select " + (isCountQuery ? "count(quote)" : "quote") + """
              from QuoteModel quote
             where (cast(:search as text) is null or (
                         :search = 'content' and (:searchValue is null or lower(quote.content) like :searchValue)
                      or :search = 'author'  and (:searchValue is null or lower(quote.author)  like :searchValue)
            )
         )
        """, resultClass);

    if (search != null) {
      String[] parts = search.split("=", 2);

      if (Set.of("author", "content").contains(parts[0].toLowerCase().trim()) && !parts[1].isEmpty()) {
        searchField = parts[0].toLowerCase().trim();
        searchValue = "%" + parts[1].toLowerCase().trim() + "%";
      }
    } else {
      searchField = null;
      searchValue = null;
    }

    query.setParameter("search", searchField);
    query.setParameter("searchValue", searchValue);

    if (!isCountQuery) query.setFirstResult((page - 1) * limit).setMaxResults(limit);

    return query;
  }
}
