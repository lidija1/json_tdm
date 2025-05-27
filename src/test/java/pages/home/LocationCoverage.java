package pages.home;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.common.BasePage;
import org.openqa.selenium.JavascriptExecutor;
import java.util.Map;

/**
 * Page class representing the location coverage functionality.
 * Handles all interactions with location coverage form elements and operations
 * including policy coverage options, residence details, and construction information.
 */
public class LocationCoverage extends BasePage {

    /**
     * Constructor for LocationCoverage page.
     * @param driver WebDriver instance to be used for browser interactions
     */
    public LocationCoverage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//*[@osviewid=\"PAI_393702_OT_2261202_OI_1_BI_1487548_CI_17657048\"]")
    public WebElement policyCoverageOption;

    @FindBy(xpath = "//*[@osviewid=\"PAI_393702_OT_2261202_OI_1_BI_490002_CI_8444105\"]")
    public WebElement ResidenceType;

    @FindBy(xpath = "/html/body/div[4]/div/div/table/tbody/tr[2]/td/div/div/div[2]/div/table/tbody/tr[2]/td[1]/div/div[1]/div/div/div/div/div[1]/div/div/input")
    public WebElement replacementCost;

    @FindBy(xpath = "//div[text()='Windstorm or Hail  Deductible']/../../../..//input/../..//div[2]")
    public WebElement windHailDeduct;

    @FindBy(xpath = "/html/body/div[4]/div/div/table/tbody/tr[2]/td/div/div/div[2]/div/table/tbody/tr[6]/td[1]/div/div[1]/div/div/div/div/div[1]/div/table/tbody/tr/td[1]/div/div[1]/div/span/input")
    public WebElement resUnderConstrNo;

    @FindBy(xpath = "/html/body/div[4]/div/div/table/tbody/tr[2]/td/div/div/div[2]/div/table/tbody/tr[7]/td[1]/div/div[1]/div/div/div/div/div[1]/div/table/tbody/tr/td[1]/div/div[1]/div/span/input")
    public WebElement underThreeYearsNo;

    @FindBy(xpath = "//div[text()='Year Built']/../../../..//input")
    public WebElement yearBuilt;

    @FindBy(xpath = "//div[text()='Construction Type']/../../../..//input")
    public WebElement constructionType;

    @FindBy(xpath = "//div[text()='Roof Type']/../../../..//input")
    public WebElement roofType;

    @FindBy(xpath = "//*[@osviewid=\"PAI_393702_AB_1661246\"]")
    public WebElement saveChanges;

    @FindBy(xpath = "//*[@osviewid=\"PAI_393702_AB_631814\"]")
    public WebElement rateQuote;

    @FindBy(xpath = "//*[@osviewid=\"PAI_306205_AB_1311848\"]")
    public WebElement requestIssue;

    @FindBy(xpath = "//*[@osviewid=\"PAI_484705_AB_343505\"]")
    public WebElement clickExit;

    /**
     * Selects the policy coverage option from the dropdown.
     * @param data Map containing policy coverage option information
     */
    public void selectPolCovOption(Map<String, String> data) {
        WebDriverWait wait = new WebDriverWait(driver, 10);

        // Click the dropdown to expand options
        wait.until(ExpectedConditions.elementToBeClickable(policyCoverageOption)).click();

        // Wait for the desired option to be present and click it
        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//li[text()='"+data.get("PolicyCoverageOption")+"']")));
        option.click();
    }

