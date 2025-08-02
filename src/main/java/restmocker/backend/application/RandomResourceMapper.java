package restmocker.backend.application;

import org.mapstruct.Mapper;
import restmocker.backend.application.dto.ColorDto;
import restmocker.backend.domain.dto.Color;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface RandomResourceMapper {

  ColorDto  mapToColorDto(Color color);

  List<ColorDto> mapToColorDtoList(List<Color> colors);
}
