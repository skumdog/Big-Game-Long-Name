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
                keyPressed[MOVELEFT]=true;
                keyPressed[MOVERIGHT]=false;
                keyPressed[MOVEUP]=false;
                keyPressed[MOVEDOWN]=false;
                break;
            case KeyEvent.VK_RIGHT:
                keyPressed[MOVELEFT]=false;
                keyPressed[MOVERIGHT]=true;
                keyPressed[MOVEUP]=false;
                keyPressed[MOVEDOWN]=false;
                break;
            case KeyEvent.VK_UP:
                keyPressed[MOVELEFT]=false;
                keyPressed[MOVERIGHT]=false;
                keyPressed[MOVEUP]=true;
                keyPressed[MOVEDOWN]=false;
                break;
            case KeyEvent.VK_DOWN:
                keyPressed[MOVELEFT]=false;
                keyPressed[MOVERIGHT]=false;
                keyPressed[MOVEUP]=false;
                keyPressed[MOVEDOWN]=true;
                break;
        }
    }
    
    public void stop(int direction) {
        switch(direction) {
            case KeyEvent.VK_LEFT:
                keyPressed[MOVELEFT]=false;
                break;
            case KeyEvent.VK_RIGHT:
                keyPressed[MOVERIGHT]=false;
                break;
            case KeyEvent.VK_UP:
                keyPressed[MOVEUP]=false;
                break;
            case KeyEvent.VK_DOWN:
                keyPressed[MOVEDOWN]=false;
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
        for (int i=0; i<4; i++) {
            if (keyPressed[i]) {
                //If a key is currently pressed generate a projectile moving in that direction
                Projectile projectile = new Projectile(player,i);
                projectiles.add(projectile);
                return;
            }
        }
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