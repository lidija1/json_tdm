package pages.home;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.common.BasePage;

import java.util.Map;

public class LocationCoverage extends BasePage {

    @FindBy(xpath = " //span[text()='save changes']")
    public WebElement saveChanges;

    @FindBy(xpath = "//div[text()='Policy Coverage Option']/../../../..//input")
    public WebElement polCovOption;

    @FindBy(xpath = "//div[contains(text(),'Residence Type')]/ancestor::div[contains(@class,'form-field')]//input")
    public WebElement residenceType;

    @FindBy(xpath = "//div[text()='Replacement Cost']/../../../..//input")
    WebElement replaceCost;

    @FindBy(xpath = "//div[text()='Windstorm or Hail  Deductible']/../../../..//input/../..//div[2]")
    public WebElement windHailDeduct;

    @FindBy(xpath = "//div[text()='Year Built']/../../../..//input")
    public WebElement yearBuilt;

    @FindBy(xpath = "//div[text()='Construction Type']/../../../..//input")
    public WebElement constructionType;

    @FindBy(xpath = "//div[text()='Roof Type']/../../../..//input")
    public WebElement roofType;


    public LocationCoverage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void locationCoverAct(Map<String, String> data) throws InterruptedException {
        setResidenceType(data);
        selectPolCovOption(data);
        setReplaceCost();
        setReplaceCostM(data);
        setWindHailDeduct(data);
        setResUnderConstrNO(data);
        setUnderThreeYearsYes(data);
        setYearBuilt(data);
        setConstructionType(data);
        setRoofType(data);
        setLossesInThreeYrNo(data);
    }


   // public void setResidenceType(Map<String, String> data) {
      ///  clickElement(driver.findElement(By.xpath("//li[text()='"+data.get("ResidenceType")+"']")),
          //      "Residence Type: "+ data.get("ResidenceType"));
   // }


