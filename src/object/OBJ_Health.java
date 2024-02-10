package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Health extends Entity {

    public OBJ_Health(GamePanel gp) {

        super(gp);

        name = "Health";
        image = setup("../res/objects/health-bar-full");
        image2 = setup("../res/objects/health-bar-empty");
    }
}
