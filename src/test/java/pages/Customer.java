package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import models.TestCase;

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

    public void createNewCustomer(TestCase testCase, int day) throws InterruptedException {
        selectCustType(testCase);
        enterRandomFirstName(testCase);
        enterRandomLastName(testCase);
        enterDOB(testCase);
        enterPhone(testCase);
        enterEmail(testCase);
        enterAddress(testCase);
        enterZIP(testCase);
        clickSearch();
        clickNewCustBtn();
        clickNext1();
        clickSkip();
        enterProducer(testCase);
        setEffDate(day);
        setProgram(testCase);
        clickNext2();
    }

    public void selectCustType(TestCase testCase) {
        typeText(selectCustType, testCase.getCustomerType(), "Customer Type: " + testCase.getCustomerType());
    }

    public void enterRandomFirstName(TestCase testCase) {
        typeText(enterFirstName, testCase.getFirstName(), "First Name: " + testCase.getFirstName());
    }

    public void enterRandomLastName(TestCase testCase) {
        typeText(enterLastName, testCase.getLastName(), "Last Name: " + testCase.getLastName());
    }

    public void enterDOB(TestCase testCase) {
        typeText(enterDOB, testCase.getDOB(), "Date Of Birth: " + testCase.getDOB());
    }

    public void enterPhone(TestCase testCase) {
        typeText(enterPhone, testCase.getPhoneNum(), "Phone Number: " + testCase.getPhoneNum());
    }

    public void enterEmail(TestCase testCase) {
        typeText(enterEmail, testCase.getEmail(), "Email: " + testCase.getEmail());
    }

    public void enterAddress(TestCase testCase) {
        typeText(enterAddress, testCase.getAddress(), "Address: " + testCase.getAddress());
    }

    public void enterZIP(TestCase testCase) {
        typeText(enterZIP, testCase.getZIP(), "ZIP code: " + testCase.getZIP());
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

    public void enterProducer(TestCase testCase) {
        clickElement(driver.findElement(By.xpath("//li[text()='" + testCase.getProducer() + "']")), 
                "Producer: " + testCase.getProducer());
    }

    public void setEffDate(int day) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, day); // Subtract 10 day to get past date
        // cal.add(Calendar.MONTH,+2);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String pastDate = dateFormat.format(cal.getTime());
        typeText(effDate, pastDate, "Effective Date: " + pastDate);
    }


    public void setProgram(TestCase testCase) throws InterruptedException {
        clickElement(program, "Program dropdown menu.");
        Thread.sleep(1000);
        clickElement(driver.findElement(By.xpath("//li[text()='" + testCase.getProgram() + "']")), 
                "Program: " + testCase.getProgram());
    }

    public void clickNext2() {
        clickElement(next2Btn, "Next");
    }
}
