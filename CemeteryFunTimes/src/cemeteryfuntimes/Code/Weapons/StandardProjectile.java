package cemeteryfuntimes.Code.Weapons;

import cemeteryfuntimes.Code.Shared.PosVel;

// @author David Kozloff & Tyler Law

import java.awt.image.BufferedImage;
import java.util.Random;

public class StandardProjectile extends Projectile {
    
    public StandardProjectile(PosVel posVel, int direction, BufferedImage projectileImage, Weapon weapon) {
        super(weapon);
        damage = weapon.Damage();
        rotation = posVel.rotation();
        if (weapon.EnemyWeapon()) {enemyWeaponInit(posVel,weapon);}
        else {playerWeaponInit(posVel,direction,weapon);}
        int height = weapon.ProjectileHeight();
        int width = weapon.ProjectileWidth();
        //TODO update xRad and yRad code when collision detection can handle rotated rectangle collisions
        xRad = height/2;
        yRad = width/2;
        this.projectileImage = cemeteryfuntimes.Code.Shared.Utilities.rotateImage(projectileImage, rotation);
        int xImagePad = projectileImage.getWidth()/2;
        int yImagePad = projectileImage.getHeight()/2;
        xSide -= xImagePad;
        ySide -= yImagePad;
        
    }
    
    private void playerWeaponInit(PosVel posVel, int direction, Weapon weapon) {
        float speed = weapon.ProjectileSpeed();
        int offset = weapon.ProjectileOffset();
        float playerRad = posVel.rad();
  
        //Create projectile
        //Decide starting position by player position and direction projectile is being fired
        //Decide velocity by adding PROJECTILESPEED with player velocity in that direction 
        int[] horVert = cemeteryfuntimes.Code.Shared.Utilities.getHorizontalVertical(direction);
        
        xPos = posVel.xPos() + horVert[HORIZONTAL] * playerRad - horVert[VERTICAL] * offset;
        yPos = posVel.yPos() + horVert[VERTICAL] * playerRad + horVert[HORIZONTAL] * offset;
        xVel = horVert[HORIZONTAL] * (PROJECTILEBOOST * posVel.xVel() + speed);
        yVel = horVert[VERTICAL] * (PROJECTILEBOOST * posVel.yVel() + speed);
        handleSpread(weapon);
    }
    
    private void enemyWeaponInit(PosVel posVel, Weapon weapon) {
        float speed = weapon.ProjectileSpeed();
        //TODO use offset: int offset = weapon.ProjectileOffset();
        float playerRad = posVel.rad();
  
        //Create projectile
        //Decide starting position by player position and direction projectile is being fired
        //Decide velocity by adding PROJECTILESPEED with player velocity in that direction 
        
        float enemySpeed = (float) Math.sqrt(posVel.xVel()*posVel.xVel()+posVel.yVel()*posVel.yVel());
        xVel =  PROJECTILEBOOST * posVel.xVel() + posVel.xVel()*speed/enemySpeed;
        yVel = PROJECTILEBOOST * posVel.yVel() + posVel.yVel()*speed/enemySpeed;
        handleSpread(weapon);
        //TODO properly set xPos and yPos based on velocity vector
        xPos = posVel.xPos();
        yPos = posVel.yPos();
    }
     
    private void handleSpread(Weapon weapon) {
         double spread = weapon.ProjectileSpread();
         if (spread != 0) {
            Random random = new Random();
            int sign = random.nextFloat() < 0.5 ? -1 : 1;
            spread = Math.toRadians(sign * random.nextFloat() * spread);
            //Rotates velocity vector by angle spread
            xVel = (float) (xVel * Math.cos(spread) - yVel * Math.sin(spread));
            yVel = (float) (xVel * Math.sin(spread) + yVel * Math.cos(spread));
            rotation += spread;
        }
     }
    
}