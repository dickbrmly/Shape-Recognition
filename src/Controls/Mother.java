package Controls;

import Utilities.PictureFiler;
import java.util.LinkedList;
import Utilities.ShapeFiler;
import Utilities.ColorLink;
import java.util.ArrayList;
import Finders.ShapeFinder;
import Finders.HalfArchFinder;
import Finders.ArchFinder;
import Finders.CircleFinder;
import Finders.LineFinder;
import Position.Position;
import java.util.List;
import Objects.Shape;
import javafx.scene.image.WritableImage;

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
  WritableImage currentImage;

  public Mother(Picture picture) {/* find all colors within a picture and determine ratios */

    PictureFiler pictureFiler = new PictureFiler("png");
    Position position = new Position(0, 0);
    List<ColorLink> table = new ArrayList<ColorLink>();
    ShapeFiler shapeFiler = new ShapeFiler();
    List<Shape> shapes = new LinkedList<Shape>();
    ShapeFinder shapeFinder = new ShapeFinder(picture);
    LineFinder lineFinder = new LineFinder();
    HalfArchFinder halfArchFinder = new HalfArchFinder();
    ArchFinder archFinder = new ArchFinder();
    CircleFinder circleFinder = new CircleFinder();
    boolean found = false;
    ColorLink lastColor;
    int counter = 0;


    picture.colorCount = 1;
    final ColorLink first = new ColorLink(picture.getFxPixel(position));
    table.add(first);

    for (position.row = 0; position.row < picture.height; position.row++) {
      for (position.column = 0; position.column < picture.width; position.column++) {
        for (ColorLink sample : table) {
          if (picture.getFxPixel(position).equals(sample.getFxColor())) {
            sample.setCount(sample.getCount() + 1);
            found = true;
            break;
          }
        }
        if (!found) {
          picture.colorCount = picture.colorCount + 1;
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

    for (position.row = 0; position.row < picture.height; position.row++) {
      for (position.column = 0; position.column < picture.width; position.column++) {
        lastColor = new ColorLink(picture.getFxPixel(position));

        if (!lastColor.getFxColor().equals(MASK.getFxColor())) {

          shapes.add(counter, shapeFinder.discover(lastColor.getAwtColor(), position));
          shapes.get(counter).image = picture.pixelEatter(shapes.get(counter), lastColor.getFxColor());
          pictureFiler.file(shapes.get(counter).image, counter);

          //the sequence line to circle should be as such
          lineFinder.construct(shapes.get(counter));
          halfArchFinder.construct(shapes.get(counter));
          archFinder.construct(shapes.get(counter));
          circleFinder.construct(shapes.get(counter));

          shapeFiler.file(shapes.get(counter), counter);
          counter++;
        }
      }
    }
  }
}