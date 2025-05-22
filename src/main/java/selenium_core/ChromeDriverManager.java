package selenium_core;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ChromeDriverManager extends DriverManager {

    @Override
    public void createWebDriver(String version) {
        WebDriverManager.chromedriver()
                .browserVersion("136")
                .setup();
                
        ChromeOptions options = new ChromeOptions();
        
        // Performance and stability settings
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.notifications", 2);
        prefs.put("profile.default_content_settings.popups", 0);
        prefs.put("download.prompt_for_download", false);
        // Add security settings
        prefs.put("ssl.error_override_allowed", true);
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        options.setExperimentalOption("prefs", prefs);
        
        // Security and stability settings
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--ignore-certificate-errors");
        options.addArguments("--allow-insecure-localhost");
        options.addArguments("--disable-web-security");
        options.addArguments("--reduce-security-for-testing");
        
        // Renderer process settings
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-software-rasterizer");
        options.addArguments("--disable-gpu-sandbox");
        options.addArguments("--disable-gpu-compositing");
        options.addArguments("--disable-renderer-backgrounding");
        options.addArguments("--renderer-process-limit=1");
        
        // Network settings
        options.addArguments("--disable-browser-side-navigation");
        options.addArguments("--disable-client-side-phishing-detection");
        options.addArguments("--disable-default-apps");
        options.addArguments("--disable-hang-monitor");
        options.addArguments("--disable-prompt-on-repost");
        
        // Window settings
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--force-device-scale-factor=1");
        
        // Create a unique user data directory in system temp
        String tempDir = System.getProperty("java.io.tmpdir");
        String userDataDir = tempDir + "chrome_automation_" + System.currentTimeMillis();
        options.addArguments("--user-data-dir=" + userDataDir);
        
        // Disable automation flags
        options.setExperimentalOption("excludeSwitches", 
            Collections.singletonList("enable-automation"));
        options.setExperimentalOption("useAutomationExtension", false);
        
        try {
            System.setProperty("webdriver.chrome.whitelistedIps", "");
            System.setProperty("webdriver.chrome.verboseLogging", "true");
            driver = new ChromeDriver(options);
            
            // Set timeouts after creating the driver
            driver.manage().window().maximize();
        } catch (Exception e) {
            System.err.println("Failed to create ChromeDriver: " + e.getMessage());
            throw e;
        }
    }
}
