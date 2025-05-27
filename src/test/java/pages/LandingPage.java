package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.common.BasePage;

/**
 * Page class representing the landing page functionality.
 * This is the entry page for employee login.
 */
public class LandingPage extends BasePage {

    /**
     * Constructor for LandingPage.
     * @param driver WebDriver instance to be used for browser interactions
     */
    public LandingPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "employeePortal")
    WebElement loginToEmpPortal;

    /**
     * Clicks the login button to access the Employee Portal.
     * This method initiates the login process from the landing page.
     */
    public void loginToEmpPortal() {
        clickElement(loginToEmpPortal, "Clicked on the 'LOGIN' button to access the Employee Portal.");
    }
}
