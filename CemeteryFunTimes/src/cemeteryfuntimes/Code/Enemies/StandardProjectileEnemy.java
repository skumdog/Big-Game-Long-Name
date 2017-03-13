package cemeteryfuntimes.Code.Enemies;

// @author David Kozloff & Tyler Law

import cemeteryfuntimes.Code.*;
import cemeteryfuntimes.Code.Weapons.Weapon;
import java.awt.Graphics2D;

public class StandardProjectileEnemy extends Enemy {
    
    public StandardProjectileEnemy(Player player, int xPos, int yPos, int enemyKey) {
        super(player,xPos,yPos,enemyKey);
        weapon = new Weapon(this,weaponKey);
        weapon.keyPressed(LEFT);
    }

    @Override
    public void calcVels() {
        //Calculates what the current velocity of the enemy should be
        //Find the vector pointing from the enemy to the player
        if (xVel ==0 && yVel==0) {
            int x = 6;
        }
        float xDist = player.xPos() - xPos;
        float yDist = player.yPos() - yPos;
        float totDist = (float) Math.sqrt(xDist*xDist + yDist*yDist);
        
        //Scale the vector to be the length of enemy speed to get speed
        xVel = speed * (xDist / totDist);
        yVel = speed * (yDist / totDist);
        rotateEnemyImage();
    }
    
    @Override
    public void update() {
        //Update xPos and yPos
        super.update();
        weapon.update();
    }
    
    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
        weapon.draw(g);
    }
    
}
