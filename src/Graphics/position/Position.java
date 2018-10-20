package Graphics.position;

public class Position
{
  public int column;
  public int row;

  public Position() { }

  public Position(int x, int y)
  {
    column = x;
    row = y;
  }

  Position(Position there)
  {
    column = there.column;
    row = there.row;
  }
  public Position getPosition() { return this; }
}