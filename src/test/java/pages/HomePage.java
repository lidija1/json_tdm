package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

// The post-login homepage for authenticated employees
public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//span[contains(@class, 'x-btn-inner') and text()='new incident']")
    WebElement newIncidentButton;

    public void clickNewIncident() {
        clickElement(newIncidentButton, "Successfully Clicked On New Incident Button");
    }
}
