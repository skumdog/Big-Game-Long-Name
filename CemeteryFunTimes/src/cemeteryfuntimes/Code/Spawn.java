package cemeteryfuntimes.Code;
import java.awt.image.BufferedImage;
import java.util.Random;
/**
* Spawn class contains variables and methods related to enemy spawns.
* @author David Kozloff & Tyler Law
*/
public class Spawn {
    private final int xPos;
    private final int yPos;
    private final int delay;
    private int currentDifficulty;
    public int getCurrentDifficulty() {
        return currentDifficulty;
    }
    private final int maxDifficulty;
    public int getMaxDifficulty() {
        return maxDifficulty;
    }
    private final int[] enemyArray;
    private long lastUpdate;
    private final Player player;
    private final NormalRoom room;
    private BufferedImage spawnImage;
    /**
    * Spawn class constructor initializes variables related to spawns.
    * 
    * @param player      The player.
    * @param room        The room containing this spawn.
    * @param x           The x-coordinate of the spawn point.
    * @param y           The y-coordinate of the spawn point.
    * @param delay       The time delay between each spawn.
    * @param max         The maximum difficulty value of the spawn point.
    * @param enemyArray  The array of enemy keys specifying which enemies can
    *                    be spawned at this spawn point.
    */
    public Spawn(Player player, NormalRoom room, int x, int y, int delay, int max, int[] enemyArray) {
        this.xPos = x;
        this.yPos = y;
        this.delay = delay;
        this.maxDifficulty = max;
        this.currentDifficulty = 0;
        this.room = room;
        this.player = player;
        this.enemyArray = enemyArray;
    }
    /**
    * Updates the spawn, spawning a new enemy if necessary.
    */
    public void update() {
        // Check to see if more enemies should be spawned.
        if (this.currentDifficulty < this.maxDifficulty) {
            // Make sure enough time has passed to spawn a new enemy.
            long now = System.currentTimeMillis();
            if (now - lastUpdate < delay) {
                return;
            }
            lastUpdate = now;
            
            Random random = new Random();
            int index = random.nextInt(this.enemyArray.length);
            int type = this.enemyArray[index];
            // Spawn a new enemy.
            Enemy newEnemy = new Enemy(this.player, this.xPos, this.yPos, type);
            this.room.getEnemies().add(newEnemy);
            this.currentDifficulty += newEnemy.Difficulty();
        }
    }
}
