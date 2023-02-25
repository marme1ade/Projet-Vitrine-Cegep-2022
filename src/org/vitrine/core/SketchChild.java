package org.vitrine.core;

import processing.core.PApplet;

/**
 * @author Jason
 * This class need to be extended by every child / subclass of a sketch
 * See following example
 *
 * Koch.java extends PApplet - Main class file of the sketch
 * KochFractal.java extends SketchChild - Subclass file of the Koch.java sketch
 * KochLine.java extends SketchChild - Sublass file of the Koch.java sketch
 */

public class SketchChild {
    public PApplet parent;
    public int width;
    public int height;

    public SketchChild(PApplet parent) {
        this.parent = parent;
        width = parent.width;
        height = parent.height;
    }

    public void stroke(int rgb) {
        parent.stroke(rgb);
    }

    public void line(float x1, float y1, float x2, float y2) {
        parent.line(x1, y1, x2, y2);
    }

    public float radians(float degrees) {
        return degrees * 0.017453292F;
    }
}
