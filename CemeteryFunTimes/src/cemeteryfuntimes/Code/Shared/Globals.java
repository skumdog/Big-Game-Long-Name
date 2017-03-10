package cemeteryfuntimes.Code.Shared;

// @author David Kozloff & Tyler Law

public interface Globals {
    
    //Game dimensions
    public final static int SCREENHEIGHT=800;
    public final static int SCREENWIDTH=1200;
    public final static int GAMEHEIGHT=800;
    public final static int GAMEWIDTH=800;
    public final static int GAMEBORDER=(SCREENWIDTH-GAMEWIDTH)/2;
    
    //Movement constants
    public final static float PLAYERACCEL=.2f;
    public final static float PLAYERMAXSPEED=1;
    public final static float PLAYERDAMP=.05f; //Friction coeffecient
    public final static float PLAYERCOLLISIONVEL=3f; //Player accel upon enemy collision
    
    //Ballistic constants
    public final static float PROJECTILEPLAYERBOOST=.25f;  //This constant represents the fraction of the players velocity that is added to the projectile speed
    
    //Creature dimensions
    public final static int PLAYERSIZE=100;
    
    //Player commands
    public final static int LEFT=0;
    public final static int RIGHT=1;
    public final static int UP=2;
    public final static int DOWN=3;
    public final static double[] ROTATION = {
        0,Math.PI,Math.PI/2,3*Math.PI/2
    };  
    // probably delete all of these... switch to just using LEFT RIGHT UP and DOWN
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
    public final static long INVINCFRAMES=1000;
    public final static int LEFTWALL=0;
    public final static int RIGHTWALL=1;
    public final static int TOPWALL=2;
    public final static int BOTTOMWALL=3;
    public final static double MINIMUMROTATION=0.0174533; //1 degrees in radians
    public final static String IMAGEPATH="src/cemeteryfuntimes/Resources/Images/";
    public final static String TEMPLATEPATH="src/cemeteryfuntimes/Resources/Templates/";
    
    //Weapons
    public final static int PISTOL=1;
    public final static int MACHINEGUN=2;
    public final static int SHOTGUN=3;
    public final static int ROCKETLAUNCHER=4;
    public final static int FLAMETHROWER=5;
    public final static int LASER=6;
    
    //Weapon Types
    public final static int REGULARBALLISTIC=1; //Simple firearms: pistol, machinegun, etc.
    public final static int SINGLEBULLET=2; //Firearms that only have one projectile: flamethrower, single solid laser
    
    //Action Types
    //These represent the various actions that a user can do in the game
    public final static int MOVEMENT=1;
    public final static int SHOOT=2;
    public final static int CHANGEWEAPON=3;
    public final static int GAME=4; //Place holder, things like pausing? 
    
    /*//Damage Type
    public final static int CONTACTDAMAGE=1;
    public final static int PROJECTILEDAMAGE=2;*/
    
}
