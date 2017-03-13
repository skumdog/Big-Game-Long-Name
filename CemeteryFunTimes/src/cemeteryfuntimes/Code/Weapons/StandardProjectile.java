package cemeteryfuntimes.Code.Weapons;

import cemeteryfuntimes.Code.Player;

// @author David Kozloff & Tyler Law

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

public class StandardProjectile extends Projectile {
    
    public StandardProjectile(Player player, int direction, BufferedImage projectileImage, Weapon weapon) {
        damage = weapon.Damage();
        type = weapon.Type();
        
        float speed = weapon.ProjectileSpeed();
        int height = weapon.ProjectileHeight();
        int width = weapon.ProjectileWidth();
        int offset = weapon.ProjectileOffset();
        double spread = weapon.ProjectileSpread();
        float playerRad = player.rad();
        double rotation = ROTATION[direction];
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
        if (spread != 0) {
            Random random = new Random();
            int sign = random.nextFloat() < 0.5 ? -1 : 1;
            spread = Math.toRadians(sign * random.nextFloat() * spread);
            if (horizontal == 1) {
                yVel = (float) Math.sin(spread) * xVel;
                xVel = (float) Math.cos(spread) * xVel;
                rotation += spread;
            }
            else {
                xVel = (float) Math.sin(spread) * yVel;
                yVel = (float) Math.cos(spread) * yVel;
                rotation -= spread;
            }
        }
        
        xRad = horizontal * height / 2 + vertical * width / 2;
        yRad = vertical * height / 2 + horizontal * width / 2;
        this.projectileImage = cemeteryfuntimes.Code.Shared.Utilities.rotateImage(projectileImage, rotation);
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