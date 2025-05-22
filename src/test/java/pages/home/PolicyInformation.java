package pages.home;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pages.common.BasePage;
import org.openqa.selenium.JavascriptExecutor;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import java.util.Map;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.IOException;

public class PolicyInformation extends BasePage {

    public PolicyInformation(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }


    @FindBy(xpath = "//div[text()='Billing Method']/../../../..//input")
    public WebElement billingMethod;

    @FindBy(xpath = "//div[text()='Program Type']/../../../..//input")
    public WebElement programType;

    @FindBy(xpath = "//a[contains(text(), 'homeowners | location coverage')]")
    public WebElement locationCoverage;

    @FindBy(xpath = "//div[text()='Is Child or Day Care run out of the home?']/../../../..//label[text()='No']/../span")
    public WebElement childDayCareNo;

    /**@FindBy(xpath = "//div[text()='Is Child or Day Care run out of the home?']/../../../..//label[text()='Yes']/../span")
    public WebElement childDayCareYes;**/

    @FindBy(xpath = "//div[text()='Any underground oil or storage tanks?']/../../../..//label[text()='No']/../span")
    public WebElement undergroundNo;

    /**@FindBy(xpath = "//div[text()='Any underground oil or storage tanks?']/../../../..//label[text()='Yes']/../span")
    public WebElement undergroundYes;**/

    @FindBy(xpath = "//div[text()='Is the residence rented more than 10 weeks per year?']/../../../..//label[text()='No']/../span")
    public WebElement rentedResidenceNo;

    /** @FindBy(xpath = "//div[text()='Is the residence rented more than 10 weeks per year?']/../../../..//label[text()='Yes']/../span")
    public WebElement rentedResidenceYes;**/

    @FindBy(xpath = "//div[text()='Is the residence vacant?']/../../../..//label[text()='No']/../span")
    public WebElement vacantNo;

    /**@FindBy(xpath = "//div[text()='Is the residence vacant?']/../../../..//label[text()='Yes']/../span")
    public WebElement vacantYes;**/

    @FindBy(xpath = "//div[text()='Are there any animals or exotic pets kept on the premises?']/../../../..//label[text()='No']/../span")
    public WebElement petsNo;

    /** @FindBy(xpath = "//div[text()='Are there any animals or exotic pets kept on the premises?']/../../../..//label[text()='Yes']/../span")
    public WebElement petsYes;**/

    @FindBy(xpath = "//span[text()='save changes']")
    public WebElement btnSaveChange;

    @FindBy(xpath = "//span[text()='exit']")
    public WebElement btnExit;


    public void policyInfo(Map<String, String> data) throws InterruptedException, IOException {
        try {
            selectBillingMethod(data);
            
            // Add a small delay to ensure billing method is processed before continuing
            Thread.sleep(500);
            
            selectProgramType(data);
            
            // Make sure program type is selected before continuing to next fields
            try {
                String actualValue = programType.getAttribute("value");
                if (actualValue == null || !actualValue.equals(data.get("ProgramType"))) {
                    System.out.println("Program type value not set correctly, trying again");
                    selectProgramType(data);
                }
            } catch (Exception e) {
                System.out.println("Could not verify program type: " + e.getMessage());
            }
            
            selectChildDayCare(data);
            selectOilStorage(data);
            selectResidenceRented(data);
            selectResidenceVacant(data);
            selectExoticPets(data);
            btnSaveChangeClick();
            
            // Check if the locationCoverage link is visible and enabled
            try {
                WebDriverWait wait = new WebDriverWait(driver, 3);
                wait.until(ExpectedConditions.elementToBeClickable(locationCoverage));
                System.out.println("Location coverage link is clickable");
            } catch (Exception e) {
                System.out.println("Location coverage link is not clickable: " + e.getMessage());
                // Take a screenshot to see what's happening
                takeScreenshot("location_coverage_error");
            }
            
            // Try using JavaScript to click if regular click might be failing
            try {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].scrollIntoView(true);", locationCoverage);
                js.executeScript("arguments[0].click();", locationCoverage);
                System.out.println("Clicked on Location Coverage link using JavaScript");
            } catch (Exception e) {
                System.out.println("Failed to click using JavaScript: " + e.getMessage());
                // Try normal click as fallback
                selectLocationCoverage();
            }
        } catch (Exception e) {
            System.out.println("Error in policy info step: " + e.getMessage());
            e.printStackTrace();
            takeScreenshot("policy_info_error");
            // Try to continue anyway by clicking next if possible
            try {
                selectLocationCoverage();
            } catch (Exception ex) {
                System.out.println("Failed to continue: " + ex.getMessage());
            }
        }
    }


    public void selectBillingMethod(Map<String, String> data) {
        typeText(billingMethod, data.get("BillingMethod"), "Billing Method: " + data.get("BillingMethod"));
    }


    // Helper method to wait for element
