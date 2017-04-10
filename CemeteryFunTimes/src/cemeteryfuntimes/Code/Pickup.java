package cemeteryfuntimes.Code;
import cemeteryfuntimes.Code.Shared.PosVel;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
/**
* Pickup class contains variables and methods related to pickups.
* @author David Kozloff & Tyler Law
*/
public class Pickup extends PosVel {
    private final Player player;
    // Make this global?
    private BufferedImage heartContainer = null;
    private final static int HEARTSIZE = 40;
    /**
    * Pickup class constructor initializes variables related to pickups.
    * 
    * @param player The player.
    * @param x      The x-coordinate of the pickup.
    * @param y      The y-coordinate of the pickup.
    * @param type   The type of the pickup object, i.e. health, ammo, etc.
    */
    public Pickup (Player player, float x, float y, int type) {
        this.player = player;
        xPos = x;
        yPos = y;
        rad = HEARTSIZE/2; xRad = rad; yRad = rad;
        xSide = GAMEBORDER - rad;
        ySide = - rad;
        setupImages();
    }
    /**
    * Renders the pickup.
    * 
    * @param g The Graphics object used by Java to render everything in the game.
    */
    public void draw (Graphics g) {
        g.drawImage(heartContainer,(int)(xSide + xPos),(int)(ySide + yPos),null);
    }
    /**
    * Initializes BufferedImage objects, which are used to render images.
    */
    private void setupImages() {
       // Make this a shared method?
       heartContainer = cemeteryfuntimes.Code.Shared.Utilities.getScaledInstance(IMAGEPATH+"General/heart.png",HEARTSIZE,HEARTSIZE);
    }
}