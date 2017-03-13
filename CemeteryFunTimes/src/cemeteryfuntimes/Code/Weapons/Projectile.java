package cemeteryfuntimes.Code.Weapons;

// @author David Kozloff & Tyler Law

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
    
    public Projectile() {}
    public void update() {}
    public void draw(Graphics2D g) {}
    
    //Not using this yet, but maybe eventually will be
    public void collide(float xPos, float yPos, int direction) {}
    
}