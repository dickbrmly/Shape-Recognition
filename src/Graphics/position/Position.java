package Graphics.position;

public class Position
{
  private int column;
  private int row;

  public Position() { }

  public Position(int x, int y)
  {
    column = x;
    row = y;
  }

  public Position(Position there)
  {
    column = there.column;
    row = there.row;
  }

  public void setPosition(Position there)
  {
    column = there.column;
    row = there.row;
  }

  public Position getPosition() { return this; }
  public int getColumn() { return column; }
  public int getRow() { return row; }
  public void setColumn(int column) { this.column = column; }
  public void setRow(int row) { this.row = row; }
}