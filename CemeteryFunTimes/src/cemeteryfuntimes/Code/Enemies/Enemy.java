package cemeteryfuntimes.Code.Enemies;

import cemeteryfuntimes.Code.Player;
import cemeteryfuntimes.Code.Weapons.Weapon;
import cemeteryfuntimes.Code.Shared.*;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import org.w3c.dom.NamedNodeMap;

// @author David Kozloff & Tyler Law

public abstract class Enemy extends PosVel {
    
    //Member variables
    public float health;
    protected final Player player;
    protected float xImagePad;
    protected float yImagePad;
    
    //Enemy definition
    protected int contactDamage;
    public int ContactDamage() {
        return contactDamage;
    }
    protected int weaponKey;
    protected float speed;
    public float Speed() {
        return speed;
    }
    protected int type;
    public int Type() {
        return type;
    }
    protected String name;
    protected BufferedImage enemyImage;
    protected final BufferedImage sourceEnemyImage;
    protected Weapon weapon;
    public Weapon getWeapon() {
        return weapon;
    }
    
    public Enemy(Player player, int xPos, int yPos, int key) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.player = player;
        loadEnemy(key);
        xRad = rad; yRad = rad;
        xSide = GAMEBORDER;
        ySide = 0;
        sourceEnemyImage = enemyImage;
        xImagePad = enemyImage.getWidth()/2;
        yImagePad = enemyImage.getHeight()/2;
    }
    
    public void update() {
        xPos += xVel;
        yPos += yVel;
    }
    
    public abstract void calcVels();
    
    public void rotateEnemyImage() {
        if (xVel == 0 && yVel == 0) {return;}
        double radians = Math.PI - Math.atan2(xVel, yVel);
        if (Math.abs(rotation - radians) > MINIMUMROTATION) {
            rotation = radians;
            enemyImage = cemeteryfuntimes.Code.Shared.Utilities.rotateImage(sourceEnemyImage, rotation);
            xImagePad = enemyImage.getWidth()/2;
            yImagePad = enemyImage.getHeight()/2;
        }
    }
    
    private void loadEnemy(int enemyKey) {
        NamedNodeMap attributes = cemeteryfuntimes.Code.Shared.Utilities.loadTemplate("Enemies.xml","Enemy",enemyKey);
        contactDamage = Integer.parseInt(attributes.getNamedItem("ContactDamage").getNodeValue());
        health = Integer.parseInt(attributes.getNamedItem("Health").getNodeValue());
        name = attributes.getNamedItem("Name").getNodeValue();
        speed = Float.parseFloat(attributes.getNamedItem("EnemySpeed").getNodeValue());
        rad = Integer.parseInt(attributes.getNamedItem("EnemySize").getNodeValue());
        type = Integer.parseInt(attributes.getNamedItem("Type").getNodeValue());
        enemyImage = cemeteryfuntimes.Code.Shared.Utilities.getScaledInstance(IMAGEPATH+attributes.getNamedItem("EnemyImage").getNodeValue(),rad*2,rad*2);
        if (type == PROJECTILEENEMY) {
            weaponKey = Integer.parseInt(attributes.getNamedItem("WeaponKey").getNodeValue());
        }
    }
    
    @Override
    public void damaged(float damage) {
        health -= damage;
    }
    
    public void draw(Graphics2D g) {
        if(health>0) {g.drawImage(enemyImage, Math.round(xSide+xPos-xImagePad), Math.round(ySide+yPos-yImagePad), null);}
    }
    
}
