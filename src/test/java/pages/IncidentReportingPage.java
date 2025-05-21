package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;
import java.util.List;

public class IncidentReportingPage extends BasePage {

    public IncidentReportingPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//input[@osviewid = 'PAI_1041646_OT_3203746_OI_1_BI_1143846_CI_16317746']")
    WebElement notifierIsField;

    @FindBy(xpath = "//input[@osviewid = 'PAI_1041646_OT_3203746_OI_1_BI_1143846_CI_16412646']")
    WebElement countryField;

    @FindBy(xpath = "//input[@osviewid = 'PAI_1041646_OT_3203746_OI_1_BI_1143846_CI_17408546']")
    WebElement contactNumField;

    @FindBy(xpath = "//input[@osviewid = 'PAI_1041646_OT_3203746_OI_1_BI_1066846_CI_15950846']")
    WebElement insuredSearchField;

    @FindBy(xpath = "//input[@osviewid = 'PAI_1041646_OT_3203746_OI_1_BI_1066846_CI_15951046']")
    WebElement insuredListField;

    @FindBy(xpath = "//input[@osviewid = 'PAI_1041646_OT_3203746_OI_1_BI_1143146_CI_15665946']")
    WebElement incidentTypeField;

    @FindBy(xpath = "//div[@class='x-boundlist x-boundlist-floating x-layer x-boundlist-default x-border-box']//li[contains(@class, 'x-boundlist-item')]")
    List<WebElement> incidentTypeList;

    @FindBy(xpath = "//input[@osviewid = 'PAI_1041646_OT_3203746_OI_1_BI_1143146_CI_15955446']")
    WebElement incidentCauseField;

    @FindBy(xpath = "//div[@class='x-boundlist x-boundlist-floating x-layer x-boundlist-default x-border-box']//li[contains(@class, 'x-boundlist-item')]")
    List<WebElement> incidentCauseList;

    @FindBy(xpath = "//textarea[@osviewid='PAI_1041646_OT_3203746_OI_1_BI_1143146_CI_15666146']")
    WebElement incidentDescriptionField;

    @FindBy(xpath = "//input[@osviewid='PAI_1041646_OT_3203746_OI_1_BI_1143146_CI_17939146_EC_2']")
    WebElement estClaimValueRadioBtn;

    @FindBy(xpath = "//input[@osviewid='PAI_1041646_OT_3203746_OI_1_BI_1143646_CI_15665846']")
    WebElement incidentDateField;

    @FindBy(xpath = "//input[@osviewid='PAI_1041646_OT_3203746_OI_1_BI_1143646_CI_16125346']")
    WebElement incidentTimeField;

    @FindBy(xpath = "//input[@osviewid='PAI_1041646_OT_3203746_OI_1_BI_1143646_CI_16355546']")
    WebElement reportingDateField;

    @FindBy(xpath = "//span[@osviewid='PAI_1041646_AB_1589746']")
    WebElement getDetailsBtn;

    @FindBy(xpath = "//span[@osviewid='PAI_1041646_AB_1287246']")
    WebElement createFNOLBtn;

    @FindBy(xpath = "//span[@osviewid='PAI_1339046_AB_1537146']")
    WebElement createNewIncidentBtn;

    @FindBy(xpath = "//input[@osviewid='PAI_1114146_OT_3201546_OI_1_BI_1140946_CI_16324546']")
    WebElement claimantField; // "//input[@osviewid='PAI_1111946_OT_3345646_OI_1_BI_1171646_CI_16543946']")

    @FindBy(xpath = "//div[@class='x-boundlist x-boundlist-floating x-layer x-boundlist-default x-border-box']//li[contains(@class, 'x-boundlist-item')]")
    List<WebElement> claimantList;

    @FindBy(xpath = "//input [@osviewid='PAI_1114146_OT_3201546_OI_1_BI_1140546_CI_16325846']")
    WebElement lossTypeField; //PAI_1111946_OT_3345646_OI_1_BI_1171746_CI_16545546

    @FindBy(xpath = "//li[contains(text(),'Property Damage OR Loss')]")
    WebElement lossTypeSelect;

    @FindBy(xpath = "//div[@class='x-boundlist x-boundlist-floating x-layer x-boundlist-default x-border-box']//li[contains(@class, 'x-boundlist-item')]")
    List<WebElement> lossTypeList;

    @FindBy(xpath = "//input[@osviewid='PAI_1114146_OT_3201546_OI_1_BI_1140546_CI_16324446']")
    WebElement propertyField;

    @FindBy(xpath = "//li[contains(text(),'851 Hauck Course')]")
    WebElement propertySelect;

    @FindBy(xpath = "//div[@class='x-boundlist x-boundlist-floating x-layer x-boundlist-default x-border-box']//li[contains(@class, 'x-boundlist-item')]")
    List<WebElement> propertyList;

    @FindBy(xpath = "//label[contains(text(), 'Select this to setup Inspection appointment.')]")
    WebElement setupInspectionAppointmentCheckbox;

