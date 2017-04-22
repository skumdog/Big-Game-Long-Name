package cemeteryfuntimes.Code;
import cemeteryfuntimes.Code.Shared.Globals;
import cemeteryfuntimes.Code.Shared.PosVel;
import cemeteryfuntimes.Code.Shared.Utilities;
import cemeteryfuntimes.Code.Weapons.Weapon;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
/**
* Boss abstract class contains variables and methods related to boss enemies.
* @author David Kozloff & Tyler Law
*/
public abstract class Boss extends PosVel implements Globals {
    protected float health;
    protected final Player player;
    private final ArrayList<Weapon> weapons;
    public ArrayList<Weapon> weapons() {
        return weapons;
    }
    private final BufferedImage sourceBossImage;
    /**
    * Boss class constructor initializes variables related to boss enemies.
    * 
    * @param player     The player.
     * @param imagePath Image path for the boss.
    */
    public Boss(Player player, String imagePath, int height, int width, int radius) {
        this.player = player;
        weapons = new ArrayList();
        sourceBossImage = Utilities.getScaledInstance(IMAGEPATH+imagePath, width, height);
        xSide = GAMEBORDER;
        ySide = 0;
        xRad = radius; yRad = radius;
    }
    /**
    * Updates the boss.  Overridden by a specific boss implementation.
    */
    public abstract void update();
    /**
    * Renders the boss.
    * 
    * @param g The Graphics object used by Java to render everything in the game.
    */
    public void draw(Graphics g) {
        BufferedImage bossImage = Utilities.rotateImage(sourceBossImage, rotation);
        float xImagePad = bossImage.getWidth()/2;
        float yImagePad = bossImage.getHeight()/2;
        if(health>0) {g.drawImage(bossImage, Math.round(xSide+xPos-xImagePad), Math.round(ySide+yPos-yImagePad), null);}
    }
    /**
    * Indicates if the boss is dead.
    * 
    * @return A boolean indicating if the boss is dead.
    */
    public boolean isDead() {
        return this.health <= 0;
    }
}