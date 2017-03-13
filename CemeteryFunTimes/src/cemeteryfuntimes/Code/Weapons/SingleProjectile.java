package cemeteryfuntimes.Code.Weapons;

import cemeteryfuntimes.Code.Shared.PosVel;

// @author David Kozloff & Tyler Law

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class SingleProjectile extends Projectile {
    
    private final PosVel posVel;
    private final Weapon weapon;
    private final float sourceDamage;
    private final int height;
    private final int width;
    private final float offset;
    private final BufferedImage sourceProjectileImage;
    
    private boolean active = false;
    private int currentDirection = -1;
    private float xPadding;
    private float yPadding;
    
    public SingleProjectile(PosVel posVel, BufferedImage projectileImage, Weapon weapon) {
        super(weapon);
        this.weapon = weapon;
        offset = weapon.ProjectileOffset();
        sourceDamage = weapon.Damage();
        damage = 0; //This projectile has no damage unless active
        this.posVel = posVel;
        sourceProjectileImage=projectileImage;
        
        height = weapon.ProjectileHeight();
        width = weapon.ProjectileWidth();
    }
    
    public void update() {
        int direction = weapon.shootDirection();
        if (direction == -1) { active = false; damage = 0; return; }
        active = true; damage = sourceDamage;
        //Update the position of the projectile to the current posVel position
        if (currentDirection != direction) {
            currentDirection = direction;
            
            //update the x and y padding
            int horizontal = (direction == LEFT || direction == RIGHT) ? 1 : 0;
            int vertical = (direction == UP || direction == DOWN) ? 1 : 0;
            int positive = (direction == RIGHT || direction == DOWN) ? 1 : -1;
            xRad = horizontal * height / 2 + vertical * width / 2;
            yRad = vertical * height / 2 + horizontal * width / 2;
            xPadding = positive * (horizontal * (posVel.rad() + xRad) - vertical * offset);
            yPadding = positive * (vertical * (posVel.rad() + yRad) + horizontal * offset);
            
            //Rotate the projectile image to match the posVel rotation
            projectileImage = cemeteryfuntimes.Code.Shared.Utilities.rotateImage(sourceProjectileImage,posVel.rotation());
            xSide = GAMEBORDER - projectileImage.getWidth()/2;
            ySide = - projectileImage.getHeight()/2;
        }
        xPos = xPadding + posVel.xPos();
        yPos = yPadding + posVel.yPos();
    }
    
    public void draw(Graphics2D g) {
        if (active) { g.drawImage(projectileImage, Math.round(xSide+xPos), Math.round(ySide+yPos), null); }
    }
    
}