package Graphics.Pictures;

import java.io.File;
import javafx.scene.image.*;
import javafx.scene.image.Image;

public class Picture
{
  private static Picture instance;
  private PixelReader pixelReader;
  private PixelWriter pixelWriter;
  private Image image;

  private int width = 0;
  private int height = 0;
  private int colorCount = 0;

  private Picture(String imageFile)
  {
    File file = new File(imageFile);
    image = new Image(file.toURI().toString());

    width = (int) image.getWidth() - 1;
    height = (int) image.getHeight() - 1;
    byte[] buffer = new byte[width * height * 4];

    WritableImage interim = new WritableImage(width, height);
    pixelReader = image.getPixelReader();
    pixelWriter = interim.getPixelWriter();

    // The miracle pixelReader Writer creator
    pixelReader.getPixels(0, 0, width, height, PixelFormat.getByteBgraInstance(),
        buffer, 0, width * 4);
    pixelWriter.setPixels(0, 0, width, height, PixelFormat.getByteBgraInstance(),
        buffer, 0, width * 4);
    // taaDaa

  }

  public PixelReader getPixelReader() {return pixelReader;}
  public PixelWriter getPixelWriter() {return pixelWriter;}
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





