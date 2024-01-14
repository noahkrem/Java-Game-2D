package main;

import object.OBJ_Door;
import object.OBJ_Key;

public class AssetSetter {
 
    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {

        // Place a key at coordinates (9, 9)
        gp.obj[0] = new OBJ_Key();
        gp.obj[0].worldX = 9 * gp.tileSize;
        gp.obj[0].worldY = 9 * gp.tileSize;

        // Place a key at coordinates (41, 41)
        gp.obj[1] = new OBJ_Key();
        gp.obj[1].worldX = 41 * gp.tileSize;
        gp.obj[1].worldY = 41 * gp.tileSize;

        // Place a key at coordinates (9, 41)
        gp.obj[2] = new OBJ_Key();
        gp.obj[2].worldX = 9 * gp.tileSize;
        gp.obj[2].worldY = 41 * gp.tileSize;

        // Place a door at coordinates (41, 7)
        gp.obj[3] = new OBJ_Door();
        gp.obj[3].worldX = 40 * gp.tileSize;
        gp.obj[3].worldY = 6 * gp.tileSize;

    }
}
