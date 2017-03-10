package cemeteryfuntimes.Code;

import cemeteryfuntimes.Code.Shared.*;
import static cemeteryfuntimes.Code.Shared.Globals.IMAGEPATH;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

public class Test extends PosVel implements Globals {

    //This is a debugging / testing class
    //No real importance to the game
    
    private BufferedImage image;
    private double rotation;
    private long timer;
    
    public Test() {
        image = cemeteryfuntimes.Code.Shared.Utilities.getScaledInstance(IMAGEPATH+"Enemies/Zombie/zombie.png",100,100,RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR,false);
        timer = 0;
        rotation = 0.2;
    }
    
    public void update() {
        long now = System.currentTimeMillis();
        if (now - timer > 500) {
            timer = now;
            image = cemeteryfuntimes.Code.Shared.Utilities.rotateImage(image, rotation);
        }
    }
    
    public void draw(Graphics2D g) {
         g.drawImage(image, 500, 500, null);
    }
    
    public void damaged(PosVel posVel, int type) {
        
    }
    
}
