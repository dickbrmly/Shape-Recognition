package Graphics.position;


import javafx.scene.paint.Color;

public class ColorLink implements java.io.Serializable
{
  private Color color; //A discovered color in the image
  private int count;  //number of pixels that exist in this color
  private double ratio; //ratio of this color within the image

  ColorLink nextLink;

  public ColorLink()
  {
    color = new Color(0,0,0,1);
    count = 0;
  }
  public ColorLink (Color color)
  {
    this.color = color;
    this.count = 1;  //if the object is created, it's for a color pixel.
  }
  public ColorLink(ColorLink color) {
    this.color = color.color;
    this.count = color.getCount();
  }
  public ColorLink getPalete() {return this; }

  public void setColor(Color hue) { color = hue; }
  public Color getColor() { return color;}
  public void setCount(int number) { count = number;}
  public int getCount() { return count; }
  public void setRatio(double total) { ratio = count/total; } //pass the total number of pixels.
  public double getRatio() {return ratio; }
  public void setNextLink(ColorLink nextLink) { this.nextLink = nextLink; }
  public ColorLink getNextLink() { return this.nextLink; }
}
