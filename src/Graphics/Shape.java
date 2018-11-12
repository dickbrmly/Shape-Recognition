package Graphics;


import Graphics.position.Position;
import Graphics.Pictures.Picture;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

import static Graphics.position.ColorLink.MASK;

public class Shape implements java.io.Serializable {

  public int id;
  private Position minimum;
  private Position maximum;

  private Position temp;

  // happens that there are eight pixel positions from a pixel position as well. The angular
  // change from a pixel position is therefore in pi/4 radian increments.
  /*
   *       Example single pixel move.
   *               6 7 8
   *               5 x 1
   *               4 3 2
   * */

  Picture picture = Picture.getInstance();


  private boolean incompleteScan = true;
  private double distribution;
  private Color color = new Color(1, 1, 1, 1);
  private List<Position> edge = new ArrayList<Position>();
  private int index = 0;

  private int surfaces;  //how many surfaces on the Shape?  Consider a line has one, a square has four etc...

  /* The surface has an 'Shape' color pixel but so does the filled portion of the Shape.
   *  --> Therefore a surface pixel must be defined as a 'Shape' color pixel adjacent to
   *  a 'non-Shape pixel. It's only a surface pixel if and only if it is next to an out of
   *  color pixel.
   *
   *  */

  public Shape(int id, Color color, Position position) {

    edge.add(new Position(position)); //save initial position
    Position beginning = new Position(position);
    Position move = new Position(position);
    this.color = color;
    this.id = id;
    move.quad = 1;

    /* The rectangle made here would copy our Shape from the picture
     * */

    minimum = new Position(move);
    maximum = new Position(move);
    temp = new Position();
    // We assume the previous position was back one column unless this is a left-most position.
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
      if (picture.checkMove(move) && picture.getPixel(move).equals(color)) {

        if (move.column < minimum.column) minimum.column = move.column;
        if (move.column > maximum.column) maximum.column = move.column;
        if (move.row < minimum.row) minimum.row = move.row;
        if (move.row > maximum.row) maximum.row = move.row;

        if (move.equal(beginning)) incompleteScan = false;
        edge.add(new Position(move));
        //picture.setPixel(move, MASK);
      } else {
        move.makeEqual(temp);
        if (++move.quad > 8) move.quad = 1;
      }
    }
    distribution = (double) area() / picture.getArea();
  }

  public int x() {
    return minimum.column;
  }

  public int y() {
    return minimum.row;
  }

  public int width() {
    return maximum.column - minimum.column;
  }

  public int height() {
    return maximum.row - minimum.row;
  }

  int area() {return width() * height();}

  double getDistribution() {
    return distribution;
  }
}