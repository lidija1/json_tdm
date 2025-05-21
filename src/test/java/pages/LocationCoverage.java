package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Map;

public class LocationCoverage extends BasePage {

    @FindBy(xpath = " //span[text()='save changes']")
    public WebElement saveChanges;

    @FindBy(xpath = "//div[text()='PolicyCoverageOption']/../../../..//input")
    public WebElement policyCoverageOption;

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

    @FindBy(xpath = "//div[text()='Contents']/../../../..//input")
    public WebElement contents;

    @FindBy(xpath = "//div[text()='LossOfUse']/../../../..//input")
    public WebElement lossOfUse;

    @FindBy(xpath = "//div[text()='Liability']/../../../..//input")
    public WebElement liability;

    @FindBy(xpath = "//div[text()='MedicalPayments']/../../../..//input")
    public WebElement medicalPayments;


    public LocationCoverage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void locationCoverAct(Map<String, String> data) throws InterruptedException {
        Thread.sleep(500);
        selectPolCovOption(data);
        setResidenceType(data);
        clickElement(saveChanges, "Save Changes");
        Thread.sleep(2000);
        setReplaceCost(data);
        Thread.sleep(500);
        setContents(data);
        Thread.sleep(500);
        setLossOfUse(data);
        Thread.sleep(500);
        setLiability(data);
        Thread.sleep(500);
        setMedicalPayments(data);
        Thread.sleep(500);
        setWindHailDeduct(data);
        Thread.sleep(500);
        setResUnderConstrNO(data);
        Thread.sleep(1000);
        setUnderThreeYearsYes(data);
        Thread.sleep(3000);
        setYearBuilt(data);
        Thread.sleep(1500);
        setConstructionType(data);
        Thread.sleep(500);
        setRoofType(data);
        Thread.sleep(500);
        setLossesInThreeYrNo(data);
        Thread.sleep(500);
    }

    public void setContents(Map<String, String> data) {
        typeText(contents, data.get("Contents"), "Contents amount: " + data.get("Contents"));
    }

    public void setLossOfUse(Map<String, String> data) {
        typeText(lossOfUse, data.get("LossOfUse"), "Loss of use amount: " + data.get("LossOfUse"));
    }

    public void setLiability(Map<String, String> data) {
        typeText(liability, data.get("Liability"), "Liability amount: " + data.get("Liability"));
    }

    public void setMedicalPayments(Map<String, String> data) {
        typeText(medicalPayments, data.get("MedicalPayments"), "Medical payments amount: " + data.get("MedicalPayments"));
    }

    public void selectPolCovOption(Map<String, String> data) {
        WebDriverWait wait = new WebDriverWait(driver, 10);

        // Click the dropdown to expand options
        wait.until(ExpectedConditions.elementToBeClickable(policyCoverageOption)).click();

        // Wait for the desired option to be present and click it
        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//li[text()='"+data.get("PolicyCoverageOption")+"']")));
        option.click();
    }


    public void setResidenceType(Map<String, String> data) {
        clickElement(driver.findElement(By.xpath("//li[text()='"+data.get("ResidenceType")+"']")),
                "Residence Type: "+ data.get("ResidenceType"));
    }

    public void setReplaceCost(Map<String, String> data){
        typeText(replaceCost, data.get("ReplacementCost"),"Replacement Cost: " + data.get("ReplacementCost"));
    }

    public void setWindHailDeduct(Map<String, String> data) throws InterruptedException {
        Thread.sleep(1000);
        clickElement(windHailDeduct, "Windstorm or Hail Deductible dropdown");
        Thread.sleep(2000);
        clickElement(driver.findElement(By.xpath("//li[text()='"+data.get("WindsHailDeductible")+"']")),
                "Windstorm or Hail Deductible selected: "+ data.get("WindsHailDeductible"));
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
        Thread.sleep(2000);
        clickElement(driver.findElement(By.xpath("//li[text()='" + data.get("ConstructionType")+"']")),
                "Construction Type: " + data.get("ConstructionType"));
    }

    public void setRoofType(Map<String, String> data) throws InterruptedException {
        clickElement(roofType, "Roof Type");
        Thread.sleep(2000);
        clickElement(driver.findElement(By.xpath("//li[text()='"+data.get("RoofType")+"']")),
                "Roof type: "+ data.get("RoofType"));
    }

    public void setLossesInThreeYrNo(Map<String, String> data) {
        clickElement(driver.findElement(By.xpath("//div[text()='Any losses in the last three years?']/../../../..//label[text()='" + data.get("Any losses in the last three years?") + "']/../span")),
                "Any losses in the last three years? " + data.get("Any losses in the last three years?"));
    }
}

