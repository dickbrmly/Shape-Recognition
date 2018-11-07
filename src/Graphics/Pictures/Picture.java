package Graphics.Pictures;

import java.awt.image.BufferedImage;
import java.io.File;

import Graphics.position.Position;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Picture
{
  private static Picture instance;
  private PixelReader pixelReader;
  private PixelWriter pixelWriter;
  private WritableImage image;
  private BufferedImage original;
  private int width = 0;
  private int height = 0;
  private int colorCount = 0;

  private Picture(String imageFile)
  {
    File file = new File(imageFile);
    Image interim = new Image(file.toURI().toString());

    width = (int) interim.getWidth();
    height = (int) interim.getHeight();

    byte[] buffer = new byte[width * height * 4];

    original = SwingFXUtils.fromFXImage(interim,null);
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

  public Color  getPixel(Position where)
  {
    return pixelReader.getColor(where.column,where.row);
  }
  public Color  getPixel(int x, int y)
  {
    return pixelReader.getColor(x,y);
  }
  public boolean setPixel(Position where, Color mask)
  {
    try
    {
      pixelWriter.setColor(where.column,where.row,mask);
      return true;
    }
    catch(Exception e)
    {
      return false;
    }
  }

  public int getArea() { return width * height; }
  public void setColorCount(int colorCount) { this.colorCount = colorCount; }
  public int getColorCount() { return colorCount; }
  public Image getImage() { return image; }
  public int getWidth() {return width;}
  public int getHeight() {return height;}
  public BufferedImage getBufferedImage() {return original;}
  //Singleton double instance method to accommodate file name passing
  public static Picture getInstance() { return instance; }
  public static Picture getInstance(String theFile)
  {
    if (instance == null) instance = new Picture(theFile);
    return instance;
  }

}





