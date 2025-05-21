package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.Map;

public class PolicyInformation extends BasePage {

    public PolicyInformation(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    // Billing method selector
    @FindBy(xpath = "//span[text()='save changes']")
    public WebElement saveChanges;

    // Tree node selector!!
    @FindBy(xpath = "//a[contains(text(), 'homeowners | location coverage')]")
    public WebElement locationCoverage;

    @FindBy(xpath = "//div[text()='Program Type']/../../../..//input")
    public WebElement programType;

    @FindBy(xpath = "//div[text()='NewTransactionEffDate']/../../../..//input")
    public WebElement newTransactionEffDate;


    public void policyInfo(Map<String, String> data) throws InterruptedException {
        selectBillingMethod(data);
        Thread.sleep(1000);
        selectProgramType(data);
        selectChildDayCare(data);
        selectOilStorage(data);
        selectResidenceRented(data);
        selectResidenceVacant(data);
        selectExoticPets(data);
        setNewTransactionEffDate(data);
        clickSaveChanges();
        Thread.sleep(1000);
        selectLocationCoverage();
    }

    public void selectBillingMethod(Map<String, String> data) {
    public void selectProgramType(Map<String, String> data) throws InterruptedException {
       Thread.sleep(2000);
       // clickElement(driver.findElement(By.xpath("//li[text()='Basic']")),"on basic program");
        typeText(programType, data.get("Program Type"),"Program Type: " + data.get("Program Type"));
    }

    public void selectChildDayCare(Map<String, String> data) {
        clickElement(driver.findElement(By.xpath("//div[text()='Is Child or Day Care run out of the home?']/../../../..//label[text()='"  + data.get("Is Child or Day Care run out of the home?") + "']/../span")),
                "Is Child or Day Care run out of the home? " + data.get("Is Child or Day Care run out of the home?"));
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

    public void clickSaveChanges() {
        clickElement(saveChanges,"Save Changes");
    }

    public void selectLocationCoverage() throws InterruptedException {
       Thread.sleep(2000);
       clickElement(locationCoverage, "Location Coverage");
    }
}
