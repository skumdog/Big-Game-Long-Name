package cemeteryfuntimes.Code.Weapons;

import cemeteryfuntimes.Code.Player;

// @author David Kozloff & Tyler Law

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

public class StandardProjectile extends Projectile {
    
    public StandardProjectile(Player player, int direction, BufferedImage projectileImage, Weapon weapon) {
        damage = weapon.Damage();
        key = weapon.Key();
        
        float speed = weapon.ProjectileSpeed();
        int height = weapon.ProjectileHeight();
        int width = weapon.ProjectileWidth();
        int offset = weapon.ProjectileOffset();
        int rotation = 0;
        float playerRad = player.rad();
        this.projectileImage = cemeteryfuntimes.Code.Shared.Utilities.rotateImage(projectileImage, ROTATION[direction]);
 
        //Create projectile
        //Decide starting position by player position and direction projectile is being fired
        //Decide velocity by adding PROJECTILESPEED with player velocity in that direction 
        int horizontal = (direction == LEFT || direction == RIGHT) ? 1 : 0;
        int vertical = (direction == UP || direction == DOWN) ? 1 : 0;
        int positive = (direction == RIGHT || direction == DOWN) ? 1 : -1;
        
        xPos = player.xPos() + positive * (horizontal * playerRad - vertical * offset);
        yPos = player.yPos() + positive * (vertical * playerRad + horizontal * offset);
        xVel = horizontal * (PROJECTILEPLAYERBOOST * player.xVel() + positive * speed);
        yVel = vertical * (PROJECTILEPLAYERBOOST * player.yVel() + positive * speed);
        
        // If the weapon is a machine gun, add some random spread.
        if (key == MACHINEGUN) {
            Random random = new Random();
            double randomness = (random.nextFloat() - 0.5) / 1.25;
            xVel += (float)randomness;
            yVel += (float)randomness;
        }
        
        xRad = horizontal * height / 2 + vertical * width / 2;
        yRad = vertical * height / 2 + horizontal * width / 2;
        int xImagePad = projectileImage.getWidth()/2;
        int yImagePad = projectileImage.getHeight()/2;
        xSide = GAMEBORDER - xImagePad;
        ySide = - yImagePad;
    }
    
    @Override
    public void update() {
        xPos += xVel;
        yPos += yVel;
    }
    
    @Override
    public void draw(Graphics2D g) {
        g.drawImage(projectileImage, Math.round(xSide+xPos), Math.round(ySide+yPos), null);
    }
    
}