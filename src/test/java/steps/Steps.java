package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Reporter;
import pages.*;

import java.io.IOException;

public class Steps extends BaseSteps {

    private LandingPage landingPage;
    private LoginPage loginPage;
    private HomePage homePage;
    private IncidentReportingPage incidentReportingPage;
    private AddLossesAndCreateClaimPage addLossesAndCreateClaimPage;
    private AddLossPage addLossPage;
    private AddClaimantThirdPartyPage addClaimantThirdPartyPage;
    private AssignOwnerPage assignOwnerPage;
    private Quotes quotesPage;
    private Customer customerPage;
    private PolicyInformation policyInformation;
    private LocationCoverage locationCoverage;
    private Rate rate;

    final String BROWSER = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("BROWSER");
    final String BROWSER_VERSION = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("BROWSER_VERSION");
    final int WAIT = Integer.parseInt(Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("WAIT"));

    private String excelPath;
    private String tcId;

    @Before
    public void cucumberBefore() {
        baseSetUp(BROWSER, BROWSER_VERSION, WAIT);

        landingPage = new LandingPage(driver);
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
        incidentReportingPage = new IncidentReportingPage(driver);
        addLossesAndCreateClaimPage = new AddLossesAndCreateClaimPage(driver);
        addLossPage = new AddLossPage(driver);
        addClaimantThirdPartyPage = new AddClaimantThirdPartyPage(driver);
        assignOwnerPage = new AssignOwnerPage(driver);
        quotesPage = new Quotes(driver);
        customerPage = new Customer(driver);
        policyInformation = new PolicyInformation(driver);
        locationCoverage = new LocationCoverage(driver);
        rate = new Rate(driver);
    }

    @After
    public void cucumberAfter() {
        baseTearDown();
    }    @Given("the data is loaded {string}, {string}")
    public void theDataIsLoaded(String jsonPath, String tcId) throws IOException {
        this.excelPath = jsonPath;
        this.tcId = tcId;
        loadTestData(jsonPath, tcId, "1");
    }

    // Login
    @Given("the user is logged in with valid credentials")
    public void theUserIsLoggedIn() throws InterruptedException {
        driver.get("https://inforcedev.oneshield.com/splash.html");
        landingPage.loginToEmpPortal();
        Thread.sleep(6000);
        loginPage.performLogin();
    }

    // Scenario - Reporting an Incident and FNOL
    @Given("the user initiates a new incident report by clicking the New Incident button")
    public void theUserInitiatesANewIncidentReportByClickingTheNewIncidentButton() throws InterruptedException {
        Thread.sleep(2000);
        homePage.clickNewIncident();
    }

    @When("the user selects a status from the Notifier Is dropdown")
    public void theUserSelectsAStatusFromTheDropdown() {
        incidentReportingPage.setNotifierStatus(data.get("Status"));
    }

    @And("selects a country")
    public void selectsACountry() {
        incidentReportingPage.selectCountry(data.get("Country"));
    }

    @And("enters a contact number")
    public void entersAContactNumber() {
        incidentReportingPage.enterContactNum(data.get("ContactNum"));
    }

    @And("enters a value in the Insured Search field")
    public void entersAValueInTheInsuredSearchField() {
        incidentReportingPage.enterInsuredSearchValue(data.get("FullName"));
    }

    @And("selects an insured from the Insured List dropdown")
    public void selectsAnInsuredFromTheInsuredListDropdown() throws InterruptedException {
        Thread.sleep(2000);
        incidentReportingPage.selectInsuredPerson(data.get("FullName"));
    }

    @And("fills out the incident details, including type, cause, and description")
    public void fillsOutTheIncidentDetailsIncludingTypeCauseAndDescription() throws InterruptedException {
        Thread.sleep(3000);
        incidentReportingPage.selectIncidentType(data.get("IncidentType"));
        Thread.sleep(2000);
        incidentReportingPage.selectIncidentCause(data.get("IncidentCause"));
        Thread.sleep(2000);
        incidentReportingPage.enterIncidentDescription(data.get("IncidentDescription"));
    }

    @And("the user chooses the estimated claim value")
    public void theUserChoosesTheEstimatedClaimValue() throws InterruptedException {
        Thread.sleep(2000);
        incidentReportingPage.clickEstClaimValue();
    }

