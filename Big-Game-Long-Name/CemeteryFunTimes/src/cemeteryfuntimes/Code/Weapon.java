package cemeteryfuntimes.Code;

import cemeteryfuntimes.Resources.Shared.*;

// @author David Kozloff & Tyler Law
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.util.ArrayList;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;

public class Weapon implements Globals {

    //Member variables
    private final ArrayList<Projectile> projectiles;
    private final boolean[] keyPressed;
    private final Player player;
    private long lastUpdate;

    //Gun definition
    private int damage;
    private String name;
    private float weaponLength;
    private float projectileSpeed;
    private int projectileWidth;
    private int projectileHeight;
    private int projectileType;
    private BufferedImage projectileImage;
    protected BufferedImage playerImage;

    public Weapon(Player player, int weaponKey) {
        this.player = player;
        loadWeapon(weaponKey);
        projectiles = new ArrayList();
        keyPressed = new boolean[4];
    }

    public void keyPressed(int direction) {
        switch (direction) {
            case KeyEvent.VK_LEFT:
                keyPressed[SHOOTLEFT] = true;
                keyPressed[SHOOTRIGHT] = false;
                keyPressed[SHOOTUP] = false;
                keyPressed[SHOOTDOWN] = false;
                break;
            case KeyEvent.VK_RIGHT:
                keyPressed[SHOOTLEFT] = false;
                keyPressed[SHOOTRIGHT] = true;
                keyPressed[SHOOTUP] = false;
                keyPressed[SHOOTDOWN] = false;
                break;
            case KeyEvent.VK_UP:
                keyPressed[SHOOTLEFT] = false;
                keyPressed[SHOOTRIGHT] = false;
                keyPressed[SHOOTUP] = true;
                keyPressed[SHOOTDOWN] = false;
                break;
            case KeyEvent.VK_DOWN:
                keyPressed[SHOOTLEFT] = false;
                keyPressed[SHOOTRIGHT] = false;
                keyPressed[SHOOTUP] = false;
                keyPressed[SHOOTDOWN] = true;
                break;
        }
    }

    public void keyReleased(int direction) {
        switch (direction) {
            case KeyEvent.VK_LEFT:
                keyPressed[SHOOTLEFT] = false;
                break;
            case KeyEvent.VK_RIGHT:
                keyPressed[SHOOTRIGHT] = false;
                break;
            case KeyEvent.VK_UP:
                keyPressed[SHOOTUP] = false;
                break;
            case KeyEvent.VK_DOWN:
                keyPressed[SHOOTDOWN] = false;
                break;
        }
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
        if (now - lastUpdate < PROJECTILEDELAY) {
            return;
        }
        lastUpdate = now;

        //Create new projectile with correct location relative to player
        for (int i = 0; i < 4; i++) {
            if (keyPressed[i]) {
                //If a key is currently pressed generate a projectile moving in that direction
                Projectile projectile = new Projectile(player, i);
                projectiles.add(projectile);
                return;
            }
        }
    }

    public void draw(Graphics g) {
        projectiles.stream().forEach((projectile) -> {
            projectile.draw(g);
        });
    }

    private void loadWeapon(int weaponKey) {
        try {
            FileInputStream fileInputStream = new FileInputStream(TEMPLATEPATH + "Weapons.xml");
            XMLStreamReader xmlStreamReader = XMLInputFactory.newInstance().createXMLStreamReader(fileInputStream);
            int eventCode;
            while (xmlStreamReader.hasNext()) {
                eventCode = xmlStreamReader.next();
                if ((XMLStreamConstants.START_ELEMENT == eventCode)&& xmlStreamReader.getLocalName().equalsIgnoreCase("weapons")) {
                    while (xmlStreamReader.hasNext()) {
                        eventCode = xmlStreamReader.next();
                        if ((XMLStreamConstants.END_ELEMENT == eventCode)&& xmlStreamReader.getLocalName().equalsIgnoreCase("weapons")) {
                            break;
                        }
                        if ((XMLStreamConstants.START_ELEMENT == eventCode) && xmlStreamReader.getLocalName().equalsIgnoreCase("weapon")) {
                            int key = Integer.parseInt(xmlStreamReader.getAttributeValue(0));
                            if (key == weaponKey) {
                                int attributesCount = xmlStreamReader.getAttributeCount();
                                for (int i = 1; i < attributesCount; i++) {
                                    switch (xmlStreamReader.getAttributeLocalName(i)) {
                                        case "Damage":
                                            damage = Integer.parseInt(xmlStreamReader.getAttributeValue(i));
                                            break;
                                    }
                                }
                                break;
                            }
                        }
                    }
                }
            }
            //Look through Weapons.xml until you find weapon with ID = weaponKey
        } catch (Exception ex) {
            System.out.printf("Hey bud, that's a swell error message.");
        }
        setupImages();
    }

    public void setupImages() {

    }

    //Retrieve private variables
    public ArrayList<Projectile> Projectiles() {
        return projectiles;
    }

    public int Damage() {
        return damage;
    }

    public BufferedImage PlayerImage() {
        return playerImage;
    }

}
