
package Graphics.position;

import javafx.geometry.Pos;

public class Position
{
  //There are four quadrants in a circle and eight half quadrants.  It so
  // happens that there are eight pixel positions from a pixel position as well. The angular
  // change from a pixel position is therefore in pi/4 radian increments.
  /*
   *       Example single pixel move.
   *               6 7 8
   *               5 x 1
   *               4 3 2
   * */

  public int column;
  public int row;
  public int quad;

  public Position() { }

  public Position(int x, int y, int quad)
  {
    this.column = x;
    this.quad = quad;
    this.row = y;
  }
  public Position(int x, int y)
  {
    column = x;
    row = y;
  }

  public Position(Position there, int quad)
  {
    column = there.column;
    row = there.row;
    this.quad = quad;
  }

  public Position(Position there)
  {
    column = there.column;
    row = there.row;
    quad = there.quad;
  }

  public boolean positionEqual(Position that)
  {
    if (this.column == that.column && this.row == that.row) return true;
    else return false;
  }


  public void setPosition(Position there)
  {
    column = there.column;
    row = there.row;
    quad = there.quad;
  }


  public void setPosition(int x, int y, int direction)
  {
    column = x;
    row = y;
    quad = 1;
  }

  public void makeEqual(Position that)
  {
    this.column = that.column;
    this.row = that.row;
    this.quad = that.quad;
  }

  public boolean overEdge(int width, int height)
  { //determine if the position is on an edge
    if(this.column < 0 || this.row < 0 || this.column > --width || this.row > --height)
      return true;
    else return false;
  }

  public boolean hasZeros()
  {
    if(this.column == 0 || this.row == 0)
      return true;
    else return false;
  }

  public  boolean equal(Position that)
  {
    if(this.column == that.column && this.row == that.row)
      return true;
    else return  false;
  }
  public Position getPosition() { return this; }
  public int getColumn() { return column; }
  public int getRow() { return row; }
  public void setColumn(int column) { this.column = column; }
  public void setRow(int row) { this.row = row; }
}