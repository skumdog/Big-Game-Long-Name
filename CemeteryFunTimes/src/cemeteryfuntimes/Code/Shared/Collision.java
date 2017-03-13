package cemeteryfuntimes.Code.Shared;

// @author David Kozloff & Tyler Law

import cemeteryfuntimes.Code.Enemies.Enemy;
import cemeteryfuntimes.Code.Weapons.Projectile;
import cemeteryfuntimes.Code.Weapons.Weapon;
import cemeteryfuntimes.Code.*;
import java.util.ArrayList;
import java.util.Iterator;

public class Collision implements Globals {
    
    public static void checkCollisions(Player player, ArrayList<Enemy> enemies, ArrayList<Pickup> pickups) {
        //Check for collisions between Player and player projectiles with enemies
        //As well as enemy and enemy projectile collision with player
        //Also collisions with pickups / interactables and walls
        //Update accordingly
        checkBallisticCollisions(player,enemies);
        checkPlayerWallCollision(player);
        checkBallisticWallCollisions(player,enemies);
        checkEnemyEnemyCollision(enemies);
        checkEnemyPlayerCollision(player,enemies);
        checkPickupCollision(player,pickups);
    }
    
    public static int checkRoomClearCollisions(Player player) {
        ballisticWallCollisionLoop(player.getWeapon().Projectiles());
        boolean[] wall = checkPlayerWallCollision(player);
        return checkPlayerDoorCollision(player,wall);
    }
    
    public static void checkBallisticCollisions(Player player, ArrayList<Enemy> enemies) {
        Enemy enemy;
        Projectile projectile;
        Weapon playerWeapon = player.getWeapon();
        ArrayList<Projectile> projectiles = playerWeapon.Projectiles();
        float damage = playerWeapon.Damage();
        
        //Check player projectiles collision with enemies
        for (Iterator<Enemy> enemyIt = enemies.iterator(); enemyIt.hasNext();) {
            enemy = enemyIt.next();
            handleBallisticCollisions(projectiles,enemy);
            if (enemy.getWeapon() != null) {
                handleBallisticCollisions(enemy.getWeapon().Projectiles(),player);
            }
        }
    }
    
