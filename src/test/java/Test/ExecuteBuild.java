package Test;

import Listen.Listener;
import PageObject.DashboardPage;
import PageObject.Login;
import Reports.TestManager;
import core.utils.ExcelReadAndWrite;
import core.utils.PageControls;
import core.utils.PropertiesFileReader;
import org.testng.annotations.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Listeners(Listener.class)

public class ExecuteBuild {
    PropertiesFileReader propertiesFileReader;
    String username;
    String password;
    PageControls pageControls;
    DashboardPage dashboardPage;
    Login login;
    List<HashMap<String,String>> testData;
    @BeforeClass(groups = {"executeBuild"})
    public void setup(){
        ExcelReadAndWrite creds=new ExcelReadAndWrite("./TestData/JenkinsData.xlsx","Creds");
        HashMap<String,String> credsData = creds.readExcelColumnWise().get(0);
        pageControls=new PageControls();

        username = credsData.get("Username");
        password = credsData.get("Password");
        pageControls.launchBrowser("chrome");
        pageControls.maximizeWindow();


    }


    @Test(dataProvider = "data", groups = {"executeBuild"})
    public void test(Object e)  {
            TestManager.addInfo("Logged in to application as " + username);
            String url = testData.get((int) e).get("Jenkins URL");
            String env = testData.get((int) e).get("Environment");
            String customTag = testData.get((int) e).get("CustomTag");
            String exeType = testData.get((int) e).get("Execution Type");
            String alm = testData.get((int) e).get("ALM (Yes/No)");
            pageControls.loadURL(url);
            pageControls.implicitWait(5);
            login = new Login();
            dashboardPage = new DashboardPage();
            if (!dashboardPage.isBuildWithParametersDisplayed())
                login.login(username, password);

            String buildNumber = dashboardPage.buildTheData(env, exeType, alm, customTag);
            System.out.println(buildNumber);

            ExcelReadAndWrite excelReadAndWrite=new ExcelReadAndWrite("./TestData/JenkinsData.xlsx","Data");
            excelReadAndWrite.setExcelRowData((int) e+1,6,buildNumber);
            excelReadAndWrite.setExcelRowData((int) e+1,7,"");

    }

    @DataProvider(name = "data")
    public Object[] data(){
        ExcelReadAndWrite excelReadAndWrite=new ExcelReadAndWrite("./TestData/JenkinsData.xlsx","Data");
        testData=excelReadAndWrite.readExcelRowWise();
        List<Object> data=new ArrayList<>();
        for(int i=0;i< testData.size();i++){
            if(testData.get(i).get("Execute (Y/N)").equalsIgnoreCase("y")){
                data.add(i);
            }
        }
        Object d[]=new Object[data.size()];
        for(int i=0;i< data.size();i++){
                d[i] = data.get(i);
        }
        return d;
    }

    @AfterClass(groups = {"executeBuild"})
    public void tearup(){
        pageControls.quitBrowser();
    }

}
