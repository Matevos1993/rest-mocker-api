package restmocker.backend.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class User {

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
