package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import tile.TileManager;

// This class inherits the JPanel class
public class GamePanel extends JPanel implements Runnable {

    // SCREEN SETTINGS ----------

    // 16x16 tile, commonly used size in 2D games
    final int originalTileSize = 16;
    // Since the modern screen is large, our character will look tiny without a scale
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; // 48x48 tile
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // 768 total pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 total pixels

    // FPS limit
    int FPS = 60;

    // Instantiate the tile manager
    TileManager tileM = new TileManager(this);

    // Instantiate the keyHandler we have created
    KeyHandler keyH = new KeyHandler();

    // A thread of execution in our program. Multiple threads can run concurrently
    Thread gameThread;
    Player player = new Player(this, keyH);

    public GamePanel() {
        
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        // This can improve our game's rendering performance
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        // Allows us to "focus" the game panel to receive key input
        this.setFocusable(true);
    }

    public void startGameThread() {
        // NOTE: "this" is the JPanel in this case
        gameThread = new Thread(this);
        // Run our program
        gameThread.start();
    }

    // Notes from online:
    //  When an object implementing interface "Runnable" is used to create a new thread,
    //  starting the thread causeds the object's "run" method to be called in that separately executing thread.
    // Two popular styles of game loops:
    //  Sleep Method: After updating and repainting, put the thread to sleep for the remaining 
    //      time in the loop. The game loop won't do anything until the sleep time is over.
    //   Delta Method: As implemented
    @Override
    public void run() {

        // 1/60 = about 0.016 seconds, since 0.016 * 60 = 1x10^9 = 1 nanosecond
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime(); // Current system time
        long currentTime;
        long timer = 0;
        // int drawCount = 0; // For our FPS counter
        
        while (gameThread != null) {
            
            currentTime = System.nanoTime();

            // Difference between the end of the most recent interval and the current time
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            // Once we reach the end of our current interval:
            if (delta >= 1) {
                // STEP 1: Update information, such as character position
                update();
                // STEP 2: Draw the screen with updated information
                // Note: repaint() is how we call the function paintComponent()
                repaint();
                delta--; // Set delta back to zero
                // drawCount++;
            }

            // Print current FPS
            if (timer >= 1000000000) {
                // System.out.println("FPS: " + drawCount); // Testing: FPS Counter, also update the drawCount
                // drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update() {
        player.update();
    }

    // "Graphics" has many functions that we can use to draw to the screen
    public void paintComponent(Graphics g) {
        // Note: "super" is the parent class of this class, so JPanel
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g; // Typecasting, Graphics2D has some useful functions
        // Call the draw tile function
        // Must come before drawing the player, since the player is on the "top"
        tileM.draw(g2);
        player.draw(g2);

        g2.dispose(); // Save some memory
    }

    
}
