package Graphics.Pictures;

import static Graphics.position.ColorLink.MASK;
import Graphics.position.Position;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.image.*;
import Graphics.Shape;

import java.io.File;

public class Picture
{
  private static Picture instance;
  private int width = 0;
  private int height = 0;
  private int colorCount = 0;
  private Position maximum;
  private WritableImage image;
  PixelReader pixelReader;
  PixelWriter pixelWriter;

  private Picture(String imageFile)
  {
    Image interim;



    File file = new File(imageFile);
    interim = new Image(file.toURI().toString());

    width = (int) interim.getWidth();
    height = (int) interim.getHeight();

    maximum = new Position(width - 1,height - 1);

    byte[] buffer = new byte[width * height * 4];

    image = new WritableImage(width, height);
    pixelReader = interim.getPixelReader();
    pixelWriter = image.getPixelWriter();

    // The miracle pixelReader Writer creator
    pixelReader.getPixels(0, 0, width, height, PixelFormat.getByteBgraInstance(),
        buffer, 0, width * 4);
    pixelWriter.setPixels(0, 0, width, height, PixelFormat.getByteBgraInstance(),
        buffer, 0, width * 4);
    pixelReader = image.getPixelReader();
    // taaDaa
  }

  public WritableImage getImage(Shape Shape)
  {
    WritableImage piece = new WritableImage(Shape.width(), Shape.height());
    PixelWriter pixelWriter = piece.getPixelWriter();
    byte[] buffer = new byte[Shape.width() * Shape.height() * 4];
    pixelReader.getPixels(Shape.x(),Shape.y(),Shape.width(),Shape.height(),PixelFormat.getByteBgraInstance(),buffer,0,Shape.width() * 4);
    pixelWriter.setPixels(0,0,Shape.width(),Shape.height(),PixelFormat.getByteBgraInstance(),buffer,0,Shape.width() * 4);
    return piece;
  }

  public Color  getPixel(Position where)
  {
    return pixelReader.getColor(where.column,where.row);
  }
  public Color  getPixel(int x, int y)

  {
    return pixelReader.getColor(x,y);
  }

  public void setPixel(int x, int y, Color mask) { pixelWriter.setColor(x,y,mask); }
  public void setPixel(Position where, Color mask) { pixelWriter.setColor(where.column,where.row,mask); }

  public void pixelDinner(Shape food) {
    for (int y = food.y(); y < food.height(); y++)
      for (int x = food.x(); x < food.width(); ++x)
        if (this.getPixel(x,y).equals(food)) this.setPixel(x,y, Color.RED);
  }

  public void pixelEatter(Color food,Position position)
  {
    for(int quad = 1; quad < 9; ++quad)
    {
      Position test = new Position(position);
      test.changeQuad(quad);
      if(checkMove(test) && this.getPixel(test).equals(food)) {
        pixelEatter(food, test);
      } else test.makeEqual(position);
      this.setPixel(position,MASK);
    }
  }

  public boolean checkMove(Position move) {
    if (move.column > maximum.column || move.row > maximum.row ||
      move.column < 0 || move.row < 0) return false;
    else return true;
  }

  public int getArea() { return width * height; }
  public void setColorCount(int colorCount) { this.colorCount = colorCount; }
  public int getColorCount() { return colorCount; }
  public Image getImage() { return image; }
  public int getWidth() {return width;}
  public int getHeight() {return height;}
  //Singleton double instance method to accommodate file name passing
  public static Picture getInstance() { return instance; }
  public static Picture getInstance(String theFile)
  {
    if (instance == null) instance = new Picture(theFile);
    return instance;
  }

}





