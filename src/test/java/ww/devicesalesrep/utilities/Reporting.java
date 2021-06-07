package ww.devicesalesrep.utilities;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class Reporting extends TestListenerAdapter {
  ExtentHtmlReporter htmlReporter;
  ExtentReports extent;
  ExtentTest logger;
  TestUtility testUtility;
  WebDriver driver;

  /*
   * public Reporting(WebDriver driver) throws InterruptedException { this.driver = driver;
   * testUtility = new TestUtility(driver); }
   */
  public void onStart(ITestContext testContext) {
    String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());// time stamp
    String repName = "Test-Report-" + timeStamp + ".html";
    htmlReporter =
        new ExtentHtmlReporter(System.getProperty("user.dir") + "/test-output/" + repName);// specify
                                                                                           // location
                                                                                           // of the
                                                                                           // report
    htmlReporter.loadXMLConfig(System.getProperty("user.dir") + "/extent-config.xml");

    extent = new ExtentReports();

    extent.attachReporter(htmlReporter);
    extent.setSystemInfo("Host name", "localhost");
    extent.setSystemInfo("Environemnt", "QA_Staging");
    extent.setSystemInfo("user", "Arjun");

    htmlReporter.config().setDocumentTitle("ORX Device Slaes Rep"); // Tile of report
    htmlReporter.config().setReportName("Functional/Regression Test Report"); // name of the report
    htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP); // location of the chart
    htmlReporter.config().setTheme(Theme.DARK);
  }

  public void onTestSuccess(ITestResult tr) {

    logger = extent.createTest(tr.getName()); // create new entry in th report
    logger.log(Status.PASS, MarkupHelper.createLabel(tr.getName(), ExtentColor.GREEN)); // send the
    try {
      testUtility = new TestUtility(driver);
    } catch (InterruptedException e2) {
      // TODO Auto-generated catch block
      e2.printStackTrace();
    }
    String path = null;
    try {
      path = testUtility.captureScreen();
    } catch (IOException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }

    try {
      // logger.log(Status.INFO, "orx Page opened" + logger.addScreenCaptureFromPath(path));
      // logger.log(Status.PASS, "Passed test 2" + logger.addScreenCaptureFromPath(path));
      logger.log(Status.PASS, "TEST CASE Passed IS " + tr.getName()); // to add name in extent
                                                                      // report
      logger.log(Status.PASS, "TEST CASE Exception IS " + tr.getThrowable()); // to add
                                                                              // error/exception in
                                                                              // extent report
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void onTestFailure(ITestResult tr) {
    try {
      testUtility = new TestUtility(driver);
    } catch (InterruptedException e2) {
      // TODO Auto-generated catch block
      e2.printStackTrace();
    }
    logger = extent.createTest(tr.getName()); // create new entry in th report
    logger.log(Status.FAIL, MarkupHelper.createLabel(tr.getName(), ExtentColor.RED));

    String path = null;
    try {
      path = testUtility.captureScreen();
    } catch (IOException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }

    try {
      // logger.log(Status.INFO, "orx Page opened" + logger.addScreenCaptureFromPath(path));
      logger.log(Status.FAIL, "Failed test 2" + logger.addScreenCaptureFromPath(path));
      logger.log(Status.FAIL, "TEST CASE FAILED IS " + tr.getName()); // to add name in extent
                                                                      // report
      logger.log(Status.FAIL, "TEST CASE Exception IS " + tr.getThrowable()); // to add
                                                                              // error/exception in
                                                                              // extent report
    } catch (Exception e) {
      e.printStackTrace();
    }
    // ******JIRA ticket creation on test fail******


    
  }

  public void onTestSkipped(ITestResult tr) {
    logger = extent.createTest(tr.getName()); // create new entry in th report
    logger.log(Status.SKIP, MarkupHelper.createLabel(tr.getName(), ExtentColor.ORANGE));
  }

  public void onFinish(ITestContext testContext) {
    extent.flush();
  }
}
