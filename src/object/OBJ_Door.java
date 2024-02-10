package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Door extends Entity {

    public OBJ_Door(GamePanel gp) {

        super(gp);

        name = "Door";
        collision = true;
        down1 = setup("../res/objects/wooden-door-object");

        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = gp.tileSize;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}
