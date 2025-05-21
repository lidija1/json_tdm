package selenium_core;

import org.openqa.selenium.WebDriver;
// Abstract DriverManager class located in the selenium_core package.
// Once we create this system, it can be reused in any future project.
public abstract class DriverManager {


    protected WebDriver driver; // Accessible only by classes extending DriverManager

    // Creates the WebDriver instance, must be implemented by subclasses
    public abstract void createWebDriver(String version);

    // Advanced quit method to safely close the WebDriver instance
    // Sets driver to null after quitting, ensuring no further access to the driver unless it's recreated
    public void quit() {
        if(driver != null) {
            driver.quit();
            driver = null;
        }
    }

    // Getter for the WebDriver instance
    public WebDriver getDriver(String version) {
        if (driver == null) {
            createWebDriver(version); // creates it if not already initialized
        }
        return driver;
    }
}
