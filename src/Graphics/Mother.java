package Graphics;

import Graphics.Pictures.Picture;
import Graphics.position.ColorLink;
import Graphics.position.Position;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.*;
import java.awt.image.BufferedImage;

public class Mother
{   //mother finds and creates shape files from the picture file
  Position position = new Position(0,0);
  Picture picture = Picture.getInstance();
  PixelReader pixelReader = picture.getPixelReader();


  ColorLink table = new ColorLink(picture.getPixelReader().getColor(position.getColumn(),position.getRow()));

  ObjectFiler objectFiler = new ObjectFiler();
  PictureFiler pictureFiler = new PictureFiler("jpeg");
  BufferedImage original = SwingFXUtils.fromFXImage(picture.getImage(),null);

  final ColorLink first = table;

  /*
  *  Mother determines the color table which is a linked list of colors that are in the image with statistics
  *  to determine What the objects that have that color could be.  These statistics will be used to eliminate
  *  for and back ground chatter.
  *
  * */
  public Mother()
  {/* find all colors within a picture and determine ratios */
    boolean found = false;
    int count = 1;

    for (position.setRow(0); position.getRow() < picture.getHeight(); position.setRow(position.getRow() + 1))
    {
      for (position.setColumn(0); position.getColumn() < picture.getWidth();
           position.setColumn(position.getColumn() + 1))
      {
        table = first;
        for (int j = 0; j <= picture.getColorCount(); j++)
        {
          if (pixelReader.getColor(position.getColumn(), position.getRow()) == (table.getColor()))
          {
            table.setCount(table.getCount() + 1);
            found = true;
            break;
          } else if (table.getNextLink() != null) table = table.getNextLink();
        }
        if (!found)
        {
          picture.setColorCount(picture.getColorCount() + 1);
          ColorLink newColor = new ColorLink(pixelReader.getColor(position.getColumn(), position.getRow()));
          table.setNextLink(newColor);
          table = newColor;
        }
        found = false;
      }
    }
    table = first;
    for (int j = 0; j < picture.getColorCount(); j++)
    {
      table.setRatio((double) picture.getArea());
      table = table.getNextLink();
    }
    /*
    *     find all the shapes within a picture
    *
    * */
    position.setColumn(0);
    position.setRow(0);
    ColorLink lastColor = new ColorLink();
    Object current = new Object(lastColor, position);

    for (position.setRow(0); position.getRow() < picture.getHeight(); position.setRow(position.getRow() + 1))
    {
      for (position.setColumn(0); position.getColumn() < picture.getWidth(); position.setColumn(position.getColumn() + 1))
        if (pixelReader.getColor(position.getColumn(), position.getRow()) != lastColor.getColor()) {
          lastColor.setColor(pixelReader.getColor(position.getColumn(), position.getRow()));
          Object object = new Object(lastColor, position);
          objectFiler.file(object, count);
          BufferedImage bufferedImage = original.getSubimage(object.x(),object.y(),
            object.width(),object.height());
          pictureFiler.file(bufferedImage, count);
        }
    }
  }
}