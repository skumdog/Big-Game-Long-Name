// Magic Spell by Tyler Law
// Assignment 1
// CSCI 5611

import java.util.ArrayList;

int count = 250;
float maxLife = 8.0;
float radius = 50;
float dt = 0.2;
float acceleration = 9.8;
ArrayList<Float> posX;
ArrayList<Float> posY;
ArrayList<Float> velY;
ArrayList<Float> life;
int blue = 0;

void setup() {
  size(800, 800, P2D);
  surface.setTitle("Magic Spell");
  strokeWeight(8);
  posX = new ArrayList<Float>();
  posY = new ArrayList<Float>();
  velY = new ArrayList<Float>();
  life = new ArrayList<Float>();
}

void draw() {  
  background(0, 0, 0);
  spawnParticles();
  if (millis() > 1500) {
    drawParticles();
  }
  moveParticles();
  killParticles();
  
  // Frame rate benchmark
  println("FPS:", 1000*frameCount/millis());
  
  // Number of particles currently displayed
  println("Number of particles:", posX.size());
}

void keyPressed() {
  if (key == 'w') {
    if (blue != 250) {
      blue = blue + 50;
    }
  } 
  if (key == 's') {
    if (blue != 0) {
      blue = blue - 50;
    }
  }
}

void spawnParticles() {
  for (int i = 0; i < count; i++) {
    float r = radius * sqrt(random(0, 1));
    float theta = 2 * PI * random(0, 2*PI);
    posX.add(r * sin(theta) + 400);
    posY.add(r * cos(theta) + 250);
    velY.add(0.0);
    life.add(0.0);
  }
}

void drawParticles() {
  for (int i = 0; i < velY.size(); i++) {
    if ((life.get(i) > 0) && (life.get(i) < 3)) {
      stroke(255, 45, blue, 100);
    } else if (life.get(i) < 5) {
      stroke(255, 171, blue, 100);
    } else if (life.get(i) < 6) {
      stroke(255, 129, blue, 100);
    } else if (life.get(i) < 7) {
      stroke(255, 87, blue, 100);
    } else if (life.get(i) < 8) {
      stroke(255, 45, blue, 100);
    } else if (life.get(i) < 10) {
      stroke(255, 0, blue, 100);
    }
    point(posX.get(i), posY.get(i));
  }
}

void moveParticles() {  
  for (int i = 0; i < velY.size(); i++) {
    velY.set(i, velY.get(i) + acceleration * dt);
    posY.set(i, posY.get(i) + velY.get(i) * dt);
    posX.set(i, posX.get(i) + random(-5.5, 5.5));
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