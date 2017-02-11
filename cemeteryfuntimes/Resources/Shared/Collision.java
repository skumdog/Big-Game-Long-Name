package cemeteryfuntimes.Resources.Shared;

// @author David Kozloff & Tyler Law

import static cemeteryfuntimes.Resources.Shared.Globals.GAMEHEIGHT;
import static cemeteryfuntimes.Resources.Shared.Globals.GAMEWIDTH;


public class Collision {
    
    public static int checkWallCollision(float xPos, int xRad, float yPos, int yRad) {
        if (xPos + xRad > GAMEWIDTH) {
            return 1;
        }
        else if (xPos - xRad < 0) {
            return 2;
        }
        if (yPos + yRad > GAMEHEIGHT) {
            return 3;
        }
        else if (yPos - yRad < 0) {
            return 4;
        }
        return 0;
    }
    
}