    /**
     * Enters the residence type with retry mechanism for reliability.
     * @param data Map containing residence type information
     * @throws InterruptedException if the thread sleep is interrupted
     * @throws Exception if all retry attempts fail
     */
    public void enterResidenceType(Map<String, String> data) throws InterruptedException, Exception {
        String value = data.getOrDefault("ResidenceType", "");
        if (!value.isEmpty()) {
            WebDriverWait wait = new WebDriverWait(driver, 30);
            int maxRetries = 3;
            int retryCount = 0;
            JavascriptExecutor js = (JavascriptExecutor) driver;
            
            while (retryCount < maxRetries) {
                try {
                    // Wait for and click the ResidenceType field
                    wait.until(ExpectedConditions.elementToBeClickable(ResidenceType));
                    wait.until(ExpectedConditions.visibilityOf(ResidenceType));
                    js.executeScript("arguments[0].click();", ResidenceType);
                    Thread.sleep(1000);
                    
                    // Try multiple XPath patterns to find the option
                    String[] xpathAttempts = {
                        String.format("//li[text()='%s']", value),
                        String.format("//div[contains(@class, 'x-boundlist')]//li[text()='%s']", value),
                        String.format("//div[contains(@class, 'x-boundlist-item')][text()='%s']", value),
                        String.format("//li[contains(text(), '%s')]", value)
                    };
                    
                    WebElement option = null;
                    for (String xpath : xpathAttempts) {
                        try {
                            option = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
                            if (option != null && option.isDisplayed()) {
                                // Try JavaScript click first
                                js.executeScript("arguments[0].click();", option);
                                Thread.sleep(500);
                                
                                // Verify if selection worked
                                if (ResidenceType.getAttribute("value").contains(value)) {
                                    System.out.println("Successfully selected ResidenceType using xpath: " + xpath);
                                    return;
                                }
                                
                                // If JavaScript click didn't work, try regular click
                                option.click();
                                Thread.sleep(500);
                                
                                // Verify again
                                if (ResidenceType.getAttribute("value").contains(value)) {
                                    System.out.println("Successfully selected ResidenceType using regular click");
                                    return;
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("Failed with xpath: " + xpath + " - " + e.getMessage());
                            continue;
                        }
                    }
                    
                    // If we haven't returned by now, try direct input
                    ResidenceType.clear();
                    ResidenceType.sendKeys(value);
                    Thread.sleep(1000);
                    
                    // Try to find and click the option after typing
                    try {
                        WebElement typedOption = wait.until(ExpectedConditions.elementToBeClickable(
                            By.xpath(String.format("//li[contains(text(), '%s')]", value))
                        ));
                        typedOption.click();
                        Thread.sleep(500);
                    } catch (Exception e) {
                        // If clicking the option fails, try pressing Enter
                        ResidenceType.sendKeys(org.openqa.selenium.Keys.ENTER);
                    }
                    
                    // Final verification
                    if (ResidenceType.getAttribute("value").contains(value)) {
                        System.out.println("Successfully selected ResidenceType using direct input");
                        return;
                    }
                    
                    throw new Exception("Failed to select ResidenceType after all attempts");
                    
                } catch (Exception e) {
                    retryCount++;
                    System.out.println("Attempt " + retryCount + " failed: " + e.getMessage());
                    
                    if (retryCount == maxRetries) {
                        System.out.println("All attempts to select ResidenceType failed");
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
    }

    /**
     * Enters the replacement cost for the property.
     * Uses a retry mechanism to ensure reliable data entry.
     * @param data Map containing replacement cost information
     * @throws InterruptedException if the thread sleep is interrupted
     * @throws Exception if all retry attempts fail
     */
    public void enterReplacementCost(Map<String, String> data) throws InterruptedException, Exception {
        String value = data.getOrDefault("ReplacementCost", "1200000");
        System.out.println("Attempting to enter Replacement Cost: " + value);
        
        WebDriverWait wait = new WebDriverWait(driver, 30);
        int maxRetries = 3;
        int retryCount = 0;
        
        while (retryCount < maxRetries) {
            try {
                // Wait for element to be ready
                wait.until(ExpectedConditions.elementToBeClickable(replacementCost));
                wait.until(ExpectedConditions.visibilityOf(replacementCost));
                
                // Clear existing value
                replacementCost.clear();
                Thread.sleep(500);
                
                // Try regular sendKeys first
                replacementCost.sendKeys(value);
                Thread.sleep(500);
                
                // Verify if value was entered
                String enteredValue = replacementCost.getAttribute("value");
                if (enteredValue != null && !enteredValue.trim().isEmpty()) {
                    System.out.println("Successfully entered Replacement Cost using regular input");
                    return;
                }
                
                // If regular input didn't work, try JavaScript
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].value = arguments[1];", replacementCost, value);
                Thread.sleep(500);
                
                // Verify again
                enteredValue = replacementCost.getAttribute("value");
                if (enteredValue != null && !enteredValue.trim().isEmpty()) {
                    System.out.println("Successfully entered Replacement Cost using JavaScript");
                    return;
                }
                
                throw new Exception("Failed to enter Replacement Cost after both attempts");
                
            } catch (Exception e) {
                retryCount++;
                System.out.println("Attempt " + retryCount + " failed: " + e.getMessage());
                
                if (retryCount == maxRetries) {
                    System.out.println("All attempts to enter Replacement Cost failed");
                    throw e;
                }
                
                Thread.sleep(2000);
            }
        }
    }

    /**
     * Sets the windstorm/hail deductible option.
     * @param data Map containing deductible information
     * @throws InterruptedException if the thread sleep is interrupted
     */
    public void setWindHailDeduct(Map<String, String> data) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 30); // Increased timeout
        int maxRetries = 3;
        int retryCount = 0;
        
        while (retryCount < maxRetries) {
            try {
                // Wait for dropdown to be clickable and visible
                wait.until(ExpectedConditions.elementToBeClickable(windHailDeduct));
                wait.until(ExpectedConditions.visibilityOf(windHailDeduct));
                
                // Try to click using JavaScript first
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].click();", windHailDeduct);
                
                // Add a pause to let the dropdown open
                Thread.sleep(2000);
                
                // Create a more robust XPath for the option
                String optionXPath = String.format(
                    "//li[normalize-space(text())='%s' or contains(normalize-space(text()), '%s')]",
                    data.get("WindsHailDeductible"),
                    data.get("WindsHailDeductible")
                );
                
                // Wait for option to be clickable and visible
                WebElement option = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath(optionXPath)));
                wait.until(ExpectedConditions.visibilityOf(option));
                
                // Try JavaScript click first
                js.executeScript("arguments[0].click();", option);
                
                // Log success
                System.out.println("Successfully selected WindsHailDeductible: " + data.get("WindsHailDeductible"));
                
                // If we get here, the selection was successful
                break;
            } catch (Exception e) {
                retryCount++;
                System.out.println("Attempt " + retryCount + " failed: " + e.getMessage());
                
                if (retryCount == maxRetries) {
                    System.out.println("All attempts to select WindsHailDeductible failed");
                    throw e;
                }
                // Wait longer between retries
                Thread.sleep(2000);
                
                // Try to reset the state by clicking elsewhere
                try {
                    JavascriptExecutor js = (JavascriptExecutor) driver;
                    js.executeScript("document.body.click()");
                } catch (Exception ignored) {}
            }
        }
    }

