package restmocker.backend.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TokenBody {
  private int id;
  private String email;
  private String username;
  private Date expiresIn;
}
