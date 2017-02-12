package cemeteryfuntimes.Code;

import cemeteryfuntimes.Resources.Shared.*;

// @author David Kozloff & Tyler Law

import java.awt.Graphics;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Game implements Globals {
    
    private final Player player;
    private final Weapon playerWeapon;
    private final ArrayList<Enemy> enemies;
    private BufferedImage heartContainer=null;
    private BufferedImage halfHeartContainer=null;
    
    //Constants
    private final static int HEARTSIZE=40;
    private final static int HEARTPADDING=10;
    
    public Game() {
        player = new Player(PLAYERSIZE/2,PLAYERSIZE/2);
        playerWeapon = new Weapon(player);
        enemies = new ArrayList();
        enemies.add(new Enemy(player,GAMEWIDTH/2-ENEMYSIZE/2,GAMEHEIGHT/2-ENEMYSIZE/2));
        setupImages();
    }
    
    public void update() {
        player.update();
        playerWeapon.update();
        enemies.stream().forEach((enemie) -> {
            enemie.update();
        });
        cemeteryfuntimes.Resources.Shared.Collision.checkCollisions(player, enemies, playerWeapon);
    }
    
    public void draw(Graphics g) {
        drawHUD(g);
        player.draw(g);
        playerWeapon.draw(g);
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
    
    public void keyPressed(int keyCode) {
        switch(keyCode) {
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_UP:
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_RIGHT:
                playerWeapon.keyPressed(keyCode);
                break;
            case KeyEvent.VK_W:
            case KeyEvent.VK_A:
            case KeyEvent.VK_S:
            case KeyEvent.VK_D:
                player.keyChanged(keyCode,true);
                break;
            case KeyEvent.VK_SPACE: //Roll?
        }
    }
    
    public void keyReleased(int keyCode) {
        switch(keyCode) {
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_UP:
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_RIGHT:
                playerWeapon.keyReleased(keyCode);
                break;
            case KeyEvent.VK_W:
            case KeyEvent.VK_A:
            case KeyEvent.VK_S:
            case KeyEvent.VK_D:
                player.keyChanged(keyCode,false);
                break;
            case KeyEvent.VK_SPACE: //Roll?
        }
    }
    
    private void setupImages() {
       //Initialize always relevent images images
       try { 
            heartContainer = ImageIO.read(new File(IMAGEPATH+"heart.png"));
            heartContainer = cemeteryfuntimes.Resources.Shared.Other.getScaledInstance(heartContainer,HEARTSIZE,HEARTSIZE,RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR,false);
        } catch (IOException e) { }
       try { 
            halfHeartContainer = ImageIO.read(new File(IMAGEPATH+"halfHeart.png"));
            halfHeartContainer = cemeteryfuntimes.Resources.Shared.Other.getScaledInstance(halfHeartContainer,HEARTSIZE/2,HEARTSIZE,RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR,false);
        } catch (IOException e) { }
        
    }
    
}