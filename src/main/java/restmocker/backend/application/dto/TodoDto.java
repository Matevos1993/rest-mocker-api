package restmocker.backend.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TodoDto {

  private long id;
  private String todo;
  private Boolean completed;
  private long userId;
  private Date createdAt;
  private Date updatedAt;
}
