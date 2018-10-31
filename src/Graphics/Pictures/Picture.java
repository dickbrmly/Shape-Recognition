package Graphics.Pictures;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

import java.awt.image.BufferedImage;

public class Picture
{
  public static BufferedImage img;
  public static int width;
  public static int height;
  public static int colorCount;
  public static int area()  {
    return width * height;
  }
}





