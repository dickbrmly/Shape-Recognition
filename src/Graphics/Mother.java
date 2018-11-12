package Graphics;

import Graphics.Pictures.Picture;
import Graphics.position.ColorLink;
import Graphics.position.Position;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static Graphics.position.ColorLink.MASK;

public class Mother {   //mother finds and creates shape files from the picture file
  Position position = new Position(0, 0);
  Picture picture = Picture.getInstance();
  ShapeFiler shapeFiler = new ShapeFiler();
  PictureFiler pictureFiler = new PictureFiler("png");
  int counter = 0;
  List<ColorLink> table = new ArrayList<ColorLink>();
  BufferedImage bufferedImage;
  Shape Shape;
  Image image;
;

  /*
   *  Mother determines the color table which is a linked list of colors that are in the image with statistics
   *  to determine What the Shapes that have that color could be.  These statistics will be used to eliminate
   *  for and back ground chatter.
   *
   * */
  public Mother() throws IOException {/* find all colors within a picture and determine ratios */
    boolean found = false;
    picture.setColorCount(1);

    final ColorLink first = new ColorLink(picture.getPixel(position));
    table.add(first);

    for (position.row = 0; position.row < picture.getHeight(); position.row++) {
      for (position.column = 0; position.column < picture.getWidth(); position.column++) {
        for (ColorLink sample : table) {
          if (picture.getPixel(position).equals(sample.getColor())) {
            sample.setCount(sample.getCount() + 1);
            found = true;
            break;
          }
        }
        if (!found) {
          picture.setColorCount(picture.getColorCount() + 1);
          ColorLink newColor = new ColorLink(picture.getPixel(position));
          table.add(newColor);
        }
        found = false;
      }
    }
    for (ColorLink sample : table) {
      sample.setRatio((double) picture.getArea());
    }
    /*
     *     find all the shapes within a picture
     *
     * */
    position.setPosition(0,0,1);
    Color lastColor = new Color(0, 0, 0, 0);

    for (position.row = 0; position.row < picture.getHeight(); position.row++) {
      for (position.column = 0; position.column < picture.getWidth(); position.column++)
        if (!(picture.getPixel(position).equals(lastColor) || picture.getPixel(position).equals(MASK)) ) {
          lastColor = picture.getPixel(position);
          Shape = new Shape(counter, lastColor, position);
          picture.pixelDinner(Shape);
          pictureFiler.file(picture.getImage(Shape), ++counter);
        }
    }
  }
}