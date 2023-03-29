package org.vitrine.core.api;

import org.vitrine.common.Utils;
import org.vitrine.core.Console;
import org.vitrine.core.Main;

import java.io.IOException;

public class Parser {
    /**
     * Parse a API query
     * @param query Query string to process
     * @return API response
     */
    public static String parseQuery(String query) {
        String response = "";
        String[] queryData = query.split("\\|");

        if (queryData.length < 1) return response;

        switch (queryData[0].toUpperCase()) {
            case "CMD": // Execute a shell command (CMD|Commands)
                if (queryData.length >= 2) {
                    response = executeCommand(queryData[1]) ? "OK" : "ERROR";
                }
                break;
            case "LFRACTALS": // Get the list of loaded fractals
                response = "LFRACTALS|" + getFractalsListString();
                break;
            case "TVSTATUS": // Get if tv is ready or not
                response = "TVSTATUS|" + (Main.tv.isInUse() ? 0 : 1); // 1 : ready
                break;
            case "TVCONNECT": // Create a new TV session if possible (TVCONNECT|code)
                if (queryData.length == 2) {
                    response = "TVCONNECT|" + Main.tv.connect(queryData[1]);
                }
                break;
            case "TVSESSIONSTATUS": // Check and update TV session activity (TVSESSIONSTATUS|sessionId|sessionTotp)
                if (queryData.length == 3) {
                    response = "TVSESSIONSTATUS|" + (Main.tv.isTvSessionValid(queryData[1], queryData[2]) ? 1 : 0);
                }
                break;
            case "PLAYF":
                if (queryData.length == 4) { // Play a sketch (PLAYF|sketchName|tvSessionId|tvSessionCode)
                    response = "PLAYF|" + ((playFractalSketch(queryData[1], queryData[2], queryData[3])) ? 1 : 0);
                }
                break;
            case "TEST": // Test api connection
                response = "OK";
                break;
            default:
                response = "UNSUPPORTED";
                break;
        }

        return response;
    }

    /**
     * Execute a shell command
     * @param cmd Command top execute
     * @return Return whether an exception occurred or not
     */
    private static boolean executeCommand(String cmd) {
        try {
            Runtime.getRuntime().exec(cmd);
        } catch (IOException e) {
            return false;
        }

        return true;
    }

    /**
     * Get a string that contains all the fractals name
     * @return String with fractals name separated by ;
     */
    private static String getFractalsListString() {
        String s = "";

        for (String fractalName : Utils.getFractalsList()) {
            s += fractalName + ";";
        }

        return s.substring(0, s.length() - 1); // We remove the last ; separator from the string
    }

    private static boolean playFractalSketch(String sketchName, String tvSessionId, String tvSessionCode) {

        if (!Main.tv.isTvSessionValid(tvSessionId, tvSessionCode)) {
            return false;
        }

        if (!Utils.FractalSketchExist(sketchName)) {
            return false;
        }

        String completeSketchName = Utils.findCompleteSketchName(sketchName);
        if (completeSketchName.isEmpty()) {
            Console.println("2", Console.Color.RED);
           return false;
        }

        Console.println(completeSketchName, Console.Color.RED);
        return Main.loadSketch(completeSketchName);
    }
}
