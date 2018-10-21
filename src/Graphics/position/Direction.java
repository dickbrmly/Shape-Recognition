package Graphics.position;

public class Direction extends Position
{
  public int quad; //There re four quadrants in a circle and eight half quadrants.  It so happens that there
  //are eight pixel positions from a pixel position as well. The angular change from a pixel
  //pixel position is therefore in pi/4 radian increments.
  /*
   *       Example single pixel move.
   *               4 3 2
   *               5 x 1
   *               6 7 8
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

  public  boolean equal(Direction that)
  {
    if(this.quad == that.quad)
      if(this.column == that.column)
        if(this.row == that.row)
          return true;
        return  false;
  }
  public boolean edge(int width, int height)
  {
    if(this.column > 0 && this.row > 0 && this.column < width && this.row < height)
      return false;
    else return true;
  }
}
