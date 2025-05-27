package pages.home;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pages.common.BasePage;
import org.openqa.selenium.JavascriptExecutor;
import java.util.Map;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Page class representing the policy information functionality.
 * Handles all interactions with policy information form elements and operations
 * including program type, coverage options, and various policy-related questions.
 */
public class PolicyInformation extends BasePage {

    /**
     * Constructor for PolicyInformation page.
     * @param driver WebDriver instance to be used for browser interactions
     */
    public PolicyInformation(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//div[text()='Program Type']/../../../..//input")
    public WebElement programType;


    @FindBy(xpath = "//a[contains(text(), 'homeowners | location coverage')]")
    public WebElement locationCoverage;


    @FindBy(xpath = "//div[text()='Is Child or Day Care run out of the home?']/../../../..//label[text()='No']/../span")
    public WebElement childDayCareNo;


    @FindBy(xpath = "//div[text()='Any underground oil or storage tanks?']/../../../..//label[text()='No']/../span")
    public WebElement undergroundNo;


    @FindBy(xpath = "//div[text()='Is the residence rented more than 10 weeks per year?']/../../../..//label[text()='No']/../span")
    public WebElement rentedResidenceNo;


    @FindBy(xpath = "//div[text()='Is the residence vacant?']/../../../..//label[text()='No']/../span")
    public WebElement vacantNo;


    @FindBy(xpath = "//div[text()='Are there any animals or exotic pets kept on the premises?']/../../../..//label[text()='No']/../span")
    public WebElement petsNo;


    @FindBy(xpath = "//span[text()='save changes']")
    public WebElement btnSaveChange;


    @FindBy(xpath = "//span[text()='exit']")
    public WebElement btnExit;


    @FindBy(xpath = "/html/body/div[2]/div[3]/div[2]/div/div[2]/table[4]/tbody/tr/td/div/a")
    public WebElement btnLocCov;


    /**
     * Handles the complete policy information entry process.
     * This method orchestrates all the policy information input steps including
     * billing method, program type, and various policy-related questions.
     * 
     * @param data Map containing all necessary policy information
     * @throws Exception if any step in the process fails
     */
    public void policyInfo(Map<String, String> data) throws Exception {
            selectBillingMethod(data);
            Thread.sleep(5000);
            selectProgramType(data);
            selectChildDayCare(data);
            selectOilStorage(data);
            selectResidenceRented(data);
            selectResidenceVacant(data);
            selectExoticPets(data);
            btnSaveChangeClick();
            clickLocCov();
    }

    /**
     * Selects the billing method from the provided data.
     * @param data Map containing billing method information
     */
    public void selectBillingMethod(Map<String, String> data) {
        clickElement(driver.findElement(By.xpath("//li[text()='"+ data.get("BillingMethod") + "']")),
                "Billing method: " + data.get("BillingMethod"));
    }
    /**
     * Clicks the location coverage button to navigate to coverage details.
     */
    public void clickLocCov() {
        clickElement(btnLocCov, "btnLocCov");
    }

    /**
     * Selects the program type from the provided data.
     * @param data Map containing program type information
     */
    public void selectProgramType(Map<String, String> data) {
       typeText(programType, data.get("ProgramType"), "Program Type: " + data.get("ProgramType"));
    }

    /**
     * Sets whether child daycare is run from the home.
     * @param data Map containing child daycare information
     */
    public void selectChildDayCare(Map<String, String> data) {
        clickElement(childDayCareNo, "Child DayCare NO");
    }
    /**
     * Sets whether there are underground oil storage tanks.
     * @param data Map containing oil storage information
     */
    public void selectOilStorage(Map<String, String> data) {
        clickElement(undergroundNo, "Oil Storage NO");
    }

    /**
     * Sets whether the residence is rented more than 10 weeks per year.
     * @param data Map containing residence rental information
     */
    public void selectResidenceRented(Map<String, String> data) {
       clickElement(rentedResidenceNo, "Residence NO");
    }

    /**
     * Sets whether the residence is vacant.
     * @param data Map containing residence vacancy information
     */
    public void selectResidenceVacant(Map<String, String> data) {
        clickElement(vacantNo, "Residence Vacant NO");
    }

    /**
     * Sets whether there are any exotic pets on the premises.
     * @param data Map containing exotic pets information
     */
    public void selectExoticPets(Map<String, String> data) {
        clickElement(petsNo, "Pets NO");
    }


    /**
     * Clicks the save changes button to persist the form data.
     */
    public void btnSaveChangeClick() {
        clickElement(btnSaveChange, "btnSaveChange");
    }



    //No usages

    /**
     * Clicks the exit button to leave the current form.
     */
    public void btnExitClick() {clickElement(btnExit, "btnExit");}
    /**
     * Selects the location coverage option with optimized clicking strategy.
     * Attempts JavaScript click first, falls back to normal click if needed.
     * @throws InterruptedException if the thread sleep is interrupted
     */
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

}











