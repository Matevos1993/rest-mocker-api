package restmocker.backend.application;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import restmocker.backend.application.dto.AuthRequest;
import restmocker.backend.domain.AuthRepository;

@ApplicationScoped
public class AuthService {

  private final AuthRepository authRepository;
  private final AuthMapper authMapper;

  @Inject
  public AuthService(AuthRepository authRepository, AuthMapper authMapper) {
    this.authMapper = authMapper;
    this.authRepository = authRepository;
  }

  public String login(String userId, AuthRequest request) {
    return authRepository.authorizeUser(userId, authMapper.mapToAuthRequest(request));
  }
}
