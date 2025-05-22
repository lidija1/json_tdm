package pages.home;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pages.common.BasePage;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;
import java.util.List;

import java.util.Map;


public class SearchPage extends BasePage {

    public SearchPage(WebDriver driver) {
        super(driver);
        this.driver = driver; /**this is not needed if the driver for search page  is already initialized in the BasePage constructor**/
        PageFactory.initElements(driver, this); /**this is not needed if the PageFactory is already initialized in the BasePage constructor**/
    }

    @FindBy(xpath = "//div[text()='Customer Type']/../../../..//input[@value='Individual']")
    public WebElement selectCustomerType;

    @FindBy(xpath = "//div[text()='First Name']/../../../..//input")
    public WebElement enterFirstName;

    @FindBy(xpath = "//div[text()='Last Name']/../../../..//input")
    public WebElement enterLastName;

    @FindBy(xpath = "//div[text()='Date of Birth']/../../../..//input")
    public WebElement enterDOB;

    @FindBy(xpath = "//div[text()='Phone']/../../../..//input")
    public WebElement enterPhone;

    @FindBy(xpath = "//div[text()='Email']/../../../..//input")
    public WebElement enterEmail;

    @FindBy(xpath = "//div[text()='Country']/../../../..//input")
    public WebElement enterCountry;

    @FindBy(xpath = "//div[text()='ZIP Code']/../../../..//input")
    public WebElement enterZipCode;

    @FindBy(xpath = "//div[text()='State']/../../../..//input")
    public WebElement enterState;

    @FindBy(xpath = "//div[text()='City']/../../../..//input")
    public WebElement enterCity;

    @FindBy(xpath = "//div[text()='Address Line 1']/../../../..//input")
    public WebElement enterAddressLine;

    @FindBy(xpath = "//span[text()='>>> Search']")
    public WebElement searchButton;

    @FindBy(xpath = "//span[text()='Back']")
    public WebElement backButton;

    @FindBy(xpath = "//span[text()='Exit']")
    public WebElement exitButton;

    @FindBy(xpath = "//span[text()='>>> Create A New Customer']")
    public WebElement createNewCustButton;

    @FindBy(xpath = "//span[text()='   >>> next']")
    public WebElement nextButton;

    @FindBy(xpath = "//span[text()='>>> skip']")
    public WebElement skipButton;


    public void createCustomer(Map<String, String> data, int day) throws InterruptedException {
        try {
            // Fill in customer information without waits
            selectCustomerType(data);
            enterTestFirstName(data);
            enterTestLastName(data);
            enterTestDOB(data);
            enterTestPhone(data);
            enterTestEmail(data);
            enterTestCountry(data);
            enterTestZipCode(data);
            enterTestState(data);
            enterTestCity(data);
            enterTestAddressLine(data);
            
            // Search first - this checks if customer already exists
            searchButtonClick();
            
            // Reduced wait time from 3000ms to 1000ms
            Thread.sleep(1000);
            
            // Check if the customer already exists by looking for specific elements
            boolean customerAlreadyExists = false;
            try {
                // Look for indicators that customer exists (like a warning message or search results)
                List<WebElement> existingCustomerIndicators = driver.findElements(
                    By.xpath("//div[contains(text(), 'Customer already exists') or " +
                             "contains(text(), 'matching records found') or " +
                             "contains(text(), 'match found') or " +
                             "contains(text(), 'found in database')]"));
                
                if (!existingCustomerIndicators.isEmpty()) {
                    for (WebElement indicator : existingCustomerIndicators) {
                        if (indicator.isDisplayed()) {
                            System.out.println("Customer already exists. Will need to click Create New Customer twice.");
                            customerAlreadyExists = true;
                            break;
                        }
                    }
                }
                
                // Also check if there are search results in a table
                if (!customerAlreadyExists) {
                    List<WebElement> searchResults = driver.findElements(
                        By.xpath("//table//tr[position() > 1] | //div[contains(@class, 'result')] | //div[contains(@class, 'match')]"));
                    if (searchResults.size() > 0) {
                        System.out.println("Found " + searchResults.size() + " search results. Will need to click Create New Customer twice.");
                        customerAlreadyExists = true;
                    }
                }
            } catch (Exception e) {
                System.out.println("Error checking if customer exists: " + e.getMessage());
            }
            
            // Click the create new customer button
            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.elementToBeClickable(createNewCustButton));
            createNewCustButton();
            
            // If customer already exists, need to click the button again
            if (customerAlreadyExists) {
                Thread.sleep(2000);
                try {
                    wait.until(ExpectedConditions.elementToBeClickable(createNewCustButton));
                    createNewCustButton();
                    System.out.println("Clicked Create New Customer button a second time");
                } catch (Exception e) {
                    System.out.println("Error clicking Create New Customer a second time: " + e.getMessage());
                }
            }
            
            // Wait for next button to be clickable
            try {
                wait.until(ExpectedConditions.elementToBeClickable(nextButton));
                nextButtonClick();
                
                // Wait for skip button to be clickable
                wait.until(ExpectedConditions.elementToBeClickable(skipButton));
                skipButtonClick();
            } catch (Exception e) {
                System.out.println("Failed to proceed with next/skip buttons: " + e.getMessage());
                throw e;
            }
        } catch (Exception e) {
            System.out.println("Error during customer creation: " + e.getMessage());
            throw e;
        }
    }

    public void selectCustomerType(Map<String, String> data) {
        typeText(selectCustomerType, data.get("CustomerType"), "Customer Type" + data.get("CustomerType"));
    }

    public void enterTestFirstName(Map<String, String> data) {
        typeText(enterFirstName, data.get("FirstName"), "First Name" + data.get("FirstName"));
    }

    public void enterTestLastName(Map<String, String> data) {
        typeText(enterLastName, data.get("LastName"), "Last Name" + data.get("LastName"));
    }

    public void enterTestDOB(Map<String, String> data) {
        typeText(enterDOB, data.get("DOB"), "DOB" + data.get("DOB"));
    }

    public void enterTestPhone(Map<String, String> data) {
        typeText(enterPhone, data.get("PhoneNum"), "Phone Number: " + data.get("PhoneNum"));
    }
    public void enterTestEmail(Map<String, String> data) {
        typeText(enterEmail, data.get("Email"), "Email: " + data.get("Email"));

    }

    public void enterTestCountry(Map<String, String> data) {
        typeText(enterCountry, data.get("Country"), "Country: " + data.get("Country"));
    }

    public void enterTestZipCode(Map<String, String> data) {
        typeText(enterZipCode, data.get("ZIP"), "ZipCode: " + data.get("ZIP"));
    }

    public void enterTestState(Map<String, String> data) {
        typeText(enterState, data.get("State"), "State: " + data.get("State"));
    }
    public void enterTestCity(Map<String, String> data) {
        typeText(enterCity, data.get("City"), "City" + data.get("City"));
    }
    public void enterTestAddressLine(Map<String, String> data) {
        typeText(enterAddressLine, data.get("Address"), "Address" + data.get("Address"));
    }

    public void searchButtonClick() { clickElement(searchButton,"Search");}

    public void createNewCustButton() { clickElement(createNewCustButton,"New Customer");}

    public void nextButtonClick() { clickElement(nextButton,"Next");}

    public void skipButtonClick() { clickElement(skipButton,"Skip");}



   // public void backButtonClick() { clickElement(backButton,"Back");}
   // public void exitButtonClick() { clickElement(exitButton,"Exit");}



}

