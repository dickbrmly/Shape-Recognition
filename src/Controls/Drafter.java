package Controls;

import Factories.VectorObjectFactory;
import Objects.Shape;
import Position.Position;

import java.util.ArrayList;
import java.util.List;

public class Drafter {
  public Drafter() {

  }

  public void construct(Shape item) {
    List<Position> line = new ArrayList();
    List<Position> arch = new ArrayList();

    int stateMachine = 0;
    int separator = 0; //head, middle, tail spreader

    Position vectorOffset = new Position(0, 0, 0);
    Position head = new Position(0, 0, 0);
    Position middle = new Position(0, 0, 0);
    Position tail = new Position(0, 0, 0);

    // The magic vector machine.
    for (Position position : item.positionList()) {
      head.makeEqual(position.subtract(item.getMinimum())); //normalizing with its image file

      switch (stateMachine) {
        case 0: //all pointers are together
          middle.makeEqual(head);
          tail.makeEqual(head);
          vectorOffset.makeEqual(0, 0, 0);
          line.add(new Position(head));
          stateMachine = 1;
          break;

        //head begins its march forward. could be a vectorPoints or arch
        case 1:
          vectorOffset.makeEqual(head.subtract(middle));
          middle.makeEqual(head);
          stateMachine = 2;
          line.add(new Position(head));
          break;

        //is it a vectorPoints or arch ?
        case 2:
          if (head.equal(middle.add(vectorOffset))) {
            middle.makeEqual(head);
            line.add(new Position(head));
            stateMachine = 3;
          } else {
            stateMachine = 5;
            if (line.size() > 2) {
              item.data.vectorMap.add(new VectorObjectFactory().get("line", line, item.data.origin));
              tail.makeEqual(head);
              middle.makeEqual(head);
            } else {
              arch.add(new Position(line.get(0)));
              arch.add(new Position(line.get(1)));
              line.clear();
            }
            arch.add(new Position(head));
          }
          break;

        //looking for end of vectorPoints
        case 3:
          if (head.equal(middle.add(vectorOffset))) {
            middle.makeEqual(head);
            arch.add(new Position(head));
            break;
          }

          // found end of vectorPoints---record it.
        case 4:
          item.data.vectorMap.add(new VectorObjectFactory().get("line", line, item.data.origin));
          line.clear();
          line.add(new Position(head));
          stateMachine = 0;
          break;

        case 5:
          stateMachine++;
          arch.add(new Position(head));
          break;

        case 6:
          stateMachine++;
          middle.makeEqual(head);
          arch.add(new Position(head));
          break;

        case 7:
          stateMachine++;
          arch.add(new Position(head));
          break;

        case 8:
          stateMachine++;
          arch.add(new Position(head));
          break;

        case 9:
          item.data.origin = findOrigin(head, middle, tail);
          middle.makeEqual(head);
          arch.add(new Position(head));
          stateMachine++;
          break;

        case 10:
          if (item.data.origin.notEqual(findOrigin(head, middle, tail))) stateMachine++;
          break;

        default:
          item.data.vectorMap.add(new VectorObjectFactory().get("arch", arch, item.data.origin));
          arch.clear();
          stateMachine = 0;
          break;
      }
    }
    if (stateMachine == 3) item.data.vectorMap.add(new VectorObjectFactory().get("line", line, item.data.origin));
    else item.data.vectorMap.add(new VectorObjectFactory().get("arch", arch, item.data.origin));
  }

  /**
   * This method finds the origin of curvature
   * of an arch given three pixel positions.
   *
   * @param head   leading position in an arch
   * @param middle middle position in an arch
   * @param tail   tailing position in an arch
   * @return origin of curvature
   */
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