//    private void waitForElementPresent(By locator, int timeoutSeconds) {
//        long endTime = System.currentTimeMillis() + (timeoutSeconds * 1000L);
//        while (System.currentTimeMillis() < endTime) {
//            try {
//                if (driver.findElement(locator).isDisplayed()) {
//                    return;
//                }
//            } catch (Exception e) {
//                // Continue waiting
//            }
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException ie) {
//                Thread.currentThread().interrupt();
//                break;
//            }
//        }
//    }

    /** public void selectProgramType(Map<String, String> data) {
        clickElement(driver.findElement(By.xpath("//li[text()='" + data.get("ProgramType") + "']")),
                "Program type: " + data.get("ProgramType"));
    }**/

    public void selectProgramType(Map<String, String> data) throws InterruptedException {
        try {
            // Add explicit wait for program type field to be ready
            WebDriverWait wait = new WebDriverWait(driver, 5);
            wait.until(ExpectedConditions.elementToBeClickable(programType));
            
            // Scroll element into view and ensure it's visible
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView(true);", programType);
            
            // Type the text
            typeText(programType, data.get("ProgramType"), "Program Type: " + data.get("ProgramType"));
        } catch (Exception e) {
            System.out.println("Error selecting program type: " + e.getMessage());
            // Try JavaScript input as fallback
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].value = arguments[1];", programType, data.get("ProgramType"));
            System.out.println("Set Program Type via JavaScript: " + data.get("ProgramType"));
        }
    }

    /**public void selectLocationCoverage(Map<String, String> data) {
        typeText(locationCoverage, data.get("LocationCoverage"), "locationCoverage" + data.get("LocationCoverage"));
    }**/

    public void selectChildDayCare(Map<String, String> data) {
        try {
            WebElement element = driver.findElement(By.xpath("//div[text()='Is Child or Day Care run out of the home?']/../../../..//label[text()='"  + data.get("Is Child or Day Care run out of the home?") + "']/../span"));
            WebDriverWait wait = new WebDriverWait(driver, 5);
            wait.until(ExpectedConditions.elementToBeClickable(element));
            clickElement(element, "Is Child or Day Care run out of the home? " + data.get("Is Child or Day Care run out of the home?"));
        } catch (Exception e) {
            System.out.println("Error selecting Child Day Care option: " + e.getMessage());
            // Try using JavaScript click as fallback
            try {
                WebElement element = driver.findElement(By.xpath("//div[text()='Is Child or Day Care run out of the home?']/../../../..//label[text()='"  + data.get("Is Child or Day Care run out of the home?") + "']/../span"));
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].click();", element);
                System.out.println("Clicked Child Day Care using JavaScript: " + data.get("Is Child or Day Care run out of the home?"));
            } catch (Exception ex) {
                System.out.println("Failed JavaScript click for Child Day Care: " + ex.getMessage());
            }
        }
    }
    public void selectOilStorage(Map<String, String> data) {
        clickElement(driver.findElement(By.xpath("//div[text()='Any underground oil or storage tanks?']/../../../..//label[text()='" + data.get("Any underground oil or storage tanks?") + "']/../span")),
                "Any underground oil or storage tanks? " + data.get("Any underground oil or storage tanks?"));
    }

    public void selectResidenceRented(Map<String, String> data) {
        clickElement(driver.findElement(By.xpath("//div[text()='Is the residence rented more than 10 weeks per year?']/../../../..//label[text()='" + data.get("Is the residence rented more than 10 weeks per year?") + "']/../span")),
                "Is the residence rented more than 10 weeks per year? " + data.get("Is the residence rented more than 10 weeks per year?"));

    }

    public void selectResidenceVacant(Map<String, String> data) {
        clickElement(driver.findElement(By.xpath("//div[text()='Is the residence vacant?']/../../../..//label[text()='" + data.get("Is the residence vacant?") + "']/../span")),
                "Is the residence vacant? " + data.get("Is the residence vacant?"));

    }

    public void selectExoticPets(Map<String, String> data) {
        clickElement(driver.findElement(By.xpath("//div[text()='Are there any animals or exotic pets kept on the premises?']/../../../..//label[text()='" + data.get("Are there any animals or exotic pets kept on the premises?") + "']/../span")),
                "Are there any animals or exotic pets kept on the premise? " + data.get("Are there any animals or exotic pets kept on the premises?"));

    }
