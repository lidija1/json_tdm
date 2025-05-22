package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import pages.LoginPage;
import pages.Quotes;
import pages.home.*;
import config.ConfigManager;
import config.DefaultValues;
import json_core.JsonDataLoader;
import models.TestData;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.Customer;
import pages.LandingPage;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeOptions;
import java.util.concurrent.TimeUnit;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import config.JsonConfigManager;

public class Steps extends BaseSteps {

    private SearchPage searchPage;
    private QuoteRegistration quoteRegistrationPage;
    private LandingPage landingPage;
    private LoginPage loginPage;
    private Quotes quotesPage;
    private PolicyInformation policyInformationPage;
    private LocationCoverage locationCoveragePage;
    private Rate rateQuotePage;
    private Customer customer;
    private TestData testData;

    private String browser;
    private String browserVersion;
    private int wait;

    private String getBrowserParameter() {
        return System.getProperty("browser", "CHROME"); // Default to CHROME if not set
    }

    private String getBrowserVersionParameter() {
        return System.getProperty("browser.version", ""); // Empty string means use latest version
    }

    private int getWaitParameter() {
        String waitParam = System.getProperty("wait");
        return waitParam != null ? Integer.parseInt(waitParam) : 30; // Default to 30 seconds if not set
    }

    private String excelPath;
    private String tcId;

    private int browserRestartAttempts = 0;
    private final int MAX_BROWSER_RESTART_ATTEMPTS = 1; // Only try once to restart browser

    @Before
    public void cucumberBefore() {
        System.out.println("AUTOMATION_SECRET_KEY value: " + System.getenv("AUTOMATION_SECRET_KEY"));
        browser = getBrowserParameter();
        browserVersion = getBrowserVersionParameter();
        wait = getWaitParameter();
        baseSetUp(browser, browserVersion, wait);
        driver.manage().window().maximize();

        searchPage = new SearchPage(driver);
        quoteRegistrationPage = new QuoteRegistration(driver);
        landingPage = new LandingPage(driver);
        loginPage = new LoginPage(driver);
        quotesPage = new Quotes(driver);
        policyInformationPage = new PolicyInformation(driver);
        locationCoveragePage = new LocationCoverage(driver);
        rateQuotePage = new Rate(driver);
        customer = new Customer(driver);
    }

    @After
    public void cucumberAfter() {
        clearTestId();
        baseTearDown();
    }

    @Given("the data is loaded {string}, {string}")
    public void theDataIsLoaded(String jsonPath, String tcId) {
        testData = JsonDataLoader.loadTestData(jsonPath, tcId);
        System.out.println("Loaded test data: " + testData);
    }

    // Login
    @Given("the user is logged in with valid credentials")
    public void theUserIsLoggedIn() {
        driver.get("https://inforcedev.oneshield.com/oneshield");
//        landingPage.loginToEmpPortal();
        loginPage.performLogin();
    }

//    @Then("I should be able to access the system")
//    public void iShouldBeAbleToAccessTheSystem() {
//        // Add verification logic here
//        // For example, check if a specific element is present after login
//    }

    // Scenario - Home Exit on Policy Information Page

    // Scenario - Home Exit on Policy Information Page

    @When ("I create a new quote")
    public void iCreateANewQuote() throws InterruptedException {
        quotesPage.createQuote();
    }

    private Map<String, String> convertTestDataToMap(TestData data) {
        Map<String, String> map = new HashMap<>();
        map.put("CustomerType", data.getCustomerType() != null ? data.getCustomerType() : "");
        map.put("FirstName", data.getFirstName() != null ? data.getFirstName() : "");
        map.put("LastName", data.getLastName() != null ? data.getLastName() : "");
        map.put("DOB", data.getDOB() != null ? data.getDOB() : "");
        map.put("PhoneNum", data.getPhoneNum() != null ? data.getPhoneNum() : "");
        map.put("Email", data.getEmail() != null ? data.getEmail() : "");
        map.put("Country", data.getCountry() != null ? data.getCountry() : DefaultValues.DEFAULT_COUNTRY);
        map.put("ZIP", data.getZIP() != null ? data.getZIP().trim() : "");
        map.put("State", data.getState() != null ? data.getState() : DefaultValues.DEFAULT_STATE);
        map.put("City", data.getCity() != null ? data.getCity() : DefaultValues.DEFAULT_CITY);
        map.put("Address", data.getAddress() != null ? data.getAddress() : "");
        map.put("Producer", data.getProducer() != null ? data.getProducer() : "");
        map.put("Program", data.getProgram() != null ? data.getProgram() : "");
        map.put("SubmissionType", data.getSubmissionType() != null ? data.getSubmissionType() : "");
        map.put("BillingMethod", data.getBillingMethod() != null ? data.getBillingMethod() : "");
        map.put("ResidenceType", data.getResidenceType() != null ? data.getResidenceType() : "");
        map.put("ProgramType", data.getProgramType() != null ? data.getProgramType() : "");
        map.put("Is Child or Day Care run out of the home?", data.getChildDayCare() != null ? data.getChildDayCare() : "");
        map.put("Any underground oil or storage tanks?", data.getUndergroundOilTanks() != null ? data.getUndergroundOilTanks() : "");
        map.put("Is the residence rented more than 10 weeks per year?", data.getResidenceRented() != null ? data.getResidenceRented() : "");
        map.put("Is the residence vacant?", data.getResidenceVacant() != null ? data.getResidenceVacant() : "");
        map.put("Are there any animals or exotic pets kept on the premises?", data.getExoticPets() != null ? data.getExoticPets() : "");

        // Calculate yesterday's date in MM/dd/yyyy format
        LocalDate yesterday = LocalDate.now().minusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        map.put("EffectiveDate", yesterday.format(formatter));
        
        System.out.println("Converted map: " + map);
        return map;
    }

