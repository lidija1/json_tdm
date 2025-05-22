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


   public void quoteRegistration(Map<String, String> data) throws InterruptedException, IOException {
       System.out.println("Starting quote registration process");
       
       // Select Submission Type
       System.out.println("Submission Type dropdown");
       selectSubmissionType(data);
       System.out.println("Submission type set successfully");
       
       // Select Producer
       selectProducer(data);
       System.out.println("Producer selected successfully");
       
       // Select Effective Date
       selectEffDate(data);
       System.out.println("Effective date set successfully");
       
       // Select Program
       selectProgram(data);
       System.out.println("Program selected successfully");
       
       System.out.println("All quote registration details entered, proceeding to next step");
       
       // Click Next with improved navigation handling
       clickNextAndWaitForPolicyInfo();
   }

    private void clickNextAndWaitForPolicyInfo() throws InterruptedException, IOException {
        try {
            System.out.println("Waiting for Next button to be clickable...");
            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.elementToBeClickable(nextButton));
            System.out.println("Next button is now clickable, attempting to click");
            
            // Try normal click first
            clickElement(nextButton, "Next button");
            System.out.println("Clicked Next button successfully with direct click");
            
            System.out.println("Waiting for page to load after clicking Next button...");
            Thread.sleep(2000); // Short wait for initial page load
            
            // Wait for either Policy Information elements or handle redirect
            boolean policyPageFound = false;
            for (int attempt = 0; attempt < 3; attempt++) {
                try {
                    System.out.println("Navigation attempt " + (attempt + 1));
                    
                    // Print current URL and page title for debugging
                    System.out.println("Current URL: " + driver.getCurrentUrl());
                    System.out.println("Page Title: " + driver.getTitle());
                    
                    // Try to find Policy Information page using multiple possible selectors
                    String[] policyInfoSelectors = {
                        "//div[contains(text(), 'Policy Information')]",
                        "//div[contains(@class, 'policy-info')]",
                        "//div[contains(@class, 'policy')]//h1",
                        "//div[contains(text(), 'Policy Info')]"
                    };
                    
                    for (String selector : policyInfoSelectors) {
                        if (driver.findElements(By.xpath(selector)).size() > 0) {
                            System.out.println("Found Policy Information page using selector: " + selector);
                            policyPageFound = true;
                            break;
                        }
                    }
                    
                    if (policyPageFound) break;
                    
                    // If we're not on the Policy Information page, try to navigate there
                    // First check for the Quotes tab using multiple possible selectors
                    String[] quotesTabSelectors = {
                        "//span[text()='QUOTES']",
                        "//a[contains(text(), 'QUOTES')]",
                        "//div[contains(text(), 'QUOTES')]",
                        "//span[contains(text(), 'Quotes')]"
                    };
                    
                    WebElement quotesTab = null;
                    for (String selector : quotesTabSelectors) {
                        List<WebElement> elements = driver.findElements(By.xpath(selector));
                        if (elements.size() > 0) {
                            quotesTab = elements.get(0);
                            System.out.println("Found Quotes tab using selector: " + selector);
                            break;
                        }
                    }
                    
                    if (quotesTab != null) {
                        wait.until(ExpectedConditions.elementToBeClickable(quotesTab));
                        quotesTab.click();
                        System.out.println("Clicked Quotes tab");
                        Thread.sleep(1000);
                        
                        // Try to find the most recent quote using multiple possible selectors
                        String[] quoteSelectors = {
                            "//div[contains(text(), 'QUOTE REGISTRATION FOR')]",
                            "//div[contains(text(), '" + driver.findElement(By.xpath("//div[contains(text(), 'QUOTE REGISTRATION FOR')]")).getText() + "')]",
                            "//div[contains(@class, 'quote-registration')]",
                            "//div[contains(text(), 'New Business')]//ancestor::tr[1]"
                        };
                        
                        WebElement recentQuote = null;
                        for (String selector : quoteSelectors) {
                            List<WebElement> elements = driver.findElements(By.xpath(selector));
                            if (elements.size() > 0) {
                                recentQuote = elements.get(0);
                                System.out.println("Found recent quote using selector: " + selector);
                                break;
                            }
                        }
                        
                        if (recentQuote != null) {
                            wait.until(ExpectedConditions.elementToBeClickable(recentQuote));
                            recentQuote.click();
                            System.out.println("Clicked recent quote");
                            Thread.sleep(2000);
                        } else {
                            System.out.println("Could not find recent quote element");
                        }
                    } else {
                        System.out.println("Could not find Quotes tab");
                        // Try to find any clickable navigation elements
                        List<WebElement> possibleNavElements = driver.findElements(
                            By.xpath("//a | //span[contains(@class, 'clickable')] | //div[contains(@class, 'clickable')]"));
                        System.out.println("Found " + possibleNavElements.size() + " possible navigation elements");
                        for (WebElement elem : possibleNavElements) {
                            try {
                                String text = elem.getText();
                                String className = elem.getAttribute("class");
                                System.out.println("Navigation element - Text: " + text + ", Class: " + className);
                            } catch (Exception e) {
                                // Ignore stale elements
                            }
                        }
                    }
                    
                    Thread.sleep(2000); // Wait before next attempt
                } catch (Exception e) {
                    System.out.println("Navigation attempt " + (attempt + 1) + " failed: " + e.getMessage());
                    takeScreenshot("navigation_attempt_" + (attempt + 1));
                }
            }
            
            if (!policyPageFound) {
                System.out.println("Failed to reach Policy Information page after 3 attempts");
                takeScreenshot("navigation_failure");
                // Take one final screenshot of the current page state
                takeScreenshot("final_page_state");
                // Print out the page source for debugging
                System.out.println("Current page source: " + driver.getPageSource());
                throw new RuntimeException("Could not navigate to Policy Information page");
            }
            
        } catch (Exception e) {
            System.out.println("Error during navigation: " + e.getMessage());
            takeScreenshot("navigation_error");
            throw e;
        }
    }

    public void selectSubmissionType(Map<String, String> data) {
        try {
            // Wait for element to be clickable
            WebDriverWait wait = new WebDriverWait(driver, 5);
            wait.until(ExpectedConditions.elementToBeClickable(submissionType));
            
            // Type the submission type
            typeText(submissionType, data.get("SubmissionType"), "Submission Type: " + data.get("SubmissionType"));
        } catch (Exception e) {
            System.out.println("Error selecting submission type: " + e.getMessage());
            // Try JavaScript as fallback
            try {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].value = arguments[1];", submissionType, data.get("SubmissionType"));
                System.out.println("Set Submission Type via JavaScript: " + data.get("SubmissionType"));
            } catch (Exception je) {
                throw je;
            }
        }
    }

    public void selectProducer(Map<String, String> data) {
        typeText(producer, data.get("Producer"), "producer" + data.get("Producer"));
    }

    public void selectEffDate(Map<String, String> data) {
        typeText(effectiveDate, data.get("EffectiveDate"), "effDate" + data.get("EffectiveDate"));
    }

    public void selectProgram(Map<String, String> data) throws InterruptedException {
        try {
            System.out.println("Starting Program selection: " + data.get("Program"));
            
            // Wait before interacting with the dropdown
            Thread.sleep(1000);
            
            // Click the dropdown with retry logic
            boolean dropdownClicked = false;
            for (int i = 0; i < 3; i++) {
                try {
                    clickElement(program, "Program dropdown menu");
                    dropdownClicked = true;
                    break;
                } catch (Exception e) {
                    System.out.println("Attempt " + (i+1) + " to click Program dropdown failed: " + e.getMessage());
                    Thread.sleep(1000);
                }
            }
            
            if (!dropdownClicked) {
                System.out.println("Failed to click Program dropdown after 3 attempts, trying JavaScript click");
                ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", program);
            }
            
            // Wait for dropdown options to appear
            Thread.sleep(1000);
            
            // Try multiple xpath patterns to find the option
            WebDriverWait wait = new WebDriverWait(driver, 10);
            String programValue = data.get("Program");
            
            // Array of possible xpaths to locate the program option
            String[] optionXpaths = {
                "//li[text()='" + programValue + "']",
                "//li[contains(text(),'" + programValue + "')]",
                "//div[@role='option' and text()='" + programValue + "']",
                "//div[@role='listbox']//li[text()='" + programValue + "']",
                "//ul//li[contains(text(),'" + programValue + "')]"
            };
            
            boolean optionSelected = false;
            
            // Try each xpath
            for (String xpath : optionXpaths) {
                try {
                    WebElement option = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
                    clickElement(option, "Program option: " + programValue);
                    optionSelected = true;
                    System.out.println("Successfully selected Program: " + programValue + " using xpath: " + xpath);
                    break;
                } catch (Exception e) {
                    System.out.println("Failed to select program with xpath: " + xpath);
                }
            }
            
            if (!optionSelected) {
                throw new RuntimeException("Failed to select Program: " + programValue);
            }
            
            // Wait after selection to ensure UI updates
            Thread.sleep(500);
            
        } catch (Exception e) {
            System.out.println("ERROR selecting Program: " + e.getMessage());
            throw new RuntimeException("Failed to select Program: " + e.getMessage(), e);
        }
    }

    //public void backButtonClick() {clickElement(backButton, "backButton");}

//public void setReplaceCost() {
//    WebElement checkbox = driver.findElement(replacementCostCheckbox);
//    if (!checkbox.isSelected()) {
//        checkbox.click();
//    }
//public void setResidenceType() {
  //   WebElement checkbox = driver.findElement(replacementCostCheckbox);
      //    if (!checkbox.isSelected()) {
//        checkbox.click();
}

// public void exitButtonClick() {clickElement(exitButton, "exitButton");}
