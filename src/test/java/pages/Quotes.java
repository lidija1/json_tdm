package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pages.common.BasePage;

/**
 * Page class representing the quotes functionality.
 * Handles all interactions with the quotes page elements and quote creation process.
 */
public class Quotes extends BasePage {

    /**
     * Constructor for Quotes page.
     * @param driver WebDriver instance to be used for browser interactions
     */
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

    /**
     * Creates a new quote by performing the complete quote creation process.
     * This method handles clicking the quotes tab, creating a new quote,
     * selecting an agent, and proceeding to the next step.
     * @throws InterruptedException if the thread sleep is interrupted
     */
    public void createQuote() throws InterruptedException {
        clickQuotes();
        clickNewQuote();
        Thread.sleep(1000);
        selectAgent();
        clickNext();
    }

    /**
     * Clicks the Quotes tab to navigate to the quotes section.
     */
    public void clickQuotes() {
        clickElement(quotesBtn, "Quotes Tab");
    }

    /**
     * Clicks the New Quote button to initiate quote creation.
     */
    public void clickNewQuote() {
        clickElement(newQuote, ">>> New Quote");
    }

    /**
     * Selects an agent for the quote.
     * Currently selects the agent named "Finn".
     */
    public void selectAgent() {
        clickElement(selectAgent, "Agent: Finn");
    }

    /**
     * Clicks the Next button to proceed to the next step in quote creation.
     */
    public void clickNext() {
        clickElement(nextBtn, "Next");
    }
}
