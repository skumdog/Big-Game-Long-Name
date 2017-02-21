package cemeteryfuntimes.Code;

import cemeteryfuntimes.Resources.Shared.*;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

// @author David Kozloff & Tyler Law
public class Player extends PosVel implements Globals {
    
    //MOVEment based variables
    private float xAccel;
    private float yAccel;
    private final boolean[] keysPressed;
    
    //Other
    public int health;
    private Weapon weapon;
    
    //Dimensional constants
    private final float xSide;
    private final float ySide;
    
    public Player(int xPos, int yPos, int weaponKey) {
        keysPressed = new boolean [4];
        health = 6;
        rad = PLAYERSIZE/2;
        xSide = GAMEBORDER-rad;
        ySide = -rad;
        this.weapon = new Weapon(this, weaponKey);
    }
    
    public void movementKeyChanged(int direction, boolean keyIsPressed) {
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
    
    public void shootKeyChanged(int direction, boolean keyIsPressed) {
        //If an arrow key was pressed, pass event to weapon
        if (keyIsPressed) { weapon.keyPressed(direction); }
        else { weapon.keyReleased(direction); }
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
        boolean[] wall=cemeteryfuntimes.Resources.Shared.Collision.checkWallCollision(xPos,rad,yPos,rad);
        if (wall[RIGHTWALL]) {
            xVel = 0;
            xPos = GAMEWIDTH - rad;
        }
        if (wall[LEFTWALL]) {
            xVel = 0;
            xPos = rad;
        }
        if (wall[TOPWALL]) {
            yVel = 0;
            yPos = rad;
        }
        if (wall[BOTTOMWALL]) {
            yVel = 0;
            yPos = GAMEHEIGHT - rad;
        }
    }
    
    public void draw(Graphics g) {
        weapon.draw(g);
        g.setColor(PLAYERCOLOR);
        g.fillRect(Math.round(xSide+xPos),Math.round(ySide+yPos),PLAYERSIZE,PLAYERSIZE);
    }
    
    public void setupImages() {
        //Setup images for player
    }
    
    public Weapon getWeapon() {
        return weapon;
    }
    
}