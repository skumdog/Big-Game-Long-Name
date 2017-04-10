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
