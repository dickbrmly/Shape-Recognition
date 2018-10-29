package Graphics.position;

public class Direction extends Position
{
  public int quad; //There are four quadrants in a circle and eight half quadrants.  It so
  // happens that there are eight pixel positions from a pixel position as well. The angular
  // change from a pixel position is therefore in pi/4 radian increments.
  /*
   *       Example single pixel move.
   *               6 7 8
   *               5 x 1
   *               4 3 2
   * */

  public Direction()
  {
    column = 0;
    quad = 1;
    row = 0;
  }

  public Direction(Direction there)
  {
    column = there.column;
    quad =  there.quad;
    row = there.row;
  }

  public Direction(Position there, int quad)
  {
    column = there.column;
    quad =  quad;
    row = there.row;
  }

  public void makeEqual(Direction that)
  {
    this.quad = that.quad;
    this.column = that.column;
    this.row = that.row;
  }
  public  boolean equal(Direction that)
  {
    if(this.quad == that.quad && this.column == that.column && this.row == that.row)
      return true;
    else return  false;
  }
  public boolean overEdge(int width, int height)
  { //determine if the position is on an edge
    if(this.column < 0 || this.row < 0 || this.column > width || this.row > height)
      return true;
    else return false;
  }
  public boolean zeros()
  {
      if(this.column == 0 || this.row == 0)
          return true;
      else return false;
  }
}
