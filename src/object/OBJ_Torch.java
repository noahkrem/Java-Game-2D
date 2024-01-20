package object;

import java.io.IOException;

import javax.imageio.ImageIO;

// NOTE: Currently, character will not be seen holding a torch, but the item will give off light.
public class OBJ_Torch extends SuperObject {
    
    public OBJ_Torch() {

        name = "Torch";

        try {
            image = ImageIO.read(getClass().getResourceAsStream("../res/objects/torch-object.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
