package org.vitrine.common;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;
import org.vitrine.core.Sketch;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    /**
     * Get fractal sketch list (include sub package in name, ex: examples.Kock.X)
     * To get only the name, use getFractalsList()
     * @return A list of string
     */
    public static List<String> getFractalsSketchList() {
        List<String> fractals = new ArrayList<String>();

        try (ScanResult scanResult = new ClassGraph().acceptPackages("org.vitrine")
                .enableClassInfo().scan()) {

            for (ClassInfo _class : scanResult.getAllClasses()) {
                if (_class.extendsSuperclass(Sketch.class)) {
                    fractals.add(_class.getName().replace("org.vitrine.sketchs.", ""));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }

        return fractals;
    }

    /**
     * Get fractal list
     * @return Name of fractals
     */
    public static List<String> getFractalsList() {
        List<String> fractals = new ArrayList<String>();

        for (String sketch : getFractalsSketchList()) {
            String[] withoutDots = sketch.split("\\.");
            fractals.add(withoutDots[withoutDots.length-1]);
        }

        return fractals;
    }
}
