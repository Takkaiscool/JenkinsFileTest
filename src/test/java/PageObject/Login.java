package PageObject;

import core.utils.ElementControls;
import core.utils.WebdriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Login {

    @FindBy(name = "j_username")
    private WebElement username;

    @FindBy(name = "j_password")
    private WebElement password;

    @FindBy(name = "Submit")
    private WebElement loginBtn;

    private WebDriver driver;
    private ElementControls elementControls;
    public Login(){
        driver= WebdriverManager.getDriver();
        elementControls=new ElementControls();
        PageFactory.initElements(driver,this);
    }


    public void login(String username, String password){
        elementControls.type(this.username,username);
        elementControls.type(this.password,password);
        elementControls.click(this.loginBtn);
    }
}
