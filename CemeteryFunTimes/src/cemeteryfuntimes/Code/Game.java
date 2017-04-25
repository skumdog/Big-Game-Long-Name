package cemeteryfuntimes.Code;
import cemeteryfuntimes.Code.Rooms.Room;
import cemeteryfuntimes.Code.Shared.*;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
/**
* Game class contains variables and methods related to game state.
* @author David Kozloff & Tyler Law
*/
public class Game implements Globals {
    
    private final Player player;
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
        player = new Player(PLAYERSIZE/2,PLAYERSIZE/2);
        level = new Level(player);
        room = level.getCurrentRoom();
        // Image setup.
        setupImages();
        // TODO: Logic for loading new room/level as the player progresses.
    }
    /**
    * Updates the game.
    */    
    public void update() {
        if (level.changeRoom(Collision.checkCollisions(player,room))) {
            room = level.getCurrentRoom();
        }
        player.update();
        room.update();
        //Check for all collisions
    }
    /**
    * Renders the game.
    * 
    * @param g The Graphics object used by Java to render everything in the game.
    */
    public void draw(Graphics2D g) {
        room.draw(g);
        player.draw(g);
        drawHUD(g);
        level.draw(g);
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
        player.changeWeaponKeyChanged(gameCode,isPressed,false);
    }
    /**
    * Calls the callback method triggered by a set of related key events.
    * Changing weapons keys.
    * 
    * @param gameCode  The change weapon key currently being pressed.
    * @param isPressed Returns true if the key is currently being pressed.
    */
    public void changeSpecificWeaponAction(int gameCode, boolean isPressed) {
        //Game code is the relevant global in the "Player Commands" section of globals
        player.changeWeaponKeyChanged(gameCode,isPressed,true);
    }
    /**
    * Initializes BufferedImage objects, which are used to render images.
    */
    private void setupImages() {
       //Initialize always relevent images images
       ImageLoader.loadImage("General/heart.png",HEARTSIZE,HEARTSIZE);
       ImageLoader.loadImage("General/coin.png",HEARTSIZE,HEARTSIZE);
       this.heartContainer = ImageLoader.getImage("General/heart.png",0);
       this.coin = ImageLoader.getImage("General/coin.png",0);
       ImageLoader.loadImage("General/cave.png", 100, 150);
       //halfHeartContainer = cemeteryfuntimes.Resources.Shared.Other.getScaledInstance("General/halfHeart.png",HEARTSIZE/2,HEARTSIZE);
    }
}