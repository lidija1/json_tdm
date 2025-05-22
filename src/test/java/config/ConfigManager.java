package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConfigManager {
    private static final Logger logger = Logger.getLogger(ConfigManager.class.getName());
    private static final Properties properties = new Properties();
    private static boolean isLoaded = false;

    private static void loadProperties() {
        if (!isLoaded) {
            try {
                String configPath = System.getProperty("config.path", "src/test/resources/config.properties");
                properties.load(new FileInputStream(configPath));
                isLoaded = true;
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Failed to load config.properties: " + e.getMessage(), e);
                throw new RuntimeException("Failed to load config.properties", e);
            }
        }
    }

    public static String getProperty(String key) {
        loadProperties();
        String value = properties.getProperty(key);
        if (value == null) {
            logger.log(Level.SEVERE, "Property not found: " + key);
            throw new RuntimeException("Property not found: " + key);
        }
        return value;
    }

    public static String getProperty(String key, String defaultValue) {
        loadProperties();
        return properties.getProperty(key, defaultValue);
    }
} 