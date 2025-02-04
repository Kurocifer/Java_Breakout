package javabreakout;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BreakoutPanel extends JPanel implements Runnable {
    private final int FPS = 40; /// Frames per Second
    private final int BRICK_WIDTH = 64;
    private final int BRICK_HEIGHT = 32;
    private final int BRICK_BUFFER = 128;
    private final int BALL_SIZE = 11;

    private boolean ballIsDead;
    private boolean ballIsPlayable;
    private int panelHeight;
    private int panelWidth;
    private int score = 0;
    private int ballsRemaining = 3;
    private boolean stop = false;

    private Font arial40 = new Font("Arial", Font.PLAIN, 40);
    private List<Brick> bricks;
    private List<Color> colorList;

    private Ball ball;
    private Paddle paddle;
    private KeyHandler keyHandler;
    private Thread panelThread;

    public BreakoutPanel() {
        this(1024, 1024);
    }

    public BreakoutPanel(int width, int height) {
        panelWidth = width;
        panelHeight = height;

        this.setPreferredSize(new Dimension(panelWidth, panelHeight));
        this.setBackground(Color.black);
        this.setFocusable(true);

        keyHandler = new KeyHandler(this);
        this.addKeyListener(keyHandler);

        bricks = generateBricks();
        paddle = new Paddle((panelWidth / 2) - 64,
                panelHeight - 200, 128, 16, 16);
        ballIsDead = true;
        ballIsPlayable = false;
        panelThread = Thread.ofVirtual()
                .name("Breakout")
                .unstarted(this);
    }

    private void generateColors() {
        colorList = new ArrayList<>();

        colorList.add(Color.RED);
        colorList.add(Color.MAGENTA);
        colorList.add(Color.PINK);
        colorList.add(Color.GRAY);
        colorList.add(Color.YELLOW);
        colorList.add(Color.CYAN);
        colorList.add(Color.GREEN);
        colorList.add(Color.BLUE);
    }

    private List<Brick> generateBricks() {
        generateColors();

        List<Brick> returnedBricks = new ArrayList<>();
        int brickRow = 0;
        int brickCol = 0;

        while(brickCol < 16) {
            while(brickRow < 8) {
                Brick newBrick = new Brick(brickCol * BRICK_WIDTH, (brickRow * BRICK_HEIGHT) + BRICK_BUFFER,
                        brickCol + BRICK_WIDTH, brickRow + BRICK_HEIGHT, colorList.get(brickRow));

                returnedBricks.add(newBrick);
                brickRow++;
            }
            brickRow = 0;
            brickCol++;
        }
        return returnedBricks;
    }

    @Override
    public void run() {
        while(panelThread.isAlive()) {
            if(!stop) {
                update();
                repaint();

                try {
                    Thread.sleep(1000 / FPS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    ;
                }
            }
        }
    }

    private void update() {
        // Update paddle
        if (keyHandler.isLeftPressed() || keyHandler.isRightPressed()) {
            if (keyHandler.isLeftPressed()) {
                if (paddle.getPaddleX() - paddle.getPaddleSpeed() > 0)
                    paddle.moveLeft();
            } else if (paddle.getPaddleX() + paddle.getPaddleSpeed() < 900) { // This value so the paddle
                paddle.moveRight();
            }

        }

        // Update ball
        if (!ballIsDead) {
            checkCollision();

            if (ballIsPlayable) {
                ball.update();
            }
        }

        if (ballsRemaining <= 0) {
            newRound("You ran out of balls. Do you want to restart ?");
        }
    }
    private void checkCollision() {
        int ballX = ball.getBallX();
        int ballY = ball.getBallY();
        int paddleX = paddle.getPaddleX();
        int paddleY = paddle.getPaddleY();
        int paddleWidth = paddle.getPaddleWidth();
        int paddleHeight = paddle.getPaddleHeight();

        if(ballY > panelHeight) {
            ballIsDead = true;
            ballIsPlayable = false;
            ball = null;
            ballsRemaining--;
        } else if(ballY >= paddleY && !ball.isMovingUp()) {
            if (ballY < paddleY + paddleHeight &&
                    ballX >= paddleX &&
                    ballX <= paddleX + paddleWidth) {
                ball.setMovingUp(true);
                // Check for ball angle adjustment
                if(keyHandler.isLeftPressed()) {
                    if (ball.isMovingLeft()) {
                        ball.increaseAngle();
                    } else {
                        ball.decreaseAngle();
                    }
                } else if(keyHandler.isRightPressed()) {
                    if(ball.isMovingLeft()) {
                        ball.decreaseAngle();
                    } else {
                        ball.increaseAngle();
                    }
                }
            }
        } else if(ballX <= 0 && ball.isMovingLeft()) {
            ball.setMovingLeft(false);
        } else if(ballX >= panelHeight && !ball.isMovingLeft()) {
            ball.setMovingLeft(true);
        } else if (ballY <= (BRICK_HEIGHT * 8) + BRICK_BUFFER + BRICK_HEIGHT // A brick has been hit
                && ballY > BRICK_BUFFER) {

            // Handle ball collision with brick
            for (Brick brick : bricks) {
                if (!brick.isBroken()) {
                    int brickX = brick.getBrickX();
                    int brickY = brick.getBrickY();
                    int brickMaxX = brick.getBrickMaxX();
                    int brickMaxY = brick.getBrickMaxY();

                    if (ballX >= brickX && ballX <= brickMaxX
                            && ballY >= brickY && ballY <= brickMaxY) {
                        brick.setBroken(true);
                        brick.setColor(Color.BLACK);
                        score++;

                        // change (flip) the ball's direction after it hits a brick
                        ball.flipVerticalDirection();
                        break;
                    }
                }
            }
        } else if(ball.getBallY() <= 1) {
            ball.setMovingUp(false);
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Draw bricks
        for (Brick brick : bricks) {
            int brickX = brick.getBrickX();
            int brickY = brick.getBrickY();
            g2.setColor(brick.getColor());
            g2.fillRect(brickX, brickY, BRICK_WIDTH,
                    BRICK_HEIGHT);
        }

        // Draw paddle
        g2.setColor(Color.WHITE);
        g2.fillRect(paddle.getPaddleX(), paddle.getPaddleY(),
                paddle.getPaddleWidth(), paddle.getPaddleHeight());

        // Draw ball
        if(ball != null) {
            g2.setColor(new Color(192, 192, 192)); // Silver

            int centerOffset = ball.getBallSizeOffset();
            g2.fillRect(ball.getBallX() - centerOffset,
                    ball.getBallY() - centerOffset,
                    ball.getBallSize(), ball.getBallSize());
        }

        // Show Score and Balls remaining
        StringBuilder scoreBuilder = new StringBuilder("Score: ");
        scoreBuilder.append(score);

        StringBuilder currentBallBuilder = new StringBuilder("Current Ball: ");
        currentBallBuilder.append(ballsRemaining);

        g2.setColor(Color.white);
        g2.setFont(arial40);
        g2.drawString(scoreBuilder.toString(), 50, 50);
        g2.drawString(currentBallBuilder.toString(), 700, 50);

        g2.dispose();
    }

    public void start() {
        stop = false;
        panelThread.start();
    }

    public void releaseBall() {
        if(ballsRemaining > 0) {
            ball = new Ball(BALL_SIZE, panelWidth, BRICK_HEIGHT * 8, BRICK_BUFFER);

            ballIsDead = false;
            ballIsPlayable = true;
        }
    }

    public boolean getBallIsDead() {
        return this.ballIsDead;
    }

    private void newRound(String alertMessage) {
        stop = true;
        int rePlay = JOptionPane.showConfirmDialog(
                null, alertMessage, "Confirmation", JOptionPane.YES_NO_OPTION);
        if (rePlay == JOptionPane.YES_OPTION) {
            ballsRemaining = 3;
            stop = false;
        }
    }
}
