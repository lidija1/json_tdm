package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import models.TestData;
import pages.common.BasePage;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Page class representing the customer functionality.
 * Handles all interactions with customer-related form elements and operations.
 */
public class Customer extends BasePage {
    /**
     * Constructor for Customer page.
     * @param driver WebDriver instance to be used for browser interactions
     */
    public Customer(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//div[text()='Customer Type']/../../../..//input[@value='Individual']")
    public WebElement selectCustType;

    @FindBy(xpath = "//input[@osviewid='PAI_1086048_OT_3380946_OI_1_BI_1129948_CI_16118048']")
    public WebElement enterFirstName;

    @FindBy(xpath = "//input[@osviewid='PAI_1086048_OT_3380946_OI_1_BI_1129948_CI_16118248']")
    public WebElement enterLastName;

    @FindBy(xpath = "//div[text()='Date of Birth']/../../../..//input")
    public WebElement enterDOB;

    @FindBy(xpath = "//div[text()='Phone']/../../../..//input")
    public WebElement enterPhone;

    @FindBy(xpath = "//div[text()='Email']/../../../..//input")
    public WebElement enterEmail;

    @FindBy(xpath = "//div[text()='Address Line 1']/../../../..//input")
    public WebElement enterAddress;

    @FindBy(xpath = "//div[text()='ZIP Code']/../../../..//input")
    public WebElement enterZIP;

    @FindBy(xpath = "//span[text()='>>> Search']")
    public WebElement searchBtn;

    @FindBy(xpath = "//span[text()='>>> Create A New Customer']")
    public WebElement createNewCustBtn;

    @FindBy(xpath = "//span[text()='   >>> next']")
    public WebElement next1Btn;

    @FindBy(xpath = "//span[text()='>>> skip']")
    public WebElement skipBtn;

    @FindBy(xpath = "//div[text()='Effective Date']/../../../..//input")
    public WebElement effDate;

    @FindBy(xpath = "//div[text()='Program']/../../../..//input")
    public WebElement program;

    @FindBy(xpath = "//span[text()='Next']")
    public WebElement next2Btn;

    /**
     * Creates a new customer with the provided test data.
     * This method handles the complete customer creation process including personal details,
     * contact information, and policy setup.
     * 
     * @param testData The test data containing customer information
     * @param day The number of days to adjust the effective date
     * @throws InterruptedException if the thread sleep is interrupted
     */
    public void createNewCustomer(TestData testData, int day) throws InterruptedException {
        selectCustType(testData);
        enterRandomFirstName(testData);
        enterRandomLastName(testData);
        enterDOB(testData);
        enterPhone(testData);
        enterEmail(testData);
        enterAddress(testData);
        enterZIP(testData);
        clickSearch();
        clickNewCustBtn();
        clickNext1();
        clickSkip();
        enterProducer(testData);
        setEffDate(day);
        setProgram(testData);
        clickNext2();
    }

    /**
     * Selects the customer type from the provided test data.
     * @param testData The test data containing customer type information
     */
    public void selectCustType(TestData testData) {
        typeText(selectCustType, testData.getCustomerType(), "Customer Type: " + testData.getCustomerType());
    }

    /**
     * Enters the first name from the provided test data.
     * @param testData The test data containing first name information
     */
    public void enterRandomFirstName(TestData testData) {
        typeText(enterFirstName, testData.getFirstName(), "First Name: " + testData.getFirstName());
    }

    /**
     * Enters the last name from the provided test data.
     * @param testData The test data containing last name information
     */
    public void enterRandomLastName(TestData testData) {
        typeText(enterLastName, testData.getLastName(), "Last Name: " + testData.getLastName());
    }

    /**
     * Enters the date of birth from the provided test data.
     * @param testData The test data containing date of birth information
     */
    public void enterDOB(TestData testData) {
        typeText(enterDOB, testData.getDOB(), "Date Of Birth: " + testData.getDOB());
    }

    /**
     * Enters the phone number from the provided test data.
     * @param testData The test data containing phone number information
     */
    public void enterPhone(TestData testData) {
        typeText(enterPhone, testData.getPhoneNum(), "Phone Number: " + testData.getPhoneNum());
    }

    /**
     * Enters the email address from the provided test data.
     * @param testData The test data containing email information
     */
    public void enterEmail(TestData testData) {
        typeText(enterEmail, testData.getEmail(), "Email: " + testData.getEmail());
    }

    /**
     * Enters the address from the provided test data.
     * @param testData The test data containing address information
     */
    public void enterAddress(TestData testData) {
        typeText(enterAddress, testData.getAddress(), "Address: " + testData.getAddress());
    }

    /**
     * Enters the ZIP code from the provided test data.
     * @param testData The test data containing ZIP code information
     */
    public void enterZIP(TestData testData) {
        typeText(enterZIP, testData.getZIP(), "ZIP code: " + testData.getZIP());
    }

    /**
     * Clicks the search button to initiate customer search.
     */
    public void clickSearch() {
        clickElement(searchBtn, "Search");
    }

    /**
     * Clicks the Create New Customer button to start customer creation process.
     */
    public void clickNewCustBtn() {
        clickElement(createNewCustBtn, "New Customer");
    }

    /**
     * Clicks the first Next button in the customer creation workflow.
     */
    public void clickNext1() {
        clickElement(next1Btn, "Next");
    }

    /**
     * Clicks the Skip button to bypass optional steps in the workflow.
     */
    public void clickSkip() {
        clickElement(skipBtn, "Skip");
    }

    /**
     * Enters the producer information from the provided test data.
     * @param testData The test data containing producer information
     */
    public void enterProducer(TestData testData) {
        typeText(program, testData.getProducer(), "Producer: " + testData.getProducer());
    }

    /**
     * Sets the effective date by adjusting it relative to the current date.
     * @param day The number of days to adjust the effective date
     */
    public void setEffDate(int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, day);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String date = dateFormat.format(calendar.getTime());
        typeText(effDate, date, "Effective Date: " + date);
    }

    /**
     * Sets the program from the provided test data.
     * @param testData The test data containing program information
     */
    public void setProgram(TestData testData) {
        typeText(program, testData.getProgram(), "Program: " + testData.getProgram());
    }

    /**
     * Clicks the second Next button to proceed in the customer creation workflow.
     */
    public void clickNext2() {
        clickElement(next2Btn, "Next");
    }
}
