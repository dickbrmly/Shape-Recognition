package Objects;

import javafx.scene.image.WritableImage;
import Assemblies.VectorObjectAssembly;
import java.util.LinkedList;
import java.util.ArrayList;
import Position.Position;

import java.util.List;

public class Shape implements java.io.Serializable {
  public List<VectorObjectAssembly> vectorMap = new LinkedList<>();
  public List<Position> edge = new ArrayList<Position>();
  public Position Minimum = new Position();
  public java.awt.Color color;
  public WritableImage image;
  public double distribution;
  public int width;
  public int height;
}

/*

                                                  Vector Object
                        Line Vector                                          Arch Vector
                    public Position start;                                public Position start;
                    public Position end;                                  public Position end;
                    public int thickness;                                 public int thickness;
                    public int quadrant;                                  public Position origin;
                                                                          public int radius;
  */