    @And("I provide new customer details")
    public void iCreateANewCustomerL() throws InterruptedException {
        searchPage.createCustomer(convertTestDataToMap(testData), -5);
    }

    @And("I provide quote registration details")
    public void iProvidePolicyInformationL() throws InterruptedException, IOException {
        quoteRegistrationPage.quoteRegistration(convertTestDataToMap(testData));
    }

    @And("I provide policy details")
    public void iProvidePolicyDetails() throws InterruptedException, IOException {
        policyInformationPage.policyInfo(convertTestDataToMap(testData));
    }

    @And("I provide location coverage details")
    public void iProvideLocationCoverageDetails() throws InterruptedException {
        try {
            locationCoveragePage.locationCoverAct(convertTestDataToMap(testData));
        }  catch(WebDriverException e) {
            if (e.getMessage().contains("Failed to connect") || e.getMessage().contains("Connection refused")) {
                if (browserRestartAttempts < MAX_BROWSER_RESTART_ATTEMPTS) {
                    System.out.println("WebDriver connection lost. Attempting to restart browser...");
                    browserRestartAttempts++;
                    restartBrowser();
                    // Try the step again after browser restart
                    locationCoveragePage.locationCoverAct(convertTestDataToMap(testData));
                } else {
                    System.out.println("Maximum browser restart attempts reached. Stopping test.");
                    throw new RuntimeException("Test terminated after " + browserRestartAttempts + " browser restart attempts", e);
                }
            } else {
                throw e; // Re-throw any other WebDriverException
            }
        }
    }

    /**
     * Restarts the browser and reinitializes page objects when connection is lost
     */
    private void restartBrowser() {
        try {
            // Force kill any Chrome processes that might be hanging
            try {
                Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe /T");
                Runtime.getRuntime().exec("taskkill /F /IM chrome.exe /T");
                Thread.sleep(2000); // Give time for processes to be killed
            } catch (Exception e) {
                System.out.println("Error killing browser processes: " + e.getMessage());
            }
            
            // Close the current driver if it exists
            if (driver != null) {
                try {
                    driver.quit();
                } catch (Exception e) {
                    System.out.println("Error closing existing driver: " + e.getMessage());
                }
            }
            
            // Create new ChromeDriver with more stable options
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");
            options.addArguments("--disable-extensions");
            options.setExperimentalOption("useAutomationExtension", false);
            
            driver = new ChromeDriver(options);
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS); // Reduced from 30
            
            // Reinitialize page objects with new driver
            searchPage = new SearchPage(driver);
            quoteRegistrationPage = new QuoteRegistration(driver);
            landingPage = new LandingPage(driver);
            loginPage = new LoginPage(driver);
            quotesPage = new Quotes(driver);
            policyInformationPage = new PolicyInformation(driver);
            locationCoveragePage = new LocationCoverage(driver);
            rateQuotePage = new Rate(driver);
            customer = new Customer(driver);
            
            // Login again
            driver.get("https://inforcedev.oneshield.com/oneshield");
            loginPage.performLogin();
            
            System.out.println("Browser restarted successfully");
        } catch (Exception e) {
            System.out.println("Failed to restart browser: " + e.getMessage());
            throw new RuntimeException("Failed to restart browser after connection loss", e);
        }
    }

    @Then("I rate the quote")
    public void iRateTheQuote() throws InterruptedException {
        rateQuotePage.rateQuote();
    }
//    @When ("I create a new quote")
//    public void iCreateANewQuote() throws InterruptedException {
//        quotesPage.createQuote();
//    }
//
//    @And("I provide new customer details")
//    public void iProvideNewCustomerDetails() throws InterruptedException {
//        customer.createNewCustomer(testData, 0);
//    }
//
//    @And("I provide quote registration details")
//    public void iProvideQuoteRegistrationDetails() {
//        // Implementation for quote registration details
//    }
//
//    @And("I provide policy details")
//    public void iProvidePolicyDetails() {
//        // Implementation for policy details
//    }
//
//    @And("I provide location coverage details")
//    public void iProvideLocationCoverageDetails() {
//        // Implementation for location coverage details
//    }
//
//    @Then("I rate the quote")
//    public void iRateTheQuote() {
//        // Implementation for rating the quote
//    }




}