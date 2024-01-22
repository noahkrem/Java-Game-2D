package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;


public class UI {
    
    GamePanel gp;
    Graphics2D g2;
    Font arial_40;
    Font pixel_18;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;
    public String currentDialogue = "";

    public UI(GamePanel gp) {
        this.gp = gp;

        arial_40 = new Font("Arial", Font.PLAIN, 40);
        pixel_18 = new Font("Public Pixel", Font.PLAIN, 18);
    }

    public void showMessage(String text) {

        message = text;
        messageOn = true;
    }

    // Tip for performance: Don't instantiate things inside the game loop
    public void draw(Graphics2D g2) {

        this.g2 = g2;

        g2.setFont(pixel_18);
        g2.setColor(Color.white);
        
        // PLAY STATE
        if (gp.gameState == gp.playState) {
            // CODE HERE
        }
        // PAUSE STATE
        if (gp.gameState == gp.pauseState) {
            drawPauseScreen();
        }
        // PAUSE STATE
        if (gp.gameState == gp.dialogueState) {
            drawDialogueScreen();
        }
    }

    public void drawPauseScreen() {
        
        String text = "PAUSED";
        int x = getXforCenteredText(text);
        int y = gp.screenHeight/2;
    
        g2.drawString(text, x, y);
    }

    public void drawDialogueScreen() {

        int x;
        int y = gp.screenHeight - gp.tileSize;

        for (String line : currentDialogue.split("\n")) {
            x = getXforCenteredText(line);
            g2.drawString(line, x, y);
            y += gp.tileSize/2;
        }
    }

    public int getXforCenteredText(String text) {

        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth/2 - length/2;
        return x;
    }
}
