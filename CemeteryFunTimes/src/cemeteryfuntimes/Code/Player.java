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
                keysPressed[0]=keyIsPressed;
                break;
            case KeyEvent.VK_D:
                keysPressed[1]=keyIsPressed;
                break;
            case KeyEvent.VK_W:
                keysPressed[2]=keyIsPressed;
                break;
            case KeyEvent.VK_S:
                keysPressed[3]=keyIsPressed;
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
        float xDampAccel=0;
        float yDampAccel=0;
        
        if( keysPressed[0] ^ keysPressed[1] ) { 
            xAccel = (keysPressed[0] ? 1 : 0)*-PLAYERACCEL + (keysPressed[1] ? 1 : 0)*PLAYERACCEL;
        }
        if ( posVel.xVel != 0 ) {
            xDampAccel = -PLAYERDAMP * posVel.xVel;
            if ( xAccel == 0 && (Math.abs(xDampAccel) > Math.abs(posVel.xVel)) ) { xAccel=0; posVel.xVel=0; }
        }
        if( keysPressed[2] ^ keysPressed[3] ) {
            yAccel = (keysPressed[2] ? 1 : 0)*-PLAYERACCEL + (keysPressed[3] ? 1 : 0)*PLAYERACCEL;
        }
        if ( posVel.yVel != 0 ) {
            yDampAccel = -PLAYERDAMP *  posVel.yVel;
            if ( yAccel == 0 && (Math.abs(yDampAccel) > Math.abs(posVel.yVel)) ) { yAccel=0; posVel.yVel=0; }
        }
        xAccel += xDampAccel;
        yAccel += yDampAccel;
    }
    
    public void adjustForMaxSpeed() {
        float totalVel = posVel.totalSpeed();
        if (totalVel > PLAYERMAXSPEED) { 
            //Figure out some way of limiting the max speed
        }
    }
    
    public void checkWallCollision() {
        int wall=cemeteryfuntimes.Resources.Shared.Collision.checkWallCollision(posVel.xPos,rad,posVel.yPos,rad);
        switch(wall) {
            case RIGHTWALL:
                posVel.xVel = 0;
                posVel.xPos = GAMEWIDTH - rad;
                break;
            case LEFTWALL:
                posVel.xVel = 0;
                posVel.xPos = rad;
                break;
            case TOPWALL:
                posVel.yVel = 0;
                posVel.yPos = rad;
                break;
            case BOTTOMWALL:
                posVel.yVel = 0;
                posVel.yPos = GAMEHEIGHT - rad;
                break;
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
    
    public int xPos() {
        return Math.round(posVel.xPos);
    }
    
    public int yPos() {
        return Math.round(posVel.yPos);
    }
    
    public int rad() {
        return Math.round(rad);
    }
    
}