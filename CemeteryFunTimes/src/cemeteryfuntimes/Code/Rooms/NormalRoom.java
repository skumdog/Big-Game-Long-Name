package cemeteryfuntimes.Code.Rooms;
import cemeteryfuntimes.Code.Rooms.Room;
import cemeteryfuntimes.Code.Enemy;
import cemeteryfuntimes.Code.Pickup;
import cemeteryfuntimes.Code.Player;
import cemeteryfuntimes.Code.Shared.*;
import cemeteryfuntimes.Code.Spawn;
import cemeteryfuntimes.Code.Weapons.Projectile;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import org.w3c.dom.NamedNodeMap;
/**
* NormalRoom class contains variables and methods related to normal rooms.
* @author David Kozloff & Tyler Law
*/
public final class NormalRoom extends Room implements Globals {
    protected final int key;
    public final int Key() {
        return key;
    }
    private final ArrayList<Spawn> spawns;
    public ArrayList<Spawn> getSpawns() {
        return spawns;
    }
    private int numSpawns;
    public int getNumSpawns() {
        return numSpawns;
    }
    
    //Constants
    private final static float pickupSpawnProb = 1/3;
    
    /**
    * NormalRoom class constructor initializes variables related to normal rooms.
    * 
    * @param player  The player.
    */
    public NormalRoom (Player player) {
        super(player,NORMALROOM);
        int roomKey = new Random().nextInt(ROOMKEYS) + 1;
        this.key = roomKey;
        spawns = new ArrayList();
        loadRoom(roomKey);
    }
    /**
    * Updates the room.
    */
    @Override
    public void update() {
        for (int i=0; i<spawns.size(); i++) {
            spawns.get(i).update();
        }
        Projectile projectile;
        for (int i=0; i<deadEnemyProjectiles.size(); i++) {
            projectile = deadEnemyProjectiles.get(i);
            if (projectile.collide) { deadEnemyProjectiles.remove(i); }
            else { projectile.update(); }
        }
        Enemy enemy;
        Random random = new Random();
        for (Iterator<Enemy> enemyIt = enemies.iterator(); enemyIt.hasNext();) {
            enemy = enemyIt.next();
            if (enemy.health <= 0) { 
                if (random.nextFloat() <= pickupSpawnProb) {
                    pickups.add(new Pickup(enemy.xPos(), enemy.yPos(), random.nextInt(PICKUPTYPES)));
                }
                EnemyDead(enemy);
                enemyIt.remove(); 
                break;
            }
            enemy.update();
        }
    }
    /**
    *  
    * @param g 
    */
    @Override
    public void draw(Graphics2D g) {
        //Draw pickups and enemies
        for (int i=0; i<spawns.size(); i++) {
            spawns.get(i).draw(g);
        }
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
    * Determines if a room has been cleared, which is determined by the
    * specific room type.
    * 
    * @return A boolean indicating if the room has been cleared.
    */
    @Override
    public boolean RoomClear() {
        Boolean doneSpawning = false;
        int count = 0;
        for (int i=0; i<spawns.size(); i++) {
            if (spawns.get(i).getCurrentDifficulty() >= spawns.get(i).getMaxDifficulty()) {
                count++;
            }
        }
        return (enemies.isEmpty() && count==spawns.size());
    }
    /**
    * Helper method for loadRoom.  Generates an integer array of enemy keys
    * specifying which enemy types can be spawned at a given spawn point.
    * 
    * @param  enemyString   The Spawn#Enemies string given by the xml file.
    * @return enemyIntArray The integer array of enemy keys.
    */
    private int[] getSpawnEnemies(String enemyString) {
        int[] enemyIntArray;
        // The enemy string specifies specific enemies to spawn.
        if (!enemyString.isEmpty()) {
            String[] enemyStringArray = enemyString.split(",");
            enemyIntArray = new int[enemyStringArray.length];
            for (int i=0; i<enemyStringArray.length; i++) {
                enemyIntArray[i] = Integer.parseInt(enemyStringArray[i]);
            }
        // If the enemy string is empty, all standard enemies can be spawned.
        } else {
            enemyIntArray = new int[5];
            for (int i=0; i<5; i++) {
                enemyIntArray[i] = i+1;
            }
        }
        return enemyIntArray;
    }
    /**
    * Loads the room data for a specified room variant from an xml file.
    * 
    * @param roomKey The key corresponding to a specific room variant.
    */
    private void loadRoom(int roomKey) {
        NamedNodeMap attributes = cemeteryfuntimes.Code.Shared.Utilities.loadTemplate("Rooms.xml","Room",roomKey);
        numSpawns = Integer.parseInt(attributes.getNamedItem("NumSpawns").getNodeValue());
        int maxDifficulty = Integer.parseInt(attributes.getNamedItem("Difficulty1").getNodeValue());
        int spawnx = Integer.parseInt(attributes.getNamedItem("Spawn1x").getNodeValue());
        int spawny = Integer.parseInt(attributes.getNamedItem("Spawn1y").getNodeValue());
        int delay = Integer.parseInt(attributes.getNamedItem("Delay1").getNodeValue());
        String enemyString = attributes.getNamedItem("Spawn1Enemies").getNodeValue();
        int[] enemyIntArray = getSpawnEnemies(enemyString);
        spawns.add(new Spawn(this.player, this, spawnx, spawny, delay, maxDifficulty, enemyIntArray));
        if (numSpawns > 1) {
            maxDifficulty = Integer.parseInt(attributes.getNamedItem("Difficulty2").getNodeValue());
            spawnx = Integer.parseInt(attributes.getNamedItem("Spawn2x").getNodeValue());
            spawny = Integer.parseInt(attributes.getNamedItem("Spawn2y").getNodeValue());
            delay = Integer.parseInt(attributes.getNamedItem("Delay2").getNodeValue());
            enemyString = attributes.getNamedItem("Spawn2Enemies").getNodeValue();
            enemyIntArray = getSpawnEnemies(enemyString);
            spawns.add(new Spawn(this.player, this, spawnx, spawny, delay, maxDifficulty, enemyIntArray));
        }
        if (numSpawns > 2) {
            maxDifficulty = Integer.parseInt(attributes.getNamedItem("Difficulty3").getNodeValue());
            spawnx = Integer.parseInt(attributes.getNamedItem("Spawn3x").getNodeValue());
            spawny = Integer.parseInt(attributes.getNamedItem("Spawn3y").getNodeValue());
            delay = Integer.parseInt(attributes.getNamedItem("Delay3").getNodeValue());
            enemyString = attributes.getNamedItem("Spawn3Enemies").getNodeValue();
            enemyIntArray = getSpawnEnemies(enemyString);
            spawns.add(new Spawn(this.player, this, spawnx, spawny, delay, maxDifficulty, enemyIntArray));
        }
    }
}