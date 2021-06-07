package pageObjects_WolfWorks;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import ww.devicesalesrep.utilities.GenericMethods;
import ww.devicesalesrep.utilities.ReadConfig;


public class LoginPagePageObjects extends GenericMethods {
  WebDriver driver;
  ReadConfig readConfig = new ReadConfig();

  public LoginPagePageObjects(WebDriver driver) {
    super(driver);
    PageFactory.initElements(driver, this);
    System.out.println("completed login bpage creation");
    this.driver = driver;
  }

  @FindBy(how = How.ID, using = "email")
  public WebElement txtemail;
  @FindBy(how = How.XPATH, using = "(//button[text()='CONTINUE'])[2]")
  public WebElement btnContinue;
  @FindBy(how = How.ID, using = "password")
  public WebElement txtpassword;
  @FindBy(how = How.XPATH, using = "(//button[text()='LOGIN'])[2]")
  public WebElement btnLogIn;
  @FindBy(how = How.XPATH, using = "//h2[@class='greeting-only']")
  public WebElement titleGreetings;
 

  public void loginPageAction(String email, String upassword) throws InterruptedException {
    driver.get(readConfig.getBaseURL());

    enterText(txtemail, email);
    Thread.sleep(2000);
    clickWebElement(btnContinue);
    Thread.sleep(2000);
    enterText(txtpassword, upassword);

    clickWebElement(btnLogIn);
    String greetings = ((WebElement) titleGreetings).getText();
    Assert.assertEquals(greetings, greetings);

  }

  
 


}
