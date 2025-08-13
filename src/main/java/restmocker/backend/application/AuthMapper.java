package restmocker.backend.application;

import org.mapstruct.Mapper;
import restmocker.backend.domain.dto.AuthRequest;

@Mapper(componentModel = "cdi")
public interface AuthMapper {

  AuthRequest mapToAuthRequest(restmocker.backend.application.dto.AuthRequest request);
}
