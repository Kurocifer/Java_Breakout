package javabreakout;

import java.util.Random;

public class Ball {
    private final int maxSpeed = 8;
    private final int oneThird = maxSpeed / 3;

    private boolean movingUp;
    private boolean movingLeft;
    private int ballSizeOffset;
    private int ballX;
    private int ballY;
    private int ballSize;
    private int hSpeed;
    private int vSpeed;

    public Ball(int size, int panelWidth,
                int brickHeightX8, int brickBuffer) {

        ballSize = size;
        ballSizeOffset = (size / 2) + 1;

        ballX = panelWidth / 2;
        ballY = brickHeightX8 + brickBuffer + 10;

        // Starting speed and set angle (45 degrees)
        hSpeed = maxSpeed;
        vSpeed = maxSpeed;

        // Starting direction
        Random lefRightDirection = new Random();
        movingLeft = lefRightDirection.nextBoolean();
        movingUp = false;
    }

    public void update() {
        ballX = movingLeft ? (ballX - hSpeed) : (ballX + hSpeed);
        ballY = movingUp ? (ballY - hSpeed) : (ballX + hSpeed);
    }

    public void increaseAngle() {
        if(vSpeed - oneThird > 1) {
            hSpeed += oneThird;
            vSpeed -= oneThird;
        }
    }

    public void decreaseAngle() {
        if(vSpeed - oneThird > 1) {
            hSpeed -= oneThird;
            vSpeed += oneThird;
        }
    }

    public void flipVerticalDirection() {
        movingUp = !movingUp;
    }

    public boolean isMovingUp() {
        return movingUp;
    }

    public void setMovingUp(boolean movingUp) {
        this.movingUp = movingUp;
    }

    public boolean isMovingLeft() {
        return movingLeft;
    }

    public void setMovingLeft(boolean movingLeft) {
        this.movingLeft = movingLeft;
    }

    public int getBallSizeOffset() {
        return ballSizeOffset;
    }

    public int getBallX() {
        return ballX;
    }

    public void setBallX(int ballX) {
        this.ballX = ballX;
    }

    public int getBallY() {
        return ballY;
    }

    public void setBallY(int ballY) {
        this.ballY = ballY;
    }

    public int getBallSize() {
        return ballSize;
    }
}
