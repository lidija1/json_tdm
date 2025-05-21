package pages;

import io.cucumber.java.an.E;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;

public class AddLossesAndCreateClaimPage extends BasePage {

    public AddLossesAndCreateClaimPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//span[@osviewid='PAI_1019746_AB_1548946']")
    WebElement incidentsTab;

    @FindBy(xpath = "//div[@data-qtip='Insured Name']//div[@id='oslabelcolumn-1241-triggerEl']")
    WebElement insuredNameColumnFilterArrow;

    @FindBy(xpath = "//span[text()='Filters']")
    WebElement filtersField;

    @FindBy(xpath = "//input[@placeholder='Enter Filter Text...']")
    WebElement enterFilterTextField;

    @FindBy(xpath = "//div[@osviewid='PAI_1023746_OT_3201046_OI_1_BI_1050046_CI_15555246']")
    WebElement incidentNumHyperlink;

    @FindBy(xpath = "//span[@osviewid='PAI_1022946_OT_3201046_OI_1_BI_1397146_CI_17924946']")
    WebElement addMoreLossesBtn;

    @FindBy(xpath = "//div/textarea[@osviewid='PAI_1114146_OT_3201546_OI_1_BI_1335846_CI_17289646']") //PAI_1111946_OT_3345646_OI_1_BI_1336646_CI_17383046
    WebElement injuryDescriptionField;

    @FindBy(xpath = "//a[contains(text(),'Other Information')]")
    WebElement otherInformationTreeNode;

    @FindBy(xpath = "//input[@osviewid='PAI_1024246_OT_3201246_OI_1_BI_1225746_CI_16881646_EC_3']") //input[@osviewid='PAI_1024246_OT_3201246_OI_1_BI_1225746_CI_16881646_EC_3']/../../label[contains(text(),'No')]
    WebElement noFatalitiesOrDeathsRadioButton;

    @FindBy(xpath = "//span[contains(text(),'>>> Verify LOB & Coverages')]")
    WebElement verifyLOBAndCoveragesBtn;

    @FindBy(xpath = "//span[@osviewid='PAI_1512048_AB_1562546']")
    WebElement selectCoveragesAndSaveBtn;

    @FindBy(xpath = "//a[contains(text(), 'Homeowner')]")
    WebElement homeownerNode;

    @FindBy(xpath = "//span[contains(text(),'>>> create claim')]")
    WebElement createClaimBtn;

    @FindBy(xpath = "//label[contains(text(),'Single Claim')]")
    WebElement singleClaimRadioBtn;

    @FindBy(xpath = "//span [text()='claims']")
    WebElement claimsTab;

    @FindBy(xpath = "//div[@osviewid='PAI_1027846_OT_3203646_OI_1_BI_1054746_CI_15617946' and contains(text(),'Open')]")
    WebElement claimStatus;

    @FindBy(xpath = "//div[@osviewid='PAI_1027846_OT_3203646_OI_1_BI_1054746_CI_15617846']")
    WebElement claimNum;

    @FindBy(xpath = "//span[contains(text(),'transactions')]")
    WebElement transactionsTab;

    @FindBy(xpath = "//div[@osviewid='PAI_1041246_OT_3336746_OI_1_BI_1127746_CI_16235546' and contains(text(),'Processed')]")
    WebElement assignmentStatus;

    @FindBy(xpath = "//div[@osviewid='PAI_1041246_OT_3395946_OI_2_BI_1127746_CI_16235546' and contains(text(),'Processed')]")
    WebElement claimRegistrationStatus;

    @FindBy(xpath = "//span[@osviewid='PAI_1041246_AB_1290446']")
    WebElement exitPolicyBtn;

    public void clickIncidentsTab() {
        clickElement(incidentsTab, "Successfully clicked on Incidents Tab");
    }

    public void enterInsuredNameFilterText(String name) {
        clickElement(insuredNameColumnFilterArrow, "Successfully clicked on Insured Name column filter arrow");
        Actions actions = new Actions(driver);
        actions.moveToElement(filtersField).click().build().perform();
        clickElement(enterFilterTextField);
        typeText(enterFilterTextField, name, "Successfully entered filter text: " + name);
    }

    public void clickIncidentNumHyperlink() {
        clickElement(incidentNumHyperlink,"Successfully clicked on Incident Number hyperlink");
    }

    public void clickAddMoreLosses() {
        clickElement(addMoreLossesBtn, "Successfully clicked on Add More Losses button");
    }

    public void enterInjuryDescription(String description) {
        typeText(injuryDescriptionField, description,
                "Successfully entered text in \"How did the injury occur and what was the injured doing?\" field: "
                        + description);
    }

    public void clickOtherInformation() {
        clickElement(otherInformationTreeNode, "Successfully clicked on Other Information Tree Node");
    }

    public void clickNoFatalitiesOrDeaths() {
        clickElement(noFatalitiesOrDeathsRadioButton, "Successfully clicked on Are there any fatalities or deaths? - \"No\" radio button");
    }

    public void clickVerifyLOBAndCoverages() {
        clickElement(verifyLOBAndCoveragesBtn, "Successfully clicked on Verify LOB and Coverages button");
    }

    public void clickSelectCoveragesAndSave() {
        clickElement(selectCoveragesAndSaveBtn, "Successfully clicked on Select Coverages and Save button");
    }

    public void clickPDLoss(String name) {
        clickElement(driver.findElement(By.xpath("//a[contains(text(),'PD | "+name+"')]")),
                "Successfully clicked on PD Loss node");
    }

    public void clickHomeownerNode() {
        clickElement(homeownerNode, "Successfully Clicked on Homeowner node");
    }

    public void clickCreateClaim() {
        clickElement(createClaimBtn, "Successfully clicked on Create Claim button");
    }

    public void clickSingleClaim() {
        clickElement(singleClaimRadioBtn, "Successfully clicked on Single Claim radio button");
    }

    public void verifyClaimStatus() throws IOException {
        clickElement(claimsTab, "Successfully navigated to Claims");
        clickElement(claimNum, "Successfully navigated to Claim Summary | Details page");

        try {
            if (claimStatus.isDisplayed()) {
                System.out.println("Claim status verified: Open");
            } else {
                System.out.println("Claim status verification failed for: Open");
            }
        } catch (Exception e) {
            System.out.println("Claim status not found.");
        }

        new BasePage(driver).reportScreenshot("ClaimStatusScreenshot_" + System.currentTimeMillis(),
                "Claim Status Screenshot");
    }

    public void verifyAssignmentTransactionStatus() throws IOException {
            clickElement(transactionsTab, "Successfully navigated to Claim Transaction | List page");

            try {
                if (assignmentStatus.isDisplayed()) {
                    System.out.println("Assignment transaction status verified: Processed");
                } else {
                    System.out.println("Assignment transaction status verification failed for: Processed");
                }
            } catch (Exception e) {
                System.out.println("Assignment transaction status not found.");
            }

            new BasePage(driver).reportScreenshot("AssignmentTransactionStatusScreenshot_" + System.currentTimeMillis(),
                    "Assignment Transaction Status Screenshot");

    }

    public void verifyClaimRegistrationStatus() throws IOException {

        try {
            if (claimRegistrationStatus.isDisplayed()) {
                System.out.println("Claim Registration status verified: Processed");
            } else {
                System.out.println("Claim Registration status verification failed for: Processed");
            }
        } catch (Exception e) {
            System.out.println("Claim Registration transaction status not found.");
        }

        new BasePage(driver).reportScreenshot("ClaimRegistrationStatusScreenshot_" + System.currentTimeMillis(),
                "Claim Registration Status Screenshot");

    }

    public void exit() {
        clickElement(exitPolicyBtn, "Successfully clicked on Exit Button");
    }



}
