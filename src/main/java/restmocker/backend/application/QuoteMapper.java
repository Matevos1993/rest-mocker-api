package restmocker.backend.application;

import org.mapstruct.Mapper;
import restmocker.backend.application.dto.PaginatedQuoteDto;
import restmocker.backend.application.dto.QuoteDto;
import restmocker.backend.domain.dto.PaginatedQuote;
import restmocker.backend.domain.dto.Quote;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface QuoteMapper {

  QuoteDto mapToQuoteDto(Quote quote);

  List<QuoteDto> mapToQuoteDtoList(List<Quote> quotes);

  PaginatedQuoteDto mapToPaginatedQuoteDto(PaginatedQuote paginatedQuote);
}