    @FindBy(xpath = "//input[@osviewid='PAI_1114146_OT_3201146_OI_1_BI_1143346_CI_16324346']")
    WebElement scheduledInspectionDateField;

    @FindBy(xpath = "//input[@osviewid='PAI_1114146_OT_3201146_OI_1_BI_1143346_CI_16323146']")
    WebElement timeFromField;

    @FindBy(xpath = "//input[@osviewid='PAI_1114146_OT_3201146_OI_1_BI_1143346_CI_16363746']")
    WebElement timeToField;

    @FindBy(xpath = "//span[contains(text(),'save changes')]")
    WebElement saveChangesBtn;

    @FindBy(xpath = "//span [@osviewid='PAI_1114146_AB_1661946']")
    WebElement verifyLOBAndCoveragesBtn;

    @FindBy(xpath = "//label[@id='oscheckboxfield-4267-boxLabelEl' and text()='Include Coverage ?']")
    WebElement includeCoverageDwelling;

    @FindBy(xpath = "//label[@id='oscheckboxfield-4307-boxLabelEl' and text()='Include Coverage ?']")
    WebElement includeCoverageLossOfUse;

    @FindBy(xpath = "//label[@id='oscheckboxfield-4387-boxLabelEl' and text()='Include Coverage ?']")
    WebElement includeCoveragePersonalProperty;

    @FindBy(xpath = "//span[@osviewid='PAI_1512048_AB_1562546']")
    WebElement selectCoveragesAndSaveBtn;

    @FindBy(xpath = "//span[@osviewid='PAI_1512048_AB_1562246']")
    WebElement exitBtn;

    @FindBy(xpath = "//span[@osviewid='PAI_1022946_AB_1264046']")
    WebElement exitIncidentSummaryPageBtn;

    @FindBy(xpath = "//div[@osviewid='PAI_1023746_OT_3201046_OI_1_BI_1050046_CI_17529846' and text()='Lashaun Wisoky']")
    WebElement incidentsListInsuredNameField; // Used to verify that the incident is associated with the correct customer

    @FindBy(xpath = "//div[@osviewid='PAI_1023746_OT_3201046_OI_1_BI_1050046_CI_15555346' and text()='11/12/2024']")
    WebElement incidentsListIncidentDateField; // Confirms the incident date to further validate this specific incident for the customer

    @FindBy(xpath = "//div[@osviewid='PAI_1023746_OT_3201046_OI_1_BI_1050046_CI_17345946' and text()='Loss Reported']")
    WebElement lossReportedField;

    @FindBy(xpath = "//span[contains(text(),'logout')]")
    WebElement logoutBtn;

    @FindBy(xpath = "//span[contains(text(), 'OK')]")
    WebElement okBtn;

    public WebElement getCreateNewIncidentBtn() {
        return createNewIncidentBtn;
    }

    public void setNotifierStatus(String status) {
        typeText(notifierIsField, status);
        clickElement(notifierIsField, "Successfully selected from dropdown: " + status);
    }

    public void selectCountry(String country) {
        typeText(countryField, country);
        clickElement(countryField, "Successfully selected from dropdown: " + country);
    }

    public void enterContactNum(String contactNum) {
        typeText(contactNumField, contactNum);
        clickElement(contactNumField, "Successfully entered value: " + contactNum);
    }

    public void enterInsuredSearchValue(String name) {
        typeText(insuredSearchField, name, "Successfully entered text: " + name);
    }

    public void selectInsuredPerson(String name) {
        clickElement(insuredListField);
        for (WebElement option : incidentTypeList) {
            if (option.getText().contains(name)) {
                clickElement(option, "Successfully selected from dropdown: " + name);
                break;
            }
        }
    }

    public void selectIncidentType(String incidentType) {
        clickElement(incidentTypeField);
        for (WebElement option : incidentTypeList) {
            if (option.getText().contains(incidentType)) {
                clickElement(option, "Successfully selected from the Incident Type dropdown: " + incidentType);
                break;
            }
        }
    }

    public void selectIncidentCause(String incidentCause) {
        clickElement(incidentCauseField);
        for (WebElement option : incidentCauseList) {
            if (option.getText().contains(incidentCause)) {
                clickElement(option, "Successfully selected from the Incident Cause dropdown: " + incidentCause);
                break;
            }
        }
    }

    public void enterIncidentDescription(String incidentDescription) {
        clickElement(incidentDescriptionField, "Cursor position set");
        typeText(incidentDescriptionField, incidentDescription, "Incident Description successfully entered");
    }

    public void clickEstClaimValue() {
        clickElement(estClaimValueRadioBtn, "Successfully clicked on Estimated Claim Value radio button");
    }

    public void enterIncidentDate(String incidentDate) {
        clickElement(incidentDateField);
        typeText(incidentDateField, incidentDate, "Successfully entered value in Incident Date field");
    }

    public void enterIncidentTime(String incidentTime) {
        clickElement(incidentTimeField);
        typeText(incidentTimeField, incidentTime, "Successfully entered value in Incident Time field");
    }

    public void enterReportingDate(String reportingDate) {
        clickElement(reportingDateField);
        typeText(reportingDateField, reportingDate, "Successfully entered value in Reporting Date field");
    }

