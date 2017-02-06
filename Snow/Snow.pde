// Snow by Tyler Law
// Assignment 1
// CSCI 5611

import java.util.ArrayList;

int count = 5;
float maxLife = 40.0;
float minVelocity = 15;
float maxVelocity = 30;
float dt = 0.1;
ArrayList<Float> posX;
ArrayList<Float> posY;
ArrayList<Float> velY;
ArrayList<Float> life;
  
void setup() {
  size(800, 800, P2D);
  surface.setTitle("Snow");
  stroke(173, 216, 230, 50);
  strokeWeight(9);
  posX = new ArrayList<Float>();
  posY = new ArrayList<Float>();
  velY = new ArrayList<Float>();
  life = new ArrayList<Float>();
  
  for (int i = 0; i < 2000; i++) {
    posX.add(random(0, 800));
    posY.add((float) i / 2.3);
    velY.add(random(minVelocity, maxVelocity));
    life.add((float) i / 50);
  }
}

void draw() {  
  background(0, 0, 0);
  drawParticles();
  spawnParticles();
  moveParticles();
  killParticles();
}

void spawnParticles() {
    for (int i = 0; i < count; i++) {
      posX.add(random(800));
      posY.add(0.0);
      velY.add(random(minVelocity, maxVelocity));
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
    posY.set(i, posY.get(i) + velY.get(i) * dt);
    posX.set(i, posX.get(i) + (noise(posX.get(i)) * random(-10, 10)));
    life.set(i, life.get(i) + dt);
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