package cemeteryfuntimes.Code;

import cemeteryfuntimes.Code.Shared.PosVel;
import cemeteryfuntimes.Code.Shared.Globals;
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
    private AffineTransform rotation;
    private double currentRotation;
    
    public Enemy(Player player, int xPos, int yPos, int key) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.player = player;
        loadEnemy(key);
        xRad = rad; yRad = rad;
        xSide = GAMEBORDER - rad;
        ySide = - rad;
        
        damage = 2;
    }
    
    public void update() {
        xPos += xVel;
        yPos += yVel;
        //rotateEnemyImage();
    }
    
    public void calcVels() {
        //Calculates what the current velocity of the enemy should be
        //Find the vector pointing from the enemy to the player
        float xDist = player.xPos() - xPos ;
        float yDist = player.yPos() - yPos;
        float totDist = (float) Math.sqrt(xDist*xDist + yDist*yDist);
        
        //Scale the vector to be the length of enemy speed to get speed
        xVel = speed * (xDist / totDist);
        yVel = speed * (yDist / totDist);
    }
    
    public void rotateEnemyImage() {
        double radians = Math.asin(xVel/Math.sqrt((yVel*yVel+xVel*xVel)));
        if (Math.abs(currentRotation - radians) > MINIMUMROTATION) {
            enemyImage = cemeteryfuntimes.Code.Shared.Utilities.rotateImage(enemyImage, radians - currentRotation);
            currentRotation = radians;
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
