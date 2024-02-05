package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class OBJ_Health extends SuperObject {
    
    GamePanel gp;

    public OBJ_Health(GamePanel gp) {

        this.gp = gp;
        name = "Health";

        try {
            image = ImageIO.read(getClass().getResourceAsStream("../res/objects/health-bar-full.png"));
            image2 = ImageIO.read(getClass().getResourceAsStream("../res/objects/health-bar-empty.png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
            image2 = uTool.scaleImage(image2, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
