package cemeteryfuntimes.Code.Weapons;

import cemeteryfuntimes.Code.Player;

// @author David Kozloff & Tyler Law

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class SingleProjectile extends Projectile {
    
    private final Player player;
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
    
    public SingleProjectile(Player player, BufferedImage projectileImage, Weapon weapon) {
        this.weapon = weapon;
        offset = weapon.ProjectileOffset();
        sourceDamage = weapon.Damage();
        damage = 0; //This projectile has no damage unless active
        this.player = player;
        sourceProjectileImage=projectileImage;
        
        height = weapon.ProjectileHeight();
        width = weapon.ProjectileWidth();
        
        //Cannot properly set xSide and ySide as the direction of the bullet shifts
        xSide = GAMEBORDER;
        ySide = 0;
    }
    
    public void update() {
        int direction = weapon.shootDirection();
        if (direction == -1) { active = false; damage = 0; return; }
        active = true; damage = sourceDamage;
        //Update the position of the projectile to the current player position
        if (currentDirection != direction) {
            currentDirection = direction;
            
            //update the x and y padding
            int horizontal = (direction == LEFT || direction == RIGHT) ? 1 : 0;
            int vertical = (direction == UP || direction == DOWN) ? 1 : 0;
            int positive = (direction == RIGHT || direction == DOWN) ? 1 : -1;
            xRad = horizontal * height / 2 + vertical * width / 2;
            yRad = vertical * height / 2 + horizontal * width / 2;
            xPadding = positive * (horizontal * (player.rad() + xRad) - vertical * offset);
            yPadding = positive * (vertical * (player.rad() + yRad) + horizontal * offset);
            
            //Rotate the projectile image to match the player rotation
            projectileImage = cemeteryfuntimes.Code.Shared.Utilities.rotateImage(sourceProjectileImage,player.Rotation());
            xSide = GAMEBORDER - projectileImage.getWidth()/2;
            ySide = - projectileImage.getHeight()/2;
        }
        xPos = xPadding + player.xPos();
        yPos = yPadding + player.yPos();
    }
    
    public void draw(Graphics2D g) {
        if (active) { g.drawImage(projectileImage, Math.round(xSide+xPos), Math.round(ySide+yPos), null); }
    }
    
}