    public static void handleBallisticCollisions(ArrayList<Projectile> projectiles, PosVel target) {
        Projectile projectile;
        for (Iterator<Projectile> projectileIt = projectiles.iterator(); projectileIt.hasNext();) {
            projectile =projectileIt.next();
            if (target.collide(projectile)) {
                target.damaged(projectile.damage());
                projectile.collide = true;
            }
        }
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
        int[] horVert = cemeteryfuntimes.Code.Shared.Utilities.getHorizontalVertical(side);
        
        player.enemyCollide(enemy, horVert);
        
        //Adjust the position of the enemy to be right next to the player on the side of the collision
        //Also set the enemy's speed to 0 in the direction of the collision
        
        if (horVert[HORIZONTAL] != 0) {
            enemy.xPos = player.xPos() + horVert[HORIZONTAL] * (player.rad() + enemy.rad()); 
        }
        else {
            enemy.yPos =  player.yPos() + horVert[VERTICAL] * (player.rad() + enemy.rad()); 
        }
        enemy.xVel=0; 
        enemy.yVel=0; 
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
        int[] horVert = cemeteryfuntimes.Code.Shared.Utilities.getHorizontalVertical(side);
        
        //Calculate the overlapping region between the two enemies
        overlap = enemyOne.rad + enemyTwo.rad - Math.abs(horVert[HORIZONTAL] * (enemyOne.xPos - enemyTwo.xPos)) - Math.abs(horVert[VERTICAL] * (enemyOne.yPos - enemyTwo.yPos));
        
        //Update the position to no longer be overlapping
        enemyOne.xPos = enemyOne.xPos - horVert[HORIZONTAL] * overlap/2; 
        enemyTwo.xPos = enemyTwo.xPos + horVert[HORIZONTAL] * overlap/2; 
        enemyOne.yPos = enemyOne.yPos - horVert[VERTICAL] * overlap/2; 
        enemyTwo.yPos = enemyTwo.yPos + horVert[VERTICAL] * overlap/2; 
        /*
        //Update the velocities so they are no longer moving into each other
        enemyOne.xVel = Math.signum(enemyOne.xVel) * vertical * enemyOne.Speed();
        enemyTwo.xVel = Math.signum(enemyTwo.xVel) * vertical * enemyTwo.Speed();
        enemyOne.yVel = Math.signum(enemyOne.yVel) * horizontal * enemyOne.Speed();
        enemyTwo.yVel = Math.signum(enemyTwo.yVel) * horizontal * enemyTwo.Speed();
        */
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
    
    public static boolean[] checkPlayerWallCollision(Player player) {
        boolean[] wall=checkWallCollision(player);
        if (wall[RIGHT]) {
            player.xVel = 0;
            player.xPos = GAMEWIDTH - player.rad;
        }
        else if (wall[LEFT]) {
            player.xVel = 0;
            player.xPos = player.rad;
        }
        if (wall[UP]) {
            player.yVel = 0;
            player.yPos = player.rad;
        }
        else if (wall[DOWN]) {
            player.yVel = 0;
            player.yPos = GAMEHEIGHT - player.rad;
        }
        return wall;
    }
    
    public static int checkPlayerDoorCollision(Player player, boolean[] wall) {
        if (wall[RIGHT] && player.yPos <= GAMEHEIGHT/2 + 50 && player.yPos >= GAMEHEIGHT/2 - 50) {
            return RIGHT;
        }
        else if (wall[LEFT] && player.yPos <= GAMEHEIGHT/2 + 50 && player.yPos >= GAMEHEIGHT/2 - 50) {
            return LEFT;
        }
        else if (wall[UP] && player.xPos <= GAMEWIDTH/2 + 50 && player.xPos >= GAMEWIDTH/2 - 50) {
            return UP;
        }
        else if (wall[DOWN] && player.xPos <= GAMEWIDTH/2 + 50 && player.xPos >= GAMEWIDTH/2 - 50) {
            return DOWN;
        }
        return -1;
    }
    
    public static void checkBallisticWallCollisions(Player player, ArrayList<Enemy> enemies) {
        ballisticWallCollisionLoop(player.getWeapon().Projectiles());
        Weapon enemyWeapon;
        enemies.stream().forEach((Enemy enemy) -> {
            if (enemy.getWeapon() != null) {
                ballisticWallCollisionLoop(enemy.getWeapon().Projectiles());
            }
        });
    }
    
    public static void ballisticWallCollisionLoop(ArrayList<Projectile> projectiles) {
        Projectile projectile;
        for (Iterator<Projectile> projectileIt = projectiles.iterator(); projectileIt.hasNext();) {
            projectile =projectileIt.next();
            if (hitWall(projectile)) {
                projectile.collide = true;
            }
        }
    }
    
    public static boolean hitWall(PosVel posVel) {
        //Returns true if object has collided with a wall
        if (posVel.xPos + posVel.xRad > GAMEWIDTH) {
            return true;
        }
        else if (posVel.xPos - posVel.xRad < 0) {
            return true;
        }
        if (posVel.yPos + posVel.yRad > GAMEHEIGHT) {
            return true;
        }
        else if (posVel.yPos - posVel.yRad < 0) {
            return true;
        }
        return false;
    }
    
    public static boolean[] checkWallCollision(PosVel posVel) {
        //Returns an array of booleans indicating whether or not this object
        //has collided with that wall
        boolean[] wallsHit = new boolean[4];
        if (posVel.xPos + posVel.xRad > GAMEWIDTH) {
            wallsHit[RIGHTWALL]=true;
        }
        else if (posVel.xPos - posVel.xRad < 0) {
            wallsHit[LEFTWALL]=true;
        }
        if (posVel.yPos + posVel.yRad > GAMEHEIGHT) {
            wallsHit[BOTTOMWALL]=true;
        }
        else if (posVel.yPos - posVel.yRad < 0) {
            wallsHit[TOPWALL]=true;
        }
        return wallsHit;
    }
    
}
