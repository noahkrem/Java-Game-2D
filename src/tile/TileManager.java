package tile;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTools;

public class TileManager {

    GamePanel gp;
    public Tile[] tile;
    // This will store our map information
    public int mapTileNum[][];

    // Constructor for our TileManager class
    public TileManager (GamePanel gp) {
        this.gp = gp;
        // The size of the array represents how many tiles we will have
        tile = new Tile[10];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();
        loadMap("../res/maps/map-01.txt");
    }

    public void getTileImage() {

        setup(0, "grass", false);
        setup(1, "small-tree-tile", true);
        setup(2, "cracked-flagstone", false);
        setup(3, "stone-on-flagstone", true);
        setup(4, "grass-accented", false);

    }

    public void setup(int index, String imageName, boolean collision) {

        UtilityTools uTool = new UtilityTools();

        try {
            tile[index] = new Tile();
            String resourcePath = "/res/tiles/" + imageName + ".png";
            tile[index].image = ImageIO.read(getClass().getResourceAsStream(resourcePath));
            tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // Step 1: Read the input from the text file
    // Step 2: Separate each number separated by a space
    public void loadMap(String path) {

        try {

            // Use absolute classpath path so resources load both from src and from inside the JAR
            String resourcePath = path;
            if (!resourcePath.startsWith("/")) {
                // normalize ../res/... to /res/...
                resourcePath = resourcePath.replaceAll("\\.\\./", "/");
            }
            InputStream is = getClass().getResourceAsStream(resourcePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while (col < gp.maxWorldCol && row < gp.maxWorldRow) {

                String line = br.readLine();

                while (col < gp.maxWorldCol) {
                    
                    // Split the array values every time you see a space
                    String numbers[] = line.split(" "); 
                    
                    // Parses a string argument and returns an integer
                    int num = Integer.parseInt(numbers[col]);
                    // This integer is the number we must store
                    mapTileNum[col][row] = num;
                    // Increase Column count
                    col++;
                }

                if (col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }

            br.close();

        } catch (Exception e) {}
    }

    // We want a draw method inside of the tile manager
    public void draw(Graphics2D g2) {

        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

            // Tile image that will be displayed
            int tileNum = mapTileNum[worldCol][worldRow];

            // To determine the x and y values at which we draw tiles
            // NOTE: would like to go further in depth as to why this logic works
            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            // Draw tiles at the specified coordinates, which we have calculated above
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
                    g2.drawImage(tile[tileNum].image, screenX, screenY, null);
                    // Must reset the composite to its original value, or else items will be drawn less opaque
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
                }
                // If tiles are inside visible range, draw normally
                if ( worldX > leftVisibleRange && worldX < rightVisibleRange && worldY > topVisibleRange && worldY < bottomVisibleRange ) {
            
                    g2.drawImage(tile[tileNum].image, screenX, screenY, null);
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
                    g2.drawImage(tile[tileNum].image, screenX, screenY, null);
                    // Must reset the composite to its original value, or else items will be drawn less opaque
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
                }

                if ( worldX > leftVisibleRange && worldX < rightVisibleRange && worldY > topVisibleRange && worldY < bottomVisibleRange ) {

                    g2.drawImage(tile[tileNum].image, screenX, screenY, null);
                } 
            }

            worldCol++;

            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
