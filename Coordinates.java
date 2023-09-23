package org.cis1200.brickBreaker;

public class Coordinates {
    private int xCoord;
    private int yCoord;

    public Coordinates(int x, int y) {
        xCoord = x;
        yCoord = y;
    }

    @Override
    public String toString() {
            return "Brick(" + xCoord + "," + yCoord + ")";
        }

        //getters
    public int getX() {
            return xCoord;
    }

    public int getY() {
        return yCoord;
    }
}
