package Controls;

import Position.Position;
import Objects.Shape;
/*
  Shape discovers itself after being gjven a single pixel location of itself.  Every shape has a shape object.
 */
public class ShapeFinder {

  Picture picture;
  // happens that there are eight pixel positions from a pixel Position as well. The angular
  // change from a pixel Position is therefore in pi/4 radian increments.
  /*
   *       Example single pixel move.
   *               6 7 8
   *               5 x 1
   *               4 3 2
   * */

  public ShapeFinder(Picture picture) {
    this.picture = picture;
  }

  private int surfaces;  //how many surfaces on the Shape?  Consider a line has one, a square has four etc...

  /* The surface has an 'Shape' color pixel but so does the filled portion of the Shape.
   *  --> Therefore a surface pixel must be defined as a 'Shape' color pixel adjacent to
   *  a 'non-Shape pixel. It's only a surface pixel if and only if it is next to an out of
   *  color pixel.
   *
   *  */

  public Shape discover(java.awt.Color color, Position position) {
    boolean incompleteScan = true;
    Position minimum = new Position(position);
    Position maximum = new Position(position);
    Position temp = new Position();
    Shape data = new Shape();
    data.edge.add(new Position(position)); //save initial Position
    Position beginning = new Position(position);
    Position move = new Position(position);
    data.color = color;
    move.quad = 1;

    // We assume the previous Position was back one column unless this is a left-most Position.
    // We are looking for a surface pixel in a clockwise pattern.
    //***********************************************************************************************************
    while (incompleteScan) { // There are one of seven possible moves
      temp.makeEqual(move);

      switch (move.quad) {
        case 1:
          move.changeQuad(6);
          break;
        case 2:
          move.changeQuad(7);
          break;
        case 3:
          move.changeQuad(8);
          break;
        case 4:
          move.changeQuad(1);
          break;
        case 5:
          move.changeQuad(2);
          break;
        case 6:
          move.changeQuad(3);
          break;
        case 7:
          move.changeQuad(4);
          break;
        default:
          move.changeQuad(5);
          break;
      }
      if (picture.checkMove(move) && picture.getAwtPixel(move).equals(color)) {

        if (move.column < minimum.column) minimum.column = move.column;
        if (move.column > maximum.column) maximum.column = move.column;
        if (move.row < minimum.row) minimum.row = move.row;
        if (move.row > maximum.row) maximum.row = move.row;

        if (move.equal(beginning)) incompleteScan = false;
        data.edge.add(new Position(move));
      } else {
        move.makeEqual(temp);
        if (++move.quad > 8) move.quad = 1;
      }
    }
    data.Minimum.makeEqual(minimum);
    data.width = maximum.column - minimum.column;
    data.height = maximum.row - minimum.row;
    data.distribution = (double) data.width * data.height / picture.getArea();
    return data;
  }
}