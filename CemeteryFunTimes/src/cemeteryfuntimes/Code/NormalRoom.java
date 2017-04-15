package cemeteryfuntimes.Code;
import cemeteryfuntimes.Code.Shared.*;
import java.util.ArrayList;
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
    private final ArrayList<Enemy> enemies;
    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }
    private final ArrayList<Pickup> pickups;
    public ArrayList<Pickup> getPickups() {
        return pickups;
    }
    private final ArrayList<Spawn> spawns;
    public ArrayList<Spawn> getSpawns() {
        return spawns;
    }
    private int numSpawns;
    public int getNumSpawns() {
        return numSpawns;
    }
    /**
    * NormalRoom class constructor initializes variables related to normal rooms.
    * 
    * @param player  The player.
    * @param roomKey The key corresponding to a specific room type.
    */
    public NormalRoom (Player player, int roomKey) {
        super(player);
        this.key = roomKey;
        enemies = new ArrayList();
        pickups = new ArrayList();
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
        if (count == spawns.size()) {
            doneSpawning = true;
        }
        return (enemies.isEmpty() && doneSpawning);
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
    public void loadRoom(int roomKey) {
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