    /**
     * Sets whether the residence is under construction.
     * @param data Map containing construction status information
     */
    public void setResUnderConstrNo(Map<String, String> data) {
        clickElement(resUnderConstrNo, "Is the residence under construction or major renovation? " + data.get("Is the residence under construction or major renovation?"));
    }

    /**
     * Sets whether the property is under three years old.
     * @param data Map containing property age information
     */
    public void setUnderThreeYearsNo(Map<String, String> data) {
        clickElement(underThreeYearsNo, "Is the residence under construction or major renovation? " + data.get("Is the residence under construction or major renovation?"));
    }

    /**
     * Sets the year the property was built.
     * @param data Map containing year built information
     */
    public void setYearBuilt(Map<String, String> data) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        String yearValue = data.get("YearBuilt");
        if (yearValue == null || yearValue.trim().isEmpty()) {
            yearValue = "2020"; // Default value if none provided
        }
        
        try {
            // Wait for the element to be visible and clickable
            WebElement yearBuiltElement = wait.until(ExpectedConditions.elementToBeClickable(yearBuilt));
            // Clear any existing value
            yearBuiltElement.clear();
            // Type the new value
            yearBuiltElement.sendKeys(yearValue);
            // Add a small pause to ensure the value is set
            pause(500);
        } catch (Exception e) {
            // If the element is not found with the current selector, try an alternative approach
            try {
                WebElement alternativeElement = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//div[contains(text(), 'Year Built')]/following::input[1]")));
                alternativeElement.clear();
                alternativeElement.sendKeys(yearValue);
                pause(500);
            } catch (Exception ex) {
                throw new RuntimeException("Failed to set Year Built value: " + ex.getMessage());
            }
        }
    }

    /**
     * Sets the construction type of the property with retry mechanism.
     * @param data Map containing construction type information
     * @throws InterruptedException if the thread sleep is interrupted
     * @throws Exception if all retry attempts fail
     */
    public void setConstructionType(Map<String, String> data) throws InterruptedException, Exception {
        String constructionValue = data.getOrDefault("ConstructionType", "Frame");
        System.out.println("Setting Construction Type to: " + constructionValue);
        
        WebDriverWait wait = new WebDriverWait(driver, 10); // Reduced from 30 to 10 seconds
        int maxRetries = 3;
        int retryCount = 0;
        JavascriptExecutor js = (JavascriptExecutor) driver;
        
        while (retryCount < maxRetries) {
            try {
                // Wait for and click the construction type input
                wait.until(ExpectedConditions.elementToBeClickable(constructionType));
                wait.until(ExpectedConditions.visibilityOf(constructionType));
                
                // Try clicking with both methods
                try {
                    constructionType.click();
                } catch (Exception e) {
                    js.executeScript("arguments[0].click();", constructionType);
                }
                Thread.sleep(500); // Reduced from 1000 to 500ms
                
                // Try multiple XPath patterns to find the option
                String[] xpathAttempts = {
                    String.format("//li[normalize-space(text())='%s']", constructionValue),
                    String.format("//div[contains(@class, 'x-boundlist')]//li[text()='%s']", constructionValue),
                    String.format("//div[contains(@class, 'x-boundlist-item')][text()='%s']", constructionValue),
                    String.format("//li[contains(text(), '%s')]", constructionValue)
                };
                
                WebElement option = null;
                for (String xpath : xpathAttempts) {
                    try {
                        option = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
                        if (option != null && option.isDisplayed()) {
                            // Try JavaScript click first
                            js.executeScript("arguments[0].click();", option);
                            Thread.sleep(500);
                            
                            // Verify if selection worked
                            String selectedValue = constructionType.getAttribute("value");
                            if (selectedValue != null && selectedValue.contains(constructionValue)) {
                                System.out.println("Successfully selected Construction Type using JavaScript click");
                                return;
                            }
                            
                            // If JavaScript click didn't work, try regular click
                            option.click();
                            Thread.sleep(500);
                            
                            // Verify again
                            selectedValue = constructionType.getAttribute("value");
                            if (selectedValue != null && selectedValue.contains(constructionValue)) {
                                System.out.println("Successfully selected Construction Type using regular click");
                                return;
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Failed with xpath: " + xpath + " - " + e.getMessage());
                        continue;
                    }
                }
                
                // If we haven't returned by now, try direct input
                constructionType.clear();
                constructionType.sendKeys(constructionValue);
                Thread.sleep(500); // Reduced from 1000 to 500ms
                
                // Try to find and click the option after typing
                try {
                    WebElement typedOption = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath(String.format("//li[contains(text(), '%s')]", constructionValue))
                    ));
                    typedOption.click();
                    Thread.sleep(500);
                } catch (Exception e) {
                    // If clicking the option fails, try pressing Enter
                    constructionType.sendKeys(org.openqa.selenium.Keys.ENTER);
                }
                
                // Final verification
                String finalValue = constructionType.getAttribute("value");
                if (finalValue != null && finalValue.contains(constructionValue)) {
                    System.out.println("Successfully selected Construction Type using direct input");
                    return;
                }
                
                throw new Exception("Failed to select Construction Type after all attempts");
                
            } catch (Exception e) {
                retryCount++;
                System.out.println("Attempt " + retryCount + " failed: " + e.getMessage());
                
                if (retryCount == maxRetries) {
                    System.out.println("All attempts to select Construction Type failed");
                    throw e;
                }
                
                // Wait between retries and try to reset UI state
                Thread.sleep(1000); // Reduced from 2000 to 1000ms
                try {
                    js.executeScript("document.body.click()");
                } catch (Exception ignored) {}
            }
        }
    }

    /**
     * Sets the roof type of the property with retry mechanism.
     * @param data Map containing roof type information
     * @throws InterruptedException if the thread sleep is interrupted
     * @throws Exception if all retry attempts fail
     */
    public void setRoofType(Map<String, String> data) throws InterruptedException, Exception {
        String roofValue = data.getOrDefault("RoofType", "Other");
        System.out.println("Setting Roof Type to: " + roofValue);
        
        WebDriverWait wait = new WebDriverWait(driver, 30);
        int maxRetries = 3;
        int retryCount = 0;
        JavascriptExecutor js = (JavascriptExecutor) driver;
        
        while (retryCount < maxRetries) {
            try {
                // Wait for and click the roof type input
                wait.until(ExpectedConditions.elementToBeClickable(roofType));
                wait.until(ExpectedConditions.visibilityOf(roofType));
                
                // Try clicking with both methods
                try {
                    roofType.click();
                } catch (Exception e) {
                    js.executeScript("arguments[0].click();", roofType);
                }
                Thread.sleep(1000);
                
                // Try multiple XPath patterns to find the option
                String[] xpathAttempts = {
                    String.format("//li[normalize-space(text())='%s']", roofValue),
                    String.format("//div[contains(@class, 'x-boundlist')]//li[text()='%s']", roofValue),
                    String.format("//div[contains(@class, 'x-boundlist-item')][text()='%s']", roofValue),
                    String.format("//li[contains(text(), '%s')]", roofValue)
                };
                
                WebElement option = null;
                for (String xpath : xpathAttempts) {
                    try {
                        option = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
                        if (option != null && option.isDisplayed()) {
                            // Try JavaScript click first
                            js.executeScript("arguments[0].click();", option);
                            Thread.sleep(500);
                            
                            // Verify if selection worked
                            String selectedValue = roofType.getAttribute("value");
                            if (selectedValue != null && selectedValue.contains(roofValue)) {
                                System.out.println("Successfully selected Roof Type using JavaScript click");
                                return;
                            }
                            
                            // If JavaScript click didn't work, try regular click
                            option.click();
                            Thread.sleep(500);
                            
                            // Verify again
                            selectedValue = roofType.getAttribute("value");
                            if (selectedValue != null && selectedValue.contains(roofValue)) {
                                System.out.println("Successfully selected Roof Type using regular click");
                                return;
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Failed with xpath: " + xpath + " - " + e.getMessage());
                        continue;
                    }
                }
                
                // If we haven't returned by now, try direct input
                roofType.clear();
                roofType.sendKeys(roofValue);
                Thread.sleep(1000);
                
                // Try to find and click the option after typing
                try {
                    WebElement typedOption = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath(String.format("//li[contains(text(), '%s')]", roofValue))
                    ));
                    typedOption.click();
                    Thread.sleep(500);
                } catch (Exception e) {
                    // If clicking the option fails, try pressing Enter
                    roofType.sendKeys(org.openqa.selenium.Keys.ENTER);
                }
                
                // Final verification
                String finalValue = roofType.getAttribute("value");
                if (finalValue != null && finalValue.contains(roofValue)) {
                    System.out.println("Successfully selected Roof Type using direct input");
                    return;
                }
                
                throw new Exception("Failed to select Roof Type after all attempts");
                
            } catch (Exception e) {
                retryCount++;
                System.out.println("Attempt " + retryCount + " failed: " + e.getMessage());
                
                if (retryCount == maxRetries) {
                    System.out.println("All attempts to select Roof Type failed");
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
     * Sets whether there have been losses in the past three years.
     * @param data Map containing loss history information
     */
    public void setLossesInThreeYrNo(Map<String, String> data) {
        String value = data.getOrDefault("Any losses in the last three years?", "No"); // Default to "No"
        System.out.println("Setting losses in three years to: " + value);
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, 10);
            String xpath = String.format("//div[text()='Any losses in the last three years?']/../../../..//label[text()='%s']/../span", value);
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
            clickElement(element, "Any losses in the last three years? " + value);
        } catch (Exception e) {
            System.out.println("Failed to set losses in three years: " + e.getMessage());
            // Try alternative XPath if the first one fails
            try {
                String altXpath = String.format("//label[contains(text(), 'Any losses in the last three years')]/../..//label[text()='%s']/../span", value);
                WebElement element = driver.findElement(By.xpath(altXpath));
                clickElement(element, "Any losses in three years (alternative) " + value);
            } catch (Exception e2) {
                System.out.println("Failed to set losses in three years with alternative xpath: " + e2.getMessage());
                throw e2;
            }
        }
    }

    /**
     * Clicks the save changes button to persist the form data.
     */
    public void clickSaveChanges() {
        clickElement(saveChanges, "saveChanges");
    }

    /**
     * Initiates the quote rate calculation process.
     */
    public void rateQuote() {
        pause(2000);
        clickElement(rateQuote, "Rate Quote");
    }

    /**
     * Submits a request for issue.
     */
    public void requestIssue() {
        clickElement(requestIssue, "Request Issue");
    }

    /**
     * Creates a new location coverages information entry with the provided data.
     * This method orchestrates the complete process of entering all location coverage details.
     * 
     * @param data Map containing all necessary location coverage information
     * @throws InterruptedException if the thread sleep is interrupted
     * @throws Exception if any step in the process fails
     */
    public void CreateANewLocationCoveragesInformation(Map<String, String> data) throws InterruptedException, Exception {
        selectPolCovOption(data);
        pause(2000);
        enterResidenceType(data);
        pause(2000);
        enterReplacementCost(data);
        pause(2000);
        setWindHailDeduct(data);
        pause(2000);
        setResUnderConstrNo(data);
        pause(2000);
        setUnderThreeYearsNo(data);
        pause(2000);
        setYearBuilt(data);
        pause(2000);
        setConstructionType(data);
        pause(2000);
        setRoofType(data);
        pause(2000);
        setLossesInThreeYrNo(data);
        pause(2000);
        clickSaveChanges();
        pause(2000);
        rateQuote();
        pause(2000);
        requestIssue();
        pause(2000);
    }
}