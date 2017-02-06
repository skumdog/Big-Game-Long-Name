// Fire by Tyler Law
// Assignment 1
// CSCI 5611
//
// Fire sound effect provided by "Partners In Rhyme" via freesoundeffects.com
// https://www.freesoundeffects.com/free-track/fire2-89307/
//
// Sound loading code provided by the Java Processing libraries reference:
// https://processing.org/reference/libraries/sound/SoundFile.html

import java.util.ArrayList;
import processing.sound.*;

SoundFile file;
int count = 150;
int start = 0;
float maxLife = 2.5;
float minVelocity = 15;
float maxVelocity = 30;
float dt = 0.1;
boolean once = false;
ArrayList<Float> posX;
ArrayList<Float> posY;
ArrayList<Float> velY;
ArrayList<Float> life;

void setup() {
  size(800, 800, P2D);
  surface.setTitle("Fire");
  strokeWeight(9);
  fill(255, 255, 0);
  posX = new ArrayList<Float>();
  posY = new ArrayList<Float>();
  velY = new ArrayList<Float>();
  life = new ArrayList<Float>();
  file = new SoundFile(this, "fire2.mp3");
}

void draw() {
  background(0, 0, 0);
  if (millis() > 1500) {
    drawParticles();
    fireBase();
    if (once == false) {
      file.play();
      once = true;
    }
  }
  spawnParticles();
  moveParticles();
  killParticles();
  loopSound();
}

void loopSound() {
  int now = millis();
  if ((now - start > 2700) && (millis() > 4300)) {
    file.loop();
    start = millis();
  }
}

void spawnParticles() {
    for (int i = 0; i < count; i++) {
      float x = random(300, 400) + 100;
      float y = x + 200;
      posX.add(x);
      posY.add(y);
      velY.add(random(minVelocity, maxVelocity));
      life.add(0.0);
      float x2 = random(400, 500) - 100;
      float y2 = -x2 + 1000;
      posX.add(x2);
      posY.add(y2);
      velY.add(random(minVelocity, maxVelocity));
      life.add(0.0);
  }
}

void fireBase() {
  strokeWeight(25);
  line(400, 600, 475, 675);
  line(325, 675, 400, 600);
  noStroke();
  beginShape();
  vertex(300, 700);
  vertex(500, 700);
  vertex(400, 600);
  endShape();
  strokeWeight(9);
}

void drawParticles() {
  for (int i = 0; i < velY.size(); i++) {
    if (life.get(i) < 0.5) {
      stroke(255, 255, 0, 150);
    } else if (life.get(i) < 1.25) {
      stroke(255, 125, 0, 100);
    } else if (life.get(i) >= 1.25) {
      stroke(255, 0, 0, 50);
    }
    point(posX.get(i), posY.get(i));
  }
}

void moveParticles() {
  for (int i = 0; i < velY.size(); i++) {
    posY.set(i, posY.get(i) - velY.get(i) * dt);
    posX.set(i, posX.get(i) + (noise(posX.get(i)) * random(-8, 8)));
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