    public void clickGetDetails() {
        clickElement(getDetailsBtn, "Successfully clicked on Get Details button");
    }

    public void clickCreateFNOL() throws InterruptedException {
        clickElement(createFNOLBtn, "Successfully clicked on Create FNOL");
    }

    public void clickCreateNewIncidentBtn() {
        clickElement(createNewIncidentBtn, "Successfully clicked on Create New Incident button");
    }

    public void selectClaimant(String name) {
        clickElement(claimantField);
        for (WebElement option : claimantList) {
            if (option.getText().contains(name)) {
                clickElement(option, "Successfully selected from the Claimant dropdown: " + name);
                break;
            }
        }
    }

    public void selectLossType(String lossType) {
        clickElement(lossTypeField);
        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(By.xpath("//li[contains(text(),'" + lossType + "')]")))
                .click().build().perform();
        System.out.println("Successfully selected from the Loss Type dropdown: " + lossType);
    }

    public void selectProperty(String property) {
        clickElement(propertyField, "Property dropdown menu clicked");
        Actions actions = new Actions(driver);
        actions.moveToElement(propertySelect).click().build().perform();
        System.out.println("Successfully selected from the Property dropdown: " + property);
    }

    public void setupInspectionAppointment() {
        clickElement(setupInspectionAppointmentCheckbox,
                "Successfully clicked on \"Select this to setup Inspection Appointment checkbox\"");
    }

    public void enterScheduledInspectionDate(String inspectionDate) {
        clickElement(scheduledInspectionDateField);
        typeText(scheduledInspectionDateField, inspectionDate,
                "Successfully entered value in the Scheduled Inspection Date field: " + inspectionDate);
    }

    public void enterTimeFromValue(String timeFrom) {
        typeText(timeFromField, timeFrom, "Successfully entered value in the Time-From field");
    }

    public void enterTimeToValue(String timeTo) {
        typeText(timeToField, timeTo, "Successfully entered value in the Time-To field");
    }

    public void saveChanges() {
        clickElement(saveChangesBtn, "Successfully clicked on Save Changes button");
    }

    public void verifyLOB() {
        clickElement(verifyLOBAndCoveragesBtn, "Successfully clicked on Verify LOB and Coverages button");
    }

    public void includeCoverages() {
        clickElement(includeCoverageDwelling, "Successfully clicked on Include Coverage Dwelling checkbox");
        clickElement(includeCoverageLossOfUse, "Successfully clicked on Include Coverage Loss Of Use checkbox");
        clickElement(includeCoveragePersonalProperty, "Successfully clicked on Include Coverage Personal Property checkbox");
    }

    public void selectCoveragesAndSave() {
        clickElement(selectCoveragesAndSaveBtn, "Successfully clicked on Select Coverages and Save button");
    }

    public void exit() throws InterruptedException {
        clickElement(exitBtn, "Successfully clicked on Exit button");
        Thread.sleep(2000);
        clickElement(exitIncidentSummaryPageBtn, "Successfully clicked on Exit Button on Incident Summary Page");
    }

    public void verifyLossReported(String name, String incidentDate, String incidentStatus) throws IOException {
        // Verify Customer Name
        try {
            if (driver.findElement(By.xpath("//div[@osviewid='PAI_1023746_OT_3201046_OI_1_BI_1050046_CI_17529846' and text()='"
                    +name+"']")).isDisplayed()) {
                System.out.println("Customer name verified: " + name);
            } else {
                System.out.println("Customer name verification failed for: " + name);
            }
        } catch (Exception e) {
            System.out.println("Customer name " + name + " not found.");
        }

        // Verify Incident Date
        try {
            if (driver.findElement(By.xpath("//div[@osviewid='PAI_1023746_OT_3201046_OI_1_BI_1050046_CI_15555346' and text()='"
                    +incidentDate+"']")).isDisplayed()) {
                System.out.println("Incident date verified: " + incidentDate);
            } else {
                System.out.println("Incident date verification failed for: " + incidentDate);
            }
        } catch (Exception e) {
            System.out.println("Incident date " + incidentDate + " not found.");
        }

        // Verify Loss Reported Status
        try {
            if (driver.findElement(By.xpath("//div[@osviewid='PAI_1023746_OT_3201046_OI_1_BI_1050046_CI_17345946' and text()='"
                    +incidentStatus+"']")).isDisplayed()) {
                System.out.println("Status verified: " + incidentStatus);
            } else {
                System.out.println("Status verification failed for: " + incidentStatus);
            }
        } catch (Exception e) {
            System.out.println("Status " + incidentStatus + " not found.");
        }

        new BasePage(driver).reportScreenshot("LossReportedStatusScreenshot_" + System.currentTimeMillis(),
                "Loss Reported Status Screenshot");
    }

    public void clickLogout() {
        clickElement(logoutBtn, "Successfully clicked on Log Out button");
    }

    public void clickOk() {
        clickElement(okBtn, "Successfully clicked: \"Are you sure that you want to exit the system?\"");
    }



}
