package entity;

import java.awt.Rectangle;
import java.util.Random;

import main.GamePanel;

public class NPC_OldMan extends Entity {
    
    public NPC_OldMan(GamePanel gp) {

        // Pass the gamepanel to the Entity class
        super(gp);

        direction = "right";
        speed = 1;
        
        solidArea = new Rectangle(5, 10, gp.tileSize - 2*5, gp.tileSize - 10);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        
        getImage();
    }

    public void getImage() {

        right1 = setup("../res/npc/old-man-right-1");
        right2 = setup("../res/npc/old-man-right-2");
        left1 = setup("../res/npc/old-man-left-1");
        left2 = setup("../res/npc/old-man-left-2");

    }

    public void setAction() {

        actionCounterLock++;

        if (actionCounterLock == 120) {

            Random random = new Random();
            // Pick a number from 1-100
            int i = random.nextInt(100) + 1;

            if (i <= 50) {
                direction = "right";
            }
            if (i > 50 && i <= 100) {
                direction = "left";
            }

            actionCounterLock = 0;
        }
    }
}
