package cemeteryfuntimes.Resources.Shared;

// @author David Kozloff & Tyler Law

public class PosVel implements Globals {
  
    protected float xPos;   
    protected float yPos;
    protected float xVel;
    protected float yVel;
    protected float xRad;
    protected float yRad;
    protected float rad;
    
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
    public float xRad() {
        return xRad;
    }
    public float yRad() {
        return yRad;
    }
    public float rad() {
        return rad;
    }
    
    public boolean collide(PosVel other) {
        //Return true if the two PosVels are overlapping
        if (Math.abs(other.xPos - xPos) < other.xRad + xRad) {
            if (Math.abs(other.yPos - yPos) < other.yRad + yRad) {
                return true;
            }
        }
        return false;
    }
    
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
    
}