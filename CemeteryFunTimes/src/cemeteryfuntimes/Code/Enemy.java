package cemeteryfuntimes.Code;

import cemeteryfuntimes.Resources.Shared.*;
import java.awt.Graphics;

// @author David Kozloff & Tyler Law

public class Enemy extends PosVel implements Globals {
    
    public int health = 3;
    private final Player player;
    
    private final float xSide;
    private final float ySide;
    
    //Enemy definition
    private int damage;
     public int Damage() {
        return damage;
    }
    
    public Enemy(Player player, int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.player = player;
        rad = ENEMYSIZE/2; xRad = rad; yRad = rad;
        xSide = GAMEBORDER - rad;
        ySide = - rad;
        
        damage = 2;
    }
    
    public void update() {
        calcVels();
        xPos += xVel;
        yPos += yVel;
    }
    
    public void calcVels() {
        //Calculates what the current velocity of the enemy should be
        //Find the vector pointing from the enemy to the player
        float xDist = player.xPos() - xPos ;
        float yDist = player.yPos() - yPos;
        float totDist = (float) Math.sqrt(xDist*xDist + yDist*yDist);
        
        //Scale the vector to be the length of enemy speed to get speed
        xVel = ENEMYSPEED * (xDist / totDist);
        yVel = ENEMYSPEED * (yDist / totDist);
    }
    
    public void collide(int side) {
        //Handle a collision with player
        //Adjust position so this enemy is moved to the edge of the player
        switch (side) {
            case LEFTWALL:
                xPos = player.xPos() - player.rad() - rad;
                break;
            case RIGHTWALL:
                xPos = player.xPos() + player.rad() + rad;
                break;
            case TOPWALL:
                yPos = player.yPos() - player.rad() - rad;
                break;
            case BOTTOMWALL:
                yPos = player.yPos() + player.rad() + rad;
                break;
        }
    }
    
    public void draw(Graphics g) {
        g.setColor(ENEMYCOLOR);
        g.fillRect(Math.round(xSide+xPos),Math.round(ySide+yPos),ENEMYSIZE,ENEMYSIZE);
    }
    
}
