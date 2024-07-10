package javabreakout;

public class Paddle {
    private int paddleX;
    private int paddleY;
    private int paddleHeight;
    private int paddleWidth;
    private int paddleSpeed;

    public Paddle(int paddleX, int paddleY, int paddleWidth,
                  int paddleHeight, int paddleSpeed) {

        this.paddleX = paddleX;
        this.paddleY = paddleY;
        this.paddleWidth = paddleWidth;
        this.paddleHeight = paddleHeight;
        this.paddleSpeed = paddleSpeed;
    }

    public void moveLeft() {
        paddleX -= paddleSpeed;
    }

    public void moveRight() {
        paddleX += paddleSpeed;
    }

    public int getPaddleX() {
        return paddleX;
    }

    public int getPaddleY() {
        return paddleY;
    }

    public int getPaddleHeight() {
        return paddleHeight;
    }

    public int getPaddleWidth() {
        return paddleWidth;
    }

    public int getPaddleSpeed() {
        return paddleSpeed;
    }
}
