package Graphics;

import Graphics.Pictures.Picture;
import Graphics.position.Palete;
import Graphics.position.Position;
import javafx.scene.paint.Color;

import java.util.Stack;

public class Object
{
  Picture picture = Picture.getInstance();

  Palete color;
  double distribution;
  Position position = new Position();
  Stack<Position> component = new Stack<Position>();
  Stack<Position> edgeComponent = new Stack<Position>();
  int surfaces;

  Object nextObject;

  public Object(Color color, Position position) {
    this.position = position;
//    picture.writer.setColor(position.column,position.row,color);
  }
}
