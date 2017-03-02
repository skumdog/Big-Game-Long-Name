package cemeteryfuntimes.Code;

import cemeteryfuntimes.Code.Shared.Globals;
import static cemeteryfuntimes.Code.Shared.Globals.IMAGEPATH;

// @author David Kozloff & Tyler Law

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
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
    private int damage;
    public int Damage() {
        return damage;
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
    private int projectileType;
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
        projectiles = new ArrayList();
        keyPressed = new boolean[4];
    }

    public void keyPressed(int direction) {
        keyPressed[SHOOTLEFT] = false;
        keyPressed[SHOOTRIGHT] = false;
        keyPressed[SHOOTUP] = false;
        keyPressed[SHOOTDOWN] = false;
        keyPressed[direction] = true;
    }

    public void keyReleased(int direction) {
        keyPressed[direction] = false;
    }

    public void update() {
        createProjectile();
        for (int i = 0; i < projectiles.size(); i++) {
            projectiles.get(i).update();
            if (projectiles.get(i).hitWall()) {
                projectiles.remove(i);
                i--;
            }
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
        for (int i = 0; i < 4; i++) {
            if (keyPressed[i]) {
                //If a key is currently pressed generate a projectile moving in that direction
                Projectile projectile = new Projectile(player, i, projectileImage, this);
                projectiles.add(projectile);
                return;
            }
        }
    }

    public void draw(Graphics2D g) {
        projectiles.stream().forEach((projectile) -> {
            projectile.draw(g);
        });
    }

    private void loadWeapon(int weaponKey) {
        //Load the weapon definition from Weapons.xml
        NamedNodeMap attributes = cemeteryfuntimes.Code.Shared.Utilities.loadTemplate("Weapons.xml","Weapon",weaponKey);
        damage = Integer.parseInt(attributes.getNamedItem("Damage").getNodeValue());
        name = attributes.getNamedItem("Name").getNodeValue();
        weaponLength = Integer.parseInt(attributes.getNamedItem("WeaponLength").getNodeValue());
        projectileSpeed = Float.parseFloat(attributes.getNamedItem("ProjectileSpeed").getNodeValue());
        projectileWidth = Integer.parseInt(attributes.getNamedItem("ProjectileWidth").getNodeValue());
        projectileHeight= Integer.parseInt(attributes.getNamedItem("ProjectileHeight").getNodeValue());
        projectileType = Integer.parseInt(attributes.getNamedItem("ProjectileType").getNodeValue());
        projectileDelay = Integer.parseInt(attributes.getNamedItem("ProjectileDelay").getNodeValue());
        projectileOffset = Integer.parseInt(attributes.getNamedItem("ProjectileOffset").getNodeValue());
        playerImagePath = attributes.getNamedItem("PlayerImage").getNodeValue();
        projectileImage = cemeteryfuntimes.Code.Shared.Utilities.getScaledInstance(IMAGEPATH+attributes.getNamedItem("ProjectileImage").getNodeValue(),projectileHeight,projectileWidth,RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR,false);
    }

}
