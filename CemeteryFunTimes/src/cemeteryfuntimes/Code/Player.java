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
    private boolean[] keysPressed;
    
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
        // Record which directional keys are being pressed
        // A - 0, D - 1, W - 2, S - 3
        switch (direction) {
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
        // Update postion and velocity
        calcAccels();
        posVel.xVel += xAccel;
        posVel.yVel += yAccel;
        adjustForMaxSpeed();
        posVel.update();
        checkWallCollision();
    }
    
    public void calcAccels() {
        // Calculate x and y accelerations
        // If both or neither are pressed apply a small damping acceleration opposite of the current velocity
        // ^ is the Exclusive or operation
        // ? 1 : 0 casts a boolean as 1 if true and 0 if false
        if( keysPressed[0] ^ keysPressed[1] ) { 
            xAccel = (keysPressed[0] ? 1 : 0)*-PLAYERACCEL + (keysPressed[1] ? 1 : 0)*PLAYERACCEL;
        }
        else if ( posVel.xVel != 0 ) {
            xAccel = -PLAYERDAMP * Math.signum(posVel.xVel);
            if ( Math.abs(xAccel) > Math.abs(posVel.xVel) ) { 
                xAccel=0; 
                posVel.xVel=0; 
            }
        }
        else {
            xAccel = 0;
        }
        
        // Y acceleration
        // If only one of W or S is pressed
        if( keysPressed[2] ^ keysPressed[3] ) {
            yAccel = (keysPressed[2] ? 1 : 0)*-PLAYERACCEL + (keysPressed[3] ? 1 : 0)*PLAYERACCEL;
        }
        
        // If player is moving, and if both or neither W or S is being pressed
        else if ( posVel.yVel != 0 ) {
            
            // xAccel is set to damping constant if the if statement isn't executed
            yAccel = -PLAYERDAMP * Math.signum(posVel.yVel);
            
            // If current velocity is larger than damp constant
            // Why wouldn't the player just instantly stop if going sufficiently fast?
            if ( Math.abs(yAccel) > Math.abs(posVel.yVel) ) { 
                yAccel=0; 
                posVel.yVel=0; 
            }
        }
        
        // If player is not moving and W and S not pressed (or both are pressed)
        else {
            yAccel = 0;
        }
    }
    
    public void adjustForMaxSpeed() {
        float totalVel = posVel.totalSpeed();
        if (totalVel > PLAYERMAXSPEED) { 
            //Figure out some way of limiting the max speed
        }
    }
    
    public void checkWallCollision() {
        if (posVel.xPos + rad > GAMEWIDTH) {
            posVel.xVel = 0;
            posVel.xPos = GAMEWIDTH - rad;
        }
        else if (posVel.xPos - rad < 0) {
            posVel.xVel = 0;
            posVel.xPos = rad;
        }
        
        if (posVel.yPos + rad > GAMEHEIGHT) {
            posVel.yVel = 0;
            posVel.yPos = GAMEHEIGHT - rad;
        }
        else if (posVel.yPos - rad < 0) {
            posVel.yVel = 0;
            posVel.yPos = rad;
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