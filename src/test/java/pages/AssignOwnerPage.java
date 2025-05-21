package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;

public class AssignOwnerPage extends BasePage {

    public AssignOwnerPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//span[contains(text(),'assignment')]")
    WebElement assignmentBtn;

    @FindBy(xpath = "//input[@osviewid='PAI_1035046_OT_3336746_OI_1_BI_1063146_CI_15631646']")
    WebElement claimOwnerDropdown;

    @FindBy(xpath = "//span[contains(text(),'process')]")
    WebElement processBtn;

    @FindBy(xpath = "//div[@osviewid='PAI_1045646_OT_3331746_OI_1_BI_1069246_CI_17455646' and contains(text(),'Processed')]")
    WebElement processedStatus;

    @FindBy(xpath = "//div[@osviewid='PAI_1032146_OT_3336746_OI_1_BI_1059946_CI_15617346' and (contains(text(),'Processed') or contains(text(),'Created'))]")
    WebElement assignmentStatus;

    public void clickAssignmentBtn() {
        clickElement(assignmentBtn, "Successfully clicked on Assignment button");
    }

    public void selectClaimOwner(String claimOwner) {
        clickElement(claimOwnerDropdown);
        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(By.xpath("//li[contains(text(),'" + claimOwner + "')]")))
                .click().build().perform();
        System.out.println("Successfully selected from the Claim Owner dropdown: " + claimOwner);
    }

    public void clickProcessBtn() {
        clickElement(processBtn, "Successfully clicked on Process button");
    }

    public void verifyAssignedTo(String claimOwner) throws IOException {
        try {
            if (driver.findElement(By.xpath("//div[contains(text(),'"+claimOwner+"')]")).isDisplayed()) {
                System.out.println("Assigned to successfully verified: " + claimOwner);
            } else {
                System.out.println("Assigned to verification failed for: " + claimOwner);
            }
        } catch (Exception e) {
            System.out.println("Assigned owner not found.");
        }

        new BasePage(driver).reportScreenshot("AssignedToScreenshot_" + System.currentTimeMillis(),
                "Assigned To Screenshot");
    }

    public void verifyStatus() throws IOException {
        try {
            if (processedStatus.isDisplayed()) {
                System.out.println("Claim Transaction status verified: Processed");
            } else {
                System.out.println("Claim Transaction status verification failed for: Processed");
            }
        } catch (Exception e) {
            System.out.println("Claim Transaction status not found.");
        }

        new BasePage(driver).reportScreenshot("ClaimTransactionStatusScreenshot_" + System.currentTimeMillis(),
                "Claim Transaction Status Screenshot");
    }

    public void verifyClaimOwner(String claimOwner) throws IOException {
        //a[contains(text(),'Mark CSR Claims')]
        try {
            if (driver.findElement(By.xpath("//a[contains(text(),'"+claimOwner+"')]")).isDisplayed()) {
                System.out.println("Claim Owner successfully verified: " + claimOwner);
            } else {
                System.out.println("Claim Owner verification failed for: " + claimOwner);
            }
        } catch (Exception e) {
            System.out.println("Claim owner not found.");
        }

        new BasePage(driver).reportScreenshot("ClaimOwnerScreenshot_" + System.currentTimeMillis(),
                "Claim Owner Screenshot");
    }

    public void verifyTransactionAssignment() throws IOException, InterruptedException {
        scrollDown(assignmentStatus);
        try {
            if (assignmentStatus.isDisplayed()) {
                System.out.println("Transaction Assignment status verified: Processed");
            } else {
                System.out.println("Transaction Assignment status verification failed for: Processed");
            }
        } catch (Exception e) {
            System.out.println("Transaction Assignment status not found.");
        }

        new BasePage(driver).reportScreenshot("TransactionAssignmentStatusScreenshot_" + System.currentTimeMillis(),
                "Transaction Assignment Status Screenshot");
    }


}
