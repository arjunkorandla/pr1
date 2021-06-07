package ww_salesrep.ww_salesrep;

import org.apache.log4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;



public class E2EWolfWorks extends BaseClass {
  // public static WebDriver driver;

  public E2EWolfWorks() throws InterruptedException {
    logger = Logger.getLogger(E2EWolfWorks.class.getName());

  }


  @AfterClass
  public void tearDown() {

   // driver.quit();
    }


  @Test(priority = 1)
  public void logIn() throws InterruptedException {

    logger.info("User is in the login page");
    loginPage.loginPageAction(userName, password);
    logger.info("User enterded UserName and Password");
    wolfworks.packageselect();
  }
  
 

}
