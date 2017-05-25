package cemeteryfuntimes.Code.Rooms;
import cemeteryfuntimes.Code.Bosses.*;
import cemeteryfuntimes.Code.Player;
import cemeteryfuntimes.Code.Shared.Globals;
import java.awt.Graphics2D;
import java.util.Random;
/**
* BossRoom class contains variables and methods related to boss rooms.
* @author David Kozloff & Tyler Law
*/
public final class BossRoom extends Room implements Globals {
    private final Boss boss;
    public Boss getBoss() {
        return boss;
    }
    /**
    * BossRoom class constructor initializes variables related to boss rooms.
    * 
    * @param player  The player.
    */
    public BossRoom(Player player) {
        super(player,BOSSROOM);
        Random random = new Random();
        int bossKey = random.nextInt(BOSSES);
        switch(bossKey) {
            case BATLORD: 
                boss = new BatLord(player,this);
                break;
            case GHOULIE:
                boss = new Ghoulie(player);
                break;
            default:
                boss = null;
                break;
        }
    }
    /**
    * Updates the room.
    */
    @Override
    public void update() {
        boss.update();
        super.update();
    }
    /**
    * Determines if a room has been cleared, which is determined by the
    * specific room type.
    * 
    * @return A boolean indicating if the room has been cleared.
    */
    @Override
    public boolean RoomClear() {
        return boss.isDead();
    }

    @Override
    public void draw(Graphics2D g) {
        boss.draw(g); 
        super.draw(g);
    }
}