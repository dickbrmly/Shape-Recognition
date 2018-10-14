package Graphics.position;

import javafx.scene.paint.Color;

public class Palete
{
  private  Color color;
  private int count;
  private double ratio; //ratio of this color within the image

  Palete nextPoint;

  public Palete()
  {
    color = Color.WHITE;
    count = 0;
  }
  public Palete (Color color)
  {
    this.color = color;
    this.count = 0;
  }
  public Palete(Palete color) {
    this.color = color.color;
    this.count = color.getCount();
  }
  public Palete getPalete() {return this; }

  public void setColor(Color hue) { color = hue; }
  public Color getColor() { return color;}
  public void setCount(int number) { count = number;}
  public int getCount() { return count; }
  public void setRatio(double total) { ratio = count/total; } //pass the total number of pixels.
  public double getRatio() {return ratio; }
  public void setNextLink(Palete nextPoint) { this.nextPoint = nextPoint; }
  public Palete getNextLink() { return this.nextPoint; }
}
