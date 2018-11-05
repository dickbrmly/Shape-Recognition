package Graphics;

import Graphics.Pictures.Picture;
import Graphics.position.Direction;
import Graphics.position.ColorLink;
import Graphics.position.Position;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.Stack;

public class Object implements java.io.Serializable {

    private Direction leftMost;
    private Direction rightMost;
    private Direction topMost;
    private Direction btmMost;
    private Direction temp;

    Picture picture = Picture.getInstance("src/Graphics/Pictures/pic.gif");
    PixelReader pixelReader = picture.getPixelReader();
    PixelWriter pixelWriter = picture.getPixelWriter();

    private boolean incompleteScan = true;
    private double distribution;
    private Direction move = new Direction();
    private ColorLink color;
    private int MASK  = 0xFF0F0F0F;
    Stack<Position> edge = new Stack<>();

    int surfaces;  //how many surfaces on the object?  Consider a line has one, a square has four etc...

    Object nextObject;

    /* The surface has an 'object' color pixel but so does the filled portion of the object.
     *  --> Therefore a surface pixel must be defined as a 'object' color pixel adjacent to
     *  a 'non-object pixel. It's only a surface pixel if and only if it is next to an out of
     *  color pixel.
     *
     *  */

    private int ceiling(int x, int y) {
        if (x > y) return x;
        else return y;
    }

    private int floor(int x, int y) {
        if (x < y) return x;
        else return y;
    }

    public Object(ColorLink color, Position position)
    {


        this.color = color;
        move.quad = 1;
        move.column = position.column;
        move.row = position.row;

        Direction beginning = new Direction(move);

        /* The rectangle made here would copy our object from the picture
         * */
        leftMost = new Direction(move);
        rightMost = new Direction(move);
        topMost = new Direction(move);
        btmMost = new Direction(move);
        temp = new Direction();


        edge.push(new Direction(move)); //store starting pixel
        if(move.zeros()) {
            if (pixelReader.getColor(move.column + 1, move.row).equals(color.getColor())){
                move.column++;
                edge.push(new Direction(move));
            }
            else if (pixelReader.getColor(move.column + 1, move.row + 1).equals(color.getColor())) {
                move.column++;
                move.row++;
                edge.push(new Direction(move));
            }
            else {
                move.row++;
                edge.push(new Direction(move));
            }
        }
        int pointer = 0;

        // We assume the previous position was back one column unless this is a left-most position.
        // We are looking for a surface pixel in a clockwise pattern.
//***********************************************************************************************************
        while (incompleteScan) { // There are one of seven possible moves
            temp.makeEqual(move);

            switch (move.quad) {
                case 1:
                    move.column = move.column - 1;
                    move.row = move.row - 1;
                    move.quad = 6;
                    break;
                case 2:
                    move.row = move.row - 1;
                    move.quad = 7;
                    break;
                case 3:
                    move.column = move.column + 1;
                    move.row = move.row - 1;
                    move.quad = 8;
                    break;
                case 4:
                    move.column = move.column + 1;
                    move.quad = 1;
                   break;
                case 5:
                    move.column = move.column + 1;
                    move.row = move.row + 1;
                    move.quad = 2;
                    break;
                case 6:
                    move.row = move.row + 1;
                    move.quad = 3;
                    break;
                case 7:
                    move.column = move.column - 1;
                    move.row = move.row + 1;
                    move.quad = 4;
                    break;
                default:
                    move.column = move.column - 1;
                    move.quad = 5;
                    break;
            }
            if (!checkMove(move)) {
                move.makeEqual(temp);
                if(++move.quad > 8) move.quad = 1;
                }
//***********************************************************************************************************
// Is it the one of the outer four walls?
            if (move.column < leftMost.column) {
                leftMost.column = move.column;
                leftMost.row = move.row;
                leftMost.quad = move.quad;
            }
            if (move.column > rightMost.column) {
                rightMost.column = move.column;
                rightMost.row = move.row;
                rightMost.quad = move.quad;
            }
            if (move.row < topMost.row) {
                topMost.column = move.column;
                topMost.row = move.row;
                topMost.quad = move.quad;
            }
            if (move.row > btmMost.row) {
                btmMost.column = move.column;
                btmMost.row = move.row;
                btmMost.quad = move.quad;
            }
            if (move.equal(beginning)) incompleteScan = false;
//***********************************************************************************************************
        }
        distribution = (double)(btmMost.row - topMost.row) *
                (rightMost.column - leftMost.column) /
                (picture.getArea());
    }

    boolean checkMove(Direction move) {
        if (move.overEdge(picture.getwidth(),picture.getHeight())) return false;
        if (pixelReader.getColor(move.column, move.row).equals(color.getColor())) {
            edge.push(new Direction(move));
            pixelWriter.setColor(move.column,move.row, Color.WHITE);
            return true;
        }
        return false;
    }
    int x() {
        return leftMost.column;
    }
    int y() {
        return topMost.row;
    }
    int width() {
        return rightMost.column - leftMost.column;
    }
    int height() {
        return btmMost.row - topMost.row;
    }
    double getDistribution() {
        return distribution;
    }
}
