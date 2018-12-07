package Factories;

import Position.Position;

import java.util.ArrayList;
import java.util.List;

public class VectorObjectFactory {

  int length;
  int radius;
  Position origin;
  Position start;
  int thickness;
  double angle;

  public VectorObjectFactory get(String component, List<Position> pixels,Position origin) {

    switch (component) {
      case "line":
        return new LineAssembly(pixels, origin);

      case "arch":
        return new ArchAssembly(pixels, origin);
    }
    return null;
  }

  public int stub(Position outter) {
    return 0;
  }
}

