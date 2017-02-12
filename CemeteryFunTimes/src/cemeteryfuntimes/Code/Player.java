package cemeteryfuntimes.Code;

import cemeteryfuntimes.Resources.Shared.*;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

// @author David Kozloff & Tyler Law
public class Player implements Globals {
    
    //Movement based variables
    private final PosVel posVel;
    private float xAccel;
    private float yAccel;
    private final boolean[] keysPressed;
    
    //Other
    private int health;
    
    //Dimensional constants
    private static final float rad = PLAYERSIZE/2;
    private static final float xSide = GAMEBORDER-rad;
    private static final float ySide = -rad;
    
    public Player(int xPos, int yPos) {
        posVel = new PosVel();
        posVel.xPos = xPos;
        posVel.yPos = yPos;
        keysPressed = new boolean [4];
    }
    
    public void keyChanged(int direction, boolean keyIsPressed) {
        //Record which directional keys are being pressed
        switch(direction) {
            case KeyEvent.VK_A:
                keysPressed[MOVELEFT]=keyIsPressed;
                break;
            case KeyEvent.VK_D:
                keysPressed[MOVERIGHT]=keyIsPressed;
                break;
            case KeyEvent.VK_W:
                keysPressed[MOVEUP]=keyIsPressed;
                break;
            case KeyEvent.VK_S:
                keysPressed[MOVEDOWN]=keyIsPressed;
                break;
        }
    }
    
    public void update() {
        //Update postion and velocity
        calcAccels();
        posVel.xVel += xAccel;
        posVel.yVel += yAccel;
        adjustForMaxSpeed();
        posVel.update();
        checkWallCollision();
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
        xAccel += -PLAYERDAMP * posVel.xVel;
        yAccel += -PLAYERDAMP *  posVel.yVel;
    }
    
    public void adjustForMaxSpeed() {
        float totalVel = posVel.totalSpeed();
        if (totalVel > PLAYERMAXSPEED) { 
            //Figure out some way of limiting the max speed
        }
    }
    
    public void checkWallCollision() {
        boolean[] wall=cemeteryfuntimes.Resources.Shared.Collision.checkWallCollision(posVel.xPos,rad,posVel.yPos,rad);
        if (wall[RIGHTWALL]) {
            posVel.xVel = 0;
            posVel.xPos = GAMEWIDTH - rad;
        }
        if (wall[LEFTWALL]) {
            posVel.xVel = 0;
            posVel.xPos = rad;
        }
        if (wall[TOPWALL]) {
            posVel.yVel = 0;
            posVel.yPos = rad;
        }
        if (wall[BOTTOMWALL]) {
            posVel.yVel = 0;
            posVel.yPos = GAMEHEIGHT - rad;
        }
    }
    
    public void draw(Graphics g) {
        g.setColor(PLAYERCOLOR);
        g.drawRect(Math.round(xSide+posVel.xPos),Math.round(ySide+posVel.yPos),PLAYERSIZE,PLAYERSIZE);
    }
    
    //Retrieve private variables
    public int Health() {
        return health;
    }
    
    public PosVel PosVel() {
        return posVel;
    }
    
    public int rad() {
        return Math.round(rad);
    }
    
}