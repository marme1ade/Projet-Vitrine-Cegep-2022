package org.vitrine.core;

import processing.awt.PSurfaceAWT;
import processing.core.PApplet;

import java.awt.*;

public class Sketch extends PApplet {
    /**
     * Close the sketch without exiting the application
     */
    public void close() {
        Frame frame = ((PSurfaceAWT.SmoothCanvas) surface.getNative()).getFrame();
        dispose(); // Dispose the Processing applet
        frame.dispose(); // Dispose the AWT interface
    }
}
