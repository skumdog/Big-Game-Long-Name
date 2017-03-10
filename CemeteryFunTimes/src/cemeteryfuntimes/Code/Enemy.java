package cemeteryfuntimes.Code;

import cemeteryfuntimes.Code.Weapons.Weapon;
import cemeteryfuntimes.Code.Shared.*;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import org.w3c.dom.NamedNodeMap;

// @author David Kozloff & Tyler Law

public class Enemy extends PosVel {
    
    //Member variables
    public float health;
    private final Player player;
    private float xImagePad;
    private float yImagePad;
    
    //Enemy definition
    private int contactDamage;
    public int ContactDamage() {
        return contactDamage;
    }
    private float speed;
    public float Speed() {
        return speed;
    }
    private int enemyType;
    private String name;
    private BufferedImage enemyImage;
    private final BufferedImage sourceEnemyImage;
    private AffineTransform rotation;
    private double currentRotation;
    private final Weapon weapon;
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
        weapon = null; //Some enemies will have weapons I assume
    }
    
    public void update() {
        xPos += xVel;
        yPos += yVel;
    }
    
    public void calcVels() {
        //Calculates what the current velocity of the enemy should be
        //Find the vector pointing from the enemy to the player
        if (xVel ==0 && yVel==0) {
            int x = 6;
        }
        float xDist = player.xPos() - xPos;
        float yDist = player.yPos() - yPos;
        float totDist = (float) Math.sqrt(xDist*xDist + yDist*yDist);
        
        //Scale the vector to be the length of enemy speed to get speed
        xVel = speed * (xDist / totDist);
        yVel = speed * (yDist / totDist);
        rotateEnemyImage();
    }
    
    /*public void damaged(PosVel posVel, int type) {
        switch(type) {
            case PROJECTILEDAMAGE:
                Projectile projectile = (Projectile) posVel;
                health -= projectile.damage();
                break;
            case CONTACTDAMAGE:
                break;
        }
    }*/
    
    public void rotateEnemyImage() {
        if (xVel == 0 && yVel == 0) {return;}
        double radians = Math.PI - Math.atan2(xVel, yVel);
        if (Math.abs(currentRotation - radians) > MINIMUMROTATION) {
            currentRotation = radians;
            enemyImage = cemeteryfuntimes.Code.Shared.Utilities.rotateImage(sourceEnemyImage, currentRotation);
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
        enemyType = Integer.parseInt(attributes.getNamedItem("EnemyType").getNodeValue());
        enemyImage = cemeteryfuntimes.Code.Shared.Utilities.getScaledInstance(IMAGEPATH+attributes.getNamedItem("EnemyImage").getNodeValue(),rad*2,rad*2);
    }
    
    public void draw(Graphics2D g) {
        g.drawImage(enemyImage, Math.round(xSide+xPos-xImagePad), Math.round(ySide+yPos-yImagePad), null);
    }
    
}
