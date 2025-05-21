package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import json_core.JsonUtils;

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
        Thread.sleep(2000);
        clickElement(bindInformation, "Bind Information node");
        Thread.sleep(6000);
        clickElement(existingClientNo, "Existing Client: No");
        Thread.sleep(1000);
        clickElement(companyRefusedNo, "Company refused as client: No");
        Thread.sleep(1000);
        clickElement(coverageRefusedNo, "Coverage Refused: No");
        Thread.sleep(1000);
        clickElement(saveChanges, "Save Changes");
        Thread.sleep(3000);
        clickElement(rateQuote, "Rate Quote");

        Thread.sleep(2000);
        readPremiumTotalCost();

        Thread.sleep(6000);
        clickElement(requestIssue, ">>> Request Issue");
        Thread.sleep(7000);

        clickElement(saveChanges2, "Save changes");
        Thread.sleep(2000);
        clickElement(exit, "Exit");
        Thread.sleep(2000);
        clickElement(quotesTab);
        Thread.sleep(3000);
    }

    public void readPremiumTotalCost() {
        String premiumTotalCost = getText(totalPremiumCost);
        System.out.println("Extracted Premium Cost: " + premiumTotalCost);
    }

    public void clickOnQuotes() throws InterruptedException {
        clickElement(quotesTab, "Quotes Tab");
        Thread.sleep(3000);
    }

    public void readQuoteId() {
        String quoteNumber = getText(quoteId);
        System.out.println("Extracted Quote Id: " + quoteNumber);
    }

    public void extractAndWritePolicyCost(String jsonPath, String tcId) throws Exception {
        String cost = getText(policyCost);
        if (!cost.startsWith("$")) {
            cost = "$" + cost;
        }
        JsonUtils.writeToJson(jsonPath, tcId, "Actual_Total_Location_Premium", cost);
        System.out.println("Written Policy Cost to JSON: " + cost);
    }

    public void extractAndWriteQuoteId(String jsonPath, String tcId) throws Exception {
        String quote = getText(quoteId);
        JsonUtils.writeToJson(jsonPath, tcId, "Quote_ID", quote);
        System.out.println("Written Quote ID to JSON: " + quote);
    }

}
