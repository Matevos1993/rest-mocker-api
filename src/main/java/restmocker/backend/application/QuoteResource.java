package restmocker.backend.application;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import restmocker.backend.application.dto.PaginatedQuoteDto;
import restmocker.backend.domain.QuoteRepository;
import restmocker.backend.domain.dto.PaginatedQuote;
import restmocker.backend.domain.dto.Quote;

@Produces(MediaType.APPLICATION_JSON)
@Path("/quotes")
public class QuoteResource {

  private final QuoteRepository quoteRepository;
  private final QuoteMapper quoteMapper;

  @Inject
  public QuoteResource(QuoteRepository quoteRepository, QuoteMapper quoteMapper) {
    this.quoteRepository = quoteRepository;
    this.quoteMapper = quoteMapper;
  }

  @GET
  public PaginatedQuoteDto getQuotes(@QueryParam("page") @DefaultValue("1") int page,
                                     @QueryParam("limit") @DefaultValue("100") int limit,
                                     @QueryParam("search") String search) {

    return quoteMapper.mapToPaginatedQuoteDto(quoteRepository.getPaginatedQuotes(page, limit, search));
  }
}
