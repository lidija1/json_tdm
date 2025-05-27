package pages.home;

import config.EnhancedLogger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pages.common.BasePage;
import json_core.JsonResultWriter;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
// import json_core.JsonDataReader;
// import models.TestCase;

/**
 * Page class representing the rate quote functionality.
 * Handles all interactions with rate quote form elements and operations
 * including bind information, premium calculations, and quote processing.
 */
public class Rate extends BasePage {

    /**
     * Constructor for Rate page.
     * @param driver WebDriver instance to be used for browser interactions
     */
    public Rate(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//a[contains(text(), 'Bind Information')]")
    public WebElement bindInformation;

    @FindBy(xpath = "//div[text()='Existing Agency Client?']/../../../..//label[text()='No']/../span")
    public WebElement existingClientNo;

    @FindBy(xpath = "//div[text()='Has any company cancelled or refused to insure in the past 3 years?']/../../../..//label[text()='No']/../span")
    public WebElement companyRefusedNo;

    @FindBy(xpath = "//div[text()='Has coverage been non-renewed or Declined?']/../../../..//label[text()='No']/../span")
    public WebElement coverageRefusedNo;

    @FindBy(xpath = "//span[text()='save changes']")
    public WebElement saveChanges;

    @FindBy(xpath = "//span[text()='>>> rate quote']")
    public WebElement rateQuote;

    @FindBy(xpath = "//div[@osviewid='PAI_306205_OT_2276904_OI_1_BI_654913_CI_11013813']")
    public WebElement totalPremiumCost;

    @FindBy(xpath = "//span[contains(text(),'Save Changes')]")
    public WebElement saveChanges2;

    @FindBy(xpath = "//span[text()='exit']")
    public WebElement exit;

    @FindBy(xpath = "//span[text()=' >>> request issue']")
    public WebElement requestIssue;

    @FindBy(xpath = "//span[contains(text(),'quote')]")
    public WebElement quotesTab;

    @FindBy(xpath = "//div[@osviewid='PAI_88301_OT_2276904_OI_1_BI_210602_CI_17014948']")
    public WebElement quoteId;

    @FindBy(xpath = "//div[@osviewid='PAI_88301_OT_2276904_OI_1_BI_210602_CI_6479502']")
    public WebElement policyCost;

    /**
     * Processes a complete rate quote with retry mechanism.
     * This method handles the entire rate quote process including bind information,
     * client status, coverage history, and quote calculation.
     * 
     * @throws InterruptedException if the thread sleep is interrupted
     * @throws Exception if all retry attempts fail or premium cost is not displayed
     */
    public void rateQuote() throws InterruptedException, Exception {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        int maxRetries = 3;
        int retryCount = 0;
        
        while (retryCount < maxRetries) {
            try {
                wait.until(ExpectedConditions.elementToBeClickable(bindInformation));
                clickElement(bindInformation, "Bind Information node");
                Thread.sleep(1000);
                
                wait.until(ExpectedConditions.elementToBeClickable(existingClientNo));
                clickElement(existingClientNo, "Existing Client: No");
                Thread.sleep(500);
                
                wait.until(ExpectedConditions.elementToBeClickable(companyRefusedNo));
                clickElement(companyRefusedNo, "Company refused as client: No");
                Thread.sleep(500);
                
                wait.until(ExpectedConditions.elementToBeClickable(coverageRefusedNo));
                clickElement(coverageRefusedNo, "Coverage Refused: No");
                Thread.sleep(500);
                
                wait.until(ExpectedConditions.elementToBeClickable(saveChanges));
                clickElement(saveChanges, "Save Changes");
                Thread.sleep(1000);
                
                wait.until(ExpectedConditions.elementToBeClickable(rateQuote));
                clickElement(rateQuote, "Rate Quote");
                Thread.sleep(2000);
                
                wait.until(ExpectedConditions.visibilityOf(totalPremiumCost));
                if (!totalPremiumCost.isDisplayed()) {
                    throw new Exception("Premium cost not displayed after rate quote");
                }
                readPremiumTotalCost();
                
                wait.until(ExpectedConditions.elementToBeClickable(requestIssue));
                clickElement(requestIssue, ">>> Request Issue");
                Thread.sleep(1000);
                
                wait.until(ExpectedConditions.elementToBeClickable(saveChanges2));
                clickElement(saveChanges2, "Save changes");
                Thread.sleep(1000);
                
                wait.until(ExpectedConditions.elementToBeClickable(exit));
                clickElement(exit, "Exit");
                Thread.sleep(1000);
                
                wait.until(ExpectedConditions.elementToBeClickable(quotesTab));
                clickElement(quotesTab, "Quotes Tab");
                
                return;
                
            } catch (Exception e) {
                retryCount++;
                System.out.println("Attempt " + retryCount + " failed: " + e.getMessage());
                
                if (retryCount == maxRetries) {
                    System.out.println("All attempts to complete rate quote process failed");
                    throw e;
                }
                
                Thread.sleep(2000);
            }
        }
    }

    /**
     * Reads and logs the total premium cost.
     * Extracts the premium cost from the UI and logs it using the enhanced logger.
     */
    public void readPremiumTotalCost() {
        String premiumTotalCost = getText(totalPremiumCost);
        EnhancedLogger.info("Extracted Premium Cost: " + premiumTotalCost);
    }

    //no usages

    /**
     * Navigates to the quotes tab.
     * @throws InterruptedException if the thread sleep is interrupted
     */
    public void clickOnQuotes() throws InterruptedException {
        clickElement(quotesTab, "Quotes Tab");
    }

    /**
     * Reads and logs the quote ID.
     * Extracts the quote ID from the UI and logs it using the enhanced logger.
     */
    public void readQuoteId() {
        String quoteNumber = getText(quoteId);
        EnhancedLogger.info("Extracted Quote Id: " + quoteNumber);
    }

    /**
     * Extracts the policy cost and writes it to the test results.
     * Formats the cost with a dollar sign if needed and saves it to the JSON result file.
     * 
     * @param testDataPath Path to the test data file
     * @param tcId Test case identifier
     * @throws Exception if writing to the result file fails
     */
    public void extractAndWritePolicyCost(String testDataPath, String tcId) throws Exception {
        String cost = getText(policyCost);
        if (!cost.startsWith("$")) {
            cost = "$" + cost;
        }
        EnhancedLogger.info("Policy Cost: " + cost);
        JsonResultWriter.writeResult(testDataPath, tcId, "Actual_Total_Location_Premium", cost);
    }

    /**
     * Extracts the quote ID and writes it to the test results.
     * Saves the quote ID to the JSON result file.
     * 
     * @param testDataPath Path to the test data file
     * @param tcId Test case identifier
     * @throws Exception if writing to the result file fails
     */
    public void extractAndWriteQuoteId(String testDataPath, String tcId) throws Exception {
        String quote = getText(quoteId);
        EnhancedLogger.info("Quote ID: " + quote);
        JsonResultWriter.writeResult(testDataPath, tcId, "Quote_ID", quote);
    }
}
