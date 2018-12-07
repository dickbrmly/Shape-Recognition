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

  Position findOrigin(Position head, Position middle, Position tail) {

    head.makeEqual(new Position(2,9)); //test rig
    middle.makeEqual(new Position(5,10));
    tail.makeEqual(new Position(8,9));

    Position origin = new Position(middle);
    int left, right, center;
    int syntheticX, syntheticY;
    int curvature = 0;

    double slope = (double) (head.row - tail.row) / (head.column - tail.column);
    double y = slope * (double) (middle.column - tail.column) + tail.row;

    if (y < middle.row) curvature = -1;
    else curvature = 1;

    left = tail.distance(origin);
    right = head.distance(origin);
    center = middle.distance(origin);

    while(left != right) {
      if (left > right) {
        origin.column = -1 * curvature + origin.column;
        left = tail.distance(origin);
        right = head.distance(origin);
        center = middle.distance(origin);
      } else if (right > left) {
        origin.column = 1 * curvature + origin.column;
        left = tail.distance(origin);
        right = head.distance(origin);
        center = middle.distance(origin);
      }

      if (center < right || center < left) {
        origin.row = 1 * curvature + origin.row;
        left = tail.distance(origin);
        right = head.distance(origin);
        center = middle.distance(origin);
      } else if (center > right || center > left) {
        origin.row = -1 * curvature + origin.row;
        left = tail.distance(origin);
        right = head.distance(origin);
        center = middle.distance(origin);
      }
    }
    return origin;
  }
}
