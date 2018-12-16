package Factories;

import Position.Position;
import java.util.List;

public class ArchAssembly extends VectorObjectFactory {

  public Position origin = new Position();
  public int radius;

  public ArchAssembly(List<Position> pixels) {

      this.length = length;
      this.origin = origin;
      radius = start.distance(origin);
      this.start = start;

  }
}
