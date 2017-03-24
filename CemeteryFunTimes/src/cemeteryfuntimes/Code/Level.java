package cemeteryfuntimes.Code;
import cemeteryfuntimes.Code.Shared.*;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.Random;
/**
* Level class contains variables and methods related to levels.
* Levels are represented as two-dimensional arrays of rooms.
* @author David Kozloff & Tyler Law
*/

public final class Level implements Globals {
    
    private final Player player;
    private Room currentRoom;
    public Room getCurrentRoom() {
        return currentRoom;
    }
    private Object[][] map;
    public Object[][] GetMap() {
        return map;
    }
    private int[][] intMap;
    private int xCord;
    public int xCord() {
        return xCord;
    }
    private int yCord;
    public int yCord() {
        return yCord;
    }
    
    //Level creation constants
    private static final int totalRooms=15;
    private int numberOfRooms;
    private static final double roomCreationProb=1d/2d;
    private static final double doorCreationProb=1d/3d;
    private static final double noRoomProb=1d/3d;
    
    //Map drawing constants
    private final static int MAPPADDING=10;
    private final static int MAPBORDER=GAMEBORDER+GAMEWIDTH+MAPPADDING;
    private final static float MAPLENGTH=SCREENWIDTH-MAPBORDER-2*MAPPADDING;
    private final static int MAPELEMENT=(int)((MAPLENGTH-4*MAPPADDING)/3d);
    private final static int MAPDOORWIDTH=5;
    /**
    * Level constructor initializes variables related to levels.
    * 
    * @param player  The player.
    */
    public Level(Player player) {
        this.player = player;
        createMap();
        // TODO: Logic for retrieving specific level graph from parser.
    }
    /**
    * Initializes the level map.
    */
    private void createMap() {
        currentRoom = new Room(player); currentRoom.visited=true;
        map = new Object[5][5];
        intMap = new int[5][5];
        // TODO Add code to intelligently and randomly generate the map array
        createRooms(currentRoom, 2, 2);
        xCord = 2; yCord = 2;
    }
    /**
    * Create a room and assign it to a coordinate on the level map.
    * 
    * @param room   The room.
    * @param x      The x-coordinate of the room.
    * @param y      The y-coordinate of the room.
    */
    private void createRooms(Room room, int x, int y) {
        int length = map.length-1;
        numberOfRooms++;
        map[x][y] = room;
        intMap[x][y] = 1;
        if (numberOfRooms >= totalRooms) {return;}
        Random random = new Random();
        Room neighbor;
        if (x > 0 && intMap[x-1][y] != 1 && random.nextFloat() <= roomCreationProb ) {
            neighbor = new Room(player);
            neighbor.SetNeighbor(room,RIGHT);
            room.SetNeighbor(neighbor, LEFT);
            createRooms(neighbor,x-1,y);
        }
        if (x < length && intMap[x+1][y] != 1 && random.nextFloat() <= roomCreationProb ) {
            neighbor = new Room(player);
            neighbor.SetNeighbor(room,LEFT);
            room.SetNeighbor(neighbor, RIGHT);
            createRooms(neighbor,x+1,y);
        }
        if (y > 0 && intMap[x][y-1] != 1 && random.nextFloat() <= roomCreationProb ) {
            neighbor = new Room(player);
            neighbor.SetNeighbor(room,DOWN);
            room.SetNeighbor(neighbor, UP);
            createRooms(neighbor,x,y-1);
        }
        if (y < length && intMap[x][y+1] != 1 && random.nextFloat() <= roomCreationProb ) {
            neighbor = new Room(player);
            neighbor.SetNeighbor(room,UP);
            room.SetNeighbor(neighbor, DOWN);
            createRooms(neighbor,x,y+1);
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
    /**
     * Changes the current room and updates the new room to be visited.
     * 
     * @param side The direction of the new room.
     * @return If there is no neighbor on that side of current room return false.
     *         Else if room change succeeded return true.
     */
    public boolean changeRoom(int side) {
        Room newRoom = currentRoom.GetNeighbor(side);
        if (newRoom == null) {return false;}
        currentRoom = newRoom;
        currentRoom.visited = true;
        player.changeRoom(side);
        int[] horVert = Utilities.getHorizontalVertical(side);
        xCord = xCord + horVert[HORIZONTAL];
        yCord = yCord + horVert[VERTICAL];
        return true;
    }
    /**
    * Renders the map.
    * 
    * @param g The Graphics object used by Java to render everything in the game.
    */
    public void draw(Graphics2D g) {
        //Draw the level map in top right corner of screen
        Room mapRoom;
        int roomX; int roomY;
        int doorX; int doorY;
        Stroke oldStroke = g.getStroke();
        g.setStroke(new BasicStroke(3));
        Color mapBackground = new Color(255,255,255,127); //50% transparent white
        g.setColor(mapBackground);
        g.fillRect(MAPBORDER, MAPPADDING, (int) MAPLENGTH , (int) MAPLENGTH); 
        for (int x=-1;x<2;x++) {
            //Loop through all rooms centered around current room
            if (xCord + x >= 0 && xCord + x < map.length) {
                for (int y=-1;y<2;y++) {
                    if (yCord + y >= 0 && yCord + y < map.length) {
                        mapRoom = (Room) map[xCord+x][yCord+y];
                        if (mapRoom != null && mapRoom.visited) {
                            //Draw room
                            g.setColor(Color.BLACK);
                            roomX = MAPBORDER+(int)((x+1)*(MAPELEMENT+MAPPADDING))+MAPPADDING;
                            roomY = (int)((1+y)*(MAPPADDING+MAPELEMENT)+2*MAPPADDING);
                            g.drawRect(roomX,roomY,MAPELEMENT,MAPELEMENT);
                            //Draw doors of the current room
                            if (mapRoom.GetNeighbor(LEFT) != null) {
                                doorX = roomX - MAPPADDING;
                                doorY = roomY + (int)(MAPELEMENT/2d);
                                g.fillRect(doorX,doorY,MAPPADDING, MAPDOORWIDTH);
                            }
                            if (mapRoom.GetNeighbor(RIGHT) != null ) {
                                doorX = roomX + MAPELEMENT;
                                doorY = roomY + (int)(MAPELEMENT/2d);
                                g.fillRect(doorX,doorY,MAPPADDING, MAPDOORWIDTH);
                            }
                            if (mapRoom.GetNeighbor(UP) != null) {
                                doorX = roomX + (int)(MAPELEMENT/2d);
                                doorY = roomY - MAPPADDING;
                                g.fillRect(doorX,doorY,MAPDOORWIDTH,MAPPADDING);
                            }
                            if (mapRoom.GetNeighbor(DOWN) != null ) {
                                doorX = roomX + (int)(MAPELEMENT/2d);
                                doorY = roomY + MAPELEMENT;
                                g.fillRect(doorX,doorY,MAPDOORWIDTH,MAPPADDING);
                            }
                        }
                    }
                }
            }
        }
        g.setStroke(oldStroke);
    }
    
}
