package org.vitrine.core.api;

import org.vitrine.common.Utils;

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
            case "LFRACTALS":
                response = "LFRACTALS|" + getFractalsListString();
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
}
