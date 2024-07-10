package javabreakout;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.BreakIterator;

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

        if(code == KeyEvent.VK_A)
            leftPressed = true;

        if(code == KeyEvent.VK_D)
            rightPressed = true;

        if(panel.getBallIsDead()) {
            if(code == KeyEvent.VK_ENTER)
                panel.releaseBall();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_A)
            leftPressed = false;
        if(code == KeyEvent.VK_D)
            rightPressed = false;
    }

   public boolean isLeftPressed() {
        return leftPressed;
   }

   public boolean isRightPressed() {
        return rightPressed;
   }
}
