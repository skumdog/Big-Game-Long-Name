package cemeteryfuntimes.Resources.Shared;

// @author David Kozloff & Tyler Law

public class Collision implements Globals {
    
    public static int checkWallCollision(float xPos, float xRad, float yPos, float yRad) {
        if (xPos + xRad > GAMEWIDTH) {
            return RIGHTWALL;
        }
        else if (xPos - xRad < 0) {
            return LEFTWALL;
        }
        if (yPos + yRad > GAMEHEIGHT) {
            return BOTTOMWALL;
        }
        else if (yPos - yRad < 0) {
            return TOPWALL;
        }
        return 0;
    }
}
