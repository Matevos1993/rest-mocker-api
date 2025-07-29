package restmocker.backend.infrastructure;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Dependent
public class AbstractRepository {

  @Inject
  protected EntityManager entityManager;
}
