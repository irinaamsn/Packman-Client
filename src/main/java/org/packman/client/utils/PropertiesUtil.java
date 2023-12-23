package org.packman.client.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
    private static final String CONFIG_FILE = "application.yml";

    private static Properties properties;

    static {
        properties = new Properties();
        loadConfig();
    }

  /*  public PropertiesUtil() {
        this.properties = new Properties();
        loadConfig();
    }*/

    private static void loadConfig() {
        try (InputStream input = PropertiesUtil.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            properties.load(input);
            for (String key : properties.stringPropertyNames()) {
                String value = properties.getProperty(key);
                if (value != null && value.startsWith("${") && value.endsWith("}")) {
                    String envVar = value.substring(2, value.length() - 1);
                    String envVarValue = System.getenv(envVar);
                    if (envVarValue != null) {
                        properties.setProperty(key, envVarValue);
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static int getPort() {
        return Integer.valueOf(properties.getProperty("PORT"));
    }

    public static String getIP() {
        return properties.getProperty("SERVER_ADDRESS");
    }

    public static int getPeriod() {
        return Integer.valueOf(properties.getProperty("PERIOD_GAME"));
    }

    public static int getTimeGame() {
        return Integer.valueOf(properties.getProperty("TIME_GAME"));
    }
}