    public void setResidenceType(Map<String, String> data) {
        try {
            // First wait longer for the page to fully stabilize
            System.out.println("Starting Residence Type selection process...");
            Thread.sleep(5000); // Increased wait time to 5 seconds
            
            // Check if we're in an iframe and switch to it if needed
            try {
                // List all frames/iframes on the page
                java.util.List<WebElement> frames = driver.findElements(By.tagName("iframe"));
                System.out.println("Found " + frames.size() + " frames on page");
                
                // Try to find a frame that might contain our form elements
                boolean frameSwitched = false;
                for (int i = 0; i < frames.size(); i++) {
                    try {
                        driver.switchTo().frame(i);
                        // Check if elements exist in this frame
                        if (!driver.findElements(By.xpath("//*[contains(text(),'Residence Type')]")).isEmpty()) {
                            System.out.println("Found Residence Type text in frame " + i);
                            frameSwitched = true;
                            break;
                        }
                        // Switch back to default content before trying next frame
                        driver.switchTo().defaultContent();
                    } catch (Exception e) {
                        System.out.println("Error checking frame " + i + ": " + e.getMessage());
                        driver.switchTo().defaultContent();
                    }
                }
                
                if (!frameSwitched) {
                    System.out.println("No relevant frames found, staying in main content");
                    driver.switchTo().defaultContent(); // Make sure we're back in the main content
                }
            } catch (Exception e) {
                System.out.println("Error while checking frames: " + e.getMessage());
                driver.switchTo().defaultContent(); // Reset to main content
            }
            
            // Try to get a screenshot to analyze the actual page structure
            try {
                takeScreenshot("residence_type_page");
                System.out.println("Screenshot taken of the current page state");
            } catch (Exception ex) {
                System.out.println("Could not take screenshot: " + ex.getMessage());
            }
            
            // Print the page source for analysis
            try {
                System.out.println("Page URL: " + driver.getCurrentUrl());
                // Print only a portion of the page source to avoid flooding logs
                String pageSource = driver.getPageSource();
                String excerpt = pageSource.length() > 1000 ? 
                    pageSource.substring(0, 500) + "..." + 
                    pageSource.substring(pageSource.length() - 500) : 
                    pageSource;
                System.out.println("Page source excerpt: " + excerpt);
            } catch (Exception ex) {
                System.out.println("Could not print page source: " + ex.getMessage());
            }
            
            // Try to find any element containing "Residence Type" text first to understand the structure
            WebDriverWait wait = new WebDriverWait(driver, 20); // Increased timeout
            try {
                WebElement anyResidenceTypeElement = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//*[contains(text(),'Residence Type')]")));
                System.out.println("Found element with 'Residence Type' text: " + anyResidenceTypeElement.getTagName());
                
                // Get the element's attributes to understand structure
                System.out.println("Element class: " + anyResidenceTypeElement.getAttribute("class"));
                System.out.println("Element parent: " + anyResidenceTypeElement.findElement(By.xpath("./..")).getTagName());
            } catch (Exception e) {
                System.out.println("Could not find any element containing 'Residence Type' text: " + e.getMessage());
            }
            
            // Now try a comprehensive set of selectors to find and click the dropdown
            String[] dropdownXpaths = {
                // Original selectors
                "//div[contains(text(),'Residence Type')]/ancestor::div[contains(@class,'form-field')]//input",
                "//div[contains(text(),'Residence Type')]/following::input[1]",
                "//label[contains(text(),'Residence Type')]/following::input[1]",
                
                // New more comprehensive selectors
                "//div[contains(text(),'Residence Type')]/..//input",
                "//div[contains(text(),'Residence Type')]/parent::*/parent::*/descendant::input",
                "//div[contains(normalize-space(.),'Residence Type')]/ancestor::div[4]//input",
                "//div[contains(text(),'Residence')]//following::input[1]",
                "//label[contains(text(),'Residence')]//following::input[1]",
                "//div[contains(@class,'form-field')][.//text()[contains(.,'Residence Type')]]//input",
                "//div[contains(@class,'field')][.//text()[contains(.,'Residence')]]//input",
                "//select[contains(@id,'residence') or contains(@name,'residence')]",
                "//*[contains(text(),'Residence Type')]/ancestor::div[position() <= 6]//input",
                "//input[contains(@id,'residence') or contains(@name,'residence')]",
                "//*[@aria-label='Residence Type' or @aria-label='Residence']",
                "//div[contains(text(),'Residence')]//ancestor::*[contains(@class,'dropdown') or contains(@class,'select')]//input",
                
                // More general location-based selectors
                "(//div[contains(@class,'form-group')][position() < 5]//input)[1]",
                "(//*[contains(text(),'Type')]/ancestor::div[contains(@class,'form')]//input)[1]",
                
                // Plain text search if labels use different case/format
                "//*[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'),'residence type')]/..//input"
            };
            
            boolean dropdownClicked = false;
            WebElement foundElement = null;
            
            for (String xpath : dropdownXpaths) {
                try {
                    System.out.println("Trying selector: " + xpath);
                    foundElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
                    
                    // If found, try to click it
                    System.out.println("Found element with selector: " + xpath);
                    
                    // Try regular click first
                    try {
                        foundElement.click();
                        System.out.println("Successfully clicked element with direct click");
                        dropdownClicked = true;
                        break;
                    } catch (Exception e) {
                        System.out.println("Direct click failed, trying JavaScript click");
                        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", foundElement);
                        System.out.println("Successfully clicked element with JavaScript click");
                        dropdownClicked = true;
                        break;
                    }
                } catch (Exception e) {
                    System.out.println("Selector failed: " + xpath);
                }
            }
            
            // If no dropdown element was found, try to check if it might be a select element instead of input
            if (!dropdownClicked) {
                try {
                    WebElement selectElement = driver.findElement(By.xpath("//select[contains(@id,'residence') or contains(@name,'residence') or ./option[contains(text(),'Residence')]]"));
                    System.out.println("Found a SELECT element instead of dropdown input");
                    
                    // For select elements, we need to use Select class
                    org.openqa.selenium.support.ui.Select dropdown = new org.openqa.selenium.support.ui.Select(selectElement);
                    dropdown.selectByVisibleText(data.get("ResidenceType"));
                    System.out.println("Selected option using Select class: " + data.get("ResidenceType"));
                    return;
                } catch (Exception e) {
                    System.out.println("No SELECT element found either: " + e.getMessage());
                }
                
                throw new RuntimeException("Could not find or click Residence Type dropdown with any selector");
            }
            
            // Wait for dropdown options to appear
            Thread.sleep(1500);
            
            // Try to select the option with comprehensive option selectors
            String residenceTypeValue = data.get("ResidenceType");
            String[] optionXpaths = {
                // Original selectors
                "//li[text()='" + residenceTypeValue + "']",
                "//li[contains(text(),'" + residenceTypeValue + "')]",
                "//div[@role='option' and contains(text(),'" + residenceTypeValue + "')]",
                "//div[@role='listbox']//li[contains(text(),'" + residenceTypeValue + "')]",
                
                // New more comprehensive selectors
                "//ul/li[contains(text(),'" + residenceTypeValue + "')]",
                "//*[contains(@class,'option') and contains(text(),'" + residenceTypeValue + "')]",
                "//*[contains(@class,'dropdown-item') and contains(text(),'" + residenceTypeValue + "')]",
                "//*[contains(@class,'menu-item') and contains(text(),'" + residenceTypeValue + "')]",
                "//div[contains(@class,'dropdown') or contains(@class,'select')]//li[contains(text(),'" + residenceTypeValue + "')]",
                "//div[contains(@class,'overlay') or contains(@class,'popup')]//li[contains(text(),'" + residenceTypeValue + "')]",
                "//div[contains(@class,'overlay') or contains(@class,'popup')]//*[contains(text(),'" + residenceTypeValue + "')]"
            };
            
            boolean optionSelected = false;
            for (String xpath : optionXpaths) {
                try {
                    System.out.println("Trying option selector: " + xpath);
                    WebElement option = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
                    
                    // Try direct click
                    try {
                        option.click();
                        System.out.println("Successfully clicked option with direct click: " + residenceTypeValue);
                    } catch (Exception e) {
                        System.out.println("Direct option click failed, trying JavaScript click");
                        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", option);
                        System.out.println("Successfully clicked option with JavaScript click: " + residenceTypeValue);
                    }
                    
                    optionSelected = true;
                    break;
                } catch (Exception e) {
                    System.out.println("Option selector failed: " + xpath);
                }
            }
            
            if (!optionSelected) {
                throw new RuntimeException("Could not select Residence Type option: " + residenceTypeValue);
            }
            
            Thread.sleep(1000); // Wait for selection to take effect
            System.out.println("Successfully completed Residence Type selection: " + residenceTypeValue);
            
        } catch (Exception e) {
            System.out.println("CRITICAL ERROR selecting Residence Type: " + e.getMessage());
            e.printStackTrace(); // Print full stack trace for better debugging
            
            // Try to take error screenshot
            try {
                takeScreenshot("residence_type_error");
            } catch (Exception ex) {
                System.out.println("Could not take error screenshot: " + ex.getMessage());
            }
            
            throw new RuntimeException("Failed to select Residence Type: " + e.getMessage(), e);
        }
    }

