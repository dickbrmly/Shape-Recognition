package Graphics;

import Graphics.position.Position;
import Graphics.Pictures.Picture;
import javafx.scene.paint.Color;

import java.util.Stack;

public class Object implements java.io.Serializable {

  private Position leftMost;
  private Position rightMost;
  private Position topMost;
  private Position btmMost;
  private Position temp;

  // happens that there are eight pixel positions from a pixel position as well. The angular
  // change from a pixel position is therefore in pi/4 radian increments.
  /*
   *       Example single pixel move.
   *               6 7 8
   *               5 x 1
   *               4 3 2
   * */

  Picture picture = Picture.getInstance("src/Graphics/Pictures/pic.gif");

  private boolean incompleteScan = true;
  private double distribution;
  private Color color = new Color(1, 1, 1, 1);
  private Color MASK = new Color(0, 0, 0, 0);
  Stack<Position> edge = new Stack<>();

  int surfaces;  //how many surfaces on the object?  Consider a line has one, a square has four etc...

  Object nextObject;

  /* The surface has an 'object' color pixel but so does the filled portion of the object.
   *  --> Therefore a surface pixel must be defined as a 'object' color pixel adjacent to
   *  a 'non-object pixel. It's only a surface pixel if and only if it is next to an out of
   *  color pixel.
   *
   *  */

  public Object(Color color, Position position) {
    Position beginning = new Position(position);
    Position move = new Position(position);
    this.color = color;

    move.quad = 1;

    /* The rectangle made here would copy our object from the picture
     * */

    leftMost = new Position(move);
    rightMost = new Position(move);
    topMost = new Position(move);
    btmMost = new Position(move);
    temp = new Position();
    // We assume the previous position was back one column unless this is a left-most position.
    // We are looking for a surface pixel in a clockwise pattern.
    //***********************************************************************************************************
    while (incompleteScan) { // There are one of seven possible moves
      temp.makeEqual(move);

      switch (move.quad) {
        case 1:
          move.column = move.column - 1;
          move.row = move.row - 1;
          move.quad = 6;
          break;
        case 2:
          move.row = move.row - 1;
          move.quad = 7;
          break;
        case 3:
          move.column = move.column + 1;
          move.row = move.row - 1;
          move.quad = 8;
          break;
        case 4:
          move.column = move.column + 1;
          move.quad = 1;
          break;
        case 5:
          move.column = move.column + 1;
          move.row = move.row + 1;
          move.quad = 2;
          break;
        case 6:
          move.row = move.row + 1;
          move.quad = 3;
          break;
        case 7:
          move.column = move.column - 1;
          move.row = move.row + 1;
          move.quad = 4;
          break;
        default:
          move.column = move.column - 1;
          move.quad = 5;
          break;
      }
      if (checkMove(move)) {
        if (move.column < leftMost.column) {
          leftMost.column = move.column;
          leftMost.row = move.row;
        }
        if (move.column > rightMost.column) {
          rightMost.column = move.column;
          rightMost.row = move.row;
        }
        if (move.row < topMost.row) {
          topMost.column = move.column;
          topMost.row = move.row;
        }
        if (move.row > btmMost.row) {
          btmMost.column = move.column;
          btmMost.row = move.row;
        }
        if (move.equal(beginning)) incompleteScan = false;
        picture.setPixel(move, MASK);
      } else {
        move.makeEqual(temp);
        if (++move.quad > 8) move.quad = 1;
      }

    }
    distribution = (double) area() / picture.getArea();
  }

  boolean checkMove(Position move) {
    if (move.overEdge(picture.getWidth(), picture.getHeight())) return false;
    if (picture.getPixel(move).equals(color)) {
      edge.push(new Position(move));
      return true;
    }
    return false;
  }

  int x() {
    return leftMost.column;
  }

  int y() {
    return topMost.row;
  }

  int width() {
    return rightMost.column - leftMost.column;
  }

  int height() {
    return btmMost.row - topMost.row;
  }

  int area() {return width() * height();}

  double getDistribution() {
    return distribution;
  }
}
