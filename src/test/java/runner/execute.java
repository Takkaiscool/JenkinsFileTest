package runner;

import Listen.Listener;
import Test.ExecuteBuild;
import Test.GetStatus;
import org.testng.TestNG;

public class execute {
    public static void main(String[] args) {
        TestNG testSuite = new TestNG();
        testSuite.addListener(new Listener());
        testSuite.setDefaultSuiteName("Jenkins");
        testSuite.setDefaultTestName("executeBuild");
        testSuite.setOutputDirectory("./Report");
        testSuite.setTestClasses(new Class[] { TestRunner.class});

        testSuite.run();
    }
}
