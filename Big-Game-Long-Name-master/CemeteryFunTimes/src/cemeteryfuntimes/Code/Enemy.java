package cemeteryfuntimes.Code;

import cemeteryfuntimes.Code.Shared.*;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import org.w3c.dom.NamedNodeMap;

// @author David Kozloff & Tyler Law

public class Enemy extends PosVel implements Globals {
    
    public int health = 3;
    private final Player player;
    
    //Enemy definition
    private int damage;
    public int Damage() {
        return damage;
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
        xSide = GAMEBORDER - rad;
        ySide = - rad;
        
        sourceEnemyImage = enemyImage;
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
        float xDist = player.xPos() - xPos ;
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
        }
    }
    
    private void loadEnemy(int enemyKey) {
        NamedNodeMap attributes = cemeteryfuntimes.Code.Shared.Utilities.loadTemplate("Enemies.xml","Enemy",enemyKey);
        damage = Integer.parseInt(attributes.getNamedItem("Damage").getNodeValue());
        name = attributes.getNamedItem("Name").getNodeValue();
        speed = Float.parseFloat(attributes.getNamedItem("EnemySpeed").getNodeValue());
        rad = Integer.parseInt(attributes.getNamedItem("EnemySize").getNodeValue());
        enemyType = Integer.parseInt(attributes.getNamedItem("EnemyType").getNodeValue());
        enemyImage = cemeteryfuntimes.Code.Shared.Utilities.getScaledInstance(IMAGEPATH+attributes.getNamedItem("EnemyImage").getNodeValue(),rad*2,rad*2,RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR,false);
    }
    
    public void draw(Graphics2D g) {
        g.drawImage(enemyImage, Math.round(xSide+xPos), Math.round(ySide+yPos), null);
    }
    
}
