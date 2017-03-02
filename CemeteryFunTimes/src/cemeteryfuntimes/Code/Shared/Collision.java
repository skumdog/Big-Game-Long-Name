package cemeteryfuntimes.Code.Shared;

// @author David Kozloff & Tyler Law

import cemeteryfuntimes.Code.*;
import java.util.ArrayList;
import java.util.Iterator;

public class Collision implements Globals {
    
    public static void checkCollisions(Player player, ArrayList<Enemy> enemies, ArrayList<Pickup> pickups) {
        //Check for collisions between Player and player projectiles with enemies
        //As well as enemy and enemy projectile collision with player
        //Update accordingly
        checkBallisticCollisions(player,enemies);
        checkEnemyEnemyCollision(enemies);
        checkEnemyPlayerCollision(player,enemies);
        checkPickupCollision(player,pickups);
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
        for (Enemy enemy : enemies) {
            if (player.collide(enemy)) {
                handleEnemyPlayerCollision(player, enemy);
            }
        }
    }
    
    public static void handleEnemyPlayerCollision(Player player, Enemy enemy) {
        int side = player.sideCollided(enemy);
        // Positive is equal to 1 if player has the greater x or y coordinate on the side of the collision else -1
        int positive = (side == LEFTWALL || side == TOPWALL) ? 1 : -1;
        // Horizontal is equal to 1 if collision was on left or right wall else 0
        // same equivalent thing for vertical
        boolean horizontal = (side == LEFTWALL || side == RIGHTWALL);
        boolean vertical = (side == TOPWALL || side == BOTTOMWALL);
        
        //Adjust the position of the enemy to be right next to the player on the side of the collision
        //Also set the enemy's speed to 0 in the direction of the collision
        if (horizontal) {enemy.xPos = player.xPos() - positive * (player.rad() + enemy.rad()); enemy.xVel=0;}
        if (vertical) {enemy.yPos =  player.yPos() - positive * (player.rad() + enemy.rad()); enemy.yVel=0;}
    }
    
    public static void checkEnemyEnemyCollision(ArrayList<Enemy> enemies) {
        Enemy enemyOne;
        Enemy enemyTwo;
        int side;
        for (int i=0; i < enemies.size(); i++) {
            enemyOne = enemies.get(i);
            for (int j=i+1; j < enemies.size(); j++) {
                enemyTwo = enemies.get(j);
                if (enemyOne.collide(enemyTwo)) {
                    handleEnemyEnemyCollision(enemyOne, enemyTwo);
                }
            }
        }
    }
    
    public static void handleEnemyEnemyCollision(Enemy enemyOne, Enemy enemyTwo) {
        int side = enemyOne.sideCollided(enemyTwo);
        float overlap;
        // Horizontal is equal to 1 if collision was on left or right wall else 0
        // same equivalent thing for vertical
        int horizontal = (side == LEFTWALL || side == RIGHTWALL) ? 1 : 0;
        int vertical = (side == TOPWALL || side == BOTTOMWALL) ? 1 : 0;
        // Positive is equal to 1 if enemyOne has the greater x or y coordinate on the side of the collision else -1
        int positive = (side == LEFTWALL || side == TOPWALL) ? 1 : -1;
        
        //Calculate the overlapping region between the two enemies
        overlap = enemyOne.rad + enemyTwo.rad - Math.abs(horizontal * (enemyOne.xPos - enemyTwo.xPos)) - Math.abs(vertical * (enemyOne.yPos - enemyTwo.yPos));
        
        //Update the position to no longer be overlapping
        enemyOne.xPos = enemyOne.xPos + horizontal * (positive * overlap/2); 
        enemyTwo.xPos = enemyTwo.xPos - horizontal * (positive * overlap/2); 
        enemyOne.yPos = enemyOne.yPos + vertical * (positive * overlap/2); 
        enemyTwo.yPos = enemyTwo.yPos - vertical * (positive * overlap/2); 
        
        //Update the velocities so they are no longer moving into each other
        enemyOne.xVel = Math.signum(enemyOne.xVel) * vertical * enemyOne.Speed();
        enemyTwo.xVel = Math.signum(enemyTwo.xVel) * vertical * enemyTwo.Speed();
        enemyOne.yVel = Math.signum(enemyOne.yVel) * horizontal * enemyOne.Speed();
        enemyTwo.yVel = Math.signum(enemyTwo.yVel) * horizontal * enemyTwo.Speed();
    }
    
    public static void checkPickupCollision(Player player, ArrayList<Pickup> pickups) {
        for (int i = 0; i < pickups.size(); i++) {
            if (player.collide(pickups.get(i))) {
                // TODO: behavior for what to do upon receiving pickup
                pickups.remove(i); 
                i--;
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