   /** public void setResidenceType(Map<String, String> data) throws InterruptedException {
        clickElement(residenceType, "Residence Type dropdown menu.");
        Thread.sleep(1000);
        clickElement(driver.findElement(By.xpath("//li[text()='" + data.get("ResidenceType") + "']")), "Residence Type: " + data.get("ResidenceType"));
    }    ovako se postavlja dropdown ako on nije otvoren.. ovom metodom prvo otvaramo pa upisujemo**/


   public void selectPolCovOption(Map<String, String> data) {
       WebDriverWait wait = new WebDriverWait(driver, 10);

       // Click the dropdown to expand options
       wait.until(ExpectedConditions.elementToBeClickable(polCovOption)).click();

       // Wait for the desired option to be present and click it
       WebElement option = wait.until(ExpectedConditions.elementToBeClickable(
               By.xpath("//li[text()='"+data.get("PolicyCoverageOption")+"']")));
       option.click();
   }

    public void setReplaceCost() {
        String xpath = "//label[text()='Replacement cost contents?']/preceding-sibling::span/input[@type='checkbox']";
        WebElement checkbox = driver.findElement(By.xpath(xpath));

        if (!checkbox.isSelected()) {
            checkbox.click();
        }

    }
    public void setReplaceCostM(Map<String, String> data){
        typeText(replaceCost, data.get("ReplacementCost"),"Replacement Cost: " + data.get("ReplacementCost"));
    }

    public void setWindHailDeduct(Map<String, String> data) throws InterruptedException {
        clickElement(windHailDeduct, "Windstorm or Hail Deductible dropdown");
        
        // Keep a minimal wait here because we need to wait for dropdown to expand
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//li[text()='"+data.get("WindsHailDeductible")+"']")));
        option.click();
    }

    public void setResUnderConstrNO(Map<String, String> data) {
        clickElement(driver.findElement(By.xpath("//div[text()='Is the residence under construction or major renovation?']/../../../..//label[text()='" + data.get("Is the residence under construction or major renovation?") + "']/../span")),
                "Is the residence under construction or major renovation? " + data.get("Is the residence under construction or major renovation?"));
    }

    public void setUnderThreeYearsYes(Map<String, String> data) {
        clickElement(driver.findElement(By.xpath("//div[text()='Has the customer lived at this location for less than 3 years?']/../../../..//label[text()='" + data.get("Has the customer lived at this location for less than 3 years?") + "']/../span")),
                "Has the customer lived at this location for less than 3 years? " + data.get("Has the customer lived at this location for less than 3 years?"));
    }

    public void setYearBuilt(Map<String, String> data) {
        typeText(yearBuilt, data.get("YearBuilt"), "Year Built: " + data.get("YearBuilt"));
    }

    public void setConstructionType(Map<String, String> data) throws InterruptedException {
        clickElement(constructionType, "Construction Type dropdown");
        
        // Use explicit wait instead of Thread.sleep
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//li[text()='" + data.get("ConstructionType")+"']")));
        option.click();
    }

    public void setRoofType(Map<String, String> data) throws InterruptedException {
        clickElement(roofType, "Roof Type");
        
        // Use explicit wait instead of Thread.sleep
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//li[text()='"+data.get("RoofType")+"']")));
        option.click();
    }

    public void setLossesInThreeYrNo(Map<String, String> data) {
        clickElement(driver.findElement(By.xpath("//div[text()='Any losses in the last three years?']/../../../..//label[text()='" + data.get("Any losses in the last three years?") + "']/../span")),
                "Any losses in the last three years? " + data.get("Any losses in the last three years?"));
    }
}