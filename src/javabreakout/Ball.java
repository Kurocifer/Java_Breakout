package javabreakout;

import java.util.Random;

public class Ball {
    private final int MAX_SPEED = 8;
    private final int ONE_THIRD_OF_MAX_SPEED = MAX_SPEED / 3;

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
        ballY = brickHeightX8 + brickBuffer + 60;

        // Starting speed and set angle (45 degrees)
        hSpeed = MAX_SPEED;
        vSpeed = MAX_SPEED;

        // Starting direction512
        Random lefRightDirection = new Random();
        movingLeft = lefRightDirection.nextBoolean();
        movingUp = false;
    }

    public void update() {
        ballX = movingLeft ? (ballX - hSpeed) : (ballX + hSpeed);
        ballY = movingUp ? (ballY - hSpeed) : (ballY + vSpeed);
    }

    public void increaseAngle() {
        if(vSpeed - ONE_THIRD_OF_MAX_SPEED > 1) {
            hSpeed += ONE_THIRD_OF_MAX_SPEED;
            vSpeed -= ONE_THIRD_OF_MAX_SPEED;
        }
    }

    public void decreaseAngle() {
        if(vSpeed - ONE_THIRD_OF_MAX_SPEED > 1) {
            hSpeed -= ONE_THIRD_OF_MAX_SPEED;
            vSpeed += ONE_THIRD_OF_MAX_SPEED;
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

    public int getBallSize() {
        return ballSize;
    }

}
