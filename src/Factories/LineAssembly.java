package Factories;

import Position.Position;
import java.util.List;

public class LineAssembly extends VectorObjectFactory {

  public int quadrant;

  public LineAssembly(List<Position> pixels) {

    length = pixels.size();
    start = pixels.get(0);

    int rowDiff = pixels.get(length - 1).row - pixels.get(0).row;
    int columnDiff = pixels.get(length - 1).column - pixels.get(0).column;

    if(rowDiff == 0 && columnDiff > 0) quadrant = 1;
    else if (rowDiff == 0 && columnDiff < 0) quadrant = 5;
    else if (columnDiff == 0 && rowDiff > 0) quadrant = 3;
    else if (columnDiff == 0 && rowDiff < 0) quadrant = 7;
    else if (columnDiff > 0 && rowDiff > 0) quadrant = 2;
    else if (columnDiff > 0 && rowDiff < 0) quadrant = 8;
    else if (columnDiff < 0 && rowDiff > 0) quadrant = 4;
    else if (columnDiff < 0 && rowDiff < 0) quadrant = 6;

    thickness = getThickness(pixels.get(length / 2));
  }
}
