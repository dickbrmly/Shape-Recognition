package Graphics.Pictures;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;

public class Picture
{
  public PixelReader reader;
  public PixelWriter writer;
  public Image img;
  public int width;
  public int height;
  public int colorCount;

  private static Picture picture = new Picture();
  public static Picture getInstance()
  {
    return picture;
  }
  private Picture()  {  }


}





