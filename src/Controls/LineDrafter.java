package Controls;

import Factories.VectorObjectFactory;
import Objects.Shape;
import Position.Position;

import java.util.ArrayList;
import java.util.List;


public class LineDrafter {
  public LineDrafter() {

  }

  /**
   * recreate the shape using Vectors and arches
   *
   * @param item the shape
   */
  public void construct(Shape item) {
    int stateMachine = 0;
    int distance = 0;

    Position vectorOffset = new Position(0, 0, 0);
    Position origin = new Position();
    Position head = new Position(0, 0, 0);
    Position middle = new Position(0, 0, 0);
    Position tail = new Position(0, 0, 0);
    List<Position> line = new ArrayList();

    // The magic vector machine.
    for (Position position : item.edge) {
      head.makeEqual(position.subtract(item.Minimum)); //normalizing with its image file

      switch (stateMachine) {
        case 0:
          middle.makeEqual(head);
          tail.makeEqual(head);
          line.add(new Position(head));
          stateMachine = 1;
          break;

        case 1:
          vectorOffset.makeEqual(head.subtract(middle));
          middle.makeEqual(head);
          stateMachine = 2;
          line.add(new Position(head));
          break;

        case 2:
          if (head.equal(middle.add(vectorOffset))) {
            middle.makeEqual(head);
            line.add(new Position(head));
            stateMachine = 3;
            break;
          }
          item.vectorMap.add(new VectorObjectFactory().get("line", line, origin,0,line.get(0)));
          line.clear();
          line.add(new Position(head));
          tail.makeEqual(head);
          middle.makeEqual(head);
          stateMachine = 1;
          break;

        case 3:
          if (head.equal(middle.add(vectorOffset))) {
            middle.makeEqual(head);
            line.add(new Position(head));
            break;
          }

        case 4:
          item.vectorMap.add(new VectorObjectFactory().get("line", line, origin,0,line.get(0)));
          line.clear();
          line.add(new Position(head));
          tail.makeEqual(head);
          middle.makeEqual(head);
          stateMachine = 1;
          break;
      }
      item.vectorMap.add(new VectorObjectFactory().get("line", line, item.Minimum,0,line.get(0)));
    }
  }
}