    @And("enters the incident date, time, and reporting date")
    public void entersTheIncidentDateTimeAndReportingDate() {
        incidentReportingPage.enterIncidentDate(data.get("IncidentDate"));
        incidentReportingPage.enterIncidentTime(data.get("IncidentTime"));
        incidentReportingPage.enterReportingDate(data.get("ReportingDate"));
    }

    @And("clicks Get Details")
    public void clicksGetDetails() {
        incidentReportingPage.clickGetDetails();
    }

    @And("clicks Create FNOL")
    public void clicksCreateFNOL() throws InterruptedException {
        Thread.sleep(3000);
        incidentReportingPage.clickCreateFNOL();
        Thread.sleep(3000);
        if (incidentReportingPage.getCreateNewIncidentBtn().isDisplayed()) {
            incidentReportingPage.clickCreateNewIncidentBtn();
        }
    }

    @And("selects a claimant from the dropdown")
    public void selectsAClaimantFromTheDropdown() throws InterruptedException {
        Thread.sleep(3000);
        incidentReportingPage.selectClaimant(data.get("Name"));
    }

    @And("selects a loss type from the dropdown")
    public void selectsALossTypeFromTheDropdown() throws InterruptedException {
        Thread.sleep(3000);
        incidentReportingPage.selectLossType(data.get("LossType"));
    }

    @And("selects a property from the dropdown")
    public void selectsAPropertyFromTheDropdown() throws InterruptedException {
        Thread.sleep(2000);
        incidentReportingPage.selectProperty(data.get("Property"));
    }

    @And("sets up an inspection appointment by entering the scheduled date, start time, and end time")
    public void setsUpAnInspectionAppointmentByEnteringTheScheduledDateStartTimeAndEndTime() throws InterruptedException {
        Thread.sleep(2000);
        incidentReportingPage.setupInspectionAppointment();
        incidentReportingPage.enterScheduledInspectionDate(data.get("InspectionDate"));
        incidentReportingPage.enterTimeFromValue(data.get("TimeFrom"));
        incidentReportingPage.enterTimeToValue(data.get("TimeTo"));
    }

    @And("clicks Save changes")
    public void clicksSaveChanges() throws InterruptedException {
        incidentReportingPage.saveChanges();
        Thread.sleep(2000);
    }

    @And("verifies LOB and coverages, includes the coverages, and saves them")
    public void verifiesLOBAndCoveragesIncludesTheCoveragesAndSavesThem() throws InterruptedException {
        Thread.sleep(5000);
        incidentReportingPage.verifyLOB();
        incidentReportingPage.includeCoverages();
        Thread.sleep(2000);
        incidentReportingPage.selectCoveragesAndSave();
    }

    @And("exits the report")
    public void exitsTheReport() throws InterruptedException {
        Thread.sleep(3000);
        incidentReportingPage.exit();
    }

    @Then("the status of the Incident should be Loss Reported")
    public void theStatusOfTheIncidentShouldBe() throws IOException {
        incidentReportingPage.verifyLossReported(data.get("FullName"), data.get("IncidentDate"), data.get("IncidentStatus"));
    }

    @And("the user logs out")
    public void theUserLogsOut() throws InterruptedException {
        Thread.sleep(3000);
        incidentReportingPage.clickLogout();
        incidentReportingPage.clickOk();
    }


    // Scenario - Add Multiple Losses and Create a Claim
    @Given("the user navigates to the Incidents tab")
    public void theUserNavigatesToTheIncidentsTab() {
        addLossesAndCreateClaimPage.clickIncidentsTab();
    }

    @When("the user filters incidents by entering the insured name in the column filter")
    public void theUserFiltersIncidentsByEnteringTheInsuredNameInTheColumnFilter() {
        addLossesAndCreateClaimPage.enterInsuredNameFilterText(data.get("FullName"));
    }

    @And("selects an incident by clicking the incident number hyperlink")
    public void selectsAnIncidentByClickingTheIncidentNumberHyperlink() throws InterruptedException {
        Thread.sleep(2000);
        addLossesAndCreateClaimPage.clickIncidentNumHyperlink();
    }

