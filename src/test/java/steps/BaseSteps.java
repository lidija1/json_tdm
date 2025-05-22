package steps;

import config.EnhancedLogger;
import json_core.JsonDataReader;
import models.TestCase;
import org.openqa.selenium.WebDriver;
import selenium_core.DriverManager;
import selenium_core.DriverManagerFactory;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.io.IOException;
import java.util.HashMap;
import java.io.File;

public class BaseSteps {

    WebDriver driver;
    DriverManager driverManager;
    Map<String, String> data;

    /**
     * Set the test ID for logging
     * @param testId unique identifier for the current test
     */
    public void setTestId(String testId) {
        EnhancedLogger.setTestId(testId);
        EnhancedLogger.info("Test started: " + testId);
    }

    /**
     * Clear the test ID after test completion
     */
    public void clearTestId() {
        String testId = EnhancedLogger.getTestId();
        if (testId != null) {
            EnhancedLogger.info("Test completed: " + testId);
        }
        EnhancedLogger.clearTestId();
    }

    public void baseSetUp(String browser, String version, int wait) {
        // Clean up any existing chrome temp directories
        cleanupChromeTempDirectories();
        
        try {
            driverManager = DriverManagerFactory.getDriverManager(browser);
            driver = driverManager.getDriver(version);
            driver.manage().timeouts().implicitlyWait(wait, TimeUnit.SECONDS);
            driver.manage().timeouts().pageLoadTimeout(wait, TimeUnit.SECONDS);
            driver.manage().window().maximize();
            EnhancedLogger.info("Browser started: " + browser + " v" + version + " with implicit wait: " + wait + "s");
        } catch (Exception e) {
            EnhancedLogger.error("Failed to start browser: " + e.getMessage());
            throw e;
        }
    }

    public void baseTearDown() {
        EnhancedLogger.info("Closing browser");
        try {
            if (driver != null) {
                try {
                    driver.quit();
                } catch (Exception e) {
                    EnhancedLogger.error("Error quitting driver: " + e.getMessage());
                }
                driver = null; // Set to null to prevent repeated quit attempts
            }
            if (driverManager != null) {
                try {
                    driverManager.quit();
                } catch (Exception e) {
                    EnhancedLogger.error("Error quitting driver manager: " + e.getMessage());
                }
                driverManager = null; // Set to null to prevent repeated quit attempts
            }
            
            // Force kill any hanging Chrome processes
            try {
                EnhancedLogger.info("Force killing any hanging Chrome processes");
                Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe /T");
                Runtime.getRuntime().exec("taskkill /F /IM chrome.exe /T");
                Thread.sleep(2000); // Give time for processes to be killed
            } catch (Exception e) {
                EnhancedLogger.error("Error killing browser processes: " + e.getMessage());
            }
        } catch (Exception e) {
            EnhancedLogger.error("Error during browser cleanup: " + e.getMessage());
        } finally {
            cleanupChromeTempDirectories();
        }
    }
    
    private void cleanupChromeTempDirectories() {
        File currentDir = new File(".");
        File[] tempDirs = currentDir.listFiles((dir, name) -> name.startsWith("chrome-temp-"));
        if (tempDirs != null) {
            for (File dir : tempDirs) {
                deleteDirectory(dir);
            }
        }
    }
    
    private void deleteDirectory(File directory) {
        if (directory.exists()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        deleteDirectory(file);
                    } else {
                        file.delete();
                    }
                }
            }
            directory.delete();
        }
    }

    public void loadTestData(String fileName, String testCaseId) throws IOException {
        TestCase testCase = new JsonDataReader().getTestCase(fileName, testCaseId);
        data = new HashMap<>();
        
        // Convert TestCase fields to Map<String, String>
        data.put("CustomerType", testCase.getCustomerType());
        data.put("FirstName", testCase.getFirstName());
        data.put("LastName", testCase.getLastName());
        data.put("DOB", testCase.getDOB());
        data.put("PhoneNum", testCase.getPhoneNum());
        data.put("Email", testCase.getEmail());
        data.put("Address", testCase.getAddress());
        data.put("ZIP", testCase.getZIP());
        data.put("Producer", testCase.getProducer());
        data.put("EffectiveDate", testCase.getEffectiveDate());
        data.put("Program", testCase.getProgram());
        data.put("PolicyCoverageOption", testCase.getPolicyCoverageOption());
        data.put("ResidenceType", testCase.getResidenceType());
        data.put("ReplacementCost", testCase.getReplacementCost());
        data.put("Contents", testCase.getContents());
        data.put("LossOfUse", testCase.getLossOfUse());
        data.put("AllPerilsDeductible", testCase.getAllPerilsDeductible());
        data.put("WindsHailDeductible", testCase.getWindsHailDeductible());
        data.put("Liability", testCase.getLiability());
        data.put("MedicalPayments", testCase.getMedicalPayments());
        data.put("YearBuilt", testCase.getYearBuilt());
        data.put("RoofType", testCase.getRoofType());
        data.put("ConstructionType", testCase.getConstructionType());
        
        EnhancedLogger.info("Loaded test data from " + fileName + ", test case: " + testCaseId);
    }

}
