package cemeteryfuntimes.Code;
import cemeteryfuntimes.Code.Shared.Globals;
import java.util.ArrayList;

// @author David Kozloff & Tyler Law

public final class Room implements Globals {
    
    private final Player player;
    private final ArrayList<Enemy> enemies;
    private final ArrayList<Pickup> pickups;
    private final ArrayList<Room> nextRooms;
    private final int roomID;
    private final int levelID;

    //Constants
    private final static int HEARTSIZE=40;
    private final static int HEARTPADDING=10;

    public Room (Player player, int room, int level) {
        roomID = room;
        levelID = level;
        this.player = player;
        enemies = new ArrayList();
        pickups = new ArrayList();
        nextRooms = new ArrayList();

        // Test room and neighboring rooms.

        exampleRoom();
        exampleNextRooms();
        
        // TODO: Logic for retrieving specific room data from parser.
    }
    
    public void exampleRoom() {
        pickups.add(new Pickup(player, 10*(HEARTSIZE+HEARTPADDING)+HEARTPADDING, 10*HEARTPADDING, 0));
        pickups.add(new Pickup(player, 15*(HEARTSIZE+HEARTPADDING)+HEARTPADDING, 15*HEARTPADDING, 0));
        pickups.add(new Pickup(player, 12*(HEARTSIZE+HEARTPADDING)+HEARTPADDING, 18*HEARTPADDING, 0));
        enemies.add(new Enemy(player,GAMEWIDTH/2,GAMEHEIGHT/2,1));
        enemies.add(new Enemy(player,GAMEWIDTH/2,GAMEHEIGHT/2,1));
    }
    
    public void exampleNextRooms() {
        this.nextRooms.add(0, null);
        this.nextRooms.add(1, null);
        if (roomID == 1) {
            this.nextRooms.add(2, new Room(player, 2, 1));
        } else {        
            this.nextRooms.add(2, null);
        }
        this.nextRooms.add(3, null);
    }
    
    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public ArrayList<Pickup> getPickups() {
        return pickups;
    }
}
