package cemeteryfuntimes.Code;

import cemeteryfuntimes.Resources.Shared.*;

// @author David Kozloff & Tyler Law

import java.awt.Graphics;

public class Projectile extends PosVel implements Globals {
    
    private final float xSide;
    private final float ySide;
    
    public Projectile(Player player, int direction) {
        float playerRad = player.rad();
 
        //Create projectile
        //Decide starting position by player position and direction projectile is being fired
        //Decide velocity by adding PROJECTILESPEED with player velocity in that direction 
        switch( direction ) {
            case SHOOTLEFT:
                xPos = player.xPos() - playerRad;
                yPos = player.yPos();
                xVel = player.xVel()*PROJECTILEPLAYERBOOST-PROJECTILESPEED;
                xRad = PROJECTILELENGTH/2;
                yRad = PROJECTILEWIDTH/2;
                break;
            case SHOOTRIGHT:
                xPos = player.xPos() + playerRad;
                yPos = player.yPos();
                xVel = player.xVel()*PROJECTILEPLAYERBOOST+PROJECTILESPEED;
                xRad = PROJECTILELENGTH/2;
                yRad = PROJECTILEWIDTH/2;
                break;
            case SHOOTUP:
                xPos = player.xPos();
                yPos = player.yPos() - playerRad;
                yVel = player.yVel()*PROJECTILEPLAYERBOOST-PROJECTILESPEED;
                xRad = PROJECTILEWIDTH/2;
                yRad = PROJECTILELENGTH/2;
                break;
            case SHOOTDOWN:
                xPos = player.xPos();
                yPos = player.yPos() + playerRad;
                yVel = player.yVel()+PROJECTILESPEED;
                xRad = PROJECTILEWIDTH/2;
                yRad = PROJECTILELENGTH/2;
                break;
        }
        xSide = GAMEBORDER - xRad;
        ySide = - yRad;
    }
    
    public void update() {
        xPos += xVel;
        yPos += yVel;
    }
    
    public boolean hitWall() {
        //Return true if projectile has collidd with a wall
        return cemeteryfuntimes.Resources.Shared.Collision.hitWall(xPos,xRad,yPos,yRad);
    }
    
    public void draw(Graphics g) {
        g.setColor(PROJECTILECOLOR);
        g.fillRect(Math.round(xSide+xPos),Math.round(ySide+yPos),Math.round(xRad*2),Math.round(yRad*2));
    }
    
}