package cemeteryfuntimes.Code;
import cemeteryfuntimes.Code.Shared.*;

// @author David Kozloff & Tyler Law

public final class Level implements Globals {
    
    private final Player player;
    private final int id;
    private Room currentRoom;
    public Room getCurrentRoom() {
        return currentRoom;
    }
    private Object[][] map;
    public Object[][] GetMap() {
        return map;
    }
    private int xCord;
    public int xCord() {
        return xCord;
    }
    private int yCord;
    public int yCord() {
        return yCord;
    }
    
    //Level creation constants
    private static final int totalRooms=10;
    private static final double roomCreationProb=1/3;
    private static final double doorCreationProb=1/3;
    private static final double noRoomProb=1/3;
    
    public Level(Player player, int levelID) {
        id = levelID;
        this.player = player;
        createMap();
        // TODO: Logic for retrieving specific level graph from parser.
    }
    
    private void createMap() {
        currentRoom = new Room(player,1); currentRoom.visited=true;
        map = new Object[5][5];
        int[][] intMap = new int[][] {
            {1,0,0,0,1},
            {1,1,0,1,1},
            {0,1,1,1,0},
            {1,1,0,1,1},
            {1,0,0,0,1}
        };
        /* TODO Add code to intelligently and randomly generate the map array
        while (numberRooms < totalRooms) {
            for (int i=0; i<4; i++) {
                
            }
        }*/
        createRooms(currentRoom,intMap, 2, 2);
        xCord = 2; yCord = 2;
    }
    
    private void createRooms(Room room, int[][] intMap, int x, int y) {
        int length = map.length-1;
        map[x][y] = room;
        Room neighbor;
        if (x > 0 && intMap[x-1][y] == 1 && room.GetNeighbor(LEFT) == null) {
            neighbor = new Room(player,1);
            neighbor.SetNeighbor(room,cemeteryfuntimes.Code.Shared.Utilities.otherSide(LEFT));
            room.SetNeighbor(neighbor, LEFT);
            createRooms(neighbor,intMap,x-1,y);
        }
        if (x < length && intMap[x+1][y] == 1 && room.GetNeighbor(RIGHT) == null) {
            neighbor = new Room(player,1);
            neighbor.SetNeighbor(room,cemeteryfuntimes.Code.Shared.Utilities.otherSide(RIGHT));
            room.SetNeighbor(neighbor, RIGHT);
            createRooms(neighbor,intMap,x+1,y);
        }
        if (y > 0 && intMap[x][y-1] == 1 && room.GetNeighbor(UP) == null) {
            neighbor = new Room(player,1);
            neighbor.SetNeighbor(room,cemeteryfuntimes.Code.Shared.Utilities.otherSide(UP));
            room.SetNeighbor(neighbor, UP);
            createRooms(neighbor,intMap,x,y-1);
        }
        if (y < length && intMap[x][y+1] == 1 && room.GetNeighbor(DOWN) == null) {
            neighbor = new Room(player,1);
            neighbor.SetNeighbor(room,cemeteryfuntimes.Code.Shared.Utilities.otherSide(DOWN));
            room.SetNeighbor(neighbor, DOWN);
            createRooms(neighbor,intMap,x,y+1);
        }
        //populateNeighbors();
    }
    
    /* TODO tried to set this up but got confused... not really sure if this is necessary
    Idea is to use this to replace the 4 if statements in createNodes, but not sure if it is worth it
    private boolean createNode(Node node, int x, int y, int side) {
        room neighbor;
        boolean horizontal = side == LEFT || side == RIGHT;
        if (horizontal) {
            if (x > 0 && x < map.length) { return false; }
            neighbor = new Node(1);
            neighbor.SetNeighbor(node,cemeteryfuntimes.Code.Shared.Utilities.otherSide(side));
            node.SetNeighbor(neighbor, side);
            createNodes(neighbor,x-1,y);
            return true;
        }
        else {
            if (y > 0 && y < map.length) { return false; }
        }
        return true;
    }*/
    
    private void populateNeighbors(Room room, int x, int y) {
        //TODO populate neighbors of a node correctly correctly
    }
    
}
