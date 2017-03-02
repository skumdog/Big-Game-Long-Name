package cemeteryfuntimes.Code.Shared;

// @author David Kozloff & Tyler Law

public class PosVel implements Globals {
  
    protected float xPos;   
    protected float yPos;
    protected float xVel;
    protected float yVel;
    protected int xRad;
    protected int yRad;
    protected int rad;
    protected int xSide;
    protected int ySide;
    
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