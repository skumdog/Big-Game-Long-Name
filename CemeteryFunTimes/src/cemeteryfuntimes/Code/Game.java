package cemeteryfuntimes.Code;

// @author David Kozloff & Tyler Law

import cemeteryfuntimes.Code.Enemies.Enemy;
import cemeteryfuntimes.Code.Shared.Globals;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

public class Game implements Globals {
    
    private final Player player;
    private ArrayList<Enemy> enemies;
    private ArrayList<Pickup> pickups;
    private final Level level;
    private Room room;
    private BufferedImage heartContainer;
    //private BufferedImage halfHeartContainer=null;
    
    //Constants
    private final static int HEARTSIZE=40;
    private final static int HEARTPADDING=10;
  
    public Game() {
        player = new Player(PLAYERSIZE/2,PLAYERSIZE/2,PISTOL);
        level = new Level(player);
        room = level.getCurrentRoom();
        
        // Enemies and pickups in the current room (Remove final keyword later).
        
        enemies = room.getEnemies();
        pickups = room.getPickups();
        
        // Image setup.
        
        setupImages();
        
        // TODO: Logic for loading new room/level as the player progresses.
    }
    
    public void update() {
        player.update();
        if (!room.RoomClear()) {
            //Calculate new velocities for all enemies
            enemies.stream().forEach((enemie) -> {
                enemie.calcVels();
            });
            //Check for all collisions
            cemeteryfuntimes.Code.Shared.Collision.checkCollisions(player, enemies,pickups);
            Enemy enemy;
            for (Iterator<Enemy> enemyIt = enemies.iterator(); enemyIt.hasNext();) {
                enemy = enemyIt.next();
                if (enemy.health <= 0) { enemyIt.remove(); break;}
                enemy.update();
            }
        }
        else {
            int door = cemeteryfuntimes.Code.Shared.Collision.checkRoomClearCollisions(player);
            if (door >= 0 && level.changeRoom(door)) {
                room = level.getCurrentRoom();
                enemies = room.getEnemies();
                pickups = room.getPickups();
            }
        }
    }
    
    public void draw(Graphics2D g) {
        drawHUD(g);
        if (!room.RoomClear()) {
            enemies.stream().forEach((enemy) -> {
                enemy.draw(g);
            });
            pickups.stream().forEach((pickup) -> {
                pickup.draw(g);
            });
        }
        player.draw(g);
        room.draw(g);
        level.draw(g);
        //testOne.draw(g2d);
    }
    
    public void drawHUD(Graphics2D g) {
        //Draw players heart containers
        for (int i=0; i<player.health; i=i+2) {
            g.drawImage(heartContainer,(i/2)*(HEARTSIZE+HEARTPADDING)+HEARTPADDING,HEARTPADDING,null);
        }
        //Check if player has half a heart
        /*if (player.health % 2 != 0) {
            g.drawImage(halfHeartContainer,((player.health-1)/2)*(HEARTSIZE+HEARTPADDING)+HEARTPADDING,HEARTPADDING,null);
        }*/
    }
    
    public void movementAction(int gameCode, boolean isPressed) {
        //Game code is the relevant global in the "Player Commands" section of globals
        player.movementKeyChanged(gameCode, isPressed);
    }
    
    public void shootAction(int gameCode, boolean isPressed) {
        //Game code is the relevant global in the "Player Commands" section of globals
        player.shootKeyChanged(gameCode, isPressed);
    }
    
    public void changeWeaponAction(int gameCode, boolean isPressed) {
        //Game code is the relevant global in the "Player Commands" section of globals
        player.changeWeaponKeyChanged(gameCode,isPressed);
    }
    
    private void setupImages() {
       //Initialize always relevent images images
       heartContainer = cemeteryfuntimes.Code.Shared.Utilities.getScaledInstance(IMAGEPATH+"General/heart.png",HEARTSIZE,HEARTSIZE);
       //halfHeartContainer = cemeteryfuntimes.Resources.Shared.Other.getScaledInstance("General/halfHeart.png",HEARTSIZE/2,HEARTSIZE);
    }
    
}