package cemeteryfuntimes.Code.Weapons;

import cemeteryfuntimes.Code.Shared.Globals;
import cemeteryfuntimes.Code.Shared.PosVel;

// @author David Kozloff & Tyler Law

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import org.w3c.dom.NamedNodeMap;

public class Weapon implements Globals {

    //Member variables
    private final ArrayList<Projectile> projectiles;
    public ArrayList<Projectile> Projectiles() {
        return projectiles;
    }
    private final boolean[] keyPressed;
    private final PosVel posVel;
    private long lastUpdate;

    //Gun definition
    private float damage;
    public float Damage() {
        return damage;
    }
    private int key;
    public int Key() {
        return key;
    }
    private String name;
    private int weaponLength;
    private float projectileSpeed;
    public float ProjectileSpeed() {
        return projectileSpeed;
    }
    private int projectileWidth;
    public int ProjectileWidth() {
        return projectileWidth;
    }
    private int projectileHeight;
    public int ProjectileHeight() {
        return projectileHeight;
    }
    private int projectileSpread;
    public int ProjectileSpread() {
        return projectileSpread;
    }
    private int type;
    public int Type() {
        return type;
    }
    private int projectileDelay;
    private int projectileOffset;
    public int ProjectileOffset() {
        return projectileOffset;
    }
    private BufferedImage projectileImage;
    private boolean enemyWeapon;
    public boolean EnemyWeapon() {
        return enemyWeapon;
    }
    private String playerImagePath;
    public String PlayerImagePath() {
        return playerImagePath;
    }
    private SingleProjectile singleProjectile;

    public Weapon(PosVel posVel, int weaponKey) {
        this.posVel = posVel;
        loadWeapon(weaponKey);
        key = weaponKey;
        projectiles = new ArrayList();
        keyPressed = new boolean[4];
    }

    public void keyPressed(int direction) {
        keyPressed[LEFT] = false;
        keyPressed[RIGHT] = false;
        keyPressed[UP] = false;
        keyPressed[DOWN] = false;
        keyPressed[direction] = true;
    }

    public void keyReleased(int direction) {
        keyPressed[direction] = false;
    }

    public void update() {
        if (type != 2) {
            createProjectile();
        }
        Projectile projectile;
        for (Iterator<Projectile> projectileIt = projectiles.iterator(); projectileIt.hasNext();) {
            projectile = projectileIt.next();
            if (projectile.type() != 2 && projectile.collide) { projectileIt.remove(); break; }
            projectile.update();
        }
    }

    public void createProjectile() {
        //Check if enough time has passed for more projectiles to spawn
        long now = System.currentTimeMillis();
        if (now - lastUpdate < projectileDelay) {
            return;
        }
        lastUpdate = now;

        //Create new projectile with correct location relative to posVel
        int direction = shootDirection();
        if (direction >= 0) {
            Projectile projectile = new StandardProjectile(posVel, direction, projectileImage, this);
            projectiles.add(projectile);
        }
    }
    
    public int shootDirection() {
        for (int i = 0; i < 4; i++) {
            if (keyPressed[i]) {
                return i;
            }
        }
        return -1;
    }

    public void draw(Graphics2D g) {
        for (Iterator<Projectile> projectileIt = projectiles.iterator(); projectileIt.hasNext();) {
            projectileIt.next().draw(g);
        }
    }

    public void loadWeapon(int weaponKey) {
        //Load the weapon definition from Weapons.xml
        NamedNodeMap attributes = cemeteryfuntimes.Code.Shared.Utilities.loadTemplate("Weapons.xml","Weapon",weaponKey);
        //Load variables that are universal to all weapon types
        damage = Float.parseFloat(attributes.getNamedItem("Damage").getNodeValue());
        name = attributes.getNamedItem("Name").getNodeValue();
        type = Integer.parseInt(attributes.getNamedItem("Type").getNodeValue());
        enemyWeapon = Boolean.parseBoolean(attributes.getNamedItem("EnemyWeapon").getNodeValue());
        weaponLength = Integer.parseInt(attributes.getNamedItem("WeaponLength").getNodeValue());
        projectileWidth = Integer.parseInt(attributes.getNamedItem("ProjectileWidth").getNodeValue());
        projectileHeight= Integer.parseInt(attributes.getNamedItem("ProjectileHeight").getNodeValue());
        projectileOffset = Integer.parseInt(attributes.getNamedItem("ProjectileOffset").getNodeValue());
        projectileImage = cemeteryfuntimes.Code.Shared.Utilities.getScaledInstance(IMAGEPATH+attributes.getNamedItem("ProjectileImage").getNodeValue(),projectileHeight,projectileWidth);
        //Load variables that are type dependent
        if (!enemyWeapon) {
            playerImagePath = attributes.getNamedItem("PlayerImage").getNodeValue();
        }
        if (type != SINGLEBULLET) { 
            projectileSpeed = Float.parseFloat(attributes.getNamedItem("ProjectileSpeed").getNodeValue());
            projectileDelay = Integer.parseInt(attributes.getNamedItem("ProjectileDelay").getNodeValue());
            projectileSpread = Integer.parseInt(attributes.getNamedItem("ProjectileSpread").getNodeValue());
            if (singleProjectile != null) { projectiles.remove(singleProjectile); singleProjectile = null;}
        }
        if (type == SINGLEBULLET) {
            singleProjectile = new SingleProjectile(posVel,projectileImage,this);
            projectiles.add(singleProjectile); 
        }
        key = weaponKey;
    }
}