    @And("clicks the Add More Losses button")
    public void clicksTheAddMoreLossesButton() {
        addLossesAndCreateClaimPage.clickAddMoreLosses();
    }

    @And("enters an injury description")
    public void entersAnInjuryDescription() throws InterruptedException {
        addLossesAndCreateClaimPage.enterInjuryDescription(data.get("InjuryDescription"));
        Thread.sleep(4000);
    }

    @And("clicks the Other Information tree node")
    public void clicksTheOtherInformationTreeNode() throws InterruptedException {
        addLossesAndCreateClaimPage.clickOtherInformation();
        Thread.sleep(4000);
    }

    @And("selects No for the fatalities or deaths question")
    public void selectsNoForTheFatalitiesOrDeathsQuestion() throws InterruptedException {
        addLossesAndCreateClaimPage.clickNoFatalitiesOrDeaths();
        Thread.sleep(2000);
    }

    @And("verifies the LOB and coverages")
    public void verifiesTheLOBAndCoverages() throws InterruptedException {
        addLossesAndCreateClaimPage.clickVerifyLOBAndCoverages();
        Thread.sleep(4000);
    }

    @And("clicks the Select Coverages and Save button")
    public void clicksTheSelectCoveragesAndSaveButton() throws InterruptedException {
        addLossesAndCreateClaimPage.clickSelectCoveragesAndSave();
        Thread.sleep(4000);
    }

    @And("clicks the PD Loss tree node")
    public void clicksThePDLossTreeNode() throws InterruptedException {
        addLossesAndCreateClaimPage.clickPDLoss(data.get("FullName"));
        Thread.sleep(4000);
    }

    @And("clicks the Homeowner node")
    public void clicksTheHomeownerNode() throws InterruptedException {
        addLossesAndCreateClaimPage.clickHomeownerNode();
        Thread.sleep(4000);
    }

    @And("clicks the Create Claim button")
    public void clicksTheCreateClaimButton() throws InterruptedException {
        addLossesAndCreateClaimPage.clickCreateClaim();
        Thread.sleep(4000);
    }

    @And("selects the Single Claim option")
    public void selectsTheSingleClaimOption() throws InterruptedException {
        addLossesAndCreateClaimPage.clickSingleClaim();
        Thread.sleep(4000);
    }

    @And("clicks the Create Claim button again")
    public void clicksTheCreateClaimButtonAgain() throws InterruptedException {
        addLossesAndCreateClaimPage.clickCreateClaim();
        Thread.sleep(60000);
        driver.navigate().refresh();
        Thread.sleep(4000);
    }

    @Then("the claim status should be Open")
    public void theClaimStatusShouldBeOpen() throws IOException {
        addLossesAndCreateClaimPage.verifyClaimStatus();
    }

    @And("the assignment transaction should be created and processed")
    public void theAssignmentTransactionShouldBeCreatedAndProcessed() throws IOException, InterruptedException {
        Thread.sleep(4000);
        addLossesAndCreateClaimPage.verifyAssignmentTransactionStatus();
    }

    @And("the claim registration transaction should be created and processed")
    public void theClaimRegistrationTransactionShouldBeCreatedAndProcessed() throws IOException, InterruptedException {
        Thread.sleep(4000);
        addLossesAndCreateClaimPage.verifyClaimRegistrationStatus();
    }


    // Scenario - Add a New Loss to an Existing Claim
    @Given("the user navigates to the Claims tab")
    public void theUserNavigatesToTheClaimsTab() throws InterruptedException {
        addLossPage.clickClaimsTab();
        Thread.sleep(3000);
    }

    @When("the user filters claims by entering the claimant name in the column filter")
    public void theUserFiltersClaimsByEnteringTheClaimantNameInTheColumnFilter() throws InterruptedException {
        addLossPage.enterClaimantFilterText(data.get("InsuredName"));
        Thread.sleep(2000);
    }

    @And("selects a claim by clicking the claim number hyperlink")
    public void selectsAClaimByClickingTheClaimNumberHyperlink() throws InterruptedException {
        addLossPage.clickClaimNumHyperlink();
        Thread.sleep(5000);
    }

