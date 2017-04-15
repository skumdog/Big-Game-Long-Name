package cemeteryfuntimes.Code;
import cemeteryfuntimes.Code.Shared.*;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
/**
* Room abstract class contains variables and methods related to rooms.
* @author David Kozloff & Tyler Law
*/
public abstract class Room implements Globals {
    protected final Player player;
    protected final Object[] neighbors;
    public boolean visited;
    
    //Static variables
    private BufferedImage doorClosed;
    private BufferedImage doorOpen;
    private final int doorHeight=100;
    private final int doorWidth=50;
    
    /**
    * Room class constructor initializes variables related to rooms.
    * 
    * @param player  The player.
    */
    public Room (Player player) {
        this.player = player;
        neighbors = new Object[4];
        setupImages();
    }
    /**
    * Updates the room.  Overridden by a specific room implementation.
    */
    public void update() {}
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
    * Determines if a room has been cleared, which is overridden by the
    * specific room type.
    * 
    * @return A boolean indicating if the room has been cleared.
    */
    public boolean RoomClear() {
        return true;
    }
    /**
    * Gets the neighboring room according to the given side.
    * 
    * @param  side The neighboring side.
    * @return      The neighboring room.
    */
    public Room GetNeighbor(int side) {
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
