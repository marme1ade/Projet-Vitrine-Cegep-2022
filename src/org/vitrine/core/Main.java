package org.vitrine.core;

import processing.core.PApplet;

import org.openkinect.processing.*;

public class Main extends PApplet {

    private Kinect2 kinect;

    public void setup() {
        if (Config.isKinnectInstalled()) {
            kinect = new Kinect2(this);
            kinect.init();
        }
    }

    public void settings() {
        size(200, 200);
    }

    public void draw() {
        background(0);
        ellipse(mouseX, mouseY, 20, 20);
    }

    public static void main(String... args) {
        Config.loadConfig();
        PApplet.main("org.vitrine.core.Main");
    }
}
