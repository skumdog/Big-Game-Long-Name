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
    private BufferedImage heartContainer=null;
    private BufferedImage halfHeartContainer=null;
    
    //Constants
    private final static int HEARTSIZE=40;
    private final static int HEARTPADDING=10;
    
    public Game() {
        player = new Player(PLAYERSIZE/2,PLAYERSIZE/2,PISTOL);
        enemies = new ArrayList();
        enemies.add(new Enemy(player,GAMEWIDTH/2-ENEMYSIZE/2,GAMEHEIGHT/2-ENEMYSIZE/2));
        setupImages();
    }
    
    public void update() {
        player.update();
        enemies.stream().forEach((enemie) -> {
            enemie.update();
        });
        cemeteryfuntimes.Resources.Shared.Collision.checkCollisions(player, enemies);
    }
    
    public void draw(Graphics g) {
        drawHUD(g);
        player.draw(g);
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
    
}