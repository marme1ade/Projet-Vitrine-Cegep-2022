package org.vitrine.sketchs.examples.koch;

import org.vitrine.core.Sketch;

/**
 * Koch Curve
 * by Daniel Shiffman.
 * <p>
 * Renders a simple fractal, the Koch snowflake.
 * Each recursive level is drawn in sequence.
 */

public class Koch extends Sketch {

    KochFractal k;

    public void setup() {
        frameRate(1);  // Animate slowly
        k = new KochFractal(this); // Pass the parent into the KockFractal constructor
    }

    /**
     * Use size() inside the settings() method instead of setup()
     */
    public void settings() {
        size(640, 360);
    }

    public void draw() {
        background(0);
        // Draws the snowflake!
        k.render();
        // Iterate
        k.nextLevel();
        // Let's not do it more than 5 times. . .
        if (k.getCount() > 5) {
            k.restart();
        }
    }
}






