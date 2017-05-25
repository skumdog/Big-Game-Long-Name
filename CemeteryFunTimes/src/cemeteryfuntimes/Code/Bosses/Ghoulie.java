package cemeteryfuntimes.Code.Bosses;
import cemeteryfuntimes.Code.Player;
import cemeteryfuntimes.Code.Shared.*;
import java.util.Random;
/**
* Ghoulie boss.
* @author David Kozloff & Tyler Law
*/
public final class Ghoulie extends Boss implements Globals {
    private final float speed;
    public float Speed() {
        return speed;
    }
    private final int movementDelay;
    private long lastMovement;
    private int timer;
    private final int fauxXRad;
    public int getFauxXRad() {
        return fauxXRad;
    }
    private final int fauxYRad;
    public int getFauxYRad() {
        return fauxYRad;
    }
    /**
    * Ghoulie class constructor initializes variables related to the Ghoulie boss.
    * 
    * @param player     The player.
    * @param imagePath  The path to the boss image.
    * @param height     The height of the boss.
    * @param width      The width of the boss.
    */
    public Ghoulie(Player player, String imagePath, int height, int width) {
        super(player, imagePath, height, width);
        this.health = 70;
        this.rad = 150;
        this.xRad = this.rad;
        this.yRad = this.rad;
        this.fauxXRad = 8;
        this.fauxYRad = 75;
        this.xSide = GAMEBORDER;
        this.ySide = 0;
        this.movementDelay = 1000;
        this.speed = 2;
        this.timer = 0;
    }
    /**
    * Updates the boss.
    */
    @Override
    public void update() {
        xPos += xVel;
        yPos += yVel;
    }
    /**
    * Calculates the direction the boss should be moving in.
    */
    public void calcVels() {
        long now = System.currentTimeMillis();
        if ( now - lastMovement > movementDelay ) {
            this.timer++;
            if ((this.timer % 2) == 1) {
                moveRandomly();
            } else {
                moveTowardPlayer();
            }
        }
    }
    /**
    * Updates the velocity of the enemy to move randomly. 
    */
    private void moveRandomly() {
        long now = System.currentTimeMillis();
        if ( now - lastMovement > movementDelay ) {
            lastMovement = now;
            Random random = new Random();
            do {
                xVel = (random.nextFloat() < 0.5 ? -1 : 1) * random.nextFloat();
                yVel = (random.nextFloat() < 0.5 ? -1 : 1) * random.nextFloat();
            }
            while (!validMoveDirection());
            //Adjust to have correct speed
            float totalSpeed = (float) Math.sqrt(xVel*xVel + yVel*yVel);
            xVel = speed * xVel / totalSpeed;
            yVel = speed * yVel / totalSpeed;
        }
    }
    /**
     * Checks if enemy will collide with wall before changing direction
     * 
     * @return false if enemy will collide with wall, true otherwise 
     */
    private boolean validMoveDirection() {
        double steps = movementDelay/TIMERDELAY;
        boolean valid = true;
        xPos+=xVel*steps;
        yPos+=yVel*steps;
        if (this.hitWall()) {valid=false;}
        xPos-=xVel*steps;
        yPos-=yVel*steps;
        return valid;
    }
    /**
    * Updates the velocity of the enemy to be moving directly toward the player. 
    */
    private void moveTowardPlayer() {
        //Calculates what the current velocity of the enemy should be
        //Find the vector pointing from the enemy to the player
        if (xVel ==0 && yVel==0) {
            int x = 6;
        }
        float xDist = player.xPos() - xPos;
        float yDist = player.yPos() - yPos;
        float totDist = (float) Math.sqrt(xDist*xDist + yDist*yDist);
        
        //Scale the vector to be the length of enemy speed to get speed
        xVel = speed * (xDist / totDist);
        yVel = speed * (yDist / totDist);
    }
}