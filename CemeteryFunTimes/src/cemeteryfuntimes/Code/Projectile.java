package cemeteryfuntimes.Code;

import cemeteryfuntimes.Resources.Shared.*;

// @author David Kozloff & Tyler Law

import java.awt.Graphics;

public class Projectile implements Globals {
    
    private PosVel posVel;
    
    private int xLength;
    private int yLength;
    private float xSide;
    private float ySide;
    
    // Give projectile initial velocity when spawned
    public Projectile(float xPos, float yPos, int direction) {
        posVel = new PosVel(xPos,yPos,0,0);
        switch( direction ) {
            case MOVELEFT:
                posVel.xVel = -PROJECTILESPEED;
                xLength = PROJECTILELENGTH;
                yLength = PROJECTILEWIDTH;
                break;
            case MOVERIGHT:
                posVel.xVel = PROJECTILESPEED;
                xLength = PROJECTILELENGTH;
                yLength = PROJECTILEWIDTH;
                break;
            case MOVEUP:
                posVel.yVel = -PROJECTILESPEED;
                xLength = PROJECTILEWIDTH;
                yLength = PROJECTILELENGTH;
                break;
            case MOVEDOWN:
                posVel.yVel = PROJECTILESPEED;
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
    
    // Called by Weapon
    public boolean hitWall() {
        return cemeteryfuntimes.Resources.Shared.Collision.checkWallCollision(posVel.xPos,xLength/2,posVel.yPos,yLength/2) > 0;
    }
    
    public void draw(Graphics g) {
        g.setColor(PROJECTILECOLOR);
        g.drawRect(Math.round(xSide+posVel.xPos),Math.round(ySide+posVel.yPos),xLength,yLength);
    }
}