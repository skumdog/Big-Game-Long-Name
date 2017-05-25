package cemeteryfuntimes.Code.Bosses;
import cemeteryfuntimes.Code.Player;
import cemeteryfuntimes.Code.Shared.*;
/**
* BatLord boss.
* @author David Kozloff & Tyler Law
*/
public final class BatLord extends Boss implements Globals {
    
    private final static int height = 50;
    private final static int width = 100;
    private final static String imagePath = "";
    /**
    * BatLord class constructor initializes variables related to the BatLord boss.
    */
    public BatLord(Player player) {
        super(player, imagePath, height, width);
        this.health = 100;
    }

    @Override
    public void update() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
