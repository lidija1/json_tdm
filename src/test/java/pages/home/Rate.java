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

public class Rate extends BasePage {

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

    public void rateQuote() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        
        clickElement(bindInformation, "Bind Information node");
        
        // Wait for the form to load after clicking bind information
        wait.until(ExpectedConditions.elementToBeClickable(existingClientNo));
        clickElement(existingClientNo, "Existing Client: No");
        
        clickElement(companyRefusedNo, "Company refused as client: No");
        clickElement(coverageRefusedNo, "Coverage Refused: No");
        clickElement(saveChanges, "Save Changes");
        
        // Wait for save to complete
        wait.until(ExpectedConditions.elementToBeClickable(rateQuote));
        clickElement(rateQuote, "Rate Quote");

        // Wait for premium info to be available
        wait.until(ExpectedConditions.visibilityOf(totalPremiumCost));
        readPremiumTotalCost();

        // Wait for request issue to be available
        wait.until(ExpectedConditions.elementToBeClickable(requestIssue));
        clickElement(requestIssue, ">>> Request Issue");
        
        // Wait for save changes button to appear
        wait.until(ExpectedConditions.elementToBeClickable(saveChanges2));
        clickElement(saveChanges2, "Save changes");
        
        clickElement(exit, "Exit");
        clickElement(quotesTab);
    }

    public void readPremiumTotalCost() {
        String premiumTotalCost = getText(totalPremiumCost);
        EnhancedLogger.info("Extracted Premium Cost: " + premiumTotalCost);
    }

    public void clickOnQuotes() throws InterruptedException {
        clickElement(quotesTab, "Quotes Tab");
    }

    public void readQuoteId() {
        String quoteNumber = getText(quoteId);
        EnhancedLogger.info("Extracted Quote Id: " + quoteNumber);
    }

    public void extractAndWritePolicyCost(String testDataPath, String tcId) throws Exception {
        String cost = getText(policyCost);
        if (!cost.startsWith("$")) {
            cost = "$" + cost;
        }
        EnhancedLogger.info("Policy Cost: " + cost);
        JsonResultWriter.writeResult(testDataPath, tcId, "Actual_Total_Location_Premium", cost);
    }

    public void extractAndWriteQuoteId(String testDataPath, String tcId) throws Exception {
        String quote = getText(quoteId);
        EnhancedLogger.info("Quote ID: " + quote);
        JsonResultWriter.writeResult(testDataPath, tcId, "Quote_ID", quote);
    }
}
