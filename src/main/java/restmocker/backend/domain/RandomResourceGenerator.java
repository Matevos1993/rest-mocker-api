package restmocker.backend.domain;

import jakarta.enterprise.context.ApplicationScoped;
import restmocker.backend.domain.dto.Color;

import java.util.*;

@ApplicationScoped
public class RandomResourceGenerator {

  private final Random random = new Random();

  public List<Color> generateColors(int count) {

    List<Color> colors = new ArrayList<>();

    if (count < 1) {
      count = 1;
    } else if (count > 50) {
      count = 50;
    }

    for (int i = 0; i < count; i++) {
      int r = random.nextInt(256);
      int g = random.nextInt(256);
      int b = random.nextInt(256);

      colors.add(new Color(i, String.format("rgb(%d, %d, %d)", r, g, b), String.format("#%02X%02X%02X", r, g, b).toLowerCase()));
    }

    return colors;
  }
}
