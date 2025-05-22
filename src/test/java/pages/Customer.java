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

public class Customer extends BasePage {
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

    public void selectCustType(TestData testData) {
        typeText(selectCustType, testData.getCustomerType(), "Customer Type: " + testData.getCustomerType());
    }

    public void enterRandomFirstName(TestData testData) {
        typeText(enterFirstName, testData.getFirstName(), "First Name: " + testData.getFirstName());
    }

    public void enterRandomLastName(TestData testData) {
        typeText(enterLastName, testData.getLastName(), "Last Name: " + testData.getLastName());
    }

    public void enterDOB(TestData testData) {
        typeText(enterDOB, testData.getDOB(), "Date Of Birth: " + testData.getDOB());
    }

    public void enterPhone(TestData testData) {
        typeText(enterPhone, testData.getPhoneNum(), "Phone Number: " + testData.getPhoneNum());
    }

    public void enterEmail(TestData testData) {
        typeText(enterEmail, testData.getEmail(), "Email: " + testData.getEmail());
    }

    public void enterAddress(TestData testData) {
        typeText(enterAddress, testData.getAddress(), "Address: " + testData.getAddress());
    }

    public void enterZIP(TestData testData) {
        typeText(enterZIP, testData.getZIP(), "ZIP code: " + testData.getZIP());
    }

    public void clickSearch() {
        clickElement(searchBtn, "Search");
    }

    public void clickNewCustBtn() {
        clickElement(createNewCustBtn, "Create a New Customer");
    }

    public void clickNext1() {
        clickElement(next1Btn, "Next");
    }

    public void clickSkip() {
        clickElement(skipBtn, "Skip");
    }

    public void enterProducer(TestData testData) {
        clickElement(driver.findElement(By.xpath("//li[text()='" + testData.getProducer() + "']")), 
                "Producer: " + testData.getProducer());
    }

    public void setEffDate(int day) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, day);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String pastDate = dateFormat.format(cal.getTime());
        typeText(effDate, pastDate, "Effective Date: " + pastDate);
    }

    public void setProgram(TestData testData) throws InterruptedException {
        clickElement(program, "Program dropdown menu.");
        Thread.sleep(1000);
        clickElement(driver.findElement(By.xpath("//li[text()='" + testData.getProgram() + "']")), 
                "Program: " + testData.getProgram());
    }

    public void clickNext2() {
        clickElement(next2Btn, "Next");
    }
}
