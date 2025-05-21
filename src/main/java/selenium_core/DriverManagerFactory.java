package selenium_core;

// DriverManagerFactory class - uses the Factory pattern to create DriverManager instances
public class DriverManagerFactory {

    public static DriverManager getDriverManager(String type) {
        DriverManager driverManager;

        switch (type) {
            case "CHROME": {
                driverManager = new ChromeDriverManager();
            }
            break;
            case "CHROME_H": {
                driverManager = new ChromeHeadlessDriverManager();
            }
            break;
            case "FIREFOX": {
                driverManager = new FirefoxDriverManager();
            }
            break;
            default: {
                driverManager = new ChromeDriverManager();
            }
            break;
        }
        return driverManager;
    }

}
