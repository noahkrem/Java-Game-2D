package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {

    GamePanel gp;
    Tile[] tile;
    // This will store our map information
    int mapTileNum[][];

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

        try {

            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("../res/tiles/forest-grass-tile.png"));
            
            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("../res/tiles/small-tree-tile.png"));

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("../res/tiles/cracked-flagstone.png"));

            tile[3] = new Tile();
            tile[3].image = ImageIO.read(getClass().getResourceAsStream("../res/tiles/stone-on-flagstone.png"));

        } catch (IOException e) {
            e.printStackTrace();
        } 
    }

    // Step 1: Read the input from the text file
    // Step 2: Separate each number separated by a space
    public void loadMap(String path) {

        try {

            InputStream is = getClass().getResourceAsStream(path);
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
            if ( worldX > (gp.player.worldX - gp.player.screenX - gp.tileSize) &&
                    worldX < (gp.player.worldX + gp.player.screenX + gp.tileSize) &&
                    worldY > (gp.player.worldY - gp.player.screenY - gp.tileSize) &&
                    worldY < (gp.player.worldY + gp.player.screenY + gp.tileSize) ) {

                g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);

            } 

            worldCol++;

            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
