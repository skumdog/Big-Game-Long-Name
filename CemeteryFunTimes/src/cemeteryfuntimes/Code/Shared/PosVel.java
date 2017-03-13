package cemeteryfuntimes.Code.Shared;

// @author David Kozloff & Tyler Law

public abstract class PosVel implements Globals {
  
    //Member variables
    protected float xPos;   
    protected float yPos;
    protected float xVel;
    protected float yVel;
    protected int xRad;
    protected int yRad;
    protected int rad;
    protected float xSide;
    protected float ySide;
    protected double rotation;
    
    //Getters 
    public float xPos() {
        return xPos;
    }
    public float yPos() {
        return yPos;
    }
    public float xVel() {
        return xVel;
    }
    public float yVel() {
        return yVel;
    }
    public int xRad() {
        return xRad;
    }
    public int yRad() {
        return yRad;
    }
    public int rad() {
        return rad;
    }
    public double rotation() {
        return rotation;
    }
    
    public boolean collide(PosVel other) {
        //Return true if the two PosVels are overlapping
        if (Math.abs(other.xPos+other.xVel - (xPos+xVel)) < other.xRad + xRad) {
            if (Math.abs(other.yPos+other.yVel - (yPos+yVel)) < other.yRad + yRad) {
                return true;
            }
        }
        return false;
    }
    
    public void damaged(float damage) {}
    
    public int sideCollided(PosVel other) {
        //Return an integer representing which wall is was the collision point
        //Assumes the two object are colliding (i.e. collide returned true)
        float xDiff = xPos - other.xPos;
        float yDiff = yPos - other.yPos;
        float absXDiff = Math.abs(xDiff);
        float absYDiff = Math.abs(yDiff);
        if (absXDiff > absYDiff) {
           if (xDiff < 0) return RIGHTWALL;
           else return LEFTWALL;
        } 
        else {
           if (yDiff < 0) return BOTTOMWALL;
           else return TOPWALL;
        }
    }
    
    //public abstract void damaged(PosVel posVel, int type);
    
}