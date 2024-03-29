package main;

import entity.NPC_OldMan;
import monster.MON_Crow;
import object.OBJ_Door;
import object.OBJ_Key;
import object.OBJ_Torch;

public class AssetSetter {
 
    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {

        // Place a key at coordinates (9, 9)
        gp.obj[0] = new OBJ_Key(gp);
        gp.obj[0].worldX = 9 * gp.tileSize;
        gp.obj[0].worldY = 9 * gp.tileSize;

        // Place a key at coordinates (41, 41)
        gp.obj[1] = new OBJ_Key(gp);
        gp.obj[1].worldX = 41 * gp.tileSize;
        gp.obj[1].worldY = 41 * gp.tileSize;

        // Place a door at coordinates (40, 6)
        gp.obj[3] = new OBJ_Door(gp);
        gp.obj[3].worldX = 40 * gp.tileSize;
        gp.obj[3].worldY = 6 * gp.tileSize;

        // Place a door at coordinates (10, 12)
        gp.obj[4] = new OBJ_Door(gp);
        gp.obj[4].worldX = 10 * gp.tileSize;
        gp.obj[4].worldY = 12 * gp.tileSize;

        // Place a torch at coordinates (26, 22)
        gp.obj[5] = new OBJ_Torch(gp);
        gp.obj[5].worldX = 26 * gp.tileSize;
        gp.obj[5].worldY = 22 * gp.tileSize;

    }

    public void setNPC() {

        gp.npc[0] = new NPC_OldMan(gp);
        gp.npc[0].worldX = gp.tileSize*40;
        gp.npc[0].worldY = gp.tileSize*40;
    }

    public void setMonster() {
        
        gp.monster[0] = new MON_Crow(gp);
        gp.monster[0].worldX = gp.tileSize*40;
        gp.monster[0].worldY = gp.tileSize*9;

        gp.monster[1] = new MON_Crow(gp);
        gp.monster[1].worldX = gp.tileSize*40;
        gp.monster[1].worldY = gp.tileSize*10;
    }
}
