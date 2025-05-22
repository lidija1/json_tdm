package pages.home;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pages.common.BasePage;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Map;


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


   public void quoteRegistration(Map<String, String> data) throws InterruptedException {
       try {
           System.out.println("Starting quote registration process");
           
           // Add wait for page to load completely
           Thread.sleep(1500);
           
           // Select submission type with proper validation
           selectSubmissionType(data);
           System.out.println("Submission type set successfully");
           Thread.sleep(500);
           
           // Select producer with proper validation  
           selectProducer(data);
           System.out.println("Producer selected successfully");
           Thread.sleep(500);
           
           // Set effective date with proper validation
           selectEffDate(data);
           System.out.println("Effective date set successfully");
           Thread.sleep(500);
           
           // Select program with proper validation (already has robust handling)
           selectProgram(data);
           System.out.println("Program selected successfully");
           
           // Add a final wait to ensure all form elements are fully processed
           Thread.sleep(1000);
           
           // Click next button (already has robust handling)
           System.out.println("All quote registration details entered, proceeding to next step");
           nextButtonClick();
           
       } catch (Exception e) {
           System.out.println("ERROR in quote registration process: " + e.getMessage());
           try {
               takeScreenshot("quote_registration_error");
           } catch (Exception ex) {
               System.out.println("Failed to take error screenshot: " + ex.getMessage());
           }
           throw new RuntimeException("Quote registration failed: " + e.getMessage(), e);
       }
   }

    public void selectSubmissionType(Map<String, String> data) {
        try {
            System.out.println("Setting Submission Type: " + data.get("SubmissionType"));
            
            // First try to click the dropdown to open it
            clickElement(submissionType, "Submission Type dropdown");
            Thread.sleep(1000);
            
            // Try to select the option from dropdown
            String submissionTypeValue = data.get("SubmissionType");
            WebDriverWait wait = new WebDriverWait(driver, 10);
            
            String[] optionXpaths = {
                "//li[text()='" + submissionTypeValue + "']",
                "//li[contains(text(),'" + submissionTypeValue + "')]",
                "//div[@role='option' and text()='" + submissionTypeValue + "']",
                "//div[@role='listbox']//li[text()='" + submissionTypeValue + "']"
            };
            
            boolean optionSelected = false;
            for (String xpath : optionXpaths) {
                try {
                    WebElement option = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
                    clickElement(option, "Submission Type option: " + submissionTypeValue);
                    optionSelected = true;
                    break;
                } catch (Exception ex) {
                    System.out.println("Failed to select Submission Type with xpath: " + xpath);
                }
            }
            
            // If dropdown selection failed, try direct text input as fallback
            if (!optionSelected) {
                System.out.println("Dropdown selection failed, trying direct text input");
                typeText(submissionType, submissionTypeValue, "Submission Type: " + submissionTypeValue);
            }
            
        } catch (Exception e) {
            System.out.println("Error setting Submission Type: " + e.getMessage());
            // Try direct text input as last resort
            try {
                String submissionTypeValue = data.get("SubmissionType");
                typeText(submissionType, submissionTypeValue, "Submission Type (fallback): " + submissionTypeValue);
            } catch (Exception ex) {
                System.out.println("Even fallback text input failed: " + ex.getMessage());
                throw new RuntimeException("Failed to set Submission Type: " + e.getMessage(), e);
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

    public void nextButtonClick() {
        try {
            // Increase wait time and add explicit logging
            WebDriverWait wait = new WebDriverWait(driver, 15); // Increased from 10 to 15 seconds
            System.out.println("Waiting for Next button to be clickable...");
            
            // First verify the button is present
            if (nextButton == null) {
                System.out.println("ERROR: Next button reference is null");
                throw new RuntimeException("Next button reference is null");
            }
            
            // Wait with a more specific condition
            wait.until(ExpectedConditions.and(
                ExpectedConditions.visibilityOf(nextButton),
                ExpectedConditions.elementToBeClickable(nextButton)
            ));
            
            System.out.println("Next button is now clickable, attempting to click");
            
            // Try a more robust click with JavaScript fallback
            try {
                nextButton.click();
                System.out.println("Clicked Next button successfully with direct click");
            } catch (Exception e) {
                System.out.println("Direct click failed, trying JavaScript click: " + e.getMessage());
                ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", nextButton);
                System.out.println("Clicked Next button with JavaScript");
            }
            
            // Verify we've moved to the next page by waiting for an element on the next page
            wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("policyInfo"),
                ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(text(), 'Policy Information')]"))
            ));
            System.out.println("Successfully navigated after clicking Next button");
            
        } catch (Exception e) {
            System.out.println("CRITICAL ERROR when clicking Next button: " + e.getMessage());
            // Take screenshot if possible
            try {
                takeScreenshot("next_button_error");
                System.out.println("Screenshot taken for debugging");
            } catch (Exception ex) {
                System.out.println("Failed to take error screenshot: " + ex.getMessage());
            }
            throw new RuntimeException("Failed to proceed after quote registration: " + e.getMessage(), e);
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
