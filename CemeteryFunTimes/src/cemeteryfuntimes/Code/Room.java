package cemeteryfuntimes.Code;
import cemeteryfuntimes.Code.Shared.Globals;
import java.util.ArrayList;

// @author David Kozloff & Tyler Law

public final class Room implements Globals {
    
    private final Player player;
    private final ArrayList<Enemy> enemies;
    private final ArrayList<Pickup> pickups;
    private final Object[] neighbors;
    private final int id;
    public boolean visited;
    
    //Constants
    private final static int HEARTSIZE=40;
    private final static int HEARTPADDING=10;

    public Room (Player player, int roomID) {
        id = roomID;
        this.player = player;
        enemies = new ArrayList();
        pickups = new ArrayList();
        neighbors = new Object[4];
        visited = false;
        
        // Test objects in the room.
        
        exampleRoom();
        
        // TODO: Logic for retrieving specific room data from parser.
    }
    
    public void exampleRoom() {
        //pickups.add(new Pickup(player, 10*(HEARTSIZE+HEARTPADDING)+HEARTPADDING, 10*HEARTPADDING, 0));
        //pickups.add(new Pickup(player, 15*(HEARTSIZE+HEARTPADDING)+HEARTPADDING, 15*HEARTPADDING, 0));
        //pickups.add(new Pickup(player, 12*(HEARTSIZE+HEARTPADDING)+HEARTPADDING, 18*HEARTPADDING, 0));
        enemies.add(new Enemy(player,GAMEWIDTH/2,GAMEHEIGHT/2,1));
        enemies.add(new Enemy(player,GAMEWIDTH/2,GAMEHEIGHT/2,1));
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public ArrayList<Pickup> getPickups() {
        return pickups;
    }
    
    public Room GetNeighbor(int side) {
        return (Room) neighbors[side];
    }
    
    public void SetNeighbor(Room neighbor, int side) {
        neighbors[side] = neighbor;
    }
    
}
