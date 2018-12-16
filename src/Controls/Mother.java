package Controls;

import javafx.scene.image.Image;
import Utilities.PictureFiler;
import java.util.LinkedList;
import Utilities.ShapeFiler;
import Utilities.ColorLink;
import java.util.ArrayList;
import Position.Position;
import java.util.List;
import Objects.Shape;

import static Utilities.ColorLink.MASK;

/*
  Mother processes the creation of shape objects and creates doppelganger files.
 */
public class Mother {   //mother finds and creates shape files from the picture file


  /*
   *  Mother determines the color table which is a linked list of colors that are in the image with statistics
   *  to determine What the Shapes that have that color could be.  These statistics will be used to eliminate
   *  for and back ground chatter.
   *
   * */
  public Mother(Picture picture) {/* find all colors within a picture and determine ratios */

    PictureFiler pictureFiler = new PictureFiler("png");
    Position position = new Position(0, 0);
    List<ColorLink> table = new ArrayList<ColorLink>();
    ShapeFiler shapeFiler = new ShapeFiler();
    List<Shape> shapes = new LinkedList<Shape>();
    ShapeFinder shapeFinder = new ShapeFinder(picture);
    LineDrafter lineDrafter = new LineDrafter();
    ArchDrafter archDrafter = new ArchDrafter();
    boolean found = false;
    ColorLink lastColor;
    int counter = 0;
    Image image;


    picture.setColorCount(1);
    final ColorLink first = new ColorLink(picture.getFxPixel(position));
    table.add(first);

    for (position.row = 0; position.row < picture.getHeight(); position.row++) {
      for (position.column = 0; position.column < picture.getWidth(); position.column++) {
        for (ColorLink sample : table) {
          if (picture.getFxPixel(position).equals(sample.getFxColor())) {
            sample.setCount(sample.getCount() + 1);
            found = true;
            break;
          }
        }
        if (!found) {
          picture.setColorCount(picture.getColorCount() + 1);
          ColorLink newColor = new ColorLink(picture.getFxPixel(position));
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
    for (ColorLink sample : table)
      if (background.getRatio() < sample.getRatio()) background.makeEqual(sample);

    position.setPosition(0, 0, 1);
    picture.pixelDinner(background.getFxColor()); //dinner on background color.

    for (position.row = 0; position.row < picture.getHeight(); position.row++) {
      for (position.column = 0; position.column < picture.getWidth(); position.column++) {
        lastColor = new ColorLink(picture.getFxPixel(position));

        if (!lastColor.getFxColor().equals(MASK.getFxColor())) {
          shapes.add(counter, shapeFinder.discover(lastColor.getAwtColor(), position));

          lineDrafter.construct(shapes.get(counter));
          archDrafter.construct(shapes.get(counter));

          pictureFiler.file(picture.pixelEatter(shapes.get(counter), lastColor.getFxColor()), counter);
          shapeFiler.file(shapes.get(counter), counter);
          counter++;
        }
      }
    }
  }
}