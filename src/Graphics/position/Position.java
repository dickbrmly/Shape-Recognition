package Graphics.position;

public class Position
{
  public int column;
  public int row;

  public Position() { }
  public Position(int x, int y)
  {
    this.column = x;
    this.row = y;
  }
  Position(Position there)
  {
    this.column = there.column;
    this.row = there.row;
  }
  public Position getPosition() { return this; }
}
