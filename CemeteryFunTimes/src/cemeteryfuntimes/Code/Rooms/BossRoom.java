package cemeteryfuntimes.Code.Rooms;
import cemeteryfuntimes.Code.Rooms.Room;
import cemeteryfuntimes.Code.Bosses.Boss;
import cemeteryfuntimes.Code.Pickup;
import cemeteryfuntimes.Code.Player;
import cemeteryfuntimes.Code.Shared.Globals;
import java.awt.Graphics2D;
import java.util.ArrayList;
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
    * @param bossKey The key for the boss.
    */
    public BossRoom(Player player, int bossKey) {
        super(player,BOSSROOM);
        this.boss = null;
    }
    /**
    * Updates the room.
    */
    @Override
    public void update() {
        boss.update();
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
    }
}