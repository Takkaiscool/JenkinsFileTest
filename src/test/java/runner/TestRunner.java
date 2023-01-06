package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/test/resources/features/",glue = {"stepDefinitions"},
        plugin = {
                "summary", "pretty", "html:TestReports/CucumberReport/cucumber.html", "json:target/cucumber-report/cucumber.json"})
public class TestRunner extends AbstractTestNGCucumberTests {


}
