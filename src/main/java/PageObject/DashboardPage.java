package PageObject;

import core.utils.ElementControls;
import core.utils.PageControls;
import core.utils.WebdriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class DashboardPage {

    @FindBy(xpath = "//a[@title='Build with Parameters']")
    public List<WebElement> buildWithParameters;

    @FindBy(xpath = "//div[div[text()='targetEnv']]//select")
    public  WebElement targetEnv;

    @FindBy(xpath = "//div[div[text()='testType']]//select")
    public  WebElement testType;

    @FindBy(xpath = "//div[div[text()='ALM_Status_Update']]//select")
    public  WebElement almStatusUpdate;

    @FindBy(xpath = "//div[div[text()='customInputs']]//input[2]")
    public  WebElement customInputs;

    @FindBy(xpath = "//button[text()='Build']")
    public  WebElement buildBtn;

    @FindBy(xpath = "(//div[@class='pane build-name']/a)[1]")
    public WebElement buildNumber;
    private PageControls pageControls;
    private ElementControls elementControls;
    private WebDriver driver;

    public DashboardPage(){
        driver= WebdriverManager.getDriver();
        pageControls=new PageControls();
        elementControls=new ElementControls();
        PageFactory.initElements(driver,this);
    }

    public boolean isBuildWithParametersDisplayed(){
        return buildWithParameters.size()>0;
    }

    public String buildTheData(String env,String exeType,String almUpdate,String customTag){
        elementControls.click(buildWithParameters.get(0));
        elementControls.waitTillVisible(testType,30);
        elementControls.selectDropDownByVisibleText(targetEnv,env);
        elementControls.selectDropDownByVisibleText(testType,exeType);
        elementControls.clearText(customInputs);
        elementControls.type(customInputs,customTag);
        if(!almUpdate.equalsIgnoreCase("")&&(exeType.equalsIgnoreCase("Regression")||exeType.equalsIgnoreCase("Custom"))){
            elementControls.selectDropDownByVisibleText(almStatusUpdate,almUpdate);
        }

        elementControls.click(buildBtn);
        elementControls.waitTillVisible(elementControls.find(By.xpath("//a[text()='Workspace']")),30 );
        return elementControls.getText(buildNumber);
    }

    public String buildTheStatus(String buildNumber){
        WebElement status = elementControls.find(By.xpath("//div[a[text()='"+buildNumber+"']]//span[@class='build-status-icon__outer']//*[local-name()='svg']"));
        String statusValue=elementControls.getAttribute(status,"tooltip");
        if(statusValue.contains("Success")){
            return "Pass";
        }else if(statusValue.contains("Failed")){
            return "Fail";
        }
        return "In-progress";
    }

}
