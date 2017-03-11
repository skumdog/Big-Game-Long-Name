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
    private final boolean[] moveKeysPressed;
    private final boolean[] shootKeysPressed;
    private int lastShotDirection;

    //Other  
    private double rotation;
    public double Rotation() {
        return rotation;
    }
    private BufferedImage playerImage;
    private double currentRotation; //Current player orientation in radians
    public int health;
    public long invincTimer = 0;
    private final ArrayList<Weapon> weapons;
    private int weaponIndex; // Index into currently equipped weapon.
    public Weapon getWeapon() {
        return weapons.get(weaponIndex);
    }
    
    public Player(int xPos, int yPos, int weaponKey) {
        moveKeysPressed = new boolean [4];
        shootKeysPressed = new boolean [4];
        health = 6;
        this.xPos = xPos;
        this.yPos = yPos;
        rad = PLAYERSIZE/2; xRad = rad; yRad = rad;
        xSide = GAMEBORDER-rad;
        ySide = -rad;
        weaponIndex = 0;
        this.weapons = new ArrayList();
        this.weapons.add(new Weapon(this, weaponKey));
        this.weapons.add(new Weapon(this, MACHINEGUN));
        this.weapons.add(new Weapon(this, FLAMETHROWER));
        playerImage = cemeteryfuntimes.Code.Shared.Utilities.getScaledInstance(IMAGEPATH+weapons.get(weaponIndex).PlayerImagePath(),rad*2,rad*2);
    }
    
    public void movementKeyChanged(int direction, boolean keyIsPressed) {
        //Record which directional keys are being pressed
        moveKeysPressed[direction] = keyIsPressed;
    }
    
    public void shootKeyChanged(int direction, boolean keyIsPressed) {
        //If an arrow key was pressed, pass event to weapon
        if (keyIsPressed) {
            weapons.get(weaponIndex).keyPressed(direction); 
            //Also rotate the image of the player
            rotatePlayerImage(direction);
            //Record last shot direction, used in changeWeaponKeyChanged.
            lastShotDirection = direction;
        } else { 
            weapons.get(weaponIndex).keyReleased(direction); 
        }
        //Record which shoot keys are being pressed, used in changeWeaponKeyChanged.
        shootKeysPressed[direction] = keyIsPressed;
    }
    
    public void changeWeaponKeyChanged(int gameCode, boolean keyIsPressed) {
        // Check if any shoot keys are currently pressed.
        boolean shooting = false;
        for (int i=0; i<4; i++) {
            if (shootKeysPressed[i] == true) {
                shooting = true;
            }
        }
        // If change weapon key pressed, change the weapon index.
        if (keyIsPressed) {
            // If change weapon key is pressed while a shoot key is being held,
            // stop shooting the old weapon.
            if (shooting) {
                weapons.get(weaponIndex).keyReleased(lastShotDirection);
            }
            // Change weapon index.
            if (weaponIndex == weapons.size() - 1) {
                weaponIndex = 0;
            } else {
                weaponIndex++;
            }
            // If change weapon key is pressed while a shoot key is being held,
            // start shooting the new weapon.
            if (shooting) {
                weapons.get(weaponIndex).keyPressed(lastShotDirection);
            }
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
        weapons.stream().forEach((weapon) -> {
            weapon.update();
        });
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
        if( moveKeysPressed[LEFT] ^ moveKeysPressed[RIGHT] ) { 
            xAccel = (moveKeysPressed[LEFT] ? 1 : 0)* -PLAYERACCEL + (moveKeysPressed[RIGHT] ? 1 : 0)*PLAYERACCEL;
        }
        if( moveKeysPressed[UP] ^ moveKeysPressed[DOWN] ) {
            yAccel = (moveKeysPressed[UP] ? 1 : 0)* -PLAYERACCEL + (moveKeysPressed[DOWN] ? 1 : 0)*PLAYERACCEL;
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
        weapons.stream().forEach((weapon) -> {
            weapon.draw(g);
        });
        if (invincTimer != 0) {
            //Different drawing / animation of player when injured?
        }
        g.drawImage(playerImage, Math.round(xSide+xPos), Math.round(ySide+yPos), null);
    }
}