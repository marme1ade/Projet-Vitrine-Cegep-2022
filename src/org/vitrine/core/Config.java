package org.vitrine.core;


import lombok.Getter;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

public class Config {
    @Getter
    private static boolean kinnectInstalled;

    public static void loadConfig() {
        XMLConfiguration config;

        try {
            config = new XMLConfiguration("config.xml");
        } catch (ConfigurationException e) {
            throw new RuntimeException(e.toString());
        }

        kinnectInstalled = config.getBoolean("Sensors.kinnectInstalled", false);
    }
}
