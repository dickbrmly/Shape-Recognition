package Graphics;

import Graphics.Pictures.Picture;
import Graphics.position.Direction;
import Graphics.position.Palete;
import Graphics.position.Position;

import java.util.Stack;

public class Object
{
  Picture picture = Picture.getInstance();

  double distribution;
  Direction move = new Direction();

  Stack<Position> component = new Stack<Position>();
  //Stack<Direction> edge = new Stack<~>();
  int surfaces;  //how many surfaces on the object?  Consider a line has one, a square has four etc...

  Object nextObject;

  /* The surface has an 'object' color pixel but so does the filled portion of the object.
  *  --> Therefore a surface pixel must be defined as a 'object' color pixel adjacent to
  *  a 'non-object pixel. It's only a surface pixel if and only if it is next to an out of
  *  color pixel.
  *
  *  */

  private int ceiling(int x,int y)
  {
    if(x > y) return x;
    else return y;
  }

  private int floor(int x,int y)
  {
    if(x< y) return x;
    else return y;
  }

  public Object(Palete color, Position position) {

    move.quad = 1;
    move.column = position.column;
    move.row = position.row;

    Direction beginning = new Direction(move);

    /* The rectangle made here would copy our object from the picture
    * */
    Direction leftMost = new Direction(move);
    Direction rightMost = new Direction(move);
    Direction topMost = new Direction(move);
    Direction btmMost = new Direction(move);
    move.column = move.column + 1;


    // We assume the previous position was back one column unless this is a left-most position.
    // We are looking for a surface pixel in a clockwise pattern.
    while (!move.notEqual(beginning))
    { // There are one of seven possible moves
//***********************************************************************************************************

      







      if (picture.reader.getColor(ceiling(move.column - 1,0),ceiling(move.row - 1,0)).equals(color.getColor()))
      { //4
        if(move.column == 0 && move.row == 0)
        { //
          move.column = move.column + 1;
          move.quad = 1;
        }
        else if(move.row == 0)
        { //
          move.column = move.column + 1;
          move.quad = 1;
        }
        else if(move.column == 0)
        { //
          move.row = move.row - 1;
          move.quad = 3;
        }
        else
        {
          move.column = move.column - 1;
          move.row = move.row - 1;
          move.quad = 4;
        }
      }
      else if (picture.reader.getColor(move.column, ceiling(move.row - 1,0)).equals(color.getColor()))
      { //3
        move.row = move.row - 1;
        move.quad = 3;
      }
      else if (picture.reader.getColor(floor(move.column + 1,picture.width), ceiling(move.row - 1,0)).
          equals(color.getColor()))
      { //2
        move.column = move.column + 1;
        move.row = move.row - 1;
        move.quad = 2;
      }
      else if (picture.reader.getColor(floor(move.column + 1,picture.width), move.row).equals(color.getColor()))
      { //1
        move.column = move.column + 1;
        move.quad = 1;
      }
      else if (picture.reader.getColor(floor(move.column + 1,picture.width), floor(move.row + 1,picture.height)).
          equals(color.getColor()))
      { //8
        move.column = move.column + 1;
        move.row = move.row + 1;
        move.quad = 8;
      }
      else if (picture.reader.getColor(move.column, floor(move.row + 1,picture.height)).equals(color.getColor()))
      { //7
        move.row = move.row + 1;
        move.quad = 7;
      }
      else if (picture.reader.getColor(ceiling(move.column - 1,0), floor(move.row + 1,picture.height)).
          equals(color.getColor()))
      { //6
        move.column = move.column - 1;
        move.row = move.row + 1;
        move.quad = 6;
      }
      else
      { //5 Move forward
        move.column = move.column + 1;
        move.quad = 1;
      }
//***********************************************************************************************************
// Is it the one of the outer four walls?
      if (move.column < leftMost.column)
      {
        leftMost.column = move.column;
        leftMost.row = move.row;
        leftMost.quad = move.quad;
      }
      if (move.column > rightMost.column)
      {
        rightMost.column = move.column;
        rightMost.row = move.row;
        rightMost.quad = move.quad;
      }
      if (move.row < topMost.row)
      {
        topMost.column = move.column;
        topMost.row = move.row;
        topMost.quad = move.quad;
      }
      if (move.row > btmMost.row)
      {
        leftMost.column = move.column;
        leftMost.row = move.row;
        leftMost.quad = move.quad;
      }
//***********************************************************************************************************
    }
//   picture.writer.setColor(position.column,position.row,color);
  }
}
