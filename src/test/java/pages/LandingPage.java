package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.common.BasePage;

// The entry page for employee login
public class LandingPage extends BasePage {

    public LandingPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "employeePortal")
    WebElement loginToEmpPortal;

    public void loginToEmpPortal() {
        clickElement(loginToEmpPortal, "Clicked on the 'LOGIN' button to access the Employee Portal.");
    }
}
