package Objects;

import Factories.VectorObjectFactory;
import java.util.LinkedList;
import java.util.ArrayList;
import Position.Position;
import java.util.List;

public class Shape implements java.io.Serializable {
  public List<VectorObjectFactory> vectorMap = new LinkedList();
  public List<Position> edge = new ArrayList<Position>();
  public double distribution;
  public java.awt.Color color;
  public Position Minimum = new Position();
  public int width;
  public int height;
}

/*

                                                  Vector Object
                        Line Vector                                          Arch Vector
                    public Position start;                                public Position start;
                    public int length;                                    public int length;
                    public int thickness;                                 public int thickness;
                    public int quadrant;                                  public Position origin;
                                                                          public int radius;
  */