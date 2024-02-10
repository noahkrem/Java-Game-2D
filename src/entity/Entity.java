package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTools;

// Contains characteristics that all entities will have
public class Entity {
    
    GamePanel gp;

    // Difference between world coordinates and screen coordinates
    public int worldX, worldY;
    public int speed;
    // Used for shift to sprint, also other entities' sprint
    public boolean sprint = false;

    // Describes an image with an accessible buffer of image data
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction = "down";

    // Used for the walking animation
    public int spriteCounter = 0;
    public int spriteNum = 1;

    // Used for collision detection
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;

    // Helps the npcs stay in the same direction
    public int actionCounterLock = 0;

    // For npc and character dialogues
    String dialogue[] = new String[20];
    int dialogueIndex = 0;

    // FROM OLD SUPEROBJECT CLASS
    public BufferedImage image, image2;
    public String name;
    public boolean collision = false;

    // CHARACTER STATUS
    public int maxLife;
    public int life;


    public Entity(GamePanel gp) {

        this.gp = gp;
    }

    // If the subclass also has a setAction method, that method will take priority
    public void setAction() {

    }

    public void speak() {

        if (dialogue[dialogueIndex] == null) {
            dialogueIndex = 0;
        }

        gp.ui.currentDialogue = dialogue[dialogueIndex];
        dialogueIndex++;

        switch (gp.player.direction) {
        case "up" :
            direction = "down";
            break;
        case "down" :
            direction = "up";
            break;
        case "right" :
            direction = "left";
            break;
        case "left" :
            direction = "right";
            break;
        }
    }
    
    public void update() {

        setAction();

        collisionOn = false;
        gp.collisionC.checkTile(this);
        gp.collisionC.checkObject(this, false);
        gp.collisionC.checkPlayer(this);

        // If no collision, entity can move
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

    public void draw(Graphics2D g2) {

        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if ( worldX > gp.player.worldX - gp.player.screenX - gp.tileSize && 
                worldX < gp.player.worldX + gp.player.screenX + gp.tileSize && 
                worldY > gp.player.worldY - gp.player.screenY - gp.tileSize &&
                worldY < gp.player.worldY + gp.player.screenY + gp.tileSize ) {

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

    public BufferedImage setup(String imagePath) {
        
        UtilityTools uTool = new UtilityTools();
        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }
}
