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
    private static final double roomCreationProb=1d/3d;
    private static final double noRoomProb=1d/3d;
    private static final int mapSize = 11;
    
    //Map drawing constants
    private final static int MAPPADDING=7;
    private final static int MAPBORDER=GAMEBORDER+GAMEWIDTH+MAPPADDING;
    private final static float MAPLENGTH=SCREENWIDTH-MAPBORDER-2*MAPPADDING;
    private final static int MAPELEMENTS=5;
    private final static int MAPELEMENT=(int)((MAPLENGTH-(MAPELEMENTS+1)*MAPPADDING)/MAPELEMENTS);
    private final static int MAPDOORWIDTH=5;
    private final static int MAPDOORPADDING=(int)(MAPELEMENT/2d-MAPDOORWIDTH/2d);
    private final static int doorHeight=100;
    private final static int doorWidth=50;
    private final static int[][] doorPadding={
        {-MAPPADDING,MAPDOORPADDING},
        {MAPELEMENT,MAPDOORPADDING},
        {MAPDOORPADDING,-MAPPADDING},
        {MAPDOORPADDING,-MAPPADDING}
    };
    /**
    * Level constructor initializes variables related to levels.
    * 
    * @param player  The player.
    */
    public Level(Player player) {
        //Load level images
        ImageLoader.loadImage("General/doorClosed.png",doorHeight,doorWidth);
        ImageLoader.loadImage("General/doorOpen.png",doorHeight,doorWidth);
        //Initialze map
        this.player = player;
        createMap();
    }
    /**
    * Initializes the level map.
    */
    private void createMap() {
        currentRoom = new Room(player, 1); 
        currentRoom.visited=true;
        map = new Object[mapSize][mapSize];
        intMap = new int[mapSize][mapSize];
        // TODO Add code to intelligently and randomly generate the map array
        map[mapSize/2][mapSize/2] = currentRoom;
        intMap[mapSize/2][mapSize/2] = 1;
        createRooms(currentRoom,mapSize/2,mapSize/2);
        xCord = mapSize/2; yCord = mapSize/2;
        int attempts = 0;
        do {
            for (int x=0; x<intMap.length; x++) 
                for (int y=0; y<intMap[0].length; y++) 
                    if (intMap[x][y] == 1) {
                        createRooms((Room)map[x][y],x,y);
                    }
            attempts++;
            if (attempts >= 20) {
                int q=6;
                for (int x=0; x<intMap.length; x++) 
                    for (int y=0; y<intMap[0].length; y++)
                            if (intMap[x][y] == -1) { intMap[x][y] = 0; }
                attempts=0;
            }
        }
        while (numberOfRooms < totalRooms);
        createSpecialRoom(BOSSROOM);
        createSpecialRoom(STOREROOM);
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
        if (x > 0 ) { checkAndCreateRoom(room,x,y,LEFT); }
        if (x < length ) { checkAndCreateRoom(room,x,y,RIGHT); }
        if (y > 0 ) { checkAndCreateRoom(room,x,y,UP); }
        if (y < length ) { checkAndCreateRoom(room,x,y,DOWN); }
    }
    /**
     * Helper routine for create room
     * @param room
     * @param x
     * @param y
     * @param side 
     */
    private void checkAndCreateRoom(Room room,int x, int y, int side) {
        Random random = new Random();
        int[] horVert = Utilities.getHorizontalVertical(side);
        if (intMap[x+horVert[HORIZONTAL]][y+horVert[VERTICAL]] == 0) {
            if (random.nextFloat() <= roomCreationProb) {
                createRoom(room,x+horVert[HORIZONTAL],y+horVert[VERTICAL],side);
            }
            else if (random.nextFloat() <= noRoomProb) {
                intMap[x+horVert[HORIZONTAL]][y+horVert[VERTICAL]] = -1;
            }
        }
    }
    /**
     * Helping routine for Create Rooms, which generates the new room
     * @param neighbor
     * @param x
     * @param y
     * @param side 
     */
    private void createRoom(Room neighbor, int x, int y, int side) {
        Room newRoom = new Room(player, 1);
        neighbor.SetNeighbor(newRoom,side);
        newRoom.SetNeighbor(neighbor,Utilities.otherSide(side));
        numberOfRooms++;
        intMap[x][y]=1;
        map[x][y]=newRoom;
    }
    private void createSpecialRoom(int type) {
        
    }
    /**
     * Changes the current room and updates the new room to be visited.
     * 
     * @param side The direction of the new room.
     * @return If there is no neighbor on that side of current room return false.
     *         Else if room change succeeded return true.
     */
    public boolean changeRoom(int side) {
        Room newRoom = currentRoom.GetNeighbor(side);
        if (newRoom == null) { return false; }
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
        int mapAdjust = MAPELEMENTS/2;
        Stroke oldStroke = g.getStroke();
        g.setStroke(new BasicStroke(3));
        Color mapBackground = new Color(255,255,255,127); //50% transparent white
        Color currentRoomBackground = new Color(255,255,0,127); //50% transparent yellow
        g.setColor(mapBackground);
        g.fillRect(MAPBORDER, MAPPADDING, (int) MAPLENGTH , (int) MAPLENGTH); 
        g.setColor(Color.BLACK);
        for (int x=-mapAdjust;x<=mapAdjust;x++) {
            //Loop through all rooms centered around current room
            if (xCord + x >= 0 && xCord + x < map.length) {
                for (int y=-mapAdjust;y<=mapAdjust;y++) {
                    if (yCord + y >= 0 && yCord + y < map.length) {
                        mapRoom = (Room) map[xCord+x][yCord+y];
                        if (mapRoom != null && mapRoom.visited) {
                            //Draw room
                            roomX = MAPBORDER+(int)((x+mapAdjust)*(MAPELEMENT+MAPPADDING))+MAPPADDING;
                            roomY = (int)((y+mapAdjust)*(MAPPADDING+MAPELEMENT)+2*MAPPADDING);
                            g.drawRect(roomX,roomY,MAPELEMENT,MAPELEMENT);
                            //Draw doors of the current room
                            if (mapRoom.GetNeighbor(LEFT) != null) {
                                doorX = roomX - MAPPADDING;
                                doorY = roomY + MAPDOORPADDING;
                                g.fillRect(doorX,doorY,MAPPADDING, MAPDOORWIDTH);
                            }
                            if (mapRoom.GetNeighbor(RIGHT) != null ) {
                                doorX = roomX + MAPELEMENT;
                                doorY = roomY + MAPDOORPADDING;
                                g.fillRect(doorX,doorY,MAPPADDING, MAPDOORWIDTH);
                            }
                            if (mapRoom.GetNeighbor(UP) != null) {
                                doorX = roomX + MAPDOORPADDING;
                                doorY = roomY - MAPPADDING;
                                g.fillRect(doorX,doorY,MAPDOORWIDTH,MAPPADDING);
                            }
                            if (mapRoom.GetNeighbor(DOWN) != null ) {
                                doorX = roomX + MAPDOORPADDING;
                                doorY = roomY + MAPELEMENT;
                                g.fillRect(doorX,doorY,MAPDOORWIDTH,MAPPADDING);
                            }
                        }
                    }
                }
            }
        }
        g.setColor(currentRoomBackground);
        roomX = MAPBORDER+(int)((mapAdjust)*(MAPELEMENT+MAPPADDING))+MAPPADDING;
        roomY = (int)((mapAdjust)*(MAPPADDING+MAPELEMENT)+2*MAPPADDING);
        g.fillRect(roomX,roomY,MAPELEMENT,MAPELEMENT);
        g.setStroke(oldStroke);
    }
    
}
