package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
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

    // Indicates how many keys the player currently has
    public int hasKey = 0;
    public boolean hasTorch = false;

    public Player(GamePanel gp, KeyHandler keyH) {
        
        this.gp = gp;
        this.keyH = keyH;

        // Player character's position is always on the middle of the screen
        screenX = gp.screenWidth/2 - gp.tileSize/2;
        screenY = gp.screenHeight/2 - gp.tileSize/2;

        solidArea = new Rectangle(14, 18, gp.tileSize - 2*14, gp.tileSize - 18);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {

        // These are the start coordinates, we can change them to whatever we want
        worldX = gp.tileSize * 25;
        worldY = gp.tileSize * 25;
        speed = 4;
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

    // 1. Check input direction
    // 2. Check for collision in that direction
    // 3. If no collision, player can move
    public void update() {

        if (keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true) {
            // Note: Since the upper left corner is (0, 0), must decrease playerY
            //      to move upwards, and so on.
            if (keyH.upPressed == true) {
                direction = "up";
            }
            else if (keyH.downPressed == true) {
                direction = "down";
            }
            else if (keyH.leftPressed == true) {
                direction = "left";
            }
            else if (keyH.rightPressed == true) {
                direction = "right";
            }

            // Check if the character is sprinting or not
            if (keyH.shiftPressed == true) {
                speed = 7;
            }
            // If the shift key is unpressed, we must change back to default speed 
            else {
                speed = 4;
            }

            // check tile collision
            collisionOn = false;
            gp.collisionC.checkTile(this);

            // Check object collision
            int objIndex = gp.collisionC.checkObject(this, true);
            objectInteraction(objIndex);

            // If no collision, player can move
            if (collisionOn == false) {
                switch (direction) {
                case "up" : worldY -= speed; break;
                case "down" : worldY += speed; break;
                case "left" : worldX -= speed; break;
                case "right" : worldX += speed; break;
                }
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

    public void objectInteraction(int i) {

        if (i != 999) {

            String objectName = gp.obj[i].name;

            switch (objectName) {
            case "Key" :
                gp.playSE(0);
                hasKey++;
                gp.obj[i] = null;
                gp.ui.showMessage("Found a key.");
                break;
            case "Door" :
                if (hasKey > 0) {
                    gp.playSE(1);
                    gp.obj[i].collision = false;
                    gp.obj[i] = null; // When the "open door" is implemented, delete this line
                    hasKey--;
                    gp.ui.showMessage("Door unlocked.");
                } else {
                    gp.ui.showMessage("Door is locked.");
                }
                break;
            case "Torch" :
                gp.playSE(2);
                hasTorch = true;
                gp.obj[i] = null;
                break;
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
