package object;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.GamePanel;

public class SuperObject {

    public BufferedImage image;
    public String name;
    public boolean collision = false;
    public int worldX, worldY;

    public void draw(Graphics2D g2, GamePanel gp) {

            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            // Draw items at the specified coordinates, which we have calculated above
            // However, we do not want to draw tiles that are not visible on the screen,
            //  as this could slow down the performance of our game
            if ( worldX > (gp.player.worldX - gp.player.screenX - gp.tileSize) &&
                    worldX < (gp.player.worldX + gp.player.screenX + gp.tileSize) &&
                    worldY > (gp.player.worldY - gp.player.screenY - gp.tileSize) &&
                    worldY < (gp.player.worldY + gp.player.screenY + gp.tileSize) ) {

                g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

        } 
    }
}
