package cemeteryfuntimes.Code.Bosses;

import cemeteryfuntimes.Code.Player;
import cemeteryfuntimes.Code.Shared.*;

/**
* Boss abstract class contains variables and methods related to boss enemies.
* @author David Kozloff & Tyler Law
*/
public class BatLord extends Boss implements Globals {
    
    private final static int height = 50;
    private final static int width = 100;
    private final static String imagePath = "";

    public BatLord(Player player) {
        super(player, imagePath, height, width);
    }

    @Override
    public void update() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
