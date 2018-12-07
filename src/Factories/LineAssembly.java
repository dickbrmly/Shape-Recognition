package Factories;

import Position.Position;
import javafx.geometry.Pos;

import java.util.List;

public class LineAssembly extends VectorObjectFactory {

  public LineAssembly(List<Position> pixels, Position origin) {

    length = pixels.size();
    start = pixels.get(0);
    radius = 0;

    int dividend = pixels.get(length - 1).row - pixels.get(0).row;
    int divisor = pixels.get(length - 1).column - pixels.get(0).column;

    if(dividend == 0 && divisor > 0) angle = 0.0;
    else if (dividend == 0 && divisor < 0) angle = 180.0;
    else if (divisor == 0 && dividend > divisor) angle = 90.0 * -1.0;
    else if (divisor == 0 && dividend < divisor) angle = 90.0;
    else angle = Math.atan(dividend / divisor) * -1.0;
    thickness = stub(pixels.get(length / 2));
  }


}
