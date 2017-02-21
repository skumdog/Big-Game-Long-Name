package cemeteryfuntimes.Code;

import cemeteryfuntimes.Resources.Shared.*;
import java.awt.Graphics;

// @author David Kozloff & Tyler Law

public class Enemy extends PosVel implements Globals {
    
    public int health = 800;
    private final Player player;
    
    private final float xSide;
    private final float ySide;
    
    public Enemy(Player player, int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.player = player;
        rad = ENEMYSIZE/2;
        xSide = GAMEBORDER - rad;
        ySide = - rad;
    }
    
    public void update() {
        xPos += xVel;
        yPos += yVel;
    }
    
    public void draw(Graphics g) {
        g.setColor(ENEMYCOLOR);
        g.fillRect(Math.round(xSide+xPos),Math.round(ySide+yPos),ENEMYSIZE,ENEMYSIZE);
    }
    
}
