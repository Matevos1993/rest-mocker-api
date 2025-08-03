package restmocker.backend.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

  private int id;
  private String firstName;
  private String lastName;
  private String gender;
  private LocalDate dateOfBirth;
  private int age;
  private String email;
  private String username;
  private String password;
  private String role;
  private List<TodoDto> todos;
}
