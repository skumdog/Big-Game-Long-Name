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
    public final static float PLAYERACCEL=.01f;
    public final static float PLAYERMAXSPEED=1;
    public final static float PLAYERDAMP=.2f;
    
    //Ballistic constants
    public final static float PROJECTILESPEED=5;
    public final static int PROJECTILELENGTH=7;
    public final static int PROJECTILEWIDTH=2;
    public final static long PROJECTILEDELAY=100;
    
    //Creature dimensions
    public final static int PLAYERSIZE=50;
    
    //Colors
    public final static Color PLAYERCOLOR=Color.BLACK;
    public final static Color PROJECTILECOLOR=Color.BLACK;
    
    //Player commands //IS THIS NECESSARY? OR SHOULD WE STICK WITH THE KEYEVENTS
    public final static int MOVELEFT=1;
    public final static int MOVERIGHT=2;
    public final static int MOVEUP=3;
    public final static int MOVEDOWN=4;
    public final static int SHOOTLEFT=5;
    public final static int SHOOTRIGHT=6;
    public final static int SHOOTUP=7;
    public final static int SHOOTDOWN=8;
    
    //Other
    public final static int TIMERDELAY=10;
    public final static int LEFTWALL=1;
    public final static int RIGHTWALL=2;
    public final static int TOPWALL=3;
    public final static int BOTTOMWALL=4;
    
}
