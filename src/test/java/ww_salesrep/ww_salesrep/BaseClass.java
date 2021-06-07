package ww_salesrep.ww_salesrep;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

import pageObjects_WolfWorks.LoginPagePageObjects;
import pageObjects_WolfWorks.WolfWorksE2E;
import ww.devicesalesrep.utilities.DriverInstance;
import ww.devicesalesrep.utilities.ReadConfig;
import ww.devicesalesrep.utilities.Reporting;
import ww.devicesalesrep.utilities.TestUtility;


public class BaseClass {
  public WebDriver driver;


  public LoginPagePageObjects loginPage;
  public WolfWorksE2E wolfworks;
  public TestUtility testUtility;
  public ReadConfig readConfig = new ReadConfig();
  public String url = readConfig.getBaseURL();
  public Reporting reporting;
  public String browser = readConfig.getBrowser();

  public String userName = readConfig.getUserName("username1");
  public String password = readConfig.getPassword("password1");
  public String wrongUserName = readConfig.getUserName("incorrectUsername");
  public String wrongPassword = readConfig.getPassword("incorrectPassword");
  public String invalidEmail = readConfig.getUserName("InvalidEmail");


  public Logger logger;

  public static ExtentHtmlReporter htmlReporter;
  public static ExtentReports extent;
  public static ExtentTest loggerExtent;


  public BaseClass() throws InterruptedException {


    logger = Logger.getLogger("orx_SalesRep");
    PropertyConfigurator.configure("Log4j.properties");

  }

  /*
   * driver = new DriverInstance().browserInitilization(); loginPage = new
   * LoginPagePageObjects(driver); System.out.println("after login"); adminDashboard = new
   * AdminDashboard(driver); System.out.println("after dashboard"); addProcedure = new
   * AddProcedurePage(driver); selectProcedure = new SelectFacilityPageObjects(driver);
   * System.out.println("after facility"); selectSurgeon = new SelectASurgeonPage(driver); addVendor
   * = new AddVendorPage(driver); System.out.println("after vendor"); confirmInformation = new
   * ConfirmInformationPage(driver); quickCase = new QuickCasePage(driver); pso = new
   * PostSurgeryOverviewPageObjects(driver); shareBill = new ShareBillPageObjects(driver);
   * emailFacility = new GmailFacilityPageObjects(driver); emailVendor = new
   * EmailVendorPageObjects(driver); // reporting = new Reporting(driver);
   */


  @BeforeSuite
  public void beforeSuite() throws InterruptedException {
    logger = Logger.getLogger(BaseClass.class.getName());


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

  @AfterMethod
  public void afterMethod(ITestResult tr) {


    System.out.println("****************Invoking After Method***************");

    if (tr.getStatus() == ITestResult.SUCCESS) {
      if (null == extent) {
        System.out.println("#########extent is Null##########");
      }
      loggerExtent = extent.createTest(tr.getName()); // create new entry in th report
      loggerExtent.log(Status.PASS, MarkupHelper.createLabel(tr.getName(), ExtentColor.GREEN));
      loggerExtent.log(Status.PASS, "TEST CASE Passed IS " + tr.getName()); // to add name in

    } else if (tr.getStatus() == ITestResult.FAILURE) {

      loggerExtent = extent.createTest(tr.getName()); // create new entry in th report
      loggerExtent.log(Status.FAIL, MarkupHelper.createLabel(tr.getName(), ExtentColor.RED));

      String path = null;
      try {
        path = testUtility.captureScreen();
      } catch (IOException e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
      }

      try {
        loggerExtent.log(Status.FAIL,
            "Failed test 2" + loggerExtent.addScreenCaptureFromPath(path));
        loggerExtent.log(Status.FAIL, "TEST CASE FAILED IS " + tr.getName()); // to add name in
                                                                              // extent
        // report
        loggerExtent.log(Status.FAIL, "TEST CASE Exception IS " + tr.getThrowable()); // to add
        // error/exception in
        // extent report
      } catch (Exception e) {
        e.printStackTrace();
      }

    } else {

      loggerExtent = extent.createTest(tr.getName()); // create new entry in th report
      loggerExtent.log(Status.SKIP, MarkupHelper.createLabel(tr.getName(), ExtentColor.ORANGE));

    }
    if (null == extent) {
      System.out.println("#########at end extent is Null##########");
    } else {
      System.out.println("#########at end extent is Not Null##########");
    }
  }

  @AfterSuite
  public void afterSuite(ITestContext testContext) {
    System.out.println("This is after Suite " + testContext.getClass().getName());
    extent.flush();
    //driver.quit();
  }

  @BeforeClass
  public void setUp() throws InterruptedException {

    driver = new DriverInstance().browserInitilization();
    System.out.println("after log4j");
    logger.info("Before class is invoked**********");
    loginPage = new LoginPagePageObjects(driver);
    wolfworks = new WolfWorksE2E(driver);
    reporting = new Reporting();
    testUtility = new TestUtility(driver);
    System.out.println(" ater objects");

  }

  public WebDriver getDriver() {
    return driver;
  }

  public void setDriver(WebDriver driver) {
    this.driver = driver;
  }


}
