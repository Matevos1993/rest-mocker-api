package restmocker.backend.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Quote {

  private int id;
  private String content;
  private String author;
}
