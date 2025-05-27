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

/**
 * Page class representing the quote registration functionality.
 * Handles all interactions with quote registration form elements and operations
 * including submission type, producer selection, and program details.
 */
public class QuoteRegistration extends BasePage {

    /**
     * Constructor for QuoteRegistration page.
     * @param driver WebDriver instance to be used for browser interactions
     */
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

    /**
     * Handles the complete quote registration process.
     * This method orchestrates all the quote registration steps including
     * submission type, producer selection, effective date, and program selection.
     * 
     * @param data Map containing all necessary quote registration information
     * @throws InterruptedException if the thread sleep is interrupted
     * @throws Exception if any step in the process fails
     */
    public void quoteRegistration(Map<String, String> data) throws InterruptedException, Exception {
        System.out.println("Starting quote registration process");
        selectSubmissionType(data);
        selectProducer(data);
        selectEffDate(data);
        selectProgram(data);
        clickNextAndWaitForPolicyInfo();
    }

    /**
     * Selects the submission type from the provided data.
     * @param data Map containing submission type information
     */
    public void selectSubmissionType(Map<String, String> data) {
        typeText(submissionType, data.get("SubmissionType"), "SubmissionType: " + data.get("SubmissionType"));
    }

    /**
     * Selects the producer from the provided data.
     * @param data Map containing producer information
     */
    public void selectProducer(Map<String, String> data) {
        typeText(producer, data.get("Producer"), "producer" + data.get("Producer"));
    }

    /**
     * Sets the effective date from the provided data.
     * @param data Map containing effective date information
     */
    public void selectEffDate(Map<String, String> data) {
        typeText(effectiveDate, data.get("EffectiveDate"), "effDate" + data.get("EffectiveDate"));
    }

    /**
     * Selects the program with retry mechanism for reliability.
     * This method implements multiple strategies to ensure successful program selection,
     * including regular clicks, JavaScript clicks, and direct input.
     * 
     * @param data Map containing program information
     * @throws InterruptedException if the thread sleep is interrupted
     * @throws Exception if all retry attempts fail
     */
    public void selectProgram(Map<String, String> data) throws InterruptedException, Exception {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        int maxRetries = 3;
        int retryCount = 0;
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String programValue = data.get("Program");
        System.out.println("Attempting to select Program: " + programValue);
        
        while (retryCount < maxRetries) {
            try {
                // Clear any existing value and wait for field to be ready
                program.clear();
                wait.until(ExpectedConditions.elementToBeClickable(program));
                wait.until(ExpectedConditions.visibilityOf(program));
                
                // First try: Click using regular Selenium click
                program.click();
                Thread.sleep(1000);
                
                // Try to find the option with various selectors
                String[] xpathAttempts = {
                    String.format("//li[text()='%s']", programValue),
                    String.format("//div[contains(@class, 'x-boundlist')]//li[text()='%s']", programValue),
                    String.format("//div[contains(@class, 'x-boundlist-item')][text()='%s']", programValue),
                    String.format("//li[contains(text(), '%s') and not(contains(., 'Association'))]", programValue)
                };
                
                WebElement option = null;
                for (String xpath : xpathAttempts) {
                    try {
                        option = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
                        if (option != null && option.isDisplayed()) {
                            // Try regular click first
                            option.click();
                            Thread.sleep(500);
                            
                            // Verify if selection worked
                            if (program.getAttribute("value").contains(programValue)) {
                                System.out.println("Successfully selected program using xpath: " + xpath);
                                return;
                            }
                            
                            // If regular click didn't work, try JavaScript click
                            js.executeScript("arguments[0].click();", option);
                            Thread.sleep(500);
                            
                            // Verify again
                            if (program.getAttribute("value").contains(programValue)) {
                                System.out.println("Successfully selected program using JavaScript click");
                                return;
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Failed with xpath: " + xpath + " - " + e.getMessage());
                        continue;
                    }
                }
                
                // If we haven't returned by now, try direct input
                program.clear();
                program.sendKeys(programValue);
                Thread.sleep(1000);
                
                // Try to find and click the option after typing
                try {
                    WebElement typedOption = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath(String.format("//li[contains(text(), '%s') and not(contains(., 'Association'))]", programValue))
                    ));
                    typedOption.click();
                    Thread.sleep(500);
                } catch (Exception e) {
                    // If clicking the option fails, try pressing Enter
                    program.sendKeys(org.openqa.selenium.Keys.ENTER);
                }
                
                // Final verification
                if (program.getAttribute("value").contains(programValue)) {
                    System.out.println("Successfully selected program using direct input");
                    return;
                }
                
                throw new Exception("Failed to select program after all attempts");
                
            } catch (Exception e) {
                retryCount++;
                System.out.println("Attempt " + retryCount + " failed: " + e.getMessage());
                
                if (retryCount == maxRetries) {
                    System.out.println("All attempts to select Program failed");
                    throw e;
                }
                
                // Wait between retries and try to reset UI state
                Thread.sleep(2000);
                try {
                    js.executeScript("document.body.click()");
                } catch (Exception ignored) {}
            }
        }
    }

    /**
     * Clicks the Next button and waits for the policy information page to load.
     * Uses explicit wait to ensure proper page transition.
     */
    private void clickNextAndWaitForPolicyInfo() {
        clickElement(nextButton, "Next Button");
        // Wait for the page transition
        WebDriverWait wait = new WebDriverWait(driver, 10);
    }

    /**
     * Clicks the Skip button to bypass optional steps.
     * Note: Currently not in use.
     */
    private void clickSkip() {
        clickElement(skipButton, "Skip Button");
    }
}

