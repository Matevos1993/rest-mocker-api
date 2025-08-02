package restmocker.backend.application;

import org.mapstruct.Mapper;
import restmocker.backend.application.dto.UserDto;
import restmocker.backend.domain.dto.User;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface UserResourceMapper {

  UserDto mapToUserDto(User user);

  List<UserDto> mapToUserDtoList(List<User> users);

  User mapToUser(UserDto user);
}
