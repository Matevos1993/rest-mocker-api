package restmocker.backend.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
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
}
