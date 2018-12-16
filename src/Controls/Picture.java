package Controls;

import static Utilities.ColorLink.MASK;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.image.*;
import Utilities.ColorLink;
import Position.Position;
import java.util.List;
import Objects.Shape;
import java.io.File;


public class Picture
{
  private int width = 0;
  private int height = 0;
  private int colorCount = 0;
  private Position maximum;
  private final WritableImage image;
  PixelReader pixelReader;
  PixelWriter pixelWriter;

  public Picture(String imageFile)
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

  public WritableImage getImage(Shape shape)
  {
    WritableImage piece = new WritableImage(shape.width, shape.height);
    PixelWriter pixelChunk = piece.getPixelWriter();
    byte[] buffer = new byte[shape.width * shape.height * 4];
    pixelReader.getPixels(shape.Minimum.column,shape.Minimum.row,shape.width,shape.height,PixelFormat.getByteBgraInstance(),
        buffer,0,shape.width * 4);
    pixelChunk.setPixels(0,0,shape.width,shape.height,PixelFormat.getByteBgraInstance(),buffer,
        0,shape.width * 4);
    return piece;
  }

  public javafx.scene.paint.Color  getFxPixel(Position where)
  {
    if(!checkMove(where)) return MASK.getFxColor();
    return pixelReader.getColor(where.column,where.row);
  }

  public java.awt.Color  getAwtPixel(Position where)
  {
    if(!checkMove(where)) return MASK.getAwtColor();

    return new ColorLink(pixelReader.getColor(where.column,where.row)).getAwtColor();
  }

  public Color  getFxPixel(int x, int y) { return pixelReader.getColor(x,y); }

  public void setPixel(int x, int y, javafx.scene.paint.Color mask) { pixelWriter.setColor(x,y,mask); }
  public void setPixel(Position where, javafx.scene.paint.Color mask) { pixelWriter.setColor(where.column,where.row,mask); }

  public void pixelDinner(javafx.scene.paint.Color background) {
    for (int y = 0; y < height; y++)
      for (int x = 0; x < width; ++x)
        if (this.getFxPixel(x,y).equals(background)) pixelWriter.setColor(x,y, MASK.getFxColor());
  }

  public WritableImage pixelEatter(final Shape item, Color food)
  {
    int lastRow = item.height + item.Minimum.row;
    WritableImage piece = new WritableImage(item.width + 1, item.height + 1);
    PixelWriter pixelChunk = piece.getPixelWriter();
    List<Position> edge = item.edge;
    Position left = new Position();


    for (int x = item.Minimum.row; x <= lastRow; ++x)
    {
      for (Position right : edge)
      {
        if (right.row == x && this.getFxPixel(right.column + 1, right.row) != food)
        {
          left.makeEqual(right);
          while (!this.getFxPixel(left.column - 1, left.row).equals(MASK.getFxColor())) -- left.column;
          eatALine(left.column, right.column, right.row);
          for (int y = left.column; y <= right.column; y++)
            pixelChunk.setColor(y - item.Minimum.column,right.row - item.Minimum.row,Color.BLACK);
        }
      }
      for (Position lft : edge)
      {
        if (lft.row == x && this.getFxPixel(lft.column - 1, lft.row) != food)
        {
          left.makeEqual(lft);
          while (!this.getFxPixel(left.column + 1, left.row).equals(MASK.getFxColor())) ++left.column;
          eatALine(lft.column, left.column, lft.row);
          for (int y = lft.column; y <= left.column; y++)
            pixelChunk.setColor(y - item.Minimum.column,left.row - item.Minimum.row,Color.BLACK);
        }
      }
    }
    return piece;
  }

    private void eatALine(int left,int right, int row) {
    for (int x = left; x <= right; x++) pixelWriter.setColor(x,row,MASK.getFxColor());
  }

  public boolean checkMove(Position move) {
    if (move.column > maximum.column || move.row > maximum.row ||
      move.column < 0 || move.row < 0) return false;
    else return true;
  }

  public int getArea() { return width * height; }
  public void setColorCount(int colorCount) { this.colorCount = colorCount; }
  public int getColorCount() { return colorCount; }
  public WritableImage getImage() { return image; }
  public int getWidth() {return width;}
  public int getHeight() {return height;}
}





