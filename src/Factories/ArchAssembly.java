package Factories;

import Position.Position;
import java.util.List;

public class ArchAssembly extends VectorObjectFactory {

  public ArchAssembly(List<Position> pixels, Position center) {
      length = pixels.size();
      radius = pixels.get(0).distance(origin);
      origin = new Position(center);
      start = new Position(pixels.get(0));
      thickness = stub(pixels.get(0));
  }
}
