package cemeteryfuntimes.Code.Weapons;

import cemeteryfuntimes.Code.Player;
import cemeteryfuntimes.Code.Shared.Globals;

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
    private final Player player;
    private long lastUpdate;

    //Gun definition
    private float damage;
    public float Damage() {
        return damage;
    }
    private final int key;
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
    private String playerImagePath;
    public String PlayerImagePath() {
        return playerImagePath;
    }

    public Weapon(Player player, int weaponKey) {
        this.player = player;
        loadWeapon(weaponKey);
        key = weaponKey;
        projectiles = new ArrayList();
        keyPressed = new boolean[4];
        if (type == SINGLEBULLET) { projectiles.add(new SingleProjectile(player,projectileImage,this)); }
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
        if (type != 2) {createProjectile();}
        Projectile projectile;
        for (Iterator<Projectile> projectileIt = projectiles.iterator(); projectileIt.hasNext();) {
            projectile =projectileIt.next();
            if (type != 2 && projectile.collide) { projectileIt.remove(); break; }
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

        //Create new projectile with correct location relative to player
        int direction = shootDirection();
        if (direction >= 0) {
            Projectile projectile = new StandardProjectile(player, direction, projectileImage, this);
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

    private void loadWeapon(int weaponKey) {
        //Load the weapon definition from Weapons.xml
        NamedNodeMap attributes = cemeteryfuntimes.Code.Shared.Utilities.loadTemplate("Weapons.xml","Weapon",weaponKey);
        //Load variables that are universal to all weapon types
        damage = Float.parseFloat(attributes.getNamedItem("Damage").getNodeValue());
        name = attributes.getNamedItem("Name").getNodeValue();
        type = Integer.parseInt(attributes.getNamedItem("Type").getNodeValue());
        weaponLength = Integer.parseInt(attributes.getNamedItem("WeaponLength").getNodeValue());
        projectileWidth = Integer.parseInt(attributes.getNamedItem("ProjectileWidth").getNodeValue());
        projectileHeight= Integer.parseInt(attributes.getNamedItem("ProjectileHeight").getNodeValue());
        projectileOffset = Integer.parseInt(attributes.getNamedItem("ProjectileOffset").getNodeValue());
        playerImagePath = attributes.getNamedItem("PlayerImage").getNodeValue();
        projectileImage = cemeteryfuntimes.Code.Shared.Utilities.getScaledInstance(IMAGEPATH+attributes.getNamedItem("ProjectileImage").getNodeValue(),projectileHeight,projectileWidth);
        //Load variables that are type dependent
        if (type !=SINGLEBULLET) { 
            projectileSpeed = Float.parseFloat(attributes.getNamedItem("ProjectileSpeed").getNodeValue());
            projectileDelay = Integer.parseInt(attributes.getNamedItem("ProjectileDelay").getNodeValue());
        }
    }

}
