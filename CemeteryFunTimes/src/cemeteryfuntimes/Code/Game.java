package cemeteryfuntimes.Code;

import cemeteryfuntimes.Code.Shared.Globals;

// @author David Kozloff & Tyler Law

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Game implements Globals {
    
    private final Player player;
    private final ArrayList<Enemy> enemies;
    private final ArrayList<Pickup> pickups;
    private final Level level;
    private BufferedImage heartContainer=null;
    //private BufferedImage halfHeartContainer=null;
    private Test testOne; //just a debugging thingy
    
    //Constants
    private final static int HEARTSIZE=40;
    private final static int HEARTPADDING=10;
    
    public Game() {
        player = new Player(PLAYERSIZE/2,PLAYERSIZE/2,PISTOL);
        level = new Level(player, 1);
        
        // Enemies and pickups in the current room (Remove final keyword later).
        
        enemies = level.getStartNode().room.getEnemies();
        pickups = level.getStartNode().room.getPickups();
        
        // Image setup.
        
        setupImages();
        
        // TODO: Logic for loading new room/level as the player progresses.
    }
    
    public void test() {
        pickups.add(new Pickup(player, 10*(HEARTSIZE+HEARTPADDING)+HEARTPADDING, 10*HEARTPADDING, 0));
        pickups.add(new Pickup(player, 15*(HEARTSIZE+HEARTPADDING)+HEARTPADDING, 15*HEARTPADDING, 0));
        pickups.add(new Pickup(player, 12*(HEARTSIZE+HEARTPADDING)+HEARTPADDING, 18*HEARTPADDING, 0));
        //enemies.add(new Enemy(player,GAMEWIDTH/2,GAMEHEIGHT/2,1));
        //enemies.add(new Enemy(player,GAMEWIDTH/2-200,GAMEHEIGHT/2,1));
        //enemies.add(new Enemy(player,GAMEWIDTH/2+200,GAMEHEIGHT/2,1));
        //testOne = new Test();
    }
    
    public void update() {
        player.update();
        //Calculate new velocities for all enemies
        enemies.stream().forEach((enemie) -> {
            enemie.calcVels();
        });
        //Check for all collisions
        cemeteryfuntimes.Code.Shared.Collision.checkCollisions(player, enemies,pickups);
        enemies.stream().forEach((enemie) -> {
            enemie.update();
        });
        /*if (enemies.isEmpty()) {
            //Temporarily add in a new test enemy, when the first one is killed
            enemies.add(new Enemy(player,GAMEWIDTH/2,GAMEHEIGHT/2,1));
            enemies.add(new Enemy(player,GAMEWIDTH/2-200,GAMEHEIGHT/2-200,1));
        }*/
        //testOne.update();
        
    }
    
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        drawHUD(g);
        player.draw(g2d);
        enemies.stream().forEach((enemy) -> {
            enemy.draw(g2d);
        });
        pickups.stream().forEach((pickup) -> {
            pickup.draw(g2d);
        });
        //testOne.draw(g2d);
    }
    
    public void drawHUD(Graphics g) {
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
    
    private void setupImages() {
       //Initialize always relevent images images
       heartContainer = cemeteryfuntimes.Code.Shared.Utilities.getScaledInstance(IMAGEPATH+"General/heart.png",HEARTSIZE,HEARTSIZE,RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR,false);
       //halfHeartContainer = cemeteryfuntimes.Resources.Shared.Other.getScaledInstance("General/halfHeart.png",HEARTSIZE/2,HEARTSIZE,RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR,false);
    }
    
}