    @And("clicks the Add Claimant Coverage button")
    public void clicksTheAddClaimantCoverageButton() throws InterruptedException {
        addLossPage.clickAddClaimantCoverage();
        Thread.sleep(2000);
    }

    @And("clicks the Add New Loss button")
    public void clicksTheAddNewLossButton() throws InterruptedException {
        addLossPage.clickAddNewLoss();
        Thread.sleep(4000);
    }

    @And("chooses a claimant from the dropdown")
    public void choosesAClaimantFromTheDropdown() throws InterruptedException {
        addLossPage.selectClaimant(data.get("Name"));
        Thread.sleep(4000);
    }

    @And("picks a loss type from the dropdown")
    public void picksALossTypeFromTheDropdown() throws InterruptedException {
        addLossPage.selectLossType(data.get("LossType"));
        Thread.sleep(2000);
    }

    @And("inputs an injury description")
    public void inputsAnInjuryDescription() throws InterruptedException {
        addLossPage.enterInjuryDescription(data.get("InjuryDescription"));
        Thread.sleep(2000);
    }

    @And("verifies coverages")
    public void verifiesCoverages() throws InterruptedException {
        addLossPage.clickVerifyCoverages();
        Thread.sleep(2000);
    }

    @And("processes the claim")
    public void processesTheClaim() throws InterruptedException {
        addLossPage.clickProcess();
        Thread.sleep(2000);
    }

    @Then("the claim status in the Claim Transaction Summary block should be Processed")
    public void theClaimStatusInTheClaimTransactionSummaryBlockShouldBeProcessed() throws IOException, InterruptedException {
        addLossPage.verifyClaimTransactionStatus();
        Thread.sleep(2000);
    }

    @And("the user exits")
    public void theUserExits() throws InterruptedException {
        addLossPage.exit();
        Thread.sleep(2000);
    }

    @Then("the Add Claimant Coverage Transaction should be created and marked as Processed")
    public void theAddClaimantCoverageTransactionShouldBeCreatedAndMarkedAsProcessed() throws IOException, InterruptedException {
        addLossPage.verifyAddClaimantCoverageStatus();
        Thread.sleep(2000);
    }


    // Add Claimant Third Party
    @And("navigates to the Parties tab")
    public void navigatesToThePartiesTab() throws InterruptedException {
        addClaimantThirdPartyPage.clickPartiesTab();
        Thread.sleep(2000);
    }

    @And("clicks the Add Party button")
    public void clicksTheAddPartyButton() throws InterruptedException {
        addClaimantThirdPartyPage.clickAddParty();
        Thread.sleep(2000);
    }

    @And("chooses an option from the Type dropdown")
    public void choosesAnOptionFromTheTypeDropdown() throws InterruptedException {
        addClaimantThirdPartyPage.selectType(data.get("Type"));
        Thread.sleep(2000);
    }

    @And("chooses an option from the Role dropdown")
    public void choosesAnOptionFromTheRoleDropdown() throws InterruptedException {
        addClaimantThirdPartyPage.selectRole(data.get("Role"));
        Thread.sleep(2000);
    }

    @And("enters the first name in the Name field")
    public void entersTheFirstNameInTheNameField() {
        addClaimantThirdPartyPage.enterFirstName(data.get("FirstName"));
    }

    @And("enters the last name in the Last Name field")
    public void entersTheLastNameInTheLastNameField() {
        addClaimantThirdPartyPage.enterLastName(data.get("LastName"));

    }

    @And("enters the contact number in the Contact Number field under the Additional Details block")
    public void entersTheContactNumberInTheContactNumberFieldUnderTheAdditionalDetailsBlock() {
        addClaimantThirdPartyPage.enterContactNum(data.get("ContactNum"));
    }

    @And("selects an option from the Is Party Injured? dropdown")
    public void selectsAnOptionFromTheIsPartyInjuredDropdown() throws InterruptedException {
        addClaimantThirdPartyPage.selectFromIsPartyInjured(data.get("IsPartyInjuredOption"));
        Thread.sleep(2000);
    }

    @And("selects a location from the Location dropdown under the Primary Address Details block")
    public void selectsALocationFromTheLocationDropdownUnderThePrimaryAddressDetailsBlock() throws InterruptedException {
        addClaimantThirdPartyPage.selectLocation(data.get("Location"));
        Thread.sleep(2000);
    }

