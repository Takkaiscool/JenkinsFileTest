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

public class GetStatus {
    PropertiesFileReader propertiesFileReader;
    String username;
    String password;
    PageControls pageControls;
    DashboardPage dashboardPage;
    Login login;
    List<HashMap<String,String>> testData;
    @BeforeClass(groups = {"getStatus"})
    public void setup(){
        ExcelReadAndWrite creds=new ExcelReadAndWrite("./TestData/JenkinsData.xlsx","Creds");
        HashMap<String,String> credsData = creds.readExcelColumnWise().get(0);
        pageControls=new PageControls();

        username = credsData.get("Username");
        password = credsData.get("Password");
        pageControls.launchBrowser("chrome");
        pageControls.maximizeWindow();


    }

    @Test(dataProvider = "data", groups = {"getStatus"})
    public void test(Object e)  {
            TestManager.addInfo("Logged in to application as " + username);
            String url = testData.get((int) e).get("Jenkins URL");
            String buildNumber = testData.get((int) e).get("Build #");
            pageControls.loadURL(url);
            pageControls.implicitWait(5);
            login = new Login();
            dashboardPage = new DashboardPage();
            if (!dashboardPage.isBuildWithParametersDisplayed())
                login.login(username, password);
            String status;
            if(!buildNumber.equalsIgnoreCase("")) {
                status = dashboardPage.buildTheStatus(buildNumber);
                System.out.println(status);
            }else{
                status="No build executed";
            }

            ExcelReadAndWrite excelReadAndWrite=new ExcelReadAndWrite("./TestData/JenkinsData.xlsx","Data");
            excelReadAndWrite.setExcelRowData((int) e+1,7,status);

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

    @AfterClass( groups = {"getStatus"})
    public void tearup(){
        pageControls.quitBrowser();
    }

}
