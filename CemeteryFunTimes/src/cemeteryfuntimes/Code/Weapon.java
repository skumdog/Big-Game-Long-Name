package cemeteryfuntimes.Code;

import cemeteryfuntimes.Resources.Shared.*;

// @author David Kozloff & Tyler Law

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Weapon implements Globals {
    
    private final ArrayList<Projectile> projectiles;
    private final boolean[] keyPressed;
    private final Player player;
    private long lastUpdate;
    
    
    
    public Weapon(Player player) {
        this.player = player;
        projectiles = new ArrayList();
        keyPressed = new boolean[4];
    }
    
    public void shoot(int direction) {
        switch(direction) {
            case KeyEvent.VK_LEFT:
                keyPressed[0]=true;
                keyPressed[1]=false;
                keyPressed[2]=false;
                keyPressed[3]=false;
                break;
            case KeyEvent.VK_RIGHT:
                keyPressed[0]=false;
                keyPressed[1]=true;
                keyPressed[2]=false;
                keyPressed[3]=false;
                break;
            case KeyEvent.VK_UP:
                keyPressed[0]=false;
                keyPressed[1]=false;
                keyPressed[2]=true;
                keyPressed[3]=false;
                break;
            case KeyEvent.VK_DOWN:
                keyPressed[0]=false;
                keyPressed[1]=false;
                keyPressed[2]=false;
                keyPressed[3]=true;
                break;
        }
    }
    
    public void stop(int direction) {
        switch(direction) {
            case KeyEvent.VK_LEFT:
                keyPressed[0]=false;
                break;
            case KeyEvent.VK_RIGHT:
                keyPressed[1]=false;
                break;
            case KeyEvent.VK_UP:
                keyPressed[2]=false;
                break;
            case KeyEvent.VK_DOWN:
                keyPressed[3]=false;
                break;
        }
    }
    
    public void update() {
        createProjectile();
        for (int i=0; i<projectiles.size(); i++) {
            projectiles.get(i).update();
            if (projectiles.get(i).hitWall()) {
                projectiles.remove(i);
                i--;
            }
        }
    }
    
    public void createProjectile() {
        //Check if enough time has passed for more projectiles to spawn
        long now = System.currentTimeMillis();
        if (now - lastUpdate < PROJECTILEDELAY) { return; }
        lastUpdate = now;
        
        //Create new projectile with correct location relative to player
        Projectile projectile;
        if (keyPressed[0]) {
            projectile = new Projectile(player.xPos()-player.rad(),player.yPos(),MOVELEFT);
        }
        else if (keyPressed[1]) {
            projectile = new Projectile(player.xPos()+player.rad(),player.yPos(),MOVERIGHT);
        }
        else if (keyPressed[2]) {
            projectile = new Projectile(player.xPos(),player.yPos()-player.rad(),MOVEUP);
        }
        else if (keyPressed[3]) {
            projectile = new Projectile(player.xPos(),player.yPos()+player.rad(),MOVEDOWN);
        }
        else {
            return;
        }
        projectiles.add(projectile);
    }
    
    public void draw(Graphics g) {
        projectiles.stream().forEach((projectile) -> {
            projectile.draw(g);
        });
    }
    
     //Retrieve private variables
    public ArrayList<Projectile> Projectiles() {
        return projectiles;
    }
    
}