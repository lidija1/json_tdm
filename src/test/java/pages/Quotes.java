package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class Quotes extends BasePage {

    public Quotes(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//span[text()='quotes']")
    public WebElement quotesBtn;
    @FindBy(xpath = "//span[text()='>>> new quote']")
    public WebElement newQuote;
    @FindBy(xpath = "//table[@class='x-grid-item']//tr//td//div//span//..")
    public WebElement selectAgent;
    @FindBy(xpath = "//span[text()='>>> next']")
    public WebElement nextBtn;

    public void createQuote() throws InterruptedException {
        clickQuotes();
        clickNewQuote();
        Thread.sleep(1000);
       // sleep(1000);
        selectAgent();
        clickNext();
    }

    public void clickQuotes() {
        clickElement(quotesBtn, "Quotes Tab");
    }

    public void clickNewQuote() {
        clickElement(newQuote, ">>> New Quote");
    }

    public void selectAgent() {
        clickElement(selectAgent, "Agent: Finn");
    }

    public void clickNext() {
        clickElement(nextBtn, "Next");
    }
}
