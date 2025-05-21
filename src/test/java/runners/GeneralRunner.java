package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Test
@CucumberOptions(
        features = "src/test/java/features",
        glue = "steps", // specifies the package containing step definitions
        tags = "@Home", // Run only TC_ID_0010
        plugin = {"io.qameta.allure.cucumber6jvm.AllureCucumber6Jvm"} // enables Allure reporting for Cucumber
)
public class GeneralRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}

