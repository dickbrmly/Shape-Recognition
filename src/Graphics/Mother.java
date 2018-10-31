package Graphics;

import Graphics.position.Palete;
import Graphics.position.Position;
import javafx.scene.image.*;
import javafx.scene.image.Image;
import java.awt.image.BufferedImage;

import static Graphics.Pictures.Picture.*;
import static javafx.embed.swing.SwingFXUtils.toFXImage;

public class Mother
{   //mother finds and creates shape files from the picture file
  private static Mother mother = new Mother();
  Position position = new Position(0,0);


  WritableImage interim = new WritableImage(width,height);
  Image viewedImage = toFXImage(img,interim);
  Palete table = new Palete(viewedImage.getPixelReader()
    .getArgb(position.column,position.row));

  ObjectFiler objectFiler = new ObjectFiler();
  PictureFiler pictureFiler = new PictureFiler("jpeg");
  BufferedImage objectPixels = null;

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

    for (position.row = 0; position.row < height; position.row++)
    {
      for (position.column = 0; position.column < width; position.column++)
      {
        table = first;
        for (int j = 0; j <= colorCount; j++)
        {
          if (img.getRGB(position.column, position.row) == (table.getColor()))
          {
            table.setCount(table.getCount() + 1);
            found = true;
            break;
          } else if (table.getNextLink() != null) table = table.getNextLink();
        }
        if (!found)
        {
          colorCount = colorCount + 1;
          Palete newColor = new Palete(img.getRGB(position.column, position.row));
          table.setNextLink(newColor);
          table = newColor;
        }
        found = false;
      }
    }
    table = first;
    for (int j = 0; j < colorCount; j++)
    {
      table.setRatio((double) area());
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

    for (position.row = 0; position.row < height; position.row++)
    {
      for (position.column = 0; position.column < width; position.column++)
        if (img.getRGB(position.column, position.row) != lastColor.getColor()) {
          lastColor.setColor(img.getRGB(position.column, position.row));
          Object object = new Object(lastColor, position);
          objectFiler.file(object, count);
          objectPixels = img.getSubimage(object.x(),object.y(),
            object.width(),object.height());
          pictureFiler.file(objectPixels, count);
          count++;
        }
    }
  }

  public static Mother getInstance() { return mother; }
}