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
    private final static int HEARTSIZE = 40;
    private final int type;
    public int getType() {
        return type;
    }
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
        this.type = type;
        xPos = x;
        yPos = y;
        rad = HEARTSIZE/2; xRad = rad; yRad = rad;
        xSide = GAMEBORDER - rad;
        ySide = - rad;
    }
    /**
    * Renders the pickup.
    * 
    * @param g The Graphics object used by Java to render everything in the game.
    */
    public void draw (Graphics g) {
        // Draw a heart.
        if (this.type == 0) {
            BufferedImage heart = cemeteryfuntimes.Code.Shared.ImageLoader.getImage("General/heart.png",0);
            g.drawImage(heart,(int)(xSide + xPos),(int)(ySide + yPos),null);
        // Draw a coin.
        } else if (this.type == 1) {
            BufferedImage coin = cemeteryfuntimes.Code.Shared.ImageLoader.getImage("General/coin.png",0);
            g.drawImage(coin,(int)(xSide + xPos),(int)(ySide + yPos),null);
        }
    }
}
