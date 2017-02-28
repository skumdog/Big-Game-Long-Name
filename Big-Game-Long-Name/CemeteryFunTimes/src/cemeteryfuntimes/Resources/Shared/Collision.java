package cemeteryfuntimes.Resources.Shared;

// @author David Kozloff & Tyler Law

import cemeteryfuntimes.Code.*;
import java.util.ArrayList;

public class Collision implements Globals {
    
    public static void checkCollisions (Player player, ArrayList<Enemy> enemies, ArrayList<Pickup> pickups) {
        //Check for collisions between Player and player projectiles with enemies
        //As well as enemy and enemy projectile collision with player
        //Update accordingly
        
        Enemy enemy;
        Weapon playerWeapon = player.getWeapon();
        ArrayList<Projectile> projectiles = playerWeapon.Projectiles();
        
        //Check player projectiles collision with enemies
        for (int i=0; i<projectiles.size(); i++) {
            for (int j=0; j<enemies.size(); j++) {
                enemy = enemies.get(j);
                if (enemyProjectileCollide(enemy,projectiles.get(i))) {
                    enemy.health -= playerWeapon.Damage();
                    if (enemy.health <= 0) {
                        enemies.remove(j); j--;
                    }
                    projectiles.remove(i); i--;
                }
            }
        }
        
        // Updates upon the player receiving a pickup
        
        for (int i = 0; i < pickups.size(); i++) {
            if (playerPickupCollide (player,pickups.get(i))) {
                // TODO: behavior for what to do upon receiving pickup
                pickups.remove(i); 
                i--;
            }
        }
    }
    
    public static boolean playerPickupCollide (Player player, Pickup pickup) {
        // Check if the player walks over a pickup
        
        if (Math.abs (player.xPos - pickup.xPos + 180) < player.rad + pickup.rad) {
            if (Math.abs (player.yPos - pickup.yPos - 20) < player.rad + pickup.rad) {
                return true; 
            }
        }
        return false;
    }

    public static boolean enemyProjectileCollide (Enemy enemy, Projectile projectile) {
        // Check if a projectile hits an enemy return true if so
        
        if (Math.abs(projectile.xPos - enemy.xPos) < projectile.xRad + enemy.rad) {
            if (Math.abs(projectile.yPos - enemy.yPos) < projectile.yRad + enemy.rad) {
                return true;
            }
        }
        
        return false;
    }
    
    public static boolean hitWall(float xPos, float xRad, float yPos, float yRad) {
        //Returns true if object has collided with a wall
        if (xPos + xRad > GAMEWIDTH) {
            return true;
        }
        else if (xPos - xRad < 0) {
            return true;
        }
        if (yPos + yRad > GAMEHEIGHT) {
            return true;
        }
        else if (yPos - yRad < 0) {
            return true;
        }
        return false;
    }
    
    public static boolean[] checkWallCollision(float xPos, float xRad, float yPos, float yRad) {
        //Returns an array of booleans indicating whether or not this object
        //has collided with that wall
        boolean[] wallsHit = new boolean[4];
        if (xPos + xRad > GAMEWIDTH) {
            wallsHit[RIGHTWALL]=true;
        }
        else if (xPos - xRad < 0) {
            wallsHit[LEFTWALL]=true;
        }
        if (yPos + yRad > GAMEHEIGHT) {
            wallsHit[BOTTOMWALL]=true;
        }
        else if (yPos - yRad < 0) {
            wallsHit[TOPWALL]=true;
        }
        return wallsHit;
    }
    
}
