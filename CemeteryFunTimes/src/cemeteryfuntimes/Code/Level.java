package cemeteryfuntimes.Code;
import cemeteryfuntimes.Code.Shared.*;
import java.util.ArrayList;

// @author David Kozloff & Tyler Law

public final class Level implements Globals {
    
    private final Player player;
    private final int id;
    private final Node<Room> start;
    
    public Level(Player player, int levelID) {
        id = levelID;
        this.player = player;
        
        // Root node.
        
        start = new Node<>();
        
        // The starting room of the level, the payload for the root node.
        
        start.room = new Room(player, 0);
        
        // A test node representing a neighboring room above the starting room.
        
        Node anotherNode = new Node<>();
        anotherNode.room = new Room(player, 0);
        anotherNode.nextRooms = null;
        
        // These four nodes represent rooms connected to the starting room.
        // If the node is set to null, that neighbor doesn't exist.
        // Otherwise, the node will contain a neighboring room.
        //
        // The index specifies where the next room is:
        // 0 - Left, 1 - Right, 2 - Up, 3 - Down

        start.nextRooms = new ArrayList<>();
        start.nextRooms.add(0, null);
        start.nextRooms.add(1, null);
        start.nextRooms.add(2, anotherNode);
        start.nextRooms.add(3, null);
        
        // TODO: Logic for retrieving specific level graph from parser.
    }
    
    // Level is represented as an undirected graph of rooms.
    
    public class Node<Room> {
        public Room room;
        private ArrayList<Node<Room>> nextRooms;
    }

    public Node<Room> getStartNode() {
        return start;
    }
}
