package Factories;

import Position.Position;
import java.util.List;

public class VectorObjectFactory {

  public Position start = new Position();
  public int length;
  public int thickness;

  public VectorObjectFactory get(String component, List<Position> pixels) {

    switch (component) {
      case "line":
        return new LineAssembly(pixels);

      case "arch":
        return new ArchAssembly(pixels);
    }
    return null;
  }
  public int getThickness(Position outter) {
    return 0;
  }
}

