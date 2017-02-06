// Bouncing Ball by Tyler Law
// Assignment 1
// CSCI 5611
//
// Based on Professor Guy's bouncing ball code from lecture.

float radius = 20; 
float floor = 396;
float acceleration = 9.8;
float dt = 0.2;
Ball ball;

void setup() {
  size(400, 400, P3D);
  surface.setTitle("Bouncing Ball");
  noStroke();
  lights();
  ball = new Ball(200, 200, 0);
}  

class Ball {
  float yPos;
  float xPos;
  float velocity;  
  
  Ball (float xPosInit, float yPosInit, float velocityIn) {
    xPos = xPosInit;
    yPos = yPosInit;
    velocity = velocityIn;
  }
    
  void moveBall() {  
    velocity = velocity + acceleration * dt;  
    yPos = yPos + velocity * dt;  
    if (yPos + radius > floor) {  
      yPos = floor - radius;  
      velocity *= -.8;
    }
  }
  
  void drawBall () {
    fill(0, 200, 10);
    translate(xPos, yPos);
    sphere(radius);
  }
}

void draw() {
  background(0, 0, 0);
  ball.drawBall();
  ball.moveBall();
}