    @Then("the Additional Insured should be created and displayed in the Party List block on the Claim Party | Details Page")
    public void theAdditionalInsuredShouldBeCreatedAndDisplayedInThePartyListBlockOnTheClaimPartyDetailsPage() throws InterruptedException, IOException {
        addClaimantThirdPartyPage.verifyAdditionalInsured(data.get("AdditionalInsuredName"));
        Thread.sleep(2000);
    }


    // Scenario - Assign an Owner to a Claim Coverage

    @And("clicks the Assignment button")
    public void clicksTheAssignmentButton() throws InterruptedException {
        assignOwnerPage.clickAssignmentBtn();
        Thread.sleep(2000);
    }

    @And("selects an owner from the Claim Owner dropdown")
    public void selectsAnOwnerFromTheClaimOwnerDropdown() throws InterruptedException {
        assignOwnerPage.selectClaimOwner(data.get("ClaimOwner"));
        Thread.sleep(2000);
    }

    @And("clicks Process")
    public void clicksProcess() throws InterruptedException {
        assignOwnerPage.clickProcessBtn();
        Thread.sleep(4000);
    }

    @Then("the Assigned To field in the Claim Transaction Summary block should display the selected owner")
    public void theAssignedToFieldInTheClaimTransactionSummaryBlockShouldDisplayTheSelectedOwner() throws IOException, InterruptedException {
        assignOwnerPage.verifyAssignedTo(data.get("ClaimOwner"));
        Thread.sleep(2000);
    }

    @And("the Claim Transaction status should be Processed")
    public void theClaimTransactionStatusShouldBeProcessed() throws IOException, InterruptedException {
        assignOwnerPage.verifyStatus();
        Thread.sleep(2000);
    }

    @Then("the claim owner in the Processing Details Block on Claim Summary | Details Page should be the selected one")
    public void theClaimOwnerInTheProcessingDetailsBlockOnClaimSummaryDetailsPageShouldBeTheSelectedOne() throws IOException {
        assignOwnerPage.verifyClaimOwner(data.get("ClaimOwner"));
    }

    @Then("the claim owner in the Processing Details block on the Claim Summary | Details Page should match the selected owner")
    public void theClaimOwnerInTheProcessingDetailsBlockOnTheClaimSummaryDetailsPageShouldMatchTheSelectedOwner() throws IOException, InterruptedException {
        assignOwnerPage.verifyClaimOwner(data.get("ClaimOwner"));
        Thread.sleep(2000);
    }

    @And("the Transaction Assignment in the Transactions block should be created and marked as Processed")
    public void theTransactionAssignmentInTheTransactionsBlockShouldBeCreatedAndMarkedAsProcessed() throws IOException, InterruptedException {
        assignOwnerPage.verifyTransactionAssignment();
        Thread.sleep(2000);
    }


    // Scenario - Home Policy Creation

    @When("I create a new quote")
    public void iCreateANewQuote() throws InterruptedException {
        quotesPage.createQuote();
    }

    @And("I create a new customer")
    public void iCreateANewCustomer() throws InterruptedException {
        customerPage.createNewCustomer(data, -10);
    }

    @And("I provide policy information")
    public void iProvidePolicyInformation() throws InterruptedException {
        policyInformation.policyInfo(data);
    }

    @And("I provide location coverage details")
    public void iProvideLocationCoverageDetails() throws InterruptedException {
        locationCoverage.locationCoverAct(data);
    }

    @Then("I rate the quote")
    public void iRateTheQuote() throws InterruptedException {
        rate.rateQuote();
    }

    @And("I extract and write the policy cost to Results sheet")
    public void iExtractAndWritePolicyCostToResultsSheet() throws Exception {
        rate.extractAndWritePolicyCost(this.excelPath, this.tcId);
        Thread.sleep(2000);
    }

    @And("I extract and write the quote ID to Results sheet")
    public void iExtractAndWriteQuoteIdToResultsSheet() throws Exception {
        rate.extractAndWriteQuoteId(this.excelPath, this.tcId);
        Thread.sleep(2000);
    }
}
