package cemeteryfuntimes.Resources.Shared;

// @author David Kozloff & Tyler Law

import cemeteryfuntimes.Code.*;
import java.util.ArrayList;
import java.util.Iterator;

public class Collision implements Globals {
    
    public static void checkCollisions(Player player, ArrayList<Enemy> enemies) {
        //Check for collisions between Player and player projectiles with enemies
        //As well as enemy and enemy projectile collision with player
        //Update accordingly
        checkBallisticCollisions(player,enemies);
        checkEnemyEnemyCollision(enemies);
        checkEnemyPlayerCollision(player,enemies);
    }
    
    public static void checkBallisticCollisions(Player player, ArrayList<Enemy> enemies) {
        Enemy enemy;
        Projectile projectile;
        Weapon playerWeapon = player.getWeapon();
        ArrayList<Projectile> projectiles = playerWeapon.Projectiles();
        int damage = playerWeapon.Damage();
        
        //Check player projectiles collision with enemies
        Iterator<Projectile> projectileIt = projectiles.iterator();
        while (projectileIt.hasNext()) {
            projectile =projectileIt.next();
            for (int j=0; j<enemies.size(); j++) {
                enemy = enemies.get(j);
                if (enemy.collide(projectile)) {
                    enemy.health -= damage;
                    if (enemy.health <= 0) {enemies.remove(j); j--;}
                    projectileIt.remove();
                }
            }
        }
        
        //Check enemy projectiles collision with player
    }
    
    public static void checkEnemyPlayerCollision(Player player, ArrayList<Enemy> enemies) {
        int sideCollided;
        for (Enemy enemy : enemies) {
            if (player.collide(enemy)) {
                //If the enemy and player have collided adjust the enemy's position to be on the edge of the player
                sideCollided = player.sideCollided(enemy);
                enemy.collide(sideCollided);
                player.enemyCollide(enemy,sideCollided);
            }
        }
    }
    
    public static void checkEnemyEnemyCollision(ArrayList<Enemy> enemies) {
        Enemy enemyOne;
        Enemy enemyTwo;
        for (int i=0; i < enemies.size(); i++) {
            enemyOne = enemies.get(i);
            for (int j=i+1; j < enemies.size(); j++) {
                enemyTwo = enemies.get(j);
                if (enemyOne.collide(enemyTwo)) {
                    enemyOne.collide(enemyOne.sideCollided(enemies.get(j)));
                }
            }
        }
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
