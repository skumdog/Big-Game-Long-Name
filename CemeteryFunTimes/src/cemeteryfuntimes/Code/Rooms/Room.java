package cemeteryfuntimes.Code.Rooms;
import cemeteryfuntimes.Code.Enemy;
import cemeteryfuntimes.Code.Pickup;
import cemeteryfuntimes.Code.Player;
import cemeteryfuntimes.Code.Shared.*;
import static cemeteryfuntimes.Code.Shared.Globals.*;
import cemeteryfuntimes.Code.Weapons.Projectile;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
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
    }
    /**
    * Updates the room.  Overridden by a specific room implementation.
    */
    public abstract void update();
    /**
    * Renders room objects.  Overridden by a specific room implementation.
    * 
    * @param g The Graphics object used by Java to render everything in the game.
    */
    public void draw(Graphics2D g) {
        //Draw the doors of the room
        BufferedImage sourceDoor = RoomClear() ? ImageLoader.getImage("General/doorOpen.png",0) : ImageLoader.getImage("General/doorClosed.png",0);
        BufferedImage door;
        if (GetNeighbor(LEFT) != null) {
            door = sourceDoor;
            g.drawImage(door, GAMEBORDER - door.getWidth()/2, GAMEHEIGHT/2 - door.getHeight()/2 , null);
        }
        if (GetNeighbor(RIGHT) != null) {
            door = Utilities.rotateImage(sourceDoor, ROTATION[RIGHT]);
            g.drawImage(door, SCREENWIDTH - GAMEBORDER - door.getWidth()/2, GAMEHEIGHT/2 - door.getHeight()/2 , null);
        }
        if (GetNeighbor(UP) != null) {
            door = Utilities.rotateImage(sourceDoor, ROTATION[UP]);
            g.drawImage(door, GAMEBORDER + GAMEWIDTH/2 - door.getWidth()/2, - door.getHeight()/2 , null);
        }
        if (GetNeighbor(DOWN) != null) {
            door = Utilities.rotateImage(sourceDoor, ROTATION[DOWN]);
            g.drawImage(door, GAMEBORDER + GAMEWIDTH/2 - door.getWidth()/2, GAMEHEIGHT - door.getHeight()/2 , null);
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
    * 
    * @param enemy The dead enemy
    */
    public void EnemyDead(Enemy enemy) {
        if (enemy.getWeapon() == null) { return; }
        deadEnemyProjectiles.addAll(enemy.getWeapon().Projectiles());
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
