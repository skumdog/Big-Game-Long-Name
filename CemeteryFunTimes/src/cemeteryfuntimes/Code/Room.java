package cemeteryfuntimes.Code;
import cemeteryfuntimes.Code.Shared.*;
import cemeteryfuntimes.Code.Weapons.Projectile;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import org.w3c.dom.NamedNodeMap;
import java.util.Random;
/**
* Room class contains variables and methods related to rooms.
* @author David Kozloff & Tyler Law
*/
public final class Room implements Globals {
    private int maxDifficulty;
    public int MaxDifficulty() {
        return maxDifficulty;
    }
    private int currentDifficulty;
    public int CurrentDifficulty() {
        return currentDifficulty;
    }
    private int spawnx;
    public int Spawnx() {
        return spawnx;
    }
    private int spawny;
    public int Spawny() {
        return spawny;
    }
    private int delay;
    public int Delay() {
        return delay;
    }
    private final int key;
    public final int Key() {
        return key;
    }
    private final int type;
    public final int Type() {
        return type;
    }
    private long lastUpdate;
    private final Player player;
    private final ArrayList<Enemy> enemies;
    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }
    private final ArrayList<Pickup> pickups;
    public ArrayList<Pickup> getPickups() {
        return pickups;
    }
    private final ArrayList<Projectile> deadEnemyProjectiles;
    public ArrayList<Projectile> deadEnemyProjectiles() {
        return deadEnemyProjectiles;
    }
    
    private final Object[] neighbors;
    public boolean visited;
    
    //Constants
    private final static int HEARTSIZE=40;
    private final static int HEARTPADDING=10;
    /**
    * Room class constructor initializes variables related to rooms.
    * 
    * @param player  The player.
    * @param roomKey The key corresponding to a specific room type.
    */
    public Room (Player player, int roomKey) {
        this.player = player;
        key = roomKey;
        enemies = new ArrayList();
        pickups = new ArrayList();
        deadEnemyProjectiles = new ArrayList();
        neighbors = new Object[4];
        currentDifficulty = 0;
        loadRoom(roomKey);
        type = NORMALROOM;
        // TODO: Logic for retrieving specific room data from parser.
    }
    /**
    * Populates the room with initial enemies. Enemies and pickups are only 
    * generated if the player has not visited this room before.
    */
    public void populateRoom() {
        pickups.add(new Pickup(player, 10*(HEARTSIZE+HEARTPADDING)+HEARTPADDING, 10*HEARTPADDING, 1));
//        pickups.add(new Pickup(player, 15*(HEARTSIZE+HEARTPADDING)+HEARTPADDING, 15*HEARTPADDING, 0));
//        pickups.add(new Pickup(player, 12*(HEARTSIZE+HEARTPADDING)+HEARTPADDING, 18*HEARTPADDING, 0));
        enemies.add(new Enemy(player,spawnx,spawny,ZOMBIE));
        enemies.add(new Enemy(player,spawnx,spawny,BAT));
//        enemies.add(new Enemy(player,spawn1x,spawn1y,BLOATER));
//        enemies.add(new Enemy(player,spawn1x,spawn1y,CULTIST));
        enemies.stream().forEach((enemy) -> {
            currentDifficulty += enemy.Difficulty();
        });
    }
    /**
    * Renders everything contained in the room
    * 
    * @param g The Graphics object used by Java to render everything in the game.
    */
    public void draw(Graphics2D g) {
        //Draw pickups and enemies
        for (int i=0; i < enemies.size(); i++) {
            enemies.get(i).draw(g);
        }
        for (int i=0; i < pickups.size(); i++) {
            pickups.get(i).draw(g);
        }
        for (int i=0; i < deadEnemyProjectiles.size(); i++) {
            deadEnemyProjectiles.get(i).draw(g);
        }
        //Draw the doors of the room
        boolean doorsOpen = RoomClear();
        
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
    * Updates the room, spawning new enemies if necessary.
    */
    public void update() {
        // Update dead enemy projectiles
        Projectile projectile;
        for (Iterator<Projectile> projectileIt = deadEnemyProjectiles.iterator(); projectileIt.hasNext();) {
            projectile = projectileIt.next();
            if (projectile.collide) { projectileIt.remove(); break; }
            projectile.update();
        }
        // Check to see if more enemies should be spawned.
        if (currentDifficulty < maxDifficulty) {
            // Make sure enough time has passed to spawn a new enemy.
            long now = System.currentTimeMillis();
            if (now - lastUpdate < delay) {
                return;
            }
            lastUpdate = now;
            
            Random random = new Random();
            int type = random.nextInt(5) + 1;
            // Spawn a new enemy.
            Enemy newEnemy = new Enemy(player,spawnx,spawny,type);
            enemies.add(newEnemy);
            currentDifficulty += newEnemy.Difficulty();
        }
    }
    
    public void EnemyDead(Enemy enemy) {
        if (enemy.getWeapon() == null) { return; }
        deadEnemyProjectiles.addAll(enemy.getWeapon().Projectiles());
    }
    
    public boolean RoomClear() {
        return (enemies.isEmpty() && (currentDifficulty >= maxDifficulty));
    }
    
    public Room GetNeighbor(int side) {
        return (Room) neighbors[side];
    }
    
    public void SetNeighbor(Room neighbor, int side) {
        neighbors[side] = neighbor;
    }
    /**
    * Loads the room data for a specified room variant from an xml file.
    * 
    * @param roomKey The key corresponding to a specific room type.
    */
    public void loadRoom(int roomKey) {
        NamedNodeMap attributes = cemeteryfuntimes.Code.Shared.Utilities.loadTemplate("Rooms.xml","Room",roomKey);
        maxDifficulty = Integer.parseInt(attributes.getNamedItem("Difficulty").getNodeValue());
        spawnx = Integer.parseInt(attributes.getNamedItem("Spawnx").getNodeValue());
        spawny = Integer.parseInt(attributes.getNamedItem("Spawny").getNodeValue());
        delay = Integer.parseInt(attributes.getNamedItem("Delay").getNodeValue());
    }
}