/** public void selectChildDayCareYes(Map<String, String> data) {
        typeText(childDayCareYes, data.get("Is Child or Day Care run out of the home?"), "childDayCareYes" + data.get("Is Child or Day Care run out of the home?"));
    }

    public void selectsUndergroundNo(Map<String, String> data) {
        typeText(undergroundNo, data.get("Any underground oil or storage tanks?"), "undergroundNo" + data.get("Any underground oil or storage tanks"));
    }

 /** public void selectUndergroundYes(Map<String, String> data) {
        typeText(undergroundYes, data.get("Any underground oil or storage tanks?"), "undergroundYes" + data.get("Any underground oil or storage tanks?"));
    }

    public void selectRentedResidenceNo(Map<String, String> data) {
        typeText(rentedResidenceNo, data.get("Is the residence rented more than 10 weeks per year?"), "rentedResidenceNo" + data.get("Is the residence rented more than 10 weeks per year?"));
    }

 /** public void selectRentedResidenceYes(Map<String, String> data) {
        typeText(rentedResidenceYes, data.get("Is the residence rented more than 10 weeks per year?"), "rentedResidenceYes" + data.get("Is the residence rented more than 10 weeks per year?"));
    }

    public void selectVacantNo(Map<String, String> data) {
        typeText(vacantNo, data.get("Is the residence vacant?"), "vacantNo" + data.get("Is the residence vacant?"));
    }

 /**public void selectVacantYes(Map<String, String> data) {
        typeText(vacantYes, data.get("Is the residence vacant?"), "vacantYes" + data.get("Is the residence vacant?"));
    }

    public void selectPetsNo(Map<String, String> data) {
        typeText(petsNo, data.get("Are there any animals or exotic pets kept on the premise?"), "petsNo" + data.get("Are there any animals or exotic pets kept on the premise?"));
    }

 /**public void selectPetsYes(Map<String, String> data) {
        typeText(petsYes, data.get("Are there any animals or exotic pets kept on the premise?"), "petsYes" + data.get("Are there any animals or exotic pets kept on the premise?"));
    }**/

    public void btnSaveChangeClick() {
        try {
            // Wait for the save button to be clickable
            WebDriverWait wait = new WebDriverWait(driver, 5);
            wait.until(ExpectedConditions.elementToBeClickable(btnSaveChange));
            
            // Try normal click first
            clickElement(btnSaveChange, "btnSaveChange");
            
            // Wait briefly to let the save operation complete
            Thread.sleep(500);
            
        } catch (Exception e) {
            System.out.println("Error clicking save button: " + e.getMessage());
            
            // Try JavaScript click as fallback
            try {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].click();", btnSaveChange);
                System.out.println("Clicked save button using JavaScript");
                
                // Wait briefly to let the save operation complete
                Thread.sleep(500);
                
            } catch (Exception ex) {
                System.out.println("Failed JavaScript click for save button: " + ex.getMessage());
            }
        }
    }

    public void selectLocationCoverage() throws InterruptedException {
        try {
            // Use a shorter wait time before clicking
            WebDriverWait wait = new WebDriverWait(driver, 3);
            wait.until(ExpectedConditions.elementToBeClickable(locationCoverage));
            
            // Try JavaScript click directly which is faster
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", locationCoverage);
            System.out.println("Clicked Location Coverage with JavaScript");
        } catch (Exception e) {
            System.out.println("JavaScript click failed, trying normal click: " + e.getMessage());
            // If JavaScript click fails, try normal click as fallback
            clickElement(locationCoverage, "Location Coverage");
        }
    }

    public void btnExitClick() {clickElement(btnExit, "btnExit");}

}











