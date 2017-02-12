package cemeteryfuntimes.Code;

import cemeteryfuntimes.Resources.Shared.*;

// @author David Kozloff & Tyler Law

import java.awt.Graphics;

public class Projectile implements Globals {
    
    private PosVel posVel;
    
    private int xLength;
    private int yLength;
    private final float xSide;
    private final float ySide;
    
    public Projectile(Player player, int direction) {
        PosVel playerPosVel = player.PosVel();
        float playerRad = player.rad();
 
        posVel = new PosVel();
        //Create projectile
        //Decide starting position by player position and direction projectile is being fired
        //Decide velocity by adding PROJECTILESPEED with player velocity in that direction 
        switch( direction ) {
            case MOVELEFT:
                posVel.xPos = playerPosVel.xPos - playerRad;
                posVel.yPos = playerPosVel.yPos;
                posVel.xVel = playerPosVel.xVel*PROJECTILEPLAYERBOOST-PROJECTILESPEED;
                xLength = PROJECTILELENGTH;
                yLength = PROJECTILEWIDTH;
                break;
            case MOVERIGHT:
                posVel.xPos = playerPosVel.xPos + playerRad;
                posVel.yPos = playerPosVel.yPos;
                posVel.xVel = playerPosVel.xVel*PROJECTILEPLAYERBOOST+PROJECTILESPEED;
                xLength = PROJECTILELENGTH;
                yLength = PROJECTILEWIDTH;
                break;
            case MOVEUP:
                posVel.xPos = playerPosVel.xPos;
                posVel.yPos = playerPosVel.yPos - playerRad;
                posVel.yVel = playerPosVel.yVel*PROJECTILEPLAYERBOOST-PROJECTILESPEED;
                xLength = PROJECTILEWIDTH;
                yLength = PROJECTILELENGTH;
                break;
            case MOVEDOWN:
                posVel.xPos = playerPosVel.xPos;
                posVel.yPos = playerPosVel.yPos + playerRad;
                posVel.yVel = playerPosVel.yVel+PROJECTILESPEED;
                xLength = PROJECTILEWIDTH;
                yLength = PROJECTILELENGTH;
                break;
        }
        xSide = GAMEBORDER - xLength/2;
        ySide = -yLength/2;
    }
    
    public void update() {
        posVel.update();
    }
    
    public boolean hitWall() {
        //Return true if projectile has collidd with a wall
        return cemeteryfuntimes.Resources.Shared.Collision.hitWall(posVel.xPos,xLength/2,posVel.yPos,yLength/2);
    }
    
    public void draw(Graphics g) {
        g.setColor(PROJECTILECOLOR);
        g.drawRect(Math.round(xSide+posVel.xPos),Math.round(ySide+posVel.yPos),xLength,yLength);
    }
    
}