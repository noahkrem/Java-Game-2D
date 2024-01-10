package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

// Contains characteristics specific to the player
public class Player extends Entity {

    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        // Player character's position is always on the middle of the screen
        screenX = gp.screenWidth/2;
        screenY = gp.screenHeight/2;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {

        // These are the start coordinates, we can change them to whatever we want
        worldX = gp.tileSize * 25;
        worldY = gp.tileSize * 25;
        speed = 5;
        direction = "down";
    }

    public void getPlayerImage() {

        try {

            up1 = ImageIO.read(getClass().getResourceAsStream("../res/player/knight-sprite-back-1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("../res/player/knight-sprite-back-2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("../res/player/knight-sprite-front-1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("../res/player/knight-sprite-front-2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("../res/player/knight-sprite-left-1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("../res/player/knight-sprite-left-2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("../res/player/knight-sprite-right-1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("../res/player/knight-sprite-right-2.png")); 

        } catch (IOException e) {
            e.printStackTrace();
        } 
    }

    public void update() {

        if (keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true) {
            // Note: Since the upper left corner is (0, 0), must decrease playerY
            //      to move upwards, and so on.
            if (keyH.upPressed == true) {
                direction = "up";
                worldY -= speed;
            }
            else if (keyH.downPressed == true) {
                direction = "down";
                worldY += speed;
            }
            else if (keyH.leftPressed == true) {
                direction = "left";
                worldX -= speed;
            }
            else if (keyH.rightPressed == true) {
                direction = "right";
                worldX += speed;
            }

            spriteCounter++;

            // 12 is arbitrary, use whatever looks best on the screen
            // Change the image after "X" number of frames
            if (spriteCounter > 12) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                }
                else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                // Reset sprite counter
                spriteCounter = 0;
            }
        }
    }

    public void draw(Graphics2D g2) {
        
        BufferedImage image = null;

        // Based on the direction, we pick a different image
        switch (direction) {
        case "up":
            if (spriteNum == 1) {
                image = up1;
            }
            if (spriteNum == 2) {
                image = up2;
            }
            break;
        case "down":
            if (spriteNum == 1) {
                image = down1;
            }
            if (spriteNum == 2) {
                image = down2;
            }
            break;    
        case "left":
            if (spriteNum == 1) {
                image = left1;
            }
            if (spriteNum == 2) {
                image = left2;
            }
            break;
        case "right":
            if (spriteNum == 1) {
                image = right1;
            }
            if (spriteNum == 2) {
                image = right2;
            }
            break;
        }

        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }
}