<<<<<<< HEAD:CemeteryFunTimes/src/cemeteryfuntimes/Code/Game.java
package cemeteryfuntimes.Code;

import cemeteryfuntimes.Resources.Shared.*;

// @author David Kozloff & Tyler Law

import java.awt.Graphics;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Game implements Globals {
    
    private final Player player;
    private final ArrayList<Enemy> enemies;
    private final ArrayList<Pickup> pickups;
    private BufferedImage heartContainer=null;
    private BufferedImage halfHeartContainer=null;
    
    //Constants
    private final static int HEARTSIZE=40;
    private final static int HEARTPADDING=10;
    
    public Game() {
        player = new Player(PLAYERSIZE/2,PLAYERSIZE/2,PISTOL);
        enemies = new ArrayList();
        pickups = new ArrayList();
        test();
        setupImages();
    }
    
    public void test() {
        pickups.add(new Pickup(player, 10*(HEARTSIZE+HEARTPADDING)+HEARTPADDING, 10*HEARTPADDING, 0));
        pickups.add(new Pickup(player, 15*(HEARTSIZE+HEARTPADDING)+HEARTPADDING, 15*HEARTPADDING, 0));
        pickups.add(new Pickup(player, 12*(HEARTSIZE+HEARTPADDING)+HEARTPADDING, 18*HEARTPADDING, 0));
        //enemies.add(new Enemy(player,GAMEWIDTH/2-ENEMYSIZE/2,GAMEHEIGHT/2-ENEMYSIZE/2));
        //enemies.add(new Enemy(player,GAMEWIDTH/2+ENEMYSIZE/2,GAMEHEIGHT/2+ENEMYSIZE/2));
    }
    
    public void update() {
        player.update();
        enemies.stream().forEach((enemie) -> {
            enemie.update();
        });
        cemeteryfuntimes.Resources.Shared.Collision.checkCollisions(player, enemies,pickups);
        /*if (enemies.isEmpty()) {
            //Temporarily add in a new test enemy, when the first one is killed
            enemies.add(new Enemy(player,GAMEWIDTH/2-ENEMYSIZE/2,GAMEHEIGHT/2-ENEMYSIZE/2));
            enemies.add(new Enemy(player,GAMEWIDTH/2+ENEMYSIZE/2,GAMEHEIGHT/2+ENEMYSIZE/2));
        }*/
    }
    
    public void draw(Graphics g) {
        drawHUD(g);
        player.draw(g);
        enemies.stream().forEach((enemy) -> {
            enemy.draw(g);
        });
        pickups.stream().forEach((pickup) -> {
            pickup.draw(g);
        });
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
    
    public void movementAction(int keyCode, boolean isPressed) {
        player.movementKeyChanged(keyCode, isPressed);
    }
    
    public void shootAction(int keyCode, boolean isPressed) {
        player.shootKeyChanged(keyCode, isPressed);
    }
    
    private void setupImages() {
       //Initialize always relevent images images
       heartContainer = cemeteryfuntimes.Resources.Shared.Other.getScaledInstance(IMAGEPATH+"General/heart.png",HEARTSIZE,HEARTSIZE,RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR,false);
       //halfHeartContainer = cemeteryfuntimes.Resources.Shared.Other.getScaledInstance("General/halfHeart.png",HEARTSIZE/2,HEARTSIZE,RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR,false);
    }
    
=======
package cemeteryfuntimes.Code;

import cemeteryfuntimes.Resources.Shared.*;

// @author David Kozloff & Tyler Law

import java.awt.Graphics;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Game implements Globals {
    
    private final Player player;
    private final ArrayList<Enemy> enemies;
    private final ArrayList<Pickup> pickups;
    private BufferedImage heartContainer=null;
    private BufferedImage halfHeartContainer=null;
    
    //Constants
    private final static int HEARTSIZE=40;
    private final static int HEARTPADDING=10;
    
    public Game() {
        player = new Player(PLAYERSIZE/2,PLAYERSIZE/2,PISTOL);
        enemies = new ArrayList();
        pickups = new ArrayList();
        pickups.add(new Pickup(player, 10*(HEARTSIZE+HEARTPADDING)+HEARTPADDING, 10*HEARTPADDING, 0));
        pickups.add(new Pickup(player, 15*(HEARTSIZE+HEARTPADDING)+HEARTPADDING, 15*HEARTPADDING, 0));
        pickups.add(new Pickup(player, 12*(HEARTSIZE+HEARTPADDING)+HEARTPADDING, 18*HEARTPADDING, 0));
        enemies.add(new Enemy(player,GAMEWIDTH/2-ENEMYSIZE/2,GAMEHEIGHT/2-ENEMYSIZE/2));
        setupImages();
    }
    
    public void update() {
        player.update();
        enemies.stream().forEach((enemie) -> {
            enemie.update();
        });
        cemeteryfuntimes.Resources.Shared.Collision.checkCollisions (player, enemies, pickups);
    }
    
    public void draw(Graphics g) {
        drawHUD(g);
        player.draw(g);
        pickups.stream().forEach((pickup) -> {
            pickup.draw(g);
        });
        enemies.stream().forEach((enemy) -> {
            enemy.draw(g);
        });
    }
    
    public void drawHUD(Graphics g) {
        //Draw players heart containers
        for (int i=0; i<player.health; i=i+2) {
            g.drawImage(heartContainer,(i/2)*(HEARTSIZE+HEARTPADDING)+HEARTPADDING,HEARTPADDING,null);
        }
        //Check if player has half a heart
        if (true) {
            g.drawImage(halfHeartContainer,((player.health-1)/2)*(HEARTSIZE+HEARTPADDING)+HEARTPADDING,HEARTPADDING,null);
        }
    }
    
    public void movementAction(int keyCode, boolean isPressed) {
        player.movementKeyChanged(keyCode, isPressed);
    }
    
    public void shootAction(int keyCode, boolean isPressed) {
        player.shootKeyChanged(keyCode, isPressed);
    }
    
    private void setupImages() {
       //Initialize always relevent images images
       heartContainer = cemeteryfuntimes.Resources.Shared.Other.getScaledInstance(IMAGEPATH+"General/heart.png",HEARTSIZE,HEARTSIZE,RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR,false);
       halfHeartContainer = cemeteryfuntimes.Resources.Shared.Other.getScaledInstance("General/halfHeart.png",HEARTSIZE/2,HEARTSIZE,RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR,false);
    }
    
>>>>>>> origin/master:Big-Game-Long-Name/CemeteryFunTimes/src/cemeteryfuntimes/Code/Game.java
}