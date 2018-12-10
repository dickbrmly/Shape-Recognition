package Position;

public class Position implements java.io.Serializable {
  //There are four quadrants in a circle and eight half quadrants.  It so
  // happens that there are eight pixel positions from a pixel Position as well. The angular
  // change from a pixel Position is therefore in pi/4 radian increments.
  /*
   *       Example single pixel this.
   *               6 7 8
   *               5 x 1
   *               4 3 2
   * */

  public int column;
  public int row;
  public int quad;

  public Position() { }

  public Position(int x, int y, int quad) {
    this.column = x;
    this.quad = quad;
    this.row = y;
  }

  public Position(int x, int y) {
    column = x;
    row = y;
    quad = 0;
  }

  //force a different quadrant
  public Position(Position there, int quad) {
    column = there.column;
    row = there.row;
    this.quad = quad;
  }

  public Position(Position there) {
    column = there.column;
    row = there.row;
    quad = there.quad;
  }

  public boolean positionEqual(Position that) {
    if (this.column == that.column && this.row == that.row) return true;
    else return false;
  }

  public void setPosition(Position there) {
    column = there.column;
    row = there.row;
    quad = there.quad;
  }

  public void setPosition(int x, int y, int direction) {
    column = x;
    row = y;
    quad = 1;
  }

  public void makeEqual(Position that) {
    this.column = that.column;
    this.row = that.row;
    this.quad = that.quad;
  }

  public void makeEqual(int column, int row,int quad) {
    this.column = column;
    this.row = row;
    this.quad = quad;
  }

  public boolean overEdge(int width, int height) { //determine if the Position is on an edge
    if (this.column < 0 || this.row < 0 || this.column > --width || this.row > --height) return true;
    else return false;
  }

  public Position add(Position additive) {
    Position result = new Position();
    result.column = this.column + additive.column;
    result.row = this.row + additive.row;
    return result;
  }

  public double slope(Position that) {
    return (double) (this.row - that.row) / (this.column - that.column);
  }

  public Position subtract(Position additive) {
    Position result = new Position();
    result.column = this.column - additive.column;
    result.row = this.row - additive.row;
    return result;
  }

  public boolean equal(Position that) {
    if (this.column == that.column && this.row == that.row) return true;
    else return false;
  }

  public boolean notEqual(Position that) {
    if (this.column != that.column || this.row != that.row) return true;
    else return false;
  }

  public void changeQuad(int quad) {
    this.quad = quad;
    switch (quad) {
      case 1:
        this.column = this.column + 1;
        break;
      case 2:
        this.row = this.row + 1;
        this.column = this.column + 1;
        break;
      case 3:
        this.row = this.row + 1;
        break;
      case 4:
        this.column = this.column - 1;
        this.row = this.row + 1;
        break;
      case 5:
        this.column = this.column - 1;
        break;
      case 6:
        this.column = this.column - 1;
        this.row = this.row - 1;
        break;
      case 7:
        this.row = this.row - 1;
        break;
      default:
        this.column = this.column + 1;
        this.row = this.row - 1;
        break;
    }
  }

    public int distance(Position end) {
      return (int) Math.abs(Math.sqrt((end.column - this.column) * (end.column - this.column) +
          (end.row - this.row) * (end.row - this.row)));
    }
    public Position getPosition () { return this; }

    public int getColumn () { return column; }

    public int getRow () { return row; }

    public void setColumn ( int column){ this.column = column; }

    public void setRow ( int row){ this.row = row; }
  }