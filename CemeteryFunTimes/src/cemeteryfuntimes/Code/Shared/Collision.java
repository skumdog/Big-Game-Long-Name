package cemeteryfuntimes.Code.Shared;
/**
* Collision class contains methods related to object collisions.
* @author David Kozloff & Tyler Law
*/

import cemeteryfuntimes.Code.Enemy;
import cemeteryfuntimes.Code.Weapons.Projectile;
import cemeteryfuntimes.Code.Weapons.Weapon;
import cemeteryfuntimes.Code.*;
import java.util.ArrayList;
import java.util.Iterator;

public class Collision implements Globals {
    
    /**
    * Collision class constructor calls all collision methods.
    * 
    * @param player  The player.
    * @param enemies The array list of enemies.
    * @param pickups The array list of pickups.
    */
    public static void checkCollisions(Player player, ArrayList<Enemy> enemies, ArrayList<Pickup> pickups) {
        //Check for collisions between Player and player projectiles with enemies
        //As well as enemy and enemy projectile collision with player
        //Also collisions with pickups / interactables and walls
        //Update accordingly
        checkBallisticCollisions(player,enemies);
        checkPlayerWallCollision(player);
        checkEnemyWallCollisions(enemies);
        checkBallisticWallCollisions(player,enemies);
        checkEnemyEnemyCollision(enemies);
        checkEnemyPlayerCollision(player,enemies);
        checkPickupCollision(player,pickups);
    }
    /**
     * Handles collisions upon room clear
     * 
     * @param player The player.
     * @return       An int corresponding to the door collided with, or -1 if no door was collided with.
     */
    public static int checkRoomClearCollisions(Player player) {
        ballisticWallCollisionLoop(player.getWeapon().Projectiles());
        boolean[] wall = checkPlayerWallCollision(player);
        return checkPlayerDoorCollision(player,wall);
    }
    /**
    * Checks for collisions between player projectiles and enemies.
    * 
    * @param player  The player.
    * @param enemies The array list of enemies.
    */
    private static void checkBallisticCollisions(Player player, ArrayList<Enemy> enemies) {
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
    /**
    * Handles ballistic collisions with both enemy and player.
    * 
    * @param projectiles Array of projectiles.
    * @param target      The PosVel to check if the projectiles collided with.
    */
    private static void handleBallisticCollisions(ArrayList<Projectile> projectiles, PosVel target) {
        Projectile projectile;
        for (Iterator<Projectile> projectileIt = projectiles.iterator(); projectileIt.hasNext();) {
            projectile =projectileIt.next();
            if (target.collide(projectile)) {
                target.damaged(projectile.damage());
                projectile.collide = true;
                break;
            }
        }
    }
    /**
    * Checks for collisions between the player and enemies.
    * 
    * @param player  The player.
    * @param enemies The array list of enemies.
    */
    private static void checkEnemyPlayerCollision(Player player, ArrayList<Enemy> enemies) {
        for (Enemy enemy : enemies) {
            if (player.collide(enemy)) {
                handleEnemyPlayerCollision(player, enemy);
            }
        }
    }
    /**
    * Handles collision between the player and an enemy.
    * 
    * @param player The player.
    * @param enemy  The array list of enemies.
    */
    private static void handleEnemyPlayerCollision(Player player, Enemy enemy) {
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
    /**
    * Checks for collisions between an enemy and other enemies.
    * 
    * @param enemies The array list of enemies.
    */
    private static void checkEnemyEnemyCollision(ArrayList<Enemy> enemies) {
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
    /**
    * Handles collisions between an enemy and another enemy.
    * 
    * @param enemyOne The first enemy.
    * @param enemyTwo The second enemy.
    */
    private static void handleEnemyEnemyCollision(Enemy enemyOne, Enemy enemyTwo) {
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
    }
    /**
    * Checks for collisions between the player and pickups.
    * 
    * @param player  The player.
    * @param pickups The array list of pickups.
    */
    private static void checkPickupCollision(Player player, ArrayList<Pickup> pickups) {
        for (int i = 0; i < pickups.size(); i++) {
            if (player.collide(pickups.get(i))) {
                // TODO: behavior for what to do upon receiving pickup
                pickups.remove(i); 
                i--;
            }
        }
    }
    /**
    * Checks for collisions between the player and doors.
    * 
    * @param player  The player.
    */
    private static int checkPlayerDoorCollision(Player player, boolean[] wall) {
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
    /**
    * Checks for collisions between projectiles and walls.
    * 
    * @param player  The player.
    * @param enemies The array list of enemies.
    */
    private static void checkBallisticWallCollisions(Player player, ArrayList<Enemy> enemies) {
        ballisticWallCollisionLoop(player.getWeapon().Projectiles());
        Weapon enemyWeapon;
        enemies.stream().forEach((Enemy enemy) -> {
            if (enemy.getWeapon() != null) {
                ballisticWallCollisionLoop(enemy.getWeapon().Projectiles());
            }
        });
    }
    /**
    * Sub-routine for checkBallisticWallCollisions.
    * Sets projectile.collide to true if the projectile is colliding with a wall.
    * 
    * @param projectiles The array list of projectiles.
    */
    private static void ballisticWallCollisionLoop(ArrayList<Projectile> projectiles) {
        Projectile projectile;
        for (Iterator<Projectile> projectileIt = projectiles.iterator(); projectileIt.hasNext();) {
            projectile =projectileIt.next();
            if (hitWall(projectile)) {
                projectile.collide = true;
            }
        }
    }
    /**
    * Handle enemy wall collisions.
    * 
    * @param enemies Arraylist of enemies.
    */
    private static void checkEnemyWallCollisions(ArrayList<Enemy> enemies) {
        for (Enemy enemy : enemies) {
            handleWallCollision(enemy,checkWallCollision(enemy));
        }
    }
    /**
    * Checks for collisions between the player and walls.
    * 
    * @param player  The player.
    * @return        Boolean[] telling which walls were collided with.
    */
    private static boolean[] checkPlayerWallCollision(Player player) {
        boolean[] wall=checkWallCollision(player);
        return handleWallCollision(player,wall);
    }
    /**
     * Handles player and enemy wall collisions.
     * 
     * @param posVel The posVel to handle the wall collision for.
     * @param wall   An array of booleans that tell you whether or not a wall was collided with.
     * @return       The wall that was collided with.
     */
    private static boolean[] handleWallCollision(PosVel posVel, boolean[] wall) {
        if (wall[RIGHT]) {
            posVel.xVel = 0;
            posVel.xPos = GAMEWIDTH - posVel.xRad;
        }
        else if (wall[LEFT]) {
            posVel.xVel = 0;
            posVel.xPos = posVel.xRad;
        }
        if (wall[UP]) {
            posVel.yVel = 0;
            posVel.yPos = posVel.yRad;
        }
        else if (wall[DOWN]) {
            posVel.yVel = 0;
            posVel.yPos = GAMEHEIGHT - posVel.yRad;
        }
        return wall;
    }
    /**
    * Returns true if the PosVel is colliding with a wall.
    * 
    * @param posVel The PosVel.
    * @return       True if the PosVel is colliding with a wall, false otherwise.
    */
    private static boolean hitWall(PosVel posVel) {
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
    /**
    * Returns an array of booleans indicating whether or not this PosVel is colliding with a specific wall.
    * 
    * @param posVel The PosVel.
    * @return       An array of booleans indicating whether or not this PosVel is colliding with a specific wall.
    */
    private static boolean[] checkWallCollision(PosVel posVel) {
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
