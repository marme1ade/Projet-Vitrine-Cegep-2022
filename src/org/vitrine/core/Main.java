package org.vitrine.core;

import processing.core.PApplet;

import java.lang.reflect.Constructor;

public class Main {

    public static void main(String... args) {
        Config.loadConfig();
        Main.loadSketch("examples.koch.Koch");
    }

    /**
     * Load a sketch
     * @param sketchName Reference of the sketch class
     */
    private static void loadSketch(String sketchName) {
        String completeSketchReference = "org.vitrine.sketchs." + sketchName;

        try {
            Class<?> c = Class.forName(completeSketchReference);
            Constructor<?> cons = c.getConstructor();
            PApplet sketch = (PApplet) cons.newInstance();

            PApplet.runSketch(new String[]{sketch.getClass().getSimpleName()}, sketch);
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }
    }
}
