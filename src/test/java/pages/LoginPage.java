package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
// import org.openqa.selenium.support.PageFactory;

import pages.common.BasePage;

/**
 * Page class representing the login page functionality.
 * Handles all interactions with the login form elements.
 */
public class LoginPage extends BasePage {

    // Login credentials
    private static final String PARTNER_NUM = "0";
    private static final String USERNAME = "UW Senior Manager";
    private static final String PASSWORD = "BenchUWSM10!";

    /**
     * Constructor for LoginPage.
     * @param driver WebDriver instance to be used for browser interactions
     */
    public LoginPage(WebDriver driver) {
        super(driver);
        // PageFactory.initElements(driver, this); // Already handled in BasePage
    }

    @FindBy(xpath = "//div[text()='PARTNER NUMBER']/../../../..//input")
    WebElement partnerNumField;

    @FindBy(xpath = "//div[text()='USERNAME']/../../../..//input")
    WebElement usernameField;

    @FindBy(xpath = "//div[text()='PASSWORD']/../../../..//input")
    WebElement passwordField;

    @FindBy(xpath = "//span[text()='login']")
    WebElement loginButton;

    /**
     * Enters the partner number into the partner number field.
     * @param partnerNum The partner number to be entered
     */
    public void enterPartnerNum(String partnerNum) {
        typeText(partnerNumField, partnerNum, "Entered Partner Number");
    }

    /**
     * Enters the username into the username field.
     * @param username The username to be entered
     */
    public void enterUsername(String username) {
        typeText(usernameField, username, "Entered Username");
    }

    /**
     * Enters the password into the password field.
     * @param password The password to be entered
     */
    public void enterPassword(String password) {
        typeText(passwordField, password, "Entered Password");
    }

    /**
     * Clicks the login button to submit the login form.
     */
    public void clickLogin() {
        clickElement(loginButton, "Clicked Login Button");
    }

    /**
     * Performs a complete login operation using predefined credentials.
     * This method handles the entire login process including entering partner number,
     * username, password and clicking the login button.
     */
    public void performLogin() {
        enterPartnerNum(PARTNER_NUM);
        enterUsername(USERNAME);
        enterPassword(PASSWORD);
        clickLogin();
    }
}
