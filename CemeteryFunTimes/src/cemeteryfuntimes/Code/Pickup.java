package cemeteryfuntimes.Code;
import cemeteryfuntimes.Code.Shared.PosVel;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

// @author David Kozloff & Tyler Law

public class Pickup extends PosVel {
    
    private final Player player;
    
    // Make this global?
    private BufferedImage heartContainer = null;
    private final static int HEARTSIZE = 40;
    
    public Pickup (Player player, float x, float y, int pickupType) {
        this.player = player;
        xPos = x;
        yPos = y;
        rad = HEARTSIZE/2; xRad = rad; yRad = rad;
        xSide = GAMEBORDER - rad;
        ySide = - rad;
        setupImages();
    }
    
    public void draw (Graphics g) {
        g.drawImage(heartContainer,(int)(xSide + xPos),(int)(ySide + yPos),null);
    }
    
    private void setupImages() {
       // Make this a shared method?
       heartContainer = cemeteryfuntimes.Code.Shared.Utilities.getScaledInstance(IMAGEPATH+"General/heart.png",HEARTSIZE,HEARTSIZE);
    }
    
}