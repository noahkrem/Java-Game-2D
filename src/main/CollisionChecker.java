package main;

import entity.Entity;

// We check for collisions inside of the update method
public class CollisionChecker {
    
    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    // We want to check for collisions based on the "solid" part of our entity
    // There are four coordinates to check, each side of the square that is our "solid"
    public void checkTile(Entity entity) {
        
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width; // issue here?
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX/gp.tileSize;
        int entityRightCol = entityRightWorldX/gp.tileSize;
        int entityTopRow = entityTopWorldY/gp.tileSize;
        int entityBottomRow = entityBottomWorldY/gp.tileSize;

        int tileNum1, tileNum2;

        // We include the player speed in the calculation because we are "predicting"
        //  where the player will be after they have moved
        switch (entity.direction) {
        case "up" :
            entityTopRow = (entityTopWorldY - entity.speed)/gp.tileSize;
            tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
            tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
            
            if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                entity.collisionOn = true;
            }

            break;
        case "down" :
            entityBottomRow = (entityBottomWorldY + entity.speed)/gp.tileSize;
            tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
            tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
            
            if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                entity.collisionOn = true;
            }

            break;
        case "left" :
            entityLeftCol = (entityLeftWorldX - entity.speed)/gp.tileSize;
            tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
            tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
            
            if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                entity.collisionOn = true;
            }

            break;
        case "right" :
            entityRightCol = (entityRightWorldX + entity.speed)/gp.tileSize;
            tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
            tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
            
            if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                entity.collisionOn = true;
            }

            break;
        }
    }

    public int checkObject(Entity entity, boolean player) {
        
        int index = 999;

        for (int i = 0; i < gp.obj.length; i++) {

            if (gp.obj[i] != null) {
                
                // Get entity's hitbox position
                entity.solidArea.x += entity.worldX;
                entity.solidArea.y += entity.worldY;

                // Get object's hitbox position
                gp.obj[i].solidArea.x += gp.obj[i].worldX;
                gp.obj[i].solidArea.y += gp.obj[i].worldY;

                switch (entity.direction) {
                case "up" :
                    entity.solidArea.y -= entity.speed;
                    // Checks if these objects are touching or not
                    if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
                        if (gp.obj[i].collision == true)
                            entity.collisionOn = true;
                        if (player == true)
                            index = i;
                    }
                    break;
                case "down" :
                    entity.solidArea.y += entity.speed;
                    // Checks if these objects are touching or not
                    if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
                        if (gp.obj[i].collision == true)
                            entity.collisionOn = true;
                        if (player == true)
                            index = i;
                    }
                    break;
                case "left" :
                    entity.solidArea.x -= entity.speed;
                    // Checks if these objects are touching or not
                    if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
                        if (gp.obj[i].collision == true)
                            entity.collisionOn = true;
                        if (player == true)
                            index = i;
                    }
                    break;
                case "right" :
                    entity.solidArea.x += entity.speed;
                    // Checks if these objects are touching or not
                    if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
                        if (gp.obj[i].collision == true)
                            entity.collisionOn = true;
                        if (player == true)
                            index = i;
                    }
                    break;
                }

                // Must reset, so that these values do not keep increasing forever
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
                gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;
            }
        }

        return index;
    } 

    // NPC OR MONSTER COLLISION
    public int checkEntity(Entity entity, Entity[] target) {
        
        int index = 999;

        for (int i = 0; i < target.length; i++) {

            if (target[i] != null) {
                
                // Get entity's hitbox position
                entity.solidArea.x += entity.worldX;
                entity.solidArea.y += entity.worldY;

                // Get object's hitbox position
                target[i].solidArea.x += target[i].worldX;
                target[i].solidArea.y += target[i].worldY;

                switch (entity.direction) {
                case "up" :
                    entity.solidArea.y -= entity.speed;
                    // Checks if these objects are touching or not
                    if (entity.solidArea.intersects(target[i].solidArea)) {
                        entity.collisionOn = true;
                        index = i;
                    }
                    break;
                case "down" :
                    entity.solidArea.y += entity.speed;
                    // Checks if these objects are touching or not
                    if (entity.solidArea.intersects(target[i].solidArea)) {
                        entity.collisionOn = true;
                        index = i;
                    }
                    break;
                case "left" :
                    entity.solidArea.x -= entity.speed;
                    // Checks if these objects are touching or not
                    if (entity.solidArea.intersects(target[i].solidArea)) {
                        entity.collisionOn = true;
                        index = i;
                    }
                    break;
                case "right" :
                    entity.solidArea.x += entity.speed;
                    // Checks if these objects are touching or not
                    if (entity.solidArea.intersects(target[i].solidArea)) {
                        entity.collisionOn = true;
                        index = i;
                    }
                    break;
                }

                // Must reset, so that these values do not keep increasing forever
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                target[i].solidArea.x = target[i].solidAreaDefaultX;
                target[i].solidArea.y = target[i].solidAreaDefaultY;
            }
        }

        return index;
    }

    public void checkPlayer(Entity entity) {

        // Get entity's hitbox position
        entity.solidArea.x += entity.worldX;
        entity.solidArea.y += entity.worldY;

        // Get object's hitbox position
        gp.player.solidArea.x += gp.player.worldX;
        gp.player.solidArea.y += gp.player.worldY;

        switch (entity.direction) {
        case "up" :
            entity.solidArea.y -= entity.speed;
            // Checks if these objects are touching or not
            if (entity.solidArea.intersects(gp.player.solidArea)) {
                entity.collisionOn = true;
            }
            break;
        case "down" :
            entity.solidArea.y += entity.speed;
            // Checks if these objects are touching or not
            if (entity.solidArea.intersects(gp.player.solidArea)) {
                entity.collisionOn = true;
            }
            break;
        case "left" :
            entity.solidArea.x -= entity.speed;
            // Checks if these objects are touching or not
            if (entity.solidArea.intersects(gp.player.solidArea)) {
                entity.collisionOn = true;
            }
            break;
        case "right" :
            entity.solidArea.x += entity.speed;
            // Checks if these objects are touching or not
            if (entity.solidArea.intersects(gp.player.solidArea)) {
                entity.collisionOn = true;
            }
            break;
        }

        // Must reset, so that these values do not keep increasing forever
        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.solidAreaDefaultY;
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
    }
}
