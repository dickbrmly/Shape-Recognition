package Graphics;

import Graphics.Pictures.Picture;
import Graphics.position.Palete;
import Graphics.position.Position;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritablePixelFormat;

import java.awt.*;
import java.awt.image.BufferedImage;
import javafx.scene.paint.Color;
import javax.imageio.ImageIO;

public class Mother
{   //mother finds and creates shape files from the picture file
  private static Mother mother = new Mother();
  Picture picture = Picture.getInstance();
  Position position = new Position(0,0);
  Palete table = new Palete(picture.reader.getColor(position.column,position.row));
  ObjectFiler objectFiler = new ObjectFiler();
  PictureFiler pictureFiler = new PictureFiler("jpeg");
  BufferedImage objectPixels = null;

  PixelReader subimage = picture.img.getPixelReader();

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
    int count = 1;

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
    Palete lastColor = new Palete();
    Object current = new Object(lastColor, position);

    for (position.row = 0; position.row < picture.height; position.row++)
    {
      for (position.column = 0; position.column < picture.width; position.column++)
        if (!picture.reader.getColor(position.column, position.row).equals(lastColor.getColor())) {
          lastColor.setColor(picture.reader.getColor(position.column, position.row));
          Object object = new Object(lastColor, position);
          objectFiler.file(object, count);
          BufferedImage objectPixels = SwingFXUtils.fromFXImage(picture.img,null);
          objectPixels = objectPixels.getSubimage(object.left(),object.top(),
                  object.rht() - object.left(),object.btm() - object.top());
          pictureFiler.file(objectPixels, count);
          count++;
        }
    }
  }

  public static Mother getInstance() { return mother; }
}