package ww.devicesalesrep.utilities;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.annotations.Parameters;
import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverInstance {
  public WebDriver driver;
  public ReadConfig readConfig = new ReadConfig();
  public String browser = readConfig.getBrowser();
  public String url = readConfig.getBaseURL();
  public TestUtility testUtility;

  @Parameters("browser")
  public WebDriver browserInitilization() throws InterruptedException {

    if (driver == null) {
      System.out.println("********Entered into Driver Creation*****Driver is Null*********");

      switch (browser) {
        case "chrome":
          WebDriverManager.chromedriver().setup();
          ChromeOptions options = new ChromeOptions();
          options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200",
             "--ignore-certificate-errors");
          driver = new ChromeDriver(options);
           //driver = new ChromeDriver();
          break;
        case "firefox":
          WebDriverManager.firefoxdriver().setup();
          driver = new FirefoxDriver();
          break;
        case "safari":
          driver = new SafariDriver();
          break;
        case "InternetExplorer":
          WebDriverManager.iedriver().setup();
          driver = new InternetExplorerDriver();
          break;
        case "edge":
          WebDriverManager.edgedriver().setup();
          driver = new EdgeDriver();
          break;
        default:
          System.out.println("*************Please enter correct Browser**************");
          break;
      }

      driver.manage().window().maximize();
      driver.manage().deleteAllCookies();
      driver.manage().timeouts().pageLoadTimeout(testUtility.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
      driver.manage().timeouts().implicitlyWait(testUtility.IMPLICIT_WAIT, TimeUnit.SECONDS);
      driver.get(url);
      return driver;
    } else {
      return driver;
    }
  }
}
