package cemeteryfuntimes.Code.Rooms;
import cemeteryfuntimes.Code.Enemy;
import cemeteryfuntimes.Code.Pickup;
import cemeteryfuntimes.Code.Player;
import cemeteryfuntimes.Code.Shared.*;
import cemeteryfuntimes.Code.Weapons.Projectile;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
/**
* Room abstract class contains variables and methods related to rooms.
* @author David Kozloff & Tyler Law
*/
public abstract class Room implements Globals {
    protected final Player player;
    protected final Object[] neighbors;
    protected final ArrayList<Enemy> enemies;
    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }
    protected final ArrayList<Projectile> deadEnemyProjectiles;
    public ArrayList<Projectile> deadEnemyProjectiles() {
        return deadEnemyProjectiles;
    }
    protected final ArrayList<Pickup> pickups;
    public ArrayList<Pickup> getPickups() {
        return pickups;
    }
    public boolean visited;
    public final int type;
    private final Random randPickup;
    
     //Constants
    private final static float pickupSpawnProb = 1/3;
    
    /**
    * Room class constructor initializes variables related to rooms.
    * 
    * @param player  The player.
    */
    public Room (Player player, int type) {
        this.player = player;
        neighbors = new Object[4];
        this.type = type;
        enemies = new ArrayList();
        pickups = new ArrayList();
        deadEnemyProjectiles = new ArrayList();
        randPickup = new Random();
    }
    /**
    * Updates the room.  Overridden by a specific room implementation.
    */
    public void update() {
        Projectile projectile;
        for (int i=0; i<deadEnemyProjectiles.size(); i++) {
            projectile = deadEnemyProjectiles.get(i);
            if (projectile.collide) { deadEnemyProjectiles.remove(i); }
            else { projectile.update(); }
        }
        Enemy enemy;
        for (Iterator<Enemy> enemyIt = enemies.iterator(); enemyIt.hasNext();) {
            enemy = enemyIt.next();
            if (enemy.health <= 0) { 
                EnemyDead(enemy);
                enemyIt.remove(); 
                break;
            }
            enemy.update();
        }
    }
    /**
    * Renders room objects.  Overridden by a specific room implementation.
    * 
    * @param g The Graphics object used by Java to render everything in the game.
    */
    public void draw(Graphics2D g) {
        for (int i=0; i < enemies.size(); i++) {
            enemies.get(i).draw(g);
        }
        for (int i=0; i < pickups.size(); i++) {
            pickups.get(i).draw(g);
        }
        for (int i=0; i < deadEnemyProjectiles.size(); i++) {
            deadEnemyProjectiles.get(i).draw(g);
        }
    }
    /**
    * Determines if a room has been cleared, which is overridden by the
    * specific room type.
    * 
    * @return A boolean indicating if the room has been cleared.
    */
    public abstract boolean RoomClear();
    /**
    * Removes dead enemy frome the enemies array
    * Adds any remaining projectiles to deadEnemyProjectiles
    * Generates pickup if necessary
    * 
    * @param enemy The dead enemy
    */
    public void EnemyDead(Enemy enemy) {
        if (enemy.getWeapon() == null) { return; }
        deadEnemyProjectiles.addAll(enemy.getWeapon().Projectiles());
        if (randPickup.nextFloat() <= pickupSpawnProb) {
                    pickups.add(new Pickup(enemy.xPos(), enemy.yPos(), randPickup.nextInt(PICKUPTYPES)));
        }
    }
    /**
    * Gets the neighboring room according to the given side.
    * 
    * @param  side The neighboring side.
    * @return      The neighboring room.
    */
    public Room GetNeighbor(int side) {
        if (side < 0 || side > 3) { return null; }
        return (Room) neighbors[side];
    }
    /**
    * Sets a neighboring room according to the given room and side.
    * 
    * @param neighbor The neighboring room.
    * @param side     The neighboring side.
    */
    public void SetNeighbor(Room neighbor, int side) {
        neighbors[side] = neighbor;
    }
}
