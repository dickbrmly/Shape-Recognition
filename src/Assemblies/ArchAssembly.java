package Assemblies;

import Position.Position;
import java.util.List;

public class ArchAssembly extends VectorObjectAssembly {

  public Position origin = new Position();
  public int radius;

  public ArchAssembly() {  }
  public ArchAssembly(Position start, Position end, Position origin) {

      this.origin = origin;
      this.start.makeEqual(start);
      this.end.makeEqual(end);
      radius = start.distance(origin);
  }
  public void makeEqual(ArchAssembly arch) {
    this.origin = arch.origin;
    this.start = arch.start;
    this.end = arch.end;
    this.radius = arch.radius;
    this.thickness = arch.thickness;

  }
}
