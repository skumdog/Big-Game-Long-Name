package cemeteryfuntimes.Code;
import cemeteryfuntimes.Code.Shared.Globals;
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
    private final ArrayList<Pickup> pickups;
    public ArrayList<Pickup> getPickups() {
        return pickups;
    }
    /**
    * BossRoom class constructor initializes variables related to boss rooms.
    * 
    * @param player The player.
    * @param boss   The boss object. 
    */
    public BossRoom(Player player, Boss boss) {
        super(player);
        this.boss = boss;
        pickups = new ArrayList();
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
}