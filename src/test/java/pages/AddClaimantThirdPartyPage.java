package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;

public class AddClaimantThirdPartyPage extends BasePage {

    public AddClaimantThirdPartyPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//span[contains(text(),'parties')]")
    WebElement partiesTab;

    @FindBy(xpath = "//span[contains(text(),'add party')]")
    WebElement addPartyBtn;

    @FindBy(xpath = "//input[@osviewid='PAI_1037846_OT_3200746_OI_1_BI_1094346_CI_15656646']")
    WebElement typeDropdown;

    @FindBy(xpath = "//input[@osviewid='PAI_1037846_OT_3200746_OI_1_BI_1094346_CI_15656846']")
    WebElement roleDropdown;

    @FindBy(xpath = "//input[@osviewid='PAI_1037846_OT_3200746_OI_1_BI_1094346_CI_15656446']")
    WebElement firstNameField;

    @FindBy(xpath = "//input[@osviewid='PAI_1037846_OT_3200746_OI_1_BI_1094346_CI_15656246']")
    WebElement lastNameField;

    @FindBy(xpath = "//input[@osviewid='PAI_1037846_OT_3200746_OI_1_BI_1094446_CI_16372646']")
    WebElement contactNumField;

    @FindBy(xpath = "//input[@osviewid='PAI_1037846_OT_3200746_OI_1_BI_1094446_CI_16660946']")
    WebElement isPartyInjuredDropdown;

    @FindBy(xpath = "//input[@osviewid='PAI_1037846_OT_3200746_OI_1_BI_1094646_CI_15913046']")
    WebElement locationDropdown;


    public void clickPartiesTab() {
        clickElement(partiesTab, "Successfully clicked on Parties tab");
    }

    public void clickAddParty() {
        clickElement(addPartyBtn, "Successfully clicked on Add Party button");
    }

    public void selectType(String type) {
        clickElement(typeDropdown);
        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(By.xpath("//li[contains(text(),'" + type + "')]")))
                .click().build().perform();
        System.out.println("Successfully selected from the Type dropdown: " + type);
    }

    public void selectRole(String role) {
        clickElement(roleDropdown);
        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(By.xpath("//li[contains(text(),'" + role + "')]")))
                .click().build().perform();
        System.out.println("Successfully selected from the Role dropdown: " + role);
    }

    public void enterFirstName(String firstName) {
        typeText(firstNameField, firstName, "Successfully entered text: " + firstName);
    }

    public void enterLastName(String lastName) {
        typeText(lastNameField, lastName, "Successfully entered text: " + lastName);
    }

    public void enterContactNum(String contactNum) {
        typeText(contactNumField, contactNum, "Successfully entered value: " + contactNum);
    }

    public void selectFromIsPartyInjured(String option) {
        clickElement(isPartyInjuredDropdown);
        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(By.xpath("//li[contains(text(),'" + option + "')]")))
                .click().build().perform();
        System.out.println("Successfully selected from the Is Party Injured dropdown: " + option);
    }

    public void selectLocation(String location) {
        clickElement(locationDropdown);
        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(By.xpath("//li[contains(text(),'" + location + "')]")))
                .click().build().perform();
        System.out.println("Successfully selected from the Location dropdown: " + location);
    }

    public void verifyAdditionalInsured(String insuredName) throws IOException {
        try {
            if (driver.findElement(By.xpath("//span[contains(text(),'"+insuredName+"')]")).isDisplayed()) {
                System.out.println("Additional Insured successfully verified: " + insuredName);
            } else {
                System.out.println("Additional Insured verification failed for: " + insuredName);
            }
        } catch (Exception e) {
            System.out.println("Additional Insured not found.");
        }

        new BasePage(driver).reportScreenshot("VerifyAdditionalInsuredScreenshot_" + System.currentTimeMillis(),
                "Additional Insured Verification Screenshot");
    }










}
