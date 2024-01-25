package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;


public class UI {
    
    GamePanel gp;
    Graphics2D g2;
    Font publicPixel;
    Font publicPixel_18;
    Font publicPixel_32;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;
    public String currentDialogue = "";

    // TITLE SCREEN
    BufferedImage titleScreen;
    public int commandNum = 0;
    public int titleScreenState = 0;
    int screenLock = 0;

    public UI(GamePanel gp) {
        this.gp = gp;
        
        try {
            InputStream is = getClass().getResourceAsStream("../res/font/PublicPixel.ttf");
            publicPixel = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        publicPixel_18 = publicPixel.deriveFont(18F);
        publicPixel_32 = publicPixel.deriveFont(32F);

        try {
            titleScreen = ImageIO.read(getClass().getResourceAsStream("../res/title/Bird's_Eye.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void showMessage(String text) {

        message = text;
        messageOn = true;
    }

    // Tip for performance: Don't instantiate things inside the game loop
    public void draw(Graphics2D g2) {

        this.g2 = g2;

        g2.setFont(publicPixel_18);
        g2.setColor(Color.white);
        
        // TITLE STATE
        if (gp.gameState == gp.titleState) {
            drawTitleScreen();
        }
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

    public void drawTitleScreen() {

        Font currentFont; // Change this later

        if (titleScreenState == 0) {

            // TITLE PICTURE
            g2.drawImage(titleScreen, 0, 0, 900, 600, null);

            // TITLE NAME
            g2.setFont(publicPixel_32);
            String text = "Bird's Eye";
            int x = getXforCenteredText(text);
            int y = gp.tileSize*1;

            // TITLE SHADOW
            g2.setColor(Color.gray);
            g2.drawString(text, x+3, y+3);

            // TITLE
            g2.setColor(Color.white);
            g2.drawString(text, x, y);

            // MENU
            currentFont = publicPixel.deriveFont(24F);
            g2.setFont(currentFont);

            text = "NEW GAME";
            x = getXforCenteredText(text);
            y += gp.tileSize*8;
            g2.drawString(text, x, y);
            if (commandNum == 0) {
                g2.drawString(">", x - gp.tileSize, y - gp.tileSize/16); // A cursor
            }

            // NOTE: Have not implemented save and load yet
            text = "LOAD GAME";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 1) {
                g2.drawString(">", x - gp.tileSize, y - gp.tileSize/16); // A cursor
            }

            text = "QUIT GAME";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 2) {
                g2.drawString(">", x - gp.tileSize, y - gp.tileSize/16); // A cursor
            }
        }
        else if (titleScreenState == 1) {
            
            // Anything can go here, currently just some credits
            // Could do a cinematic, a class selection, a story overview, etc.
            g2.setFont(publicPixel_18);
            String text = "A game made by Nonak";
            int x = getXforCenteredText(text);
            int y = gp.screenHeight/2;
            g2.drawString(text, x, y);
        }

    }

    public void drawPauseScreen() {
        
        String text = "PAUSED";
        int x = getXforCenteredText(text);
        int y = gp.screenHeight/2;
    
        g2.drawString(text, x, y);

        // IMPLEMENT A "FADE IN", "FADE OUT" FOR THE TEXT HERE
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
