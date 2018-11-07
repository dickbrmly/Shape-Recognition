package Graphics;

import Graphics.Pictures.Picture;
import Graphics.position.ColorLink;
import Graphics.position.Position;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.*;
import javafx.scene.paint.Color;

import java.awt.image.BufferedImage;

public class Mother {   //mother finds and creates shape files from the picture file
  Position position = new Position(0, 0);
  Picture picture = Picture.getInstance();


  ColorLink table = new ColorLink(picture.getPixel(position));

  ObjectFiler objectFiler = new ObjectFiler();
  PictureFiler pictureFiler = new PictureFiler("jpeg");


  final ColorLink first = table;

  /*
   *  Mother determines the color table which is a linked list of colors that are in the image with statistics
   *  to determine What the objects that have that color could be.  These statistics will be used to eliminate
   *  for and back ground chatter.
   *
   * */
  public Mother() {/* find all colors within a picture and determine ratios */
    boolean found = false;
    picture.setColorCount(1);

    for (position.row = 0; position.row < picture.getHeight(); position.row++) {
      for (position.column = 0; position.column < picture.getWidth(); position.column++) {
        table = first;
        for (int j = 0; j <= picture.getColorCount(); j++) {
          if (picture.getPixel(position).equals(table.getColor())) {
            table.setCount(table.getCount() + 1);
            found = true;
            break;
          } else if (table.getNextLink() != null) table = table.getNextLink();
        }
        if (!found) {
          picture.setColorCount(picture.getColorCount() + 1);
          ColorLink newColor = new ColorLink(picture.getPixel(position));
          table.setNextLink(newColor);
          table = newColor;
        }
        found = false;
      }
    }
    table = first;
    for (int j = 0; j < picture.getColorCount(); j++) {
      table.setRatio((double) picture.getArea());
      table = table.getNextLink();
    }
    /*
     *     find all the shapes within a picture
     *
     * */
    position.column = 0;
    position.row = 0;
    Color lastColor = first.getColor();
    Object current = new Object(picture.getPixel(0, 0), position);

    for (position.row = 0; position.row < picture.getHeight(); position.row++) {
      for (position.column = 0; position.column < picture.getWidth(); position.column++)
        if (picture.getPixel(position).equals(lastColor)) {
          lastColor = picture.getPixel(position);
          Object object = new Object(lastColor, position);
          objectFiler.file(object, table.getCount());
          BufferedImage bufferedImage = picture.getBufferedImage().
              getSubimage(object.x(), object.y(), object.width(), object.height());
          pictureFiler.file(bufferedImage, table.getCount());
        }
    }
  }
}