package cemeteryfuntimes.Code.Weapons;

import cemeteryfuntimes.Code.Enemy;
import cemeteryfuntimes.Code.Shared.ImageLoader;
import cemeteryfuntimes.Code.Shared.PosVel;
import java.util.Random;
/**
* StandardProjectile class contains variables and methods related to standard projectiles, i.e. projectiles for Pistol, Shotgun, etc.
* @author David Kozloff & Tyler Law
*/

public class StandardProjectile extends Projectile {
    /**
    * StandardProjectile class constructor initializes variables related to standard projectiles.
    * 
    * @param posVel              The posVel firing the projectile.
    * @param direction           The direction in which the projectile was fired.
    * @param projectileImagePath The file path for the projectile image.
    * @param weapon              The weapon firing the projectile.
    */
    public StandardProjectile(PosVel posVel, int direction, String projectileImagePath, Weapon weapon) {
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
        this.projectileImage = ImageLoader.getImage(projectileImagePath, rotation);
        int xImagePad = projectileImage.getWidth()/2;
        int yImagePad = projectileImage.getHeight()/2;
        xSide = GAMEBORDER - xImagePad;
        ySide = -yImagePad;
        
    }
    /**
    * Initializes the projectile if the player is firing it.
    * 
    * @param posVel    The player object.
    * @param direction Direction projectile is fired in.
    * @param weapon    The player's weapon.
    */
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
    /**
     * Initializes projectiles if an enemy fired it.
     * 
     * @param posVel The enemy object.
     * @param weapon The enemy's weapon.
     */
    private void enemyWeaponInit(PosVel posVel, Weapon weapon) {
        Enemy enemy = (Enemy) posVel;
        float speed = weapon.ProjectileSpeed();
        //TODO use offset: int offset = weapon.ProjectileOffset();
        float playerRad = posVel.rad();
  
        //Create projectile
        //Decide starting position by player position and direction projectile is being fired
        //Decide velocity by adding PROJECTILESPEED with player velocity in that direction 
        
        xVel = PROJECTILEBOOST * posVel.xVel();
        yVel = PROJECTILEBOOST * posVel.yVel();
        if ( enemy.MovementType() != STDTOWARDPLAYER) {
            float xDist = enemy.Player().xPos() - posVel.xPos();
            float yDist = enemy.Player().yPos() - posVel.yPos();
            float totDist = (float) Math.sqrt(xDist*xDist + yDist*yDist);
            xVel += speed * xDist / totDist;
            yVel += speed * yDist / totDist;
        }
        else {
            //Can use enemy xVel and yVel as a vector pointing towards the player
            float enemySpeed = (float) Math.sqrt(posVel.xVel()*posVel.xVel()+posVel.yVel()*posVel.yVel());
            xVel += speed * posVel.xVel() / enemySpeed;
            yVel += speed * posVel.yVel() / enemySpeed;
        }
        handleSpread(weapon);
        //TODO properly set xPos and yPos based on velocity vector
        xPos = posVel.xPos();
        yPos = posVel.yPos();
    }
    /**
     * Handles the spread of bullets upon initialization.
     * 
     * @param weapon The weapon firing this projectile.
     */
    private void handleSpread(Weapon weapon) {
        //Calculates a random angle smaller than "spread".
        //Updates xVel and yVel and rotation to account for the spread.
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