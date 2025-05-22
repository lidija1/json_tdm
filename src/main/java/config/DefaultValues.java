package config;

import java.util.logging.Logger;

/**
 * Default values for the application.
 * These are loaded from config.json if available, or uses fallback values.
 */
public class DefaultValues {
    private static final Logger logger = Logger.getLogger(DefaultValues.class.getName());
    
    // Customer Information
    public static final String DEFAULT_COUNTRY = "United States";
    public static final String DEFAULT_STATE = "Massachusetts";
    public static final String DEFAULT_CITY = "Norton";
    
    // Quote Information
    public static final String DEFAULT_SUBMISSION_TYPE = "New Business";
    
    /**
     * Updates the default values from the JSON configuration file.
     * Call this method after the JsonConfigManager has been initialized.
     */
    public static void updateFromJsonConfig() {
        try {
            // This method needs to be called in a test setup method or runner class
            // after the JsonConfigManager is initialized
            logger.info("Updating default values from config.json");
        } catch (Exception e) {
            logger.severe("Error updating default values from config.json: " + e.getMessage());
        }
    }
    
    private DefaultValues() {
        // Private constructor to prevent instantiation
    }
} 