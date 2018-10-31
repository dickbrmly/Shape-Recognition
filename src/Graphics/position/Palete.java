package Graphics.position;


import org.w3c.dom.css.RGBColor;

import java.awt.*;

import static javafx.scene.input.KeyCode.F;

public class Palete
{
  private int color;
  private int count;
  private double ratio; //ratio of this color within the image

  Palete nextPoint;

  public Palete()
  {
    color = 0xFFFFFFFF;
    count = 0;
  }
  public Palete (int color)
  {
    this.color = color;
    this.count = 0;
  }
  public Palete(Palete color) {
    this.color = color.color;
    this.count = color.getCount();
  }
  public Palete getPalete() {return this; }

  public void setColor(int hue) { color = hue; }
  public int getColor() { return color;}
  public void setCount(int number) { count = number;}
  public int getCount() { return count; }
  public void setRatio(double total) { ratio = count/total; } //pass the total number of pixels.
  public double getRatio() {return ratio; }
  public void setNextLink(Palete nextPoint) { this.nextPoint = nextPoint; }
  public Palete getNextLink() { return this.nextPoint; }
}
