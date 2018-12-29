package Assemblies;

import Position.Position;
import java.util.List;

public class LineAssembly  extends VectorObjectAssembly {

  public int quadrant;

  public LineAssembly(List<Position> pixels) {

    end = pixels.get(pixels.size() - 1);
    start = pixels.get(0);

    int rowDiff = pixels.get(pixels.size() - 1).row - pixels.get(0).row;
    int columnDiff = pixels.get(pixels.size() - 1).column - pixels.get(0).column;

    if(rowDiff == 0 && columnDiff > 0) quadrant = 1;
    else if (rowDiff == 0 && columnDiff < 0) quadrant = 5;
    else if (columnDiff == 0 && rowDiff > 0) quadrant = 3;
    else if (columnDiff == 0 && rowDiff < 0) quadrant = 7;
    else if (columnDiff > 0 && rowDiff > 0) quadrant = 2;
    else if (columnDiff > 0 && rowDiff < 0) quadrant = 8;
    else if (columnDiff < 0 && rowDiff > 0) quadrant = 4;
    else if (columnDiff < 0 && rowDiff < 0) quadrant = 6;

    //thickness = getThickness(item, pixels.get(1));
  }  /*
  public int getThickness(Shape item, final Position start)
  {
    Position here = new Position(start);
    int direction = (quadrant + 2) % 8;
    Position adder = new Position();
    switch(direction) {
      case 1:
        adder.makeEqual(1,0,direction);
        break;
      case 2:
        adder.makeEqual(1,1,direction);
        break;
      case 3:
        adder.makeEqual(0,1,direction);
        break;
      case 4:
        adder.makeEqual(-1,1,direction);
        break;
      case 5:
        adder.makeEqual(-1,0,direction);
        break;
      case 6:
        adder.makeEqual(-1,-1,direction);
        break;
      case 7:
        adder.makeEqual(0,-1,direction);
        break;
      case 8:
        adder.makeEqual(1,-1,direction);
        break;
    }
    PixelReader image = item.image.getPixelReader();
    Color color = image.getColor(here.column, here.row);
    while (image.getColor(here.column, here.row).equals(color))
      here = here.add(adder);
    return here.distance(start);
  } */
}
