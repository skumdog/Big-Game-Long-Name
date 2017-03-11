package cemeteryfuntimes.Code;

import cemeteryfuntimes.Code.Weapons.Weapon;
import cemeteryfuntimes.Code.Shared.*;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

// @author David Kozloff & Tyler Law
public class Player extends PosVel {
    
    //Movement based variables
    private float xAccel;
    private float yAccel;
    private final boolean[] keysPressed;
    
    //Other  
    private double rotation;
    public double Rotation() {
        return rotation;
    }
    private BufferedImage playerImage;
    private double currentRotation; //Current player orientation in radians
    public int health;
    public long invincTimer = 0;
    private ArrayList<Integer> weaponKeys;
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
        weaponKeys = new ArrayList();
        weaponKeys.add(PISTOL);
        weaponKeys.add(MACHINEGUN);
        weaponKeys.add(FLAMETHROWER);
        playerImage = cemeteryfuntimes.Code.Shared.Utilities.getScaledInstance(IMAGEPATH+weapon.PlayerImagePath(),rad*2,rad*2);
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
    
    public void changeWeaponKeyChanged(int gameCode, boolean keyIsPressed) {
        // Check if any shoot keys are currently pressed.
        if (keyIsPressed) {
            
        }
    }
    
    public void rotatePlayerImage(int direction) {
        //Rotate the image of the player
        rotation = ROTATION[direction];
        playerImage = cemeteryfuntimes.Code.Shared.Utilities.rotateImage(playerImage,rotation - currentRotation);
        currentRotation = rotation;
    }
    
    public void update() {
        //Update postion and velocity
        weapon.update();
        calcAccels();
        xVel += xAccel;
        yVel += yAccel;
        xPos += xVel;
        yPos += yVel;
        //Stop invincibility frames after a certain amount of time
        if (System.currentTimeMillis() - invincTimer > INVINCFRAMES) {invincTimer = 0;}
    }
    
    public void calcAccels() {
        //Calculate x and y accelerations
        // ^ is the Exclusive or operation
        // ? 1 : 0 casts a boolean as 1 if true and 0 if false
        xAccel = 0;
        yAccel = 0;
        
        //If only one of the x directional keys is pressed apply an acceleration in that direction
        if( keysPressed[LEFT] ^ keysPressed[RIGHT] ) { 
            xAccel = (keysPressed[LEFT] ? 1 : 0)* -PLAYERACCEL + (keysPressed[RIGHT] ? 1 : 0)*PLAYERACCEL;
        }
        if( keysPressed[UP] ^ keysPressed[DOWN] ) {
            yAccel = (keysPressed[UP] ? 1 : 0)* -PLAYERACCEL + (keysPressed[DOWN] ? 1 : 0)*PLAYERACCEL;
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
    
    /**
    *If player is not currently in invincibility frames handle collision
    *@param enemy The enemy the player collided with
    *@param negative -1 if collision on the top or left side of the player
    *@param horizontal True if collision was in the horizontal direction else false
    *@param vertical True if collision was in the vertical direction else false
    */
    public void enemyCollide(Enemy enemy, int negative, boolean horizontal, boolean vertical) {
        if (invincTimer == 0) {
            invincTimer = System.currentTimeMillis();
            health -= enemy.ContactDamage();
            if (horizontal) { xVel = PLAYERCOLLISIONVEL * negative; }
            if (vertical) { yVel = PLAYERCOLLISIONVEL * negative; }
            //Maybe add in some sort of knockback on collision?
        }
    }
    
    /*public void damaged(PosVel posVel, int type) {
        
    }*/
    
    public void draw(Graphics2D g) {
        weapon.draw(g);
        if (invincTimer != 0) {
            //Different drawing / animation of player when injured?
        }
        g.drawImage(playerImage, Math.round(xSide+xPos), Math.round(ySide+yPos), null);
    }
    
}