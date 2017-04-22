package cemeteryfuntimes.Code;
import cemeteryfuntimes.Code.Shared.*;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
/**
* Game class contains variables and methods related to game state.
* @author David Kozloff & Tyler Law
*/
public class Game implements Globals {
    
    private final Player player;
    private ArrayList<Enemy> enemies;
    private ArrayList<Pickup> pickups;
    private final Level level;
    private Room room;
    private BufferedImage heartContainer;
    private BufferedImage coin;
    //private BufferedImage halfHeartContainer=null;
    
    //Constants
    private final static int HEARTSIZE=40;
    private final static int HEARTPADDING=10;
    /**
    * Game class constructor initializes variables related to game state.
    */
    public Game() {
        player = new Player(PLAYERSIZE/2,PLAYERSIZE/2,PISTOL);
        level = new Level(player);
        room = level.getCurrentRoom();
        
        // Enemies and pickups in the current room (Remove final keyword later).
        
        enemies = room.getEnemies();
        pickups = room.getPickups();
        
        // Call roomEntered when the game begins to spawn initial enemies.
        
        room.populateRoom();
        
        // Image setup.

        setupImages();
        
        // TODO: Logic for loading new room/level as the player progresses.
    }
    /**
    * Updates the game.
    */
    public void update() {
        player.update();
        room.update();
        if (!room.RoomClear()) {
            //Calculate new velocities for all enemies
            enemies.stream().forEach((enemie) -> {
                enemie.calcVels();
            });
            //Check for all collisions
            Collision.checkCollisions(player, enemies,pickups,false);
            Enemy enemy;
            for (Iterator<Enemy> enemyIt = enemies.iterator(); enemyIt.hasNext();) {
                enemy = enemyIt.next();
                if (enemy.health <= 0) { enemyIt.remove(); break;}
                enemy.update();
            }
        }
        else {
            int door = Collision.checkCollisions(player, enemies,pickups,true);
            if (door >= 0 && level.changeRoom(door)) {
                room = level.getCurrentRoom();
                enemies = room.getEnemies();
                pickups = room.getPickups();
            }
        }
    }
    /**
    * Renders the game.
    * 
    * @param g The Graphics object used by Java to render everything in the game.
    */
    public void draw(Graphics2D g) {
        drawHUD(g);
        if (!room.RoomClear()) {
            for (int i=0; i < enemies.size(); i++) {
                enemies.get(i).draw(g);
            }
        }
        pickups.stream().forEach((pickup) -> {
            pickup.draw(g);
        });
        player.draw(g);
        room.draw(g);
        level.draw(g);
        //testOne.draw(g2d);
    }
    /**
    * Renders the player's heads up display.
    * 
    * @param g The Graphics object used by Java to render everything in the game.
    */
    public void drawHUD(Graphics2D g) {
        //Draw player's heart containers
        for (int i=0; i<player.getHealth(); i=i+2) {
            g.drawImage(this.heartContainer,(i/2)*(HEARTSIZE+HEARTPADDING)+HEARTPADDING,HEARTPADDING,null);
        }
        //Draw player's coins.
        for (int i=0; i<player.getCoins(); i=i+1) {
           g.drawImage(this.coin,10*i+10,70,null);
        }
        //Check if player has half a heart
        /*if (player.health % 2 != 0) {
            g.drawImage(halfHeartContainer,((player.health-1)/2)*(HEARTSIZE+HEARTPADDING)+HEARTPADDING,HEARTPADDING,null);
        }*/
    }
    /**
    * Calls the callback method triggered by a set of related key events.
    * Movement keys.
    * 
    * @param gameCode  The movement direction key currently being pressed.
    * @param isPressed Returns true if the key is currently being pressed.
    */
    public void movementAction(int gameCode, boolean isPressed) {
        //Game code is the relevant global in the "Player Commands" section of globals
        player.movementKeyChanged(gameCode, isPressed);
    }
    /**
    * Calls the callback method triggered by a set of related key events.
    * Shooting keys.
    * 
    * @param gameCode  The shooting direction key currently being pressed.
    * @param isPressed Returns true if the key is currently being pressed.
    */
    public void shootAction(int gameCode, boolean isPressed) {
        //Game code is the relevant global in the "Player Commands" section of globals
        player.shootKeyChanged(gameCode, isPressed);
    }
    /**
    * Calls the callback method triggered by a set of related key events.
    * Changing weapons keys.
    * 
    * @param gameCode  The change weapon key currently being pressed.
    * @param isPressed Returns true if the key is currently being pressed.
    */
    public void changeWeaponAction(int gameCode, boolean isPressed) {
        //Game code is the relevant global in the "Player Commands" section of globals
        player.changeWeaponKeyChanged(gameCode,isPressed);
    }
    /**
    * Initializes BufferedImage objects, which are used to render images.
    */
    private void setupImages() {
       //Initialize always relevent images images
       cemeteryfuntimes.Code.Shared.ImageLoader.loadImage("General/heart.png",HEARTSIZE,HEARTSIZE);
       cemeteryfuntimes.Code.Shared.ImageLoader.loadImage("General/coin.png",HEARTSIZE,HEARTSIZE);
       this.heartContainer = cemeteryfuntimes.Code.Shared.ImageLoader.getImage("General/heart.png",0);
       this.coin = cemeteryfuntimes.Code.Shared.ImageLoader.getImage("General/coin.png",0);
       //halfHeartContainer = cemeteryfuntimes.Resources.Shared.Other.getScaledInstance("General/halfHeart.png",HEARTSIZE/2,HEARTSIZE);
    }
}