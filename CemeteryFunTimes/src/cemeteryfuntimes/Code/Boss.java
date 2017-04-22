package cemeteryfuntimes.Code;
import cemeteryfuntimes.Code.Shared.Globals;
import cemeteryfuntimes.Code.Weapons.Weapon;
import java.awt.Graphics;
import java.util.ArrayList;
/**
* Boss abstract class contains variables and methods related to boss enemies.
* @author David Kozloff & Tyler Law
*/
public abstract class Boss implements Globals {
    protected float health;
    protected final Player player;
    private final ArrayList<Weapon> weapons;
    public ArrayList<Weapon> weapons() {
        return weapons;
    }
    /**
    * Boss class constructor initializes variables related to boss enemies.
    * 
    * @param player  The player.
    */
    public Boss(Player player) {
        this.player = player;
        weapons = new ArrayList();
        this.health = 100;
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