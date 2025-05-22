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

    @FindBy(xpath = "//div[text()='Residence Type']/../../../..//input")
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




   /** public void setResidenceType(Map<String, String> data) throws InterruptedException {
        clickElement(residenceType, "Residence Type dropdown menu.");
        Thread.sleep(1000);
        clickElement(driver.findElement(By.xpath("//li[text()='" + data.get("ResidenceType") + "']")), "Residence Type: " + data.get("ResidenceType"));
    }    ovako se postavlja dropdown ako on nije otvoren.. ovom metodom prvo otvaramo pa upisujemo**/


   public void setResidenceType(Map<String, String> data) {
       typeText(residenceType, data.get("ResidenceType"), "Residence Type: " + data.get("ResidenceType"));
   }

   public void selectPolCovOption(Map<String, String> data) {
       WebDriverWait wait = new WebDriverWait(driver, 2);

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