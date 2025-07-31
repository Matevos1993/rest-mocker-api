package restmocker.backend.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuoteDto {

  private int id;
  private String content;
  private String author;
}
