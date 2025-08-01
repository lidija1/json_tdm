package pages.common;

import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class BasePage {

    protected WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        /*
          PageFactory class in Selenium simplifies the implementation of the POM.
          The initElements() method initializes WebElements declared in the page class.
          It is called inside a constructor that will automatically initialize all the elements in the page class
          when it is instantiated.
         */
    }

    int waitTime = 15;

    // Wrapper method for click() - An enhanced click method with extra handling
    public void clickElement(WebElement element, String log) {
        try {
            // Single wait with multiple conditions is more efficient
            WebDriverWait wdWait = new WebDriverWait(driver, waitTime);
            wdWait.until(ExpectedConditions.and(
                ExpectedConditions.visibilityOf(element),
                ExpectedConditions.elementToBeClickable(element)
            ));

            // Direct click is faster than hovering first in most cases
            element.click();
            System.out.println(log);

        } catch (StaleElementReferenceException e) {
            // If we get a stale element exception, try with Actions as fallback
            Actions actions = new Actions(driver);
            actions.moveToElement(element).click().build().perform();
            System.out.println(log);
        } catch (Exception e) {
            // For other exceptions, try JavaScript click as last resort
            try {
                System.out.println("Attempting JavaScript click on: " + log);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
            } catch (Exception je) {
                System.out.println("Failed to click element: " + log + " - " + je.getMessage());
                throw je;
            }
        }
    }

    public void clickElement(WebElement element) {
        try {
            // Single wait with multiple conditions
            WebDriverWait wdWait = new WebDriverWait(driver, waitTime);
            wdWait.until(ExpectedConditions.and(
                ExpectedConditions.visibilityOf(element),
                ExpectedConditions.elementToBeClickable(element)
            ));

            // Direct click
            element.click();

        } catch (StaleElementReferenceException e) {
            Actions actions = new Actions(driver);
            actions.moveToElement(element).click().build().perform();
        } catch (Exception e) {
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
            } catch (Exception je) {
                throw je;
            }
        }
    }

    // Wrapper method for sendKeys()
    public void typeText(WebElement element, String text, String log) {
        try {
            WebDriverWait wdWait = new WebDriverWait(driver, waitTime);
            wdWait.until(ExpectedConditions.visibilityOf(element));

            // Clear using JavaScript which is more reliable
            ((JavascriptExecutor) driver).executeScript("arguments[0].value = '';", element);
            element.sendKeys(text);

            System.out.println(log);

        } catch (StaleElementReferenceException e) {
            // First try to recover the element if it's stale
            try {
                // Re-find the element using its locator if possible
                WebElement refreshedElement = driver.findElement(By.xpath(".//*[@id='" + element.getAttribute("id") + "']"));
                refreshedElement.clear();
                refreshedElement.sendKeys(text);
            } catch (Exception ex) {
                // If that fails, just try the original element
                element.clear();
                element.sendKeys(text);
            }
            System.out.println(log + " (after handling stale element)");
        } catch (Exception e) {
            // Try JavaScript as a last resort
            try {
                System.out.println("Attempting JavaScript input on: " + log);
                ((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];", element, text);
            } catch (Exception je) {
                System.out.println("Failed to input text: " + log + " - " + je.getMessage());
                throw je;
            }
        }
    }

    public void typeText(WebElement element, String text) {
        try {
            WebDriverWait wdWait = new WebDriverWait(driver, waitTime);
            wdWait.until(ExpectedConditions.visibilityOf(element));

            ((JavascriptExecutor) driver).executeScript("arguments[0].value = '';", element);
            element.sendKeys(text);

        } catch (StaleElementReferenceException e) {
            try {
                WebElement refreshedElement = driver.findElement(By.xpath(".//*[@id='" + element.getAttribute("id") + "']"));
                refreshedElement.clear();
                refreshedElement.sendKeys(text);
            } catch (Exception ex) {
                element.clear();
                element.sendKeys(text);
            }
        } catch (Exception e) {
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];", element, text);
            } catch (Exception je) {
                throw je;
            }
        }
    }

    // Wrapper method for getText()
    public String getText(WebElement element, String log) {
        try {
            WebDriverWait wdWait = new WebDriverWait(driver, waitTime);
            wdWait.until(ExpectedConditions.visibilityOf(element));

            System.out.println("Got text from " + log);
            return element.getText();

        } catch (StaleElementReferenceException e) {
            System.out.println("Got text from " + log);
            return element.getText();
        }
    }

    public String getText(WebElement element) {
        try {
            WebDriverWait wdWait = new WebDriverWait(driver, waitTime);
            wdWait.until(ExpectedConditions.visibilityOf(element));

            return element.getText();
        } catch (StaleElementReferenceException e) {
            return element.getText();
        }
    }

    // Verifies if a web element is present in the DOM by checking if the specified locator finds at least one element
    public boolean isElementPresent(List<WebElement> elements) {
        return elements.size() > 0;
    }

    public void selectByText(WebElement element, String text, String log) {
        try {
            WebDriverWait wdWait = new WebDriverWait(driver, waitTime);
            wdWait.until(ExpectedConditions.visibilityOf(element));
            wdWait.until(ExpectedConditions.elementToBeClickable(element));

            Select select = new Select(element);
            select.selectByVisibleText(text);

            System.out.println("Selected " + text + log);

        } catch (StaleElementReferenceException e) {
            Select select = new Select(element);
            select.selectByVisibleText(text);

            System.out.println("Selected " + text + log);
        }
    }

    public void selectByText(WebElement element, String text) {
        try {
            WebDriverWait wdWait = new WebDriverWait(driver, waitTime);
            wdWait.until(ExpectedConditions.visibilityOf(element));
            wdWait.until(ExpectedConditions.elementToBeClickable(element));

            Select select = new Select(element);
            select.selectByVisibleText(text);

        } catch (StaleElementReferenceException e) {
            Select select = new Select(element);
            select.selectByVisibleText(text);
        }
    }

    public void selectByValue(WebElement element, String text, String log) {
        try {
            WebDriverWait wdWait = new WebDriverWait(driver, waitTime);
            wdWait.until(ExpectedConditions.visibilityOf(element));
            wdWait.until(ExpectedConditions.elementToBeClickable(element));

            Select select = new Select(element);
            select.selectByValue(text);

            System.out.println("Selected " + text + log);

        } catch (StaleElementReferenceException e) {
            Select select = new Select(element);
            select.selectByVisibleText(text);

            System.out.println("Selected value " + text + log);
        }
    }

    public void selectByValue(WebElement element, String text) {
        try {
            WebDriverWait wdWait = new WebDriverWait(driver, waitTime);
            wdWait.until(ExpectedConditions.visibilityOf(element));
            wdWait.until(ExpectedConditions.elementToBeClickable(element));

            Select select = new Select(element);
            select.selectByValue(text);

        } catch (StaleElementReferenceException e) {
            Select select = new Select(element);
            select.selectByVisibleText(text);
        }
    }

    public boolean hasValue(String text) {
        try {
            return !text.equals("") || !text.equals(" ");
        } catch (Exception e) {
            return false;
        }
    }

    public void takeScreenshot(String name) throws IOException {
        // Takes a screenshot and saves it as a File object (output type is FILE)
        File file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        // Copies the screenshot file to the specified path with the given name
        FileUtils.copyFile(file, new File("src/screenshots/" + name + ".png"));
    }

    // Method to create a screenshot and add it to the report
    public void reportScreenshot(String fileName, String allureName) throws IOException {
        // 1st arg = file name, 2nd arg = name displayed in Allure report
        takeScreenshot(fileName);
        Path path = Paths.get("src/screenshots/" + fileName + ".png");
        try(InputStream is = Files.newInputStream(path)) {
            // Adds the screenshot as an attachment in the Allure report
            Allure.addAttachment(allureName, is);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void scrollDown(WebElement element) throws InterruptedException {
        WebElement scrollableElement = driver.findElement(By.cssSelector("#framework-body"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollTop += arguments[0].clientHeight * 1.7;", scrollableElement);
        Thread.sleep(3000);
        WebDriverWait wdWait = new WebDriverWait(driver, waitTime);
        wdWait.until(ExpectedConditions.visibilityOf(element));
    }

    protected void pause(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
} 