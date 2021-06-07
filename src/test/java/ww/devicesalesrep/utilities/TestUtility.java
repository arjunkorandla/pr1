package ww.devicesalesrep.utilities;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class TestUtility {
  public static Logger logger;
  WebDriver driver;
  public WebDriverWait wait;
  public JavascriptExecutor js;

  public TestUtility(WebDriver driver) throws InterruptedException {

    this.driver = driver;
    js = (JavascriptExecutor) driver;

    wait = new WebDriverWait(driver, 15);
    logger = Logger.getLogger(TestUtility.class.getName());

    PropertyConfigurator.configure("Log4j.properties");

  }

  public static long PAGE_LOAD_TIMEOUT = 15;
  public static long IMPLICIT_WAIT = 15;
  public static long waittime = 60;



  public void captureScreen(String tname) throws IOException {
    String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
    TakesScreenshot ts = (TakesScreenshot) driver;
    File source = ts.getScreenshotAs(OutputType.FILE);
    File target =
        new File(System.getProperty("user.dir") + "/screenshots/" + tname + dateName + ".png");
    FileUtils.copyFile(source, target);
    System.out.println("Screenshot taken");
  }

  public String captureScreen() throws IOException {
    TakesScreenshot screen = (TakesScreenshot) driver;
    File src = screen.getScreenshotAs(OutputType.FILE);
    String dest =
        System.getProperty("user.dir") + "//screenshots//" + getCurrentDateAndTime() + ".png";
    File target = new File(dest);
    FileUtils.copyFile(src, target);
    return dest;
  }

  public String getCurrentDateAndTime() {
    String str = null;
    try {
      DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss:SSS");
      Date date = new Date();
      str = dateFormat.format(date);
      str = str.replace(" ", "").replaceAll("/", "").replaceAll(":", "");
    } catch (Exception e) {

    }
    return str;
  }

  public void scrollToSpecified(WebElement element) {
    js.executeScript("arguments[0].scrollIntoView();", element);
  }

  public void scrollTillEnd() {
    js.executeAsyncScript("window.scrollBy(0,document.body.scrollHeight)");

  }



  public void verifyToastMessage(String expectedToastMessage) {
    logger.info("The expected logger message is:  " + expectedToastMessage);
    wait.until(ExpectedConditions.presenceOfElementLocated(
        By.xpath("//p[contains(text(),'" + expectedToastMessage + "')]")));
    WebElement toastMessageElement =
        driver.findElement(By.xpath("//p[contains(text(),'" + expectedToastMessage + "')]"));
    String actualToastMessage = toastMessageElement.getText();
    logger.info("Actual Toast message is :  " + actualToastMessage);
    Assert.assertEquals(actualToastMessage.trim(), expectedToastMessage.trim(),
        "*****The toast message is not matching****");
  }

  }




