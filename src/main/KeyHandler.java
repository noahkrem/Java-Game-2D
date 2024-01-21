package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public boolean upPressed, downPressed, leftPressed, rightPressed, shiftPressed;

    // DEBUG
    public boolean checkDrawTime = false;

    // Must add the below three overridden elements whenever implementing KeyListener

    // We will not use keyTyped in this project
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
       
        // The code of the key pressed, ASCII
        int code  = e.getKeyCode();

        // MOVEMENT
        if (code == KeyEvent.VK_W) {
            upPressed = true;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = true;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = true;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = true;
        }
        if (code == KeyEvent.VK_SHIFT) {
            shiftPressed = true;
        }

        // DEBUG
        if (code == KeyEvent.VK_P) {
            if (checkDrawTime == false)
                checkDrawTime = true;
            else 
                checkDrawTime = false;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
           
        // The code of the key released, ASCII
        int code  = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }
        if (code == KeyEvent.VK_SHIFT) {
            shiftPressed = false;
        }
    }
}
