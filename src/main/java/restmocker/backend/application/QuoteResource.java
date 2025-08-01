package restmocker.backend.application;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import restmocker.backend.application.dto.PaginatedQuoteDto;
import restmocker.backend.application.dto.QuoteDto;
import restmocker.backend.domain.QuoteRepository;

import java.util.List;

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
  public PaginatedQuoteDto getPaginatedQuotes(@QueryParam("page") @DefaultValue("1") int page,
                                              @QueryParam("limit") @DefaultValue("100") int limit,
                                              @QueryParam("search") String search) {

    if (page < 1) page = 1;

    if (limit < 1) limit = 100;

    return quoteMapper.mapToPaginatedQuoteDto(quoteRepository.getPaginatedQuotes(page, limit, search));
  }

  @GET
  @Path("/{id}")
  public QuoteDto getQuoteById(@PathParam("id") int id) {

    return quoteMapper.mapToQuoteDto(quoteRepository.getQuoteById(id));
  }

  @GET
  @Path("/random")
  public QuoteDto getRandomQuote() {

    return quoteMapper.mapToQuoteDto(quoteRepository.getRandomQuote());
  }

  @GET
  @Path("/random/list")
  public List<QuoteDto> getRandomQuotes(@QueryParam("limit") @DefaultValue("10") int limit) {

    if (limit < 1) limit = 10;
    return quoteMapper.mapToQuoteDtoList(quoteRepository.getRandomQuote(limit));
  }
}
