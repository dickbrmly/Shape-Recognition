package Utilities;


import javafx.scene.paint.Color;

public class ColorLink implements java.io.Serializable
{
  javafx.scene.paint.Color FxColor;
  java.awt.Color AwtColor;

  private int count;  //number of pixels that exist in this color
  private double ratio; //ratio of this color within the image
  public static ColorLink MASK = new ColorLink(0, 0, 0, 0);

  public ColorLink()
  {
    FxColor = new javafx.scene.paint.Color(0,0,0,1);
    AwtColor = new java.awt.Color(0,0,0,1);
    count = 0;
  }

  public ColorLink(ColorLink color) {
    FxColor = new javafx.scene.paint.Color( color.getFxColor().getRed(), color.getFxColor().getGreen(),
        color.getFxColor().getBlue(), color.getFxColor().getOpacity());
    AwtColor = new java.awt.Color(color.getAwtColor().getRed(), color.getAwtColor().getGreen(),
        color.getAwtColor().getBlue(), color.getAwtColor().getAlpha());
  }

  public ColorLink(double red, double green, double blue, double opacity) {
    FxColor = new javafx.scene.paint.Color(red,green,blue,opacity);
    AwtColor = new java.awt.Color((int)red, (int)green, (int)blue, (int)opacity);
  }

  public ColorLink(javafx.scene.paint.Color color) {
    FxColor = color;
    AwtColor = new java.awt.Color( (int)color.getRed(), (int)color.getGreen(), (int)color.getBlue(),
        (int)color.getOpacity());
  }

  public ColorLink(java.awt.Color color) {
    AwtColor = color;
    FxColor = new javafx.scene.paint.Color( color.getRed(), color.getGreen(), color.getBlue(), color.getTransparency());
  }

  public void makeEqual(ColorLink that)
  {
    this.AwtColor = that.AwtColor;
    this.FxColor = that.FxColor;
    this.count = that.count;
    this.ratio = that.ratio;
  }

  public javafx.scene.paint.Color getFxColor() { return FxColor; }
  public java.awt.Color getAwtColor() { return AwtColor; }
  public void setCount(int number) { count = number;}
  public int getCount() { return count; }
  public void setRatio(double total) { ratio = count/total; } //pass the total number of pixels.
  public double getRatio() {return ratio; }
}
