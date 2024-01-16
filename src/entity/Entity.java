package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

// Contains characteristics that all entities will have
public class Entity {
    
    // Difference between world coordinates and screen coordinates
    public int worldX, worldY;
    public int speed;

    // Describes an image with an accessible buffer of image data
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction;

    // Used for the walking animation
    public int spriteCounter = 0;
    public int spriteNum = 1;

    // Used for collision detection
    public Rectangle solidArea;
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;

}
