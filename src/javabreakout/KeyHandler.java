package javabreakout;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
public class KeyHandler implements KeyListener {
    private BreakoutPanel panel;
    private boolean leftPressed = false;
    private boolean rightPressed = false;

    public KeyHandler(BreakoutPanel breakoutPanel) {
        this.panel = breakoutPanel;
    }



    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_LEFT)
            leftPressed = true;

        if(code == KeyEvent.VK_RIGHT)
            rightPressed = true;

        if(panel.getBallIsDead()) {
            if(code == KeyEvent.VK_ENTER)
                panel.releaseBall();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_LEFT)
            leftPressed = false;
        if(code == KeyEvent.VK_RIGHT)
            rightPressed = false;
    }

   public boolean isLeftPressed() {
        return leftPressed;
   }

   public boolean isRightPressed() {
        return rightPressed;
   }
}
