package org.vitrine.core.api;

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
}
