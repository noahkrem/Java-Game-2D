package object;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GamePanel;
import main.UtilityTools;

public class SuperObject {

    public BufferedImage image, image2;
    public String name;
    public boolean collision = false;
    public int worldX, worldY;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;
    UtilityTools uTool = new UtilityTools();

    public void draw(Graphics2D g2, GamePanel gp) {

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        // Draw items at the specified coordinates, which we have calculated above
        // However, we do not want to draw tiles that are not visible on the screen,
        //  as this could slow down the performance of our game
        // If player is not holding a flashlight, they can only see the area directly around them
        if (gp.player.hasTorch == false) {
            double leftVisibleRange = gp.player.worldX - gp.player.screenX + 5.95 * gp.tileSize;
            double rightVisibleRange = gp.player.worldX + gp.player.screenX - 5.95 * gp.tileSize;
            double topVisibleRange = gp.player.worldY - gp.player.screenY + 3.95 * gp.tileSize;
            double bottomVisibleRange = gp.player.worldY + gp.player.screenY - 3.95 * gp.tileSize;

            // If tiles are just outside visible range, draw with 0.5 opaque value
            if ( ( worldX > (leftVisibleRange - gp.tileSize) && worldX < (rightVisibleRange + gp.tileSize) && 
                    worldY > topVisibleRange && worldY < bottomVisibleRange ) ||
                    ( worldX > leftVisibleRange && worldX < rightVisibleRange && 
                    worldY > (topVisibleRange - gp.tileSize) && worldY < (bottomVisibleRange + gp.tileSize) ) ) {
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
                g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
                // Must reset the composite to its original value, or else items will be drawn less opaque
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
            }
            // If tiles are inside visible range, draw normally
            if ( worldX > leftVisibleRange && worldX < rightVisibleRange && worldY > topVisibleRange && worldY < bottomVisibleRange ) {
        
                g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            }
        } 
        // If the player is holding a torch
        else {
            double leftVisibleRange = gp.player.worldX - gp.player.screenX + 1.95 * gp.tileSize;
            double rightVisibleRange = gp.player.worldX + gp.player.screenX - 1.95 * gp.tileSize;
            double topVisibleRange = gp.player.worldY - gp.player.screenY + 0.95 * gp.tileSize;
            double bottomVisibleRange = gp.player.worldY + gp.player.screenY - 0.95 * gp.tileSize;

            // If tiles are just outside of visible range, draw with 0.5 opaque value
            if ( ( ( worldX > (leftVisibleRange - gp.tileSize) && worldX < (rightVisibleRange + gp.tileSize) && 
                        worldY > topVisibleRange && worldY < bottomVisibleRange ) ||
                        ( worldX > leftVisibleRange && worldX < rightVisibleRange && 
                        worldY > (topVisibleRange - gp.tileSize) && worldY < (bottomVisibleRange + gp.tileSize) ) ) ) {

                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
                g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
                // Must reset the composite to its original value, or else items will be drawn less opaque
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
            }

            if ( worldX > leftVisibleRange && worldX < rightVisibleRange && worldY > topVisibleRange && worldY < bottomVisibleRange ) {

                g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            } 
        }
    } 
}
