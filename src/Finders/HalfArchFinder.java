package Finders;

import Assemblies.ArchAssembly;
import Assemblies.LineAssembly;
import Assemblies.VectorObjectAssembly;
import Objects.Shape;
import Position.Position;

import java.util.LinkedList;
import java.util.List;

public class HalfArchFinder {

  public HalfArchFinder() {  }

  // what is an arch?  I will define an arch as a curve twisting
  // at least 45 degrees.  In our eight quadrant system that
  //requires 1 quadrant of twist.
  public void construct(Shape item) {

    boolean ToDo = true;
    int number = 0;
    int twist = 0;
    //  if the line is greater than x -- it is a line

    LineAssembly line;
    List<VectorObjectAssembly> potentials = new LinkedList<>();
    if (item.vectorMap.size() > 0) line = (LineAssembly) item.vectorMap.get(number);
    int state = 0;
    int archNumber = 0;
    int lastQuad = 0;
    Position head = new Position();
    Position middle = new Position();
    Position tail = new Position();

    while ((number + archNumber) < item.vectorMap.size()) {
      line = (LineAssembly) item.vectorMap.get(number);

      switch (state) {
        case 0:
          lastQuad = line.quadrant;
          potentials.add(line);
          tail.makeEqual(line.start);
          state++;
          number++;
          break;

        //if next quad not consecutive--not an arch, its another line.
        case 1:
          if (Math.abs(line.quadrant - lastQuad) > 1) {
            potentials.clear();
            state = 0;
            break;
          }
          potentials.add(line);
          if (line.quadrant == lastQuad) {
            number++;
            break;
          }
          middle.makeEqual(line.start);
          lastQuad = line.quadrant;
          potentials.add(line);
          if(number + archNumber + 1 < item.vectorMap.size()) {
            LineAssembly line2 = (LineAssembly) item.vectorMap.get(number + 1);
            if (line2.quadrant == line.quadrant) {
              potentials.add(line2);
              head.makeEqual(line2.end);
            } else  head.makeEqual(line.end);
          } else  head.makeEqual(line.end);

          item.vectorMap.add(new ArchAssembly(
              new Position(potentials.get(0).start),
              new Position(potentials.get(potentials.size() - 1).end),
              new Position(findOrigin(head, middle, tail))));

          //remove lines from vectorMap.
          for (int i = 0; i < potentials.size(); i++)
            item.vectorMap.remove(potentials.get(i));
          archNumber++;
          potentials.clear();
          number = 0;
          state = 0;
          break;
      }
    }
  }

  Position findOrigin(Position head, Position middle, Position tail) {

    double slope1, slope2;
    double b1,b2;

    Matrix Perpendicular1 = new Matrix((double)(head.column - middle.column)/2 + middle.column,
        (double)(head.row - middle.row)/2 + middle.row);
    Matrix Perpendicular2 = new Matrix((double)(middle.column - tail.column)/2 + tail.column,
        (double)(middle.row - tail.row)/2 + tail.row);

    // Find the slopes between the three vectors and twist the slope
    // one quadrant clockwise.

    slope1 = -1/ head.slope(middle);
    slope2 = -1 / middle.slope(tail);

    // Find the 'b' element of the lines in slope
    // intercept form.

    b1 = Perpendicular1.y - slope1 * Perpendicular1.x;
    b2 = Perpendicular2.y - slope2 * Perpendicular2.x;

    //find the int
    Matrix vortex = new Matrix();
    vortex.x = (b2 - b1)/(slope1 - slope2);
    vortex.y = vortex.x*slope1+b1;

    Position origin = new Position((int)Math.round(vortex.x),(int)Math.round(vortex.y));
    return origin;
  }

  public class Matrix {
    double x, y;
    public Matrix() {
      this.x = 0.0;
      this.y = 0.0;
    }
    public Matrix(double x, double y) {
      this.x = x;
      this.y = y;
    }
  }
}
