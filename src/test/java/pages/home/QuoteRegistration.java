package pages.home;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pages.common.BasePage;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;

import java.util.Map;
import java.util.List;
import java.io.IOException;


public class QuoteRegistration extends BasePage {

    public QuoteRegistration(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//div[contains(text(), 'Submission Type')]/../../../..//input")
    public WebElement submissionType;

    @FindBy(xpath = "//div[text()='Producer']/../../../..//input")
    public WebElement producer;

    @FindBy(xpath = "//div[text()='Effective Date']/../../../..//input")
    public WebElement effectiveDate;

    @FindBy(xpath = "//div[text()='Program']/../../../..//input")
    public WebElement program;

    @FindBy(xpath = "//span[text()='Back']")
    public WebElement backButton;

    @FindBy(xpath = "//span[text()='Next']")
    public WebElement nextButton;

    @FindBy(xpath = "//span[text()='Exit']")
    public WebElement exitButton;

    @FindBy(xpath = "//*[@id=\"osActionButton-1460448-btnInnerEl\"]")
    public WebElement skipButton;


   public void quoteRegistration(Map<String, String> data) throws InterruptedException, IOException {
//       clickSkip(); dodato je u searchPage
       System.out.println("Starting quote registration process");
       selectSubmissionType(data);
       selectProducer(data);
       selectEffDate(data);
       selectProgram(data);
       clickNextAndWaitForPolicyInfo();
   }



    public void selectSubmissionType(Map<String, String> data) {
        typeText(submissionType, data.get("SubmissionType"), "SubmissionType: " + data.get("SubmissionType"));
    }

    public void selectProducer(Map<String, String> data) {
        typeText(producer, data.get("Producer"), "producer" + data.get("Producer"));
    }

    public void selectEffDate(Map<String, String> data) {
        typeText(effectiveDate, data.get("EffectiveDate"), "effDate" + data.get("EffectiveDate"));
    }

    public void selectProgram(Map<String, String> data) {
        try {
            // Clear any existing value first
            program.clear();
            
            // Wait for the program field to be clickable
            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.elementToBeClickable(program));
            
            // Click to open the dropdown
            clickElement(program, "Program dropdown");
            
            // Construct a more precise XPath for exact program match
            String exactProgramXPath = String.format(
                "//li[normalize-space(text())='%s' and not(contains(@class, 'disabled'))]",
                data.get("Program")
            );
            
            // Wait for and click the exact program option
            WebElement exactProgram = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath(exactProgramXPath)
            ));
            
            // Add a small delay before clicking to ensure UI is stable
            Thread.sleep(1000);
            
            // Click the exact program
            clickElement(exactProgram, "Program: " + data.get("Program"));
            
            // Verify the selection
            wait.until(ExpectedConditions.attributeContains(program, "value", data.get("Program")));
            System.out.println("Successfully selected program: " + data.get("Program"));
            
        } catch (Exception e) {
            System.out.println("Failed to select program using dropdown: " + e.getMessage());
            // Fallback to direct input if dropdown fails
            try {
                program.clear();
                typeText(program, data.get("Program"), "Program: " + data.get("Program"));
                // Wait for the value to be set
                WebDriverWait wait = new WebDriverWait(driver, 5);
                wait.until(ExpectedConditions.attributeContains(program, "value", data.get("Program")));
            } catch (Exception e2) {
                System.out.println("Both dropdown and direct input failed: " + e2.getMessage());
                throw e2;
            }
        }
    }
    private void clickNextAndWaitForPolicyInfo() {

                clickElement(nextButton, "Next Button");
                
                // Wait for the page transition
                WebDriverWait wait = new WebDriverWait(driver, 10);

    }

    private void clickSkip() {
        clickElement(skipButton, "Skip Button");
    }

}

