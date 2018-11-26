package Factories;

import position.Position;

import java.util.List;

public class VectorObjectFactory {

  public Object VectorObjectFactory(String component, List<Position> pixel) {
    switch (component) {
      case "Line":
        Object lineItem = new lineFactory(pixel);
        return lineItem;
      case "arch":
        Object archItem = new archFactory(pixel);
        return archItem;
    }
    return null;
  }

     class lineFactory {

      public lineFactory(List<Position> pixel) {
        int length = pixel.size();
        double angle = Math.atan((pixel.get(3).row - pixel.get(0).row) / (pixel.get(3).column - pixel.get(0).column));
        Position origin = new Position(pixel.get(0));
      }

    }
     class archFactory {

      public archFactory(List<Position> pixel) {

      }
    }
}

