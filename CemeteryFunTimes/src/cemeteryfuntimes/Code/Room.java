package cemeteryfuntimes.Code;
import cemeteryfuntimes.Code.Shared.Globals;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

// @author David Kozloff & Tyler Law

public final class Room implements Globals {
    
    private final Player player;
    private final ArrayList<Enemy> enemies;
    private final ArrayList<Pickup> pickups;
    private final Object[] neighbors;
    private final int id;
    public boolean visited;
    
    //Static variables
    private BufferedImage doorClosed;
    private BufferedImage doorOpen;
    private final int doorHeight=100;
    private final int doorWidth=50;
    
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
        enemies.add(new Enemy(player,GAMEWIDTH/2,GAMEHEIGHT/2,BAT));
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public ArrayList<Pickup> getPickups() {
        return pickups;
    }
    
    public void draw(Graphics2D g) {
        boolean doorsOpen = RoomClear();
        BufferedImage sourceDoor = RoomClear() ? doorOpen : doorClosed;
        BufferedImage door;
        if (GetNeighbor(LEFT) != null) {
            door = sourceDoor;
            g.drawImage(door, GAMEBORDER - door.getWidth()/2, GAMEHEIGHT/2 - door.getHeight()/2 , null);
        }
        if (GetNeighbor(RIGHT) != null) {
            door = cemeteryfuntimes.Code.Shared.Utilities.rotateImage(sourceDoor, ROTATION[RIGHT]);
            g.drawImage(door, SCREENWIDTH - GAMEBORDER - door.getWidth()/2, GAMEHEIGHT/2 - door.getHeight()/2 , null);
        }
        if (GetNeighbor(UP) != null) {
            door = cemeteryfuntimes.Code.Shared.Utilities.rotateImage(sourceDoor, ROTATION[UP]);
            g.drawImage(door, GAMEBORDER + GAMEWIDTH/2 - door.getWidth()/2, - door.getHeight()/2 , null);
        }
        if (GetNeighbor(DOWN) != null) {
            door = cemeteryfuntimes.Code.Shared.Utilities.rotateImage(sourceDoor, ROTATION[DOWN]);
            g.drawImage(door, GAMEBORDER + GAMEWIDTH/2 - door.getWidth()/2, GAMEHEIGHT - door.getHeight()/2 , null);
        }
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
    
    private void setupImages() {
       //Initialize always relevent images images
       doorClosed = cemeteryfuntimes.Code.Shared.Utilities.getScaledInstance(IMAGEPATH+"General/doorClosed.png",doorHeight,doorWidth);
       doorOpen = cemeteryfuntimes.Code.Shared.Utilities.getScaledInstance(IMAGEPATH+"General/doorOpen.png",doorHeight,doorWidth);
       //halfHeartContainer = cemeteryfuntimes.Resources.Shared.Other.getScaledInstance("General/halfHeart.png",HEARTSIZE/2,HEARTSIZE);
    }
    
}
