package cemeteryfuntimes.Code;

import cemeteryfuntimes.Code.Shared.PosVel;
import cemeteryfuntimes.Code.Shared.Globals;
import static cemeteryfuntimes.Code.Shared.Globals.IMAGEPATH;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

// @author David Kozloff & Tyler Law
public class Player extends PosVel implements Globals {
    
    //Movement based variables
    private float xAccel;
    private float yAccel;
    private final boolean[] keysPressed;
    
    //Other
    private final int[] shootDirection = {
        0,180,90,270
    };    
    private BufferedImage playerImage;
    private int currentRotation;
    public int health;
    public long hurtTimer;
    private final Weapon weapon;
    public Weapon getWeapon() {
        return weapon;
    }
    
    public Player(int xPos, int yPos, int weaponKey) {
        keysPressed = new boolean [4];
        health = 6;
        this.xPos = xPos;
        this.yPos = yPos;
        rad = PLAYERSIZE/2; xRad = rad; yRad = rad;
        xSide = GAMEBORDER-rad;
        ySide = -rad;
        this.weapon = new Weapon(this, weaponKey);
        playerImage = cemeteryfuntimes.Code.Shared.Utilities.getScaledInstance(IMAGEPATH+weapon.PlayerImagePath(),rad*2,rad*2,RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR,false);
    }
    
    public void movementKeyChanged(int direction, boolean keyIsPressed) {
        //Record which directional keys are being pressed
        keysPressed[direction] = keyIsPressed;
    }
    
    public void shootKeyChanged(int direction, boolean keyIsPressed) {
        //If an arrow key was pressed, pass event to weapon
        if (keyIsPressed) { 
            weapon.keyPressed(direction); 
            //Also rotate the image of the player
            rotatePlayerImage(direction);
        }
        else { weapon.keyReleased(direction); }
    }
    
    public void rotatePlayerImage(int direction) {
        //Rotate the image of the player
        int rotationAngle = shootDirection[direction];
        playerImage = cemeteryfuntimes.Code.Shared.Utilities.rotateImage(playerImage,Math.toRadians(rotationAngle - currentRotation));
        currentRotation = rotationAngle;
    }
    
    public void update() {
        //Update postion and velocity
        weapon.update();
        calcAccels();
        xVel += xAccel;
        yVel += yAccel;
        xPos += xVel;
        yPos += yVel;
        checkWallCollision();
        //Stop invincibility frames after a certain amount of time
        if (System.currentTimeMillis() - hurtTimer > INVFRAMES) {hurtTimer = 0;}
    }
    
    public void calcAccels() {
        //Calculate x and y accelerations
        // ^ is the Exclusive or operation
        // ? 1 : 0 casts a boolean as 1 if true and 0 if false
        xAccel = 0;
        yAccel = 0;
        
        //If only one of the x directional keys is pressed apply an acceleration in that direction
        if( keysPressed[MOVELEFT] ^ keysPressed[MOVERIGHT] ) { 
            xAccel = (keysPressed[MOVELEFT] ? 1 : 0)* -PLAYERACCEL + (keysPressed[MOVERIGHT] ? 1 : 0)*PLAYERACCEL;
        }
        if( keysPressed[MOVEUP] ^ keysPressed[MOVEDOWN] ) {
            yAccel = (keysPressed[MOVEUP] ? 1 : 0)* -PLAYERACCEL + (keysPressed[MOVEDOWN] ? 1 : 0)*PLAYERACCEL;
        }
        if (xAccel != 0 && yAccel != 0) {
            //If moving in two directions divide both accels by square root of 2
            //This ensures diagonal moving speed does not exceed linear moving speed
            xAccel /= 1.41421356237;
            yAccel /= 1.41421356237;
        }
        //Combine both player accel and friction deaccel
        xAccel += -PLAYERDAMP * xVel;
        yAccel += -PLAYERDAMP *  yVel;
    }
    
    public void checkWallCollision() {
        boolean[] wall=cemeteryfuntimes.Code.Shared.Collision.checkWallCollision(xPos,rad,yPos,rad);
        if (wall[RIGHTWALL]) {
            xVel = 0;
            xPos = GAMEWIDTH - rad;
        }
        else if (wall[LEFTWALL]) {
            xVel = 0;
            xPos = rad;
        }
        if (wall[TOPWALL]) {
            yVel = 0;
            yPos = rad;
        }
        else if (wall[BOTTOMWALL]) {
            yVel = 0;
            yPos = GAMEHEIGHT - rad;
        }
    }
    
    public void enemyCollide(Enemy enemy, int side) {
        //If player is not currently in invincibility frames handle collision
        if (hurtTimer == 0) {
            hurtTimer = System.currentTimeMillis();
            health -= enemy.Damage();
            //Maybe add in some sort of knockback on collision?
        }
    }
    
    public void draw(Graphics2D g) {
        weapon.draw(g);
        if (hurtTimer != 0) {
            //Different drawing / animation of player when injured?
        }
        g.drawImage(playerImage, Math.round(xSide+xPos), Math.round(ySide+yPos), null);
    }
    
}