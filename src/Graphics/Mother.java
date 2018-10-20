package Graphics;

import Graphics.Pictures.Picture;
import Graphics.position.Palete;
import Graphics.position.Position;
import javafx.scene.paint.Color;

public class Mother
{
  private static Mother mother = new Mother();
  Picture picture = Picture.getInstance();
  Position position = new Position(0,0);
  Palete table = new Palete(picture.reader.getColor(position.column,position.row));
  final Palete first = table;

  /*
  *  Mother determines the color table which is a linked list of colors that are in the image with statistics
  *  to determine What the objects that have that color could be.  These statistics will be used to eliminate
  *  for and back ground chatter.
  *
  * */
  private Mother()
  {/* find all colors within a picture and determine ratios */
    boolean found = false;
    for (position.row = 0; position.row < picture.height; position.row++)
    {
      for (position.column = 0; position.column < picture.width; position.column++)
      {
        table = first;
        for (int j = 0; j <= picture.colorCount; j++)
        {
          if (picture.reader.getColor(position.column, position.row).equals(table.getColor()))
          {
            table.setCount(table.getCount() + 1);
            found = true;
            break;
          } else if (table.getNextLink() != null) table = table.getNextLink();
        }
        if (!found)
        {
          picture.colorCount = picture.colorCount + 1;
          Palete newColor = new Palete(picture.reader.getColor(position.column, position.row));
          table.setNextLink(newColor);
          table = newColor;
        }
        found = false;
      }
    }
    table = first;
    for (int j = 0; j < picture.colorCount; j++)
    {
      table.setRatio((double) (picture.height * picture.width));
      table = table.getNextLink();
    }
    /*
    *     find all the shapes within a picture
    *
    * */
    position.column = 0;
    position.row = 0;
    Palete lastColor = new Palete(picture.reader.getColor(position.column,position.row));
    Object current = new Object(lastColor, position);

    for (position.row = 0; position.row < picture.height; position.row++) {
      for (position.column = 0; position.column < picture.width; position.column++) {
        if (!picture.reader.getColor(position.column, position.row).equals(lastColor)) {
          lastColor.setColor(picture.reader.getColor(position.column, position.row));
          Object object = new Object(lastColor, position);
          //current.nextObject = object;
          //current = object;
        }
      }
    }
  }
  public static Mother getInstance() { return mother; }
}