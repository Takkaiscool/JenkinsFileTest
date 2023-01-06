package Reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ReportManager {
    public static final ExtentReports extentReports = new ExtentReports();
    public synchronized static ExtentReports createExtentReports() {
        ExtentSparkReporter reporter = new ExtentSparkReporter("./reports/report.html");
        reporter.config().setReportName("Sip Automation Report");
        extentReports.attachReporter(reporter);
        return extentReports;
    }
}
