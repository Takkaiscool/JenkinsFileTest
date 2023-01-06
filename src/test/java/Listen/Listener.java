package Listen;

import Reports.ReportManager;
import Reports.TestManager;
import com.aventstack.extentreports.Status;
import core.utils.WebdriverManager;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class Listener implements ITestListener{
    private static String getTestMethodName(ITestResult iTestResult) {
        return iTestResult.getMethod().getConstructorOrMethod().getName();
    }
    @Override
    public void onStart(ITestContext iTestContext) {
        iTestContext.setAttribute("WebDriver", WebdriverManager.getDriver());

    }
    @Override
    public void onFinish(ITestContext iTestContext) {
        //Do tier down operations for ExtentReports reporting!
        ReportManager.extentReports.flush();
    }
    @Override
    public void onTestStart(ITestResult iTestResult) {
        TestManager.startTest(getTestMethodName(iTestResult),"Starting the test");
    }
    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        //ExtentReports log operation for passed tests.
        TestManager.getTest().log(Status.PASS, "Test passed");
    }
   /* @Override
    public void onTestFailure(ITestResult iTestResult) {
        //Get driver from BaseTest and assign to local webdriver variable.
        Object testClass = iTestResult.getInstance();
        WebDriver driver = WebdriverManager.getDriver();
        //Take base64Screenshot screenshot for extent reports
        String base64Screenshot =
                "data:image/png;base64," + ((TakesScreenshot) Objects.requireNonNull(driver)).getScreenshotAs(OutputType.BASE64);
        //ExtentReports log and screenshot operations for failed tests.
        TestManager.getTest().log(Status.FAIL, "Test Failed",
                TestManager.getTest().addScreenCaptureFromBase64String(base64Screenshot).getModel().getMedia().get(0));
    }*/
    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        //ExtentReports log operation for skipped tests.
        TestManager.getTest().log(Status.SKIP, "Test Skipped");
    }
    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
    }
}
