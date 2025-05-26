package pages.home;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pages.common.BasePage;
import org.openqa.selenium.JavascriptExecutor;

import java.util.List;
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

//    @FindBy(xpath = "//div[contains(text(), 'Billing Method')]/../../..//input")
//    public WebElement billingMethod;

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


    public void policyInfo(Map<String, String> data) throws InterruptedException {
//            Thread.sleep(500);
            selectBillingMethod(data);
            Thread.sleep(5000);
            selectProgramType(data);
            selectChildDayCare(data);
            selectOilStorage(data);
            selectResidenceRented(data);
            selectResidenceVacant(data);
            selectExoticPets(data);
            btnSaveChangeClick();
    }
    @FindBy(xpath = "//div[contains(text(), 'Billing Method')]/../../..//input")
    public WebElement billingMethod;

    @FindBy(xpath = "//div[contains(@class, 'x-boundlist-list-ct')]//li")
    public List<WebElement> billingMethodOptions;

    public void selectBillingMethod(Map<String, String> data) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 10);

            // First, ensure the billing method field is clickable
            wait.until(ExpectedConditions.elementToBeClickable(billingMethod));

            // Click to open dropdown
            clickElement(billingMethod, "Opening Billing Method dropdown");

            // Wait for dropdown options to be visible
            wait.until(ExpectedConditions.visibilityOfAllElements(billingMethodOptions));

            // Find and click the matching option
            String targetBillingMethod = data.get("BillingMethod");
            boolean found = false;

            for (WebElement option : billingMethodOptions) {
                if (option.getText().trim().equals(targetBillingMethod)) {
                    // Wait for the specific option to be clickable
                    wait.until(ExpectedConditions.elementToBeClickable(option));
                    clickElement(option, "Selecting Billing Method: " + targetBillingMethod);
                    found = true;
                    break;
                }
            }

            if (!found) {
                System.out.println("Could not find billing method: " + targetBillingMethod);
                // Fallback to direct input if option not found
                typeText(billingMethod, targetBillingMethod, "Billing Method (direct input): " + targetBillingMethod);
            }

            // Verify selection was made
            wait.until(ExpectedConditions.attributeContains(billingMethod, "value", targetBillingMethod));

        } catch (Exception e) {
            System.out.println("Error selecting billing method: " + e.getMessage());
            try {
                // Fallback to direct input
                billingMethod.clear();
                typeText(billingMethod, data.get("BillingMethod"), "Billing Method (fallback): " + data.get("BillingMethod"));
            } catch (Exception e2) {
                System.out.println("Both dropdown and direct input failed: " + e2.getMessage());
                throw e2;
            }
        }
    }


//    public void selectBillingMethod(Map<String, String> data) {
////        typeText(billingMethod, data.get("BillingMethod"), "Billing Method: " + data.get("BillingMethod"));
//        try {
//            // Click to open the dropdown
//            clickElement(billingMethod, "Billing Dropdown");
//
//            // Wait for and click the exact program option
//            WebDriverWait wait = new WebDriverWait(driver, 5);
//            WebElement exactProgram = wait.until(ExpectedConditions.elementToBeClickable(
//                    By.xpath("//li[text()='" + data.get("BillingMethod") + "']")));
//            clickElement(exactProgram, "BillingMethod: " + data.get("BillingMethod"));
//
//        } catch (Exception e) {
//            System.out.println("Failed to select BillingMethod using dropdown: " + e.getMessage());
//            // Fallback to direct input if dropdown fails
//            typeText(billingMethod, data.get("BillingMethod"), "BillingMethod: " + data.get("BillingMethod"));
//        }
//    }
//@FindBy(xpath = "//input[contains(@class, 'x-form-field') and @type='text']")
//public WebElement billingMethodInput;

//    public void selectBillingMethod(Map<String, String> data) {
//        try {
//            billingMethodInput.click(); // Open the dropdown
//             // Wait 1 second for the options to appear (adjust as needed)
//            String optionXpath = String.format("//li[text()='%s']", data.get("BillingMethod"));
//            WebElement option = driver.findElement(By.xpath(optionXpath));
//            option.click();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//public void selectBillingMethod(Map<String, String> data) {
//    try {
//        // Clear any existing value first
//        billingMethodInput.clear();
//
//        // Wait for the program field to be clickable
//        WebDriverWait wait = new WebDriverWait(driver, 10);
//        wait.until(ExpectedConditions.elementToBeClickable(billingMethodInput));
//
//        // Click to open the dropdown
//        clickElement(billingMethodInput, "Billing dropdown");
//
//        // Construct a more precise XPath for exact program match
//        String exactProgramXPath = String.format(
//                "//li[normalize-space(text())='%s' and not(contains(@class, 'disabled'))]",
//                data.get("BillingMethod")
//        );
//
//        // Wait for and click the exact program option
//        WebElement exactProgram = wait.until(ExpectedConditions.elementToBeClickable(
//                By.xpath(exactProgramXPath)
//        ));
//
//        // Add a small delay before clicking to ensure UI is stable
//        Thread.sleep(1000);
//
//        // Click the exact program
//        clickElement(exactProgram, "Billing Method: " + data.get("BillingMethod"));
//
//        // Verify the selection
//        wait.until(ExpectedConditions.attributeContains(billingMethodInput, "value", data.get("BillingMethod")));
//        System.out.println("Successfully selected billing: " + data.get("BillingMethod"));
//
//    } catch (Exception e) {
//        System.out.println("Failed to select billing using dropdown: " + e.getMessage());
//        // Fallback to direct input if dropdown fails
//        try {
//            billingMethodInput.clear();
//            typeText(billingMethodInput, data.get("BillingMethod"), "BillingMethod: " + data.get("BillingMethod"));
//            // Wait for the value to be set
//            WebDriverWait wait = new WebDriverWait(driver, 5);
//            wait.until(ExpectedConditions.attributeContains(billingMethodInput, "value", data.get("BillingMethod")));
//        } catch (Exception e2) {
//            System.out.println("Both dropdown and direct input failed: " + e2.getMessage());
//            throw e2;
//        }
//    }
//}

    public void selectProgramType(Map<String, String> data) {
       typeText(programType, data.get("ProgramType"), "Program Type: " + data.get("ProgramType"));
    }

    public void selectChildDayCare(Map<String, String> data) {
        clickElement(childDayCareNo, "Child DayCare NO");
    }
    public void selectOilStorage(Map<String, String> data) {
        clickElement(undergroundNo, "Oil Storage NO");
    }

    public void selectResidenceRented(Map<String, String> data) {
       clickElement(rentedResidenceNo, "Residence NO");
    }

    public void selectResidenceVacant(Map<String, String> data) {
        clickElement(vacantNo, "Residence Vacant NO");
    }

    public void selectExoticPets(Map<String, String> data) {
        clickElement(petsNo, "Pets NO");
    }

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











