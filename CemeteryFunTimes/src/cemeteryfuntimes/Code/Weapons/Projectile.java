package cemeteryfuntimes.Code.Weapons;

// @author David Kozloff & Tyler Law

import static cemeteryfuntimes.Code.Shared.Globals.GAMEBORDER;
import cemeteryfuntimes.Code.Shared.PosVel;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;


public abstract class Projectile extends PosVel {
    
    protected BufferedImage projectileImage;
    public boolean collide;
    protected float damage;
    public float damage() {
        return damage;
    }
    protected int type;
    public int type() {
        return type;
    }
    
    public Projectile(Weapon weapon) {
        type = weapon.Type();
        xSide = GAMEBORDER;
        ySide = 0;
    }
    
    public void update() {
        xPos += xVel;
        yPos += yVel;
    }
    
    public void draw(Graphics2D g) {
        g.drawImage(projectileImage, Math.round(xSide+xPos), Math.round(ySide+yPos), null);
    }
    
    //Not using this yet, but maybe eventually will be
    public void collide(float xPos, float yPos, int direction) {}
    
}