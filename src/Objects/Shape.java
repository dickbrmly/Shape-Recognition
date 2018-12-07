package Objects;

import java.util.ArrayList;

import Factories.VectorObjectFactory;
import Position.Position;
import Controls.Picture;

import java.util.LinkedList;
import java.util.List;
/*
  Shape discovers itself after being gjven a single pixel location of itself.  Every shape has a shape object.
 */
public class Shape  {

  public ShapeData data = new ShapeData();;
  public int id;
  private Position minimum;
  private Position maximum;
  private Position start;
  private Position temp;

  // happens that there are eight pixel positions from a pixel Position as well. The angular
  // change from a pixel Position is therefore in pi/4 radian increments.
  /*
   *       Example single pixel move.
   *               6 7 8
   *               5 x 1
   *               4 3 2
   * */

  // Vector objects created by Drafter.java

  Picture picture = Picture.getInstance();

  private boolean incompleteScan = true;

  private int surfaces;  //how many surfaces on the Shape?  Consider a line has one, a square has four etc...

  /* The surface has an 'Shape' color pixel but so does the filled portion of the Shape.
   *  --> Therefore a surface pixel must be defined as a 'Shape' color pixel adjacent to
   *  a 'non-Shape pixel. It's only a surface pixel if and only if it is next to an out of
   *  color pixel.
   *
   *  */

  public Shape(final int id, java.awt.Color color, Position position) {

    this.start = position;
    data.edge.add(new Position(position)); //save initial Position
    Position beginning = new Position(position);
    Position move = new Position(position);
    data.color = color;
    this.id = id;
    move.quad = 1;

    /* The rectangle made here would copy our Shape from the picture
     * */

    minimum = new Position(move);
    maximum = new Position(move);
    temp = new Position();
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
    data.distribution = (double) area() / picture.getArea();
  }

  public java.awt.Color getColor()
  {
    return data.color;
  }
  public Position start() {
    return start;
  }
  public int x() {
    return minimum.column;
  }
  public int y() {
    return minimum.row;
  }
  public Position getMinimum() { return minimum; }
  public int maxRow() { return maximum.row; }
  public ShapeData getData() { return data; }
  public List<Position> positionList() { return data.edge; }

  public int width() {
    return maximum.column - minimum.column;
  }
  public int height() {
    return maximum.row - minimum.row;
  }

  int area() {return width() * height();}

  double getDistribution() {
    return data.distribution;
  }

  public class ShapeData implements java.io.Serializable {
    public double distribution;
    public java.awt.Color color;
    public List<Position> edge = new ArrayList<Position>();

    public List<VectorObjectFactory> vectorMap = new LinkedList();
    public Position origin = new Position(0, 0);
  }
}