package restmocker.backend.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ColorDto {

  private int id;
  private String rgb;
  private String hex;
}
