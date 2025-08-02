package restmocker.backend.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Color {

  private int id;
  private String rgb;
  private String hex;
}
