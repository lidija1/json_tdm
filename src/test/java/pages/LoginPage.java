package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends BasePage {

    public LoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//input[@osviewid = 'PAI_2_OT_310_OI_1_BI_1075_CI_6376001']")
    WebElement partnerNumField;

    @FindBy(xpath = "//input[@osviewid = 'PAI_2_OT_310_OI_1_BI_1075_CI_8780402']")
    WebElement usernameField;

    @FindBy(xpath = "//input[@osviewid = 'PAI_2_OT_310_OI_1_BI_1075_CI_8780502']")
    WebElement passwordField;

    @FindBy(xpath = "//span[contains(@class, 'x-btn-inner') and text()='login']")
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

    public void performLogin() {

        if (System.getenv("PARTNER_NUM") == null || System.getenv("USERNAMEE") == null
                || System.getenv("PASSWORD") == null) {
            throw new IllegalArgumentException("Environment variables for credentials are not set!");
        }

        enterPartnerNum(System.getenv("PARTNER_NUM"));
        enterUsername(System.getenv("USERNAMEE"));
        enterPassword(System.getenv("PASSWORD"));
        clickLogin();
    }


}
