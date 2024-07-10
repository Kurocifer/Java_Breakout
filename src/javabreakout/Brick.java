package javabreakout;

import java.awt.*;

public class Brick {
    private int brickX;
    private int brickY;
    private int brickMaxX;
    private int brickMaxY;

    private Color color;
    private boolean broken;
// break brick!

    public Brick(int brickX, int brickY, int width,
                 int height, Color color) {

        this.color = color;
        this.brickX = brickX;
        this.brickY = brickY;
        this.brickMaxX = brickX + width;
        this.brickMaxY = brickY + width;
        this.broken = false;
    }

    public int getBrickX() {
        return brickX;
    }

    public int getBrickY() {
        return brickY;
    }

    public int getBrickMaxX() {
        return brickMaxX;
    }

    public int getBrickMaxY() {
        return brickMaxY;
    }

    public Color getColor() {
        return color;
    }

    public boolean isBroken() {
        return broken;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setBroken(boolean broken) {
        this.broken = broken;
    }
}
