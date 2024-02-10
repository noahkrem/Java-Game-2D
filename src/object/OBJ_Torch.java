package object;

import entity.Entity;
import main.GamePanel;

// NOTE: Currently, character will not be seen holding a torch, but the item will give off light.
public class OBJ_Torch extends Entity {

    public OBJ_Torch(GamePanel gp) {

        super(gp);

        name = "Torch";
        down1 = setup("../res/objects/torch-object");

    }
}
