package cemeteryfuntimes.Resources.Shared;

// @author David Kozloff & Tyler Law

import java.awt.Color;


public interface Globals {
    
    //Game dimensions
    public final static int SCREENHEIGHT=800;
    public final static int SCREENWIDTH=1200;
    public final static int GAMEHEIGHT=800;
    public final static int GAMEWIDTH=800;
    public final static int GAMEBORDER=(SCREENWIDTH-GAMEWIDTH)/2;
    
    //Movement constants
    public final static float PLAYERACCEL=.1f;
    public final static float PLAYERMAXSPEED=1;
    public final static float PLAYERDAMP=.02f; //Friction coeffection
    
    //Ballistic constants
    public final static float PROJECTILESPEED=10;
    public final static int PROJECTILELENGTH=7;
    public final static int PROJECTILEWIDTH=2;
    public final static long PROJECTILEDELAY=300;
    public final static float PROJECTILEPLAYERBOOST=.25f;  //This constant represents the fraction of the players velocity that is added to the projectile speed
    
    //Creature dimensions
    public final static int PLAYERSIZE=50;
    
    //Colors
    public final static Color PLAYERCOLOR=Color.BLACK;
    public final static Color PROJECTILECOLOR=Color.BLACK;
    
    //Player commands //IS THIS NECESSARY? OR SHOULD WE STICK WITH THE KEYEVENTS
    public final static int MOVELEFT=0;
    public final static int MOVERIGHT=1;
    public final static int MOVEUP=2;
    public final static int MOVEDOWN=3;
    public final static int SHOOTLEFT=0;
    public final static int SHOOTRIGHT=1;
    public final static int SHOOTUP=2;
    public final static int SHOOTDOWN=3;
    
    //Other
    public final static int TIMERDELAY=10;
    public final static int LEFTWALL=0;
    public final static int RIGHTWALL=1;
    public final static int TOPWALL=2;
    public final static int BOTTOMWALL=3;
    
}
