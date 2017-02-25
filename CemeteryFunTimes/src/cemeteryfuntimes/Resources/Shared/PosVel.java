package cemeteryfuntimes.Resources.Shared;

// @author David Kozloff & Tyler Law

public class PosVel {
  
    public float xPos;   
    public float yPos;
    public float xVel;
    public float yVel;
 
    public PosVel(float xPos, float yPos, float xVel, float yVel){
        this.xPos = xPos;
        this.yPos = yPos;
        this.xVel = xVel;
        this.yVel = yVel;
    }
    
    public PosVel() {
        xPos = 0;
        yPos = 0;
        xVel = 0;
        yVel = 0;
    }
    
    public void update() {
        xPos += xVel;
        yPos += yVel;
    }
    
    public float totalSpeed() {
        return (float) Math.sqrt(xVel*xVel + yVel*yVel);
    }
}