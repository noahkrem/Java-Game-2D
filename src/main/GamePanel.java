package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Entity;
import entity.Player;
import object.SuperObject;
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

    // WORLD MAP PARAMETERS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    // FPS limit
    int FPS = 60;

    // SYSTEM
    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler(this);
    Sound music = new Sound();
    Sound se = new Sound();
    public CollisionChecker collisionC = new CollisionChecker(this);
    public AssetSetter assetS = new AssetSetter(this);
    public UI ui = new UI(this);
    Thread gameThread;

    // ENTITY AND OBJECT
    public Player player = new Player(this, keyH);
    public SuperObject obj[] = new SuperObject[10];
    public Entity npc[] = new Entity[10];

    // GAME STATE
    public int gameState;
    public final int playState = 1;
    public final int pauseState = 2;

    public GamePanel() {
        
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        // This can improve our game's rendering performance
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        // Allows us to "focus" the game panel to receive key input
        this.setFocusable(true);
    }

    // Call the game setup before starting the game
    public void setupGame() {

        assetS.setObject();
        assetS.setNPC();
        playMusic(3);
        gameState = playState;

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
        if (gameState == playState) {
            player.update();
            
            // NPC
            for (int i = 0; i < npc.length; i++) {
                if (npc[i] != null) {
                    npc[i].update();
                }
            }
        }
        if (gameState == pauseState) {
            // ADD CODE HERE
        }
    }

    // "Graphics" has many functions that we can use to draw to the screen
    public void paintComponent(Graphics g) {


        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g; // Typecasting, Graphics2D has some useful functions
        // Call the draw tile function
        // Must come before drawing the player, since the player is on the "top"

        // DEBUG
        long drawStart = 0;
        if (keyH.checkDrawTime == true) {
            drawStart = System.nanoTime();
        }

        // TILES
        tileM.draw(g2);

        // OBJECT
        // Items must be drawn after tiles
        for (int i = 0; i < obj.length; i++) {
            if (obj[i] != null) {
                obj[i].draw(g2, this);
            }
        }

        // NPC
        for (int i = 0; i < npc.length; i++) {
            if (npc[i] != null) {
                npc[i].draw(g2);
            }
        }

        // PLAYER (Must come last)
        player.draw(g2);

        // UI
        ui.draw(g2);

        // DEBUG
        if (keyH.checkDrawTime == true) {
            long drawEnd = System.nanoTime();
            long timePassed = drawEnd - drawStart;
            System.out.println("Draw time: " + timePassed);
        }
        

        g2.dispose(); // Save some memory
    }

    // We want to play music when an event happens (such as a chase)
    // The index i is the value of the array index which stores the event music
    public void playMusic(int i) {
        
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic() {
        music.stop();
    }

    public void playSE(int i) {
        
        se.setFile(i);
        se.play();
    }
}
