package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
// import org.openqa.selenium.support.PageFactory;

import pages.common.BasePage;

public class LoginPage extends BasePage {

    // Login credentials
    private static final String PARTNER_NUM = "0";
    private static final String USERNAME = "UW Senior Manager";
    private static final String PASSWORD = "BenchUWSM10!";

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

    public void enterPartnerNum(String partnerNum) {
        typeText(partnerNumField, partnerNum, "Entered Partner Number");
    }

    public void enterUsername(String username) {
        typeText(usernameField, username, "Entered Username");
    }

    public void enterPassword(String password) {
        typeText(passwordField, password, "Entered Password");
    }

    public void clickLogin() {
        clickElement(loginButton, "Clicked Login Button");
    }

    /**
     * Login with the predefined credentials
     **/
    public void performLogin() {
        enterPartnerNum(PARTNER_NUM);
        enterUsername(USERNAME);
        enterPassword(PASSWORD);
        clickLogin();
    }
}
