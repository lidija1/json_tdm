package steps;

import json_core.JsonDataReader;
import org.openqa.selenium.WebDriver;
import selenium_core.DriverManager;
import selenium_core.DriverManagerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class BaseSteps {

    WebDriver driver;
    DriverManager driverManager;
    Map<String, String> data;

    public void baseSetUp(String browser, String version, int wait) {
        driverManager = DriverManagerFactory.getDriverManager(browser);
        driver = driverManager.getDriver(version);
        driver.manage().timeouts().implicitlyWait(wait, TimeUnit.SECONDS);
    }

    public void baseTearDown() {
       //driver.quit();
    }    public void loadTestData(String fileName, String ID, String dataNum) throws IOException {
        // Convert Excel path to JSON path
        fileName = fileName.replace(".xlsx", ".json");
        data = new JsonDataReader().getRowDataByID(fileName, ID, dataNum, false);
    }

    public void mergeTestData(String fileName, String key) throws IOException {
        // Convert Excel path to JSON path
        fileName = fileName.replace(".xlsx", ".json");
        String[] object = data.get(key).split(",");
        Map<String, String> dataToBeMerged = new HashMap<>();

        for (int i = 0; i < object.length; i++) {
            dataToBeMerged.putAll(new JsonDataReader().getRowDataByID(fileName, object[i], String.valueOf(i+1),
                    true));
        }

        data.putAll(dataToBeMerged);
    }    public void mergeTestData(String fileName, String key, int index) throws IOException {
        // Convert Excel path to JSON path
        fileName = fileName.replace(".xlsx", ".json");
        String[] object = data.get(key).split(",");
        Map<String, String> dataToBeMerged = new HashMap<>();

        for (int i = 0; i < object.length; i++) {
            dataToBeMerged.putAll(new JsonDataReader().getRowDataByID(fileName, object[i],
                    index + "_" + (i+1), true));
        }

        data.putAll(dataToBeMerged);
    }
}
