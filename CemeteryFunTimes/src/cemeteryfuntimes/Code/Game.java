package cemeteryfuntimes.Code;

import cemeteryfuntimes.Resources.Shared.*;

// @author David Kozloff & Tyler Law

import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class Game implements Globals {
    
    public Player player;
    public Weapon weapon;
    
    public Game() {
        // Initial player position
        player = new Player(PLAYERSIZE/2,PLAYERSIZE/2);
        weapon = new Weapon(player);
    }
    
    public void update() {
        player.update();
        weapon.update();
    }
    
    public void draw(Graphics g) {
        player.draw(g);
        weapon.draw(g);
    }
    
    public void keyPressed(int keyCode) {
        switch(keyCode) {
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_UP:
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_RIGHT:
                weapon.shoot(keyCode);
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
                weapon.stop(keyCode);
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
    
}