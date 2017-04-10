<<<<<<< HEAD
package cemeteryfuntimes.Code;
import cemeteryfuntimes.Code.Shared.*;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
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
    private final Object[] neighbors;
    public boolean visited;
    
    //Static variables
    private BufferedImage doorClosed;
    private BufferedImage doorOpen;
    private final int doorHeight=100;
    private final int doorWidth=50;
    
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
        neighbors = new Object[4];
        currentDifficulty = 0;
        loadRoom(roomKey);
        setupImages();
        
        // Test objects in the room.
                
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
    * Renders the doors in the room.
    * 
    * @param g The Graphics object used by Java to render everything in the game.
    */
    public void draw(Graphics2D g) {
        boolean doorsOpen = RoomClear();
        BufferedImage sourceDoor = RoomClear() ? doorOpen : doorClosed;
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
        // Check to see if more enemies should be spawned.
        if (currentDifficulty < maxDifficulty) {
            // Make sure enough time has passed to spawn a new enemy.
            long now = System.currentTimeMillis();
            if (now - lastUpdate < delay) {
                return;
            }
            lastUpdate = now;
            
            Random random = new Random();
            int type = random.nextInt(4) + 1;
            // Spawn a new enemy.
            Enemy newEnemy = new Enemy(player,spawnx,spawny,type);
            enemies.add(newEnemy);
            currentDifficulty += newEnemy.Difficulty();
        }
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
    * Initializes BufferedImage objects, which are used to render images.
    */
    private void setupImages() {
       //Initialize always relevent images images
       doorClosed = Utilities.getScaledInstance(IMAGEPATH+"General/doorClosed.png",doorHeight,doorWidth);
       doorOpen = Utilities.getScaledInstance(IMAGEPATH+"General/doorOpen.png",doorHeight,doorWidth);
       //halfHeartContainer = cemeteryfuntimes.Resources.Shared.Other.getScaledInstance("General/halfHeart.png",HEARTSIZE/2,HEARTSIZE);
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
=======
package cemeteryfuntimes.Code;
import cemeteryfuntimes.Code.Shared.*;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
/**
* Room class contains variables and methods related to rooms.
* @author David Kozloff & Tyler Law
*/
public final class Room implements Globals {
    
    private final Player player;
    private final ArrayList<Enemy> enemies;
    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }
    private final ArrayList<Pickup> pickups;
    public ArrayList<Pickup> getPickups() {
        return pickups;
    }
    private final Object[] neighbors;
    public boolean visited;
    
    //Static variables
    private BufferedImage doorClosed;
    private BufferedImage doorOpen;
    private final int doorHeight=100;
    private final int doorWidth=50;
    
    //Constants
    private final static int HEARTSIZE=40;
    private final static int HEARTPADDING=10;
    /**
    * Room class constructor initializes variables related to rooms.
    * 
    * @param player The player.
    */
    public Room (Player player) {
        this.player = player;
        enemies = new ArrayList();
        pickups = new ArrayList();
        neighbors = new Object[4];
        
        setupImages();
        
        // Test objects in the room.
        
        exampleRoom();
        
        // TODO: Logic for retrieving specific room data from parser.
    }
    
    public void exampleRoom() {
        //pickups.add(new Pickup(player, 10*(HEARTSIZE+HEARTPADDING)+HEARTPADDING, 10*HEARTPADDING, 0));
        //pickups.add(new Pickup(player, 15*(HEARTSIZE+HEARTPADDING)+HEARTPADDING, 15*HEARTPADDING, 0));
        //pickups.add(new Pickup(player, 12*(HEARTSIZE+HEARTPADDING)+HEARTPADDING, 18*HEARTPADDING, 0));
        enemies.add(new Enemy(player,GAMEWIDTH/2,GAMEHEIGHT/2,ZOMBIE));
        //enemies.add(new MeleeEnemy(player,GAMEWIDTH/2,GAMEHEIGHT/2,BAT));
        enemies.add(new Enemy(player,GAMEWIDTH/2,GAMEHEIGHT/2,BLOATER));
        enemies.add(new Enemy(player,GAMEWIDTH/2,GAMEHEIGHT/2,CULTIST));
    }
    /**
    * Renders the doors in the room.
    * 
    * @param g The Graphics object used by Java to render everything in the game.
    */
    public void draw(Graphics2D g) {
        boolean doorsOpen = RoomClear();
        BufferedImage sourceDoor = RoomClear() ? doorOpen : doorClosed;
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
    
    public void update() {
        
    }
    
    public boolean RoomClear() {
        return enemies.isEmpty();
    }
    
    public Room GetNeighbor(int side) {
        return (Room) neighbors[side];
    }
    
    public void SetNeighbor(Room neighbor, int side) {
        neighbors[side] = neighbor;
    }
    /**
    * Initializes BufferedImage objects, which are used to render images.
    */
    private void setupImages() {
       //Initialize always relevent images images
       doorClosed = Utilities.getScaledInstance(IMAGEPATH+"General/doorClosed.png",doorHeight,doorWidth);
       doorOpen = Utilities.getScaledInstance(IMAGEPATH+"General/doorOpen.png",doorHeight,doorWidth);
       //halfHeartContainer = cemeteryfuntimes.Resources.Shared.Other.getScaledInstance("General/halfHeart.png",HEARTSIZE/2,HEARTSIZE);
    }
    
}
>>>>>>> parent of 3b4e279... ladies
