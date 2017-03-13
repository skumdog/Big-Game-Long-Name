package cemeteryfuntimes.Code.Enemies;

import cemeteryfuntimes.Code.Player;

// @author David Kozloff & Tyler Law

public class MeleeEnemy extends Enemy {
    
    public MeleeEnemy(Player player, int xPos, int yPos, int key) {
        super(player,xPos,yPos,key);
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
    
    /*public void damaged(PosVel posVel, int type) {
        switch(type) {
            case PROJECTILEDAMAGE:
                Projectile projectile = (Projectile) posVel;
                health -= projectile.damage();
                break;
            case CONTACTDAMAGE:
                break;
        }
    }*/
    
}
