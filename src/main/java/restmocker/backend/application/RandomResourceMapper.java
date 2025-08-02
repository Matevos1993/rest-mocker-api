package restmocker.backend.application;

import org.mapstruct.Mapper;
import restmocker.backend.application.dto.ColorDto;
import restmocker.backend.application.dto.UserDto;
import restmocker.backend.domain.dto.Color;
import restmocker.backend.domain.dto.User;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface RandomResourceMapper {

  UserDto mapToUserDto(User user);

  List<UserDto> mapToUserDtoList(List<User> users);

  ColorDto  mapToColorDto(Color color);

  List<ColorDto> mapToColorDtoList(List<Color> colors);
}
