package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;

public class MON_Crow extends Entity {
    
    public MON_Crow(GamePanel gp) {
        super(gp);

        name = "Crow";
        speed = 3;
        maxLife = 1; 
        life = maxLife;

        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = gp.tileSize - solidArea.x*2;
        solidArea.height = gp.tileSize - solidArea.y;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
    }

    public void getImage() {

        up1 = setup("../res/monster/crow-flying-left-1");
        up2 = setup("../res/monster/crow-flying-left-2");
        down1 = setup("../res/monster/crow-flying-right-1");
        down2 = setup("../res/monster/crow-flying-right-2");    
        left1 = setup("../res/monster/crow-flying-left-1");
        left2 = setup("../res/monster/crow-flying-left-2");
        right1 = setup("../res/monster/crow-flying-right-1");
        right2 = setup("../res/monster/crow-flying-right-2");  
    }

    public void setAction() {

        actionCounterLock++;

        if (actionCounterLock == 120) {

            Random random = new Random();
            // Pick a number from 1-100
            int i = random.nextInt(100) + 1;

            if (i <= 25) {
                direction = "up";
            }
            if (i > 25 && i <= 50) {
                direction = "down";
            }
            if (i > 50 && i <= 75) {
                direction = "left";
            }
            if (i > 75 && i <= 100) {
                direction = "right";
            }

            actionCounterLock = 0;
        }
    }
}
