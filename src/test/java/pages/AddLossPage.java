package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;

public class AddLossPage extends BasePage {

    public AddLossPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//span[contains(text(),'claims')]")
    WebElement claimsTab;

    @FindBy(xpath = "//div[@id='oslabelcolumn-1241-triggerEl']")
    WebElement claimantColumnFilterArrow;

    @FindBy(xpath = "//span[text()='Filters']")
    WebElement filtersField;

    @FindBy(xpath = "//input[@placeholder='Enter Filter Text...']")
    WebElement enterFilterTextField;

    @FindBy(xpath = "//div[@osviewid='PAI_1027846_OT_3203646_OI_1_BI_1054746_CI_15617846']")
    WebElement claimNumHyperlink;

    @FindBy(xpath = "//span[contains(text(),'add claimant coverage')]")
    WebElement addClaimantCoverageBtn;

    @FindBy(xpath = "//span[contains(text(),'add new loss')]")
    WebElement addNewLossBtn;

    @FindBy(xpath = "//input[@osviewid='PAI_1111946_OT_3345646_OI_1_BI_1171646_CI_16543946']")
    WebElement claimantField;

    @FindBy(xpath = "//input[@osviewid='PAI_1111946_OT_3345646_OI_1_BI_1171746_CI_16545546']")
    WebElement lossTypeField;

    @FindBy(xpath = "//div/textarea[@osviewid='PAI_1111946_OT_3345646_OI_1_BI_1336646_CI_17383046']")
    WebElement injuryDescriptionField;

    @FindBy(xpath = "//span[contains(text(),'Verify Coverages')]")
    WebElement verifyCoveragesBtn;

    @FindBy(xpath = "//span[contains(text(),'Process')]") // //span[@osviewid='PAI_1623448_AB_1559448']
    WebElement processBtn;

    @FindBy(xpath = "//div[@osviewid='PAI_1045646_OT_3331746_OI_1_BI_1069246_CI_17455646' and contains(text(),'Processed')]")
    WebElement processedStatus;

    @FindBy(xpath = "//span[contains(text(),'exit')]") // //span[@osviewid='PAI_1045646_AB_1291746']
    WebElement exitBtn;

    @FindBy(xpath = "//div[@osviewid='PAI_1032146_OT_3345646_OI_1_BI_1059946_CI_15617346' and (contains(text(),'Processed') or contains(text(),'Created'))]")
    WebElement addClaimantCoverageStatus;


    public  void clickClaimsTab() {
        clickElement(claimsTab, "Successfully clicked on Claims Tab");
    }

    public void enterClaimantFilterText(String name) {
        clickElement(claimantColumnFilterArrow, "Successfully clicked on Claimant column filter arrow");
        Actions actions = new Actions(driver);
        actions.moveToElement(filtersField).click().build().perform();
        clickElement(enterFilterTextField);
        typeText(enterFilterTextField, name, "Successfully entered filter text: " + name);
    }

    public void clickClaimNumHyperlink() {
        clickElement(claimNumHyperlink, "Successfully clicked on Claim Number Hyperlink");
    }

    public void clickAddClaimantCoverage() {
        clickElement(addClaimantCoverageBtn, "Successfully clicked on Add Claimant Coverage button");
    }

    public void clickAddNewLoss() {
        clickElement(addNewLossBtn, "Successfully clicked on Add New Los button");
    }

    public void selectClaimant(String name) {
        clickElement(claimantField);
        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(By.xpath("//li[contains(text(),'" + name + "')]")))
                .click().build().perform();
        System.out.println("Successfully selected from the Claimant dropdown: " + name);
    }

    public void selectLossType(String lossType) {
        clickElement(lossTypeField);
        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(By.xpath("//li[contains(text(),'" + lossType + "')]")))
                .click().build().perform();
        System.out.println("Successfully selected from the Loss Type dropdown: " + lossType);
    }

    public void enterInjuryDescription(String description) {
        typeText(injuryDescriptionField, description,
                "Successfully entered text in \"How did the injury occur and what was the injured doing?\" field: "
                        + description);
    }

    public void clickVerifyCoverages() {
        clickElement(verifyCoveragesBtn,"Successfully clicked on Verify Coverages button");
    }

    public void clickProcess() {
        clickElement(processBtn, "Successfully clicked on Process button");
    }

    public void verifyClaimTransactionStatus() throws IOException {
        try {
            if (processedStatus.isDisplayed()) {
                System.out.println("Claim Transaction status verified: Processed");
            } else {
                System.out.println("Claim Transaction status verification failed for: Processed");
            }
        } catch (Exception e) {
            System.out.println("Claim Transaction status not found.");
        }

        new BasePage(driver).reportScreenshot("ClaimTransactionStatusScreenshot_" + System.currentTimeMillis(),
                "Claim Transaction Status Screenshot");
    }

    public void exit() {
        clickElement(exitBtn, "Successfully clicked on Exit button");
    }


    public void verifyAddClaimantCoverageStatus() throws IOException, InterruptedException {
        scrollDown(addClaimantCoverageStatus);
        try {
            if (addClaimantCoverageStatus.isDisplayed()) {
                System.out.println("Add Claimant Coverage status verified: Created and Processed");
            } else {
                System.out.println("Add Claimant Coverage status verification failed for: Created and Processed");
            }
        } catch (Exception e) {
            System.out.println("Add Claimant Coverage status not found.");
        }

        new BasePage(driver).reportScreenshot("AddClaimantCoverageStatusScreenshot_" + System.currentTimeMillis(),
                "Add Claimant Coverage Status Screenshot");
    }

}
