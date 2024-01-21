package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

// NOTE: Currently, character will not be seen holding a torch, but the item will give off light.
public class OBJ_Torch extends SuperObject {
    
    GamePanel gp;

    public OBJ_Torch(GamePanel gp) {

        name = "Torch";

        try {
            image = ImageIO.read(getClass().getResourceAsStream("../res/objects/torch-object.png"));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
