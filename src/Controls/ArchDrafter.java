package Controls;

import Objects.Shape;
import Position.Position;

public class ArchDrafter {


  /**
   * This method finds the origin of curvature
   * of an arch given three pixel positions.
   *
   * @param head   leading position in an arch
   * @param middle middle position in an arch
   * @param tail   tailing position in an arch
   * @return origin of curvature
   */

  public ArchDrafter () {  }

  public void construct(Shape item) {

  }


  Position findOrigin(Position head, Position middle, Position tail) {

    head.makeEqual(10,5,1);
    middle.makeEqual(9,8,1);
    tail.makeEqual(8,9,1);

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
