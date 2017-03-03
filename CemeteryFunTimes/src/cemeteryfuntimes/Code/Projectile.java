package cemeteryfuntimes.Code;

import cemeteryfuntimes.Code.Shared.PosVel;
import cemeteryfuntimes.Code.Shared.Globals;

// @author David Kozloff & Tyler Law

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Projectile extends PosVel implements Globals {
    
    private final BufferedImage projectileImage;
    private final int damage;
    public int damage() {
        return damage;
    }
    
    public Projectile(Player player, int direction, BufferedImage projectileImage, Weapon weapon) {
        float speed = weapon.ProjectileSpeed();
        int height = weapon.ProjectileHeight();
        int width = weapon.ProjectileWidth();
        int offset = weapon.ProjectileOffset();
        int rotation = 0;
        float playerRad = player.rad();
 
        //Create projectile
        //Decide starting position by player position and direction projectile is being fired
        //Decide velocity by adding PROJECTILESPEED with player velocity in that direction 
        switch( direction ) {
            case SHOOTLEFT:
                rotation = 0;
                xPos = player.xPos() - playerRad;
                yPos = player.yPos() - offset;
                xVel = player.xVel()*PROJECTILEPLAYERBOOST-speed;
                xRad = height/2;
                yRad = width/2;
                break;
            case SHOOTRIGHT:
                rotation = 180;
                xPos = player.xPos() + playerRad;
                yPos = player.yPos() + offset;
                xVel = player.xVel()*PROJECTILEPLAYERBOOST+speed;
                xRad = height/2;
                yRad = width/2;
                break;
            case SHOOTUP:
                rotation = 90;
                xPos = player.xPos() + offset;
                yPos = player.yPos() - playerRad;
                yVel = player.yVel()*PROJECTILEPLAYERBOOST-speed;
                xRad = width/2;
                yRad = height/2;
                break;
            case SHOOTDOWN:
                rotation = 270;
                xPos = player.xPos() - offset;
                yPos = player.yPos() + playerRad;
                yVel = player.yVel()*PROJECTILEPLAYERBOOST+speed;
                xRad = width/2;
                yRad = height/2;
                break;
        }
        xSide = GAMEBORDER - xRad;
        ySide = - yRad;
        this.projectileImage = cemeteryfuntimes.Code.Shared.Utilities.rotateImage(projectileImage, Math.toRadians(rotation));
        damage = weapon.Damage();
    }
    
    public void update() {
        xPos += xVel;
        yPos += yVel;
    }
    
    public void draw(Graphics2D g) {
        g.drawImage(projectileImage, Math.round(xSide+xPos), Math.round(ySide+yPos), null);
    }
    
}