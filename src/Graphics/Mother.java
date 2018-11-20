package Graphics;

import Graphics.position.ColorLink;
import Graphics.position.Position;
import Graphics.Pictures.Picture;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import java.util.LinkedList;
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
  List<Shape> shapes = new LinkedList<Shape>();
  Image image;
  /*
   *  Mother determines the color table which is a linked list of colors that are in the image with statistics
   *  to determine What the Shapes that have that color could be.  These statistics will be used to eliminate
   *  for and back ground chatter.
   *
   * */
  public Mother() {/* find all colors within a picture and determine ratios */
    boolean found = false;
    Color lastColor;

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
    ColorLink background = new ColorLink(table.get(0));
    for (ColorLink sample :table)
      if(background.getRatio() < sample.getRatio())
        background.makeEqual(sample);

    position.setPosition(0,0,1);
    picture.pixelDinner(background.getColor()); //dinner on background color.

    for (position.row = 0; position.row < picture.getHeight(); position.row++) {
      for (position.column = 0; position.column < picture.getWidth(); position.column++) {
        lastColor = picture.getPixel(position);
        if (!lastColor.equals(MASK)) {
          shapes.add(counter, new Shape(counter, lastColor, position));
          pictureFiler.file(picture.pixelEatter(shapes.get(counter),lastColor), counter);
          counter++;
        }
      }
    }
  }
}