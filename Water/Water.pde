// Water by Tyler Law
// Assignment 1
// CSCI 5611

import java.util.ArrayList;

int count = 100;
float velocity = 5.0;
float bounce = -0.55;
float maxLife = 11.0;
float radius = 50;
float dt = 0.2;
float floor = 395;  
float acceleration = 9.8;
ArrayList<Float> posX;
ArrayList<Float> posY;
ArrayList<Float> velY;
ArrayList<Float> life;

void setup() {
  size(800, 800, P2D);
  surface.setTitle("Water");
  stroke(173, 216, 230, 50);
  strokeWeight(9);
  posX = new ArrayList<Float>();
  posY = new ArrayList<Float>();
  velY = new ArrayList<Float>();
  life = new ArrayList<Float>();
}
  
void draw() {  
  background(0, 0, 0);
  spawnParticles();
  drawParticles();
  moveParticles();
  killParticles();
}

void spawnParticles() {
    for (int i = 0; i < count; i++) {
      float r = radius * sqrt(random(0, 1));
      float theta = 2 * PI * random(0, 2*PI);
      posX.add(r * sin(theta) + 200);
      posY.add(r * cos(theta) + 200);
      velY.add(0.0);
      life.add(0.0);
  }
}

void drawParticles() {
  for (int i = 0; i < velY.size(); i++) {
    point(posX.get(i), posY.get(i));
  }
}

void moveParticles() {  
  for (int i = 0; i < velY.size(); i++) {
    velY.set(i, velY.get(i) + acceleration * dt);
    posY.set(i, posY.get(i) + velY.get(i) * dt);
    posX.set(i, posX.get(i) + velocity);
    life.set(i, life.get(i) + dt);
    if (posY.get(i) > floor) {
      posY.set(i, floor);
      velY.set(i, velY.get(i) * bounce);
    }
  }
}

void killParticles() {
  for (int i = 0; i < velY.size(); i++) {
    if (life.get(i) > maxLife) {
      life.remove(i);
      posX.remove(i);
      posY.remove(i);
      velY.remove(i);
    }
  }
}