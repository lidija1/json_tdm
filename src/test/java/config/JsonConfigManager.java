package config;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class for reading JSON configuration files
 */
public class JsonConfigManager {
    private static final Logger logger = Logger.getLogger(JsonConfigManager.class.getName());
    private static JSONObject configCache;
    private static final String DEFAULT_CONFIG_PATH = "src/test/resources/config.json";
    
    /**
     * Gets the entire JSON configuration
     * 
     * @return The JSON configuration object
     */
    public static JSONObject getConfig() {
        if (configCache == null) {
            loadConfig();
        }
        return configCache;
    }
    
    /**
     * Gets a value from the JSON configuration
     * 
     * @param path The path to the value (e.g. "defaults.customer.country")
     * @return The value at the specified path
     */
    public static Object getValue(String path) {
        if (configCache == null) {
            loadConfig();
        }
        
        String[] parts = path.split("\\.");
        JSONObject current = configCache;
        
        // Navigate through the path except the last part
        for (int i = 0; i < parts.length - 1; i++) {
            current = (JSONObject) current.get(parts[i]);
            if (current == null) {
                logger.warning("Path not found in config: " + path);
                return null;
            }
        }
        
        // Get the final value
        return current.get(parts[parts.length - 1]);
    }
    
    /**
     * Gets a string value from the JSON configuration
     * 
     * @param path The path to the value
     * @param defaultValue Default value if not found
     * @return The string value or default if not found
     */
    public static String getString(String path, String defaultValue) {
        Object value = getValue(path);
        return value != null ? value.toString() : defaultValue;
    }
    
    /**
     * Gets a string value from the JSON configuration
     * 
     * @param path The path to the value
     * @return The string value
     * @throws RuntimeException if the value is not found
     */
    public static String getString(String path) {
        Object value = getValue(path);
        if (value == null) {
            throw new RuntimeException("Required config value not found: " + path);
        }
        return value.toString();
    }
    
    private static void loadConfig() {
        try {
            String configPath = System.getProperty("config.json.path", DEFAULT_CONFIG_PATH);
            JSONParser parser = new JSONParser();
            configCache = (JSONObject) parser.parse(new FileReader(configPath));
            logger.info("Loaded JSON config from " + configPath);
        } catch (IOException | ParseException e) {
            logger.log(Level.SEVERE, "Error loading config.json: " + e.getMessage(), e);
            throw new RuntimeException("Failed to load config.json", e);
        }
    }
} 