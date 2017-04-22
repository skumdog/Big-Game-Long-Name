package cemeteryfuntimes.Code;
import cemeteryfuntimes.Code.Shared.*;
import java.awt.Graphics2D;
/**
* Room abstract class contains variables and methods related to rooms.
* @author David Kozloff & Tyler Law
*/
public abstract class Room implements Globals {
    protected final Player player;
    protected final Object[] neighbors;
    public boolean visited;
    /**
    * Room class constructor initializes variables related to rooms.
    * 
    * @param player  The player.
    */
    public Room (Player player) {
        this.player = player;
        neighbors = new Object[4];
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
    public abstract void draw(Graphics2D g);
    /**
    * Determines if a room has been cleared, which is overridden by the
    * specific room type.
    * 
    * @return A boolean indicating if the room has been cleared.
    */
    public abstract boolean RoomClear();
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
}
