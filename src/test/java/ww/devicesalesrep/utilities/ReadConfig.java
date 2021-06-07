package ww.devicesalesrep.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class ReadConfig {
  Properties properties = new Properties();
  Properties dataproperty = new Properties();
  public static Logger logger;

  public ReadConfig() {

    logger = Logger.getLogger(TestUtility.class.getName());

    PropertyConfigurator.configure("Log4j.properties");
    File source = new File("./propertyfiles/environmentDetails.property");
    try {
      FileInputStream fileInputStream = new FileInputStream(source);
      properties.load(fileInputStream);
    } catch (Exception e) {
      System.out.println("Exception is" + e.getMessage());
    }

  }

  public String getBaseURL() {
    return properties.getProperty("baseURL");
  }

  public String getUserName(String username) {
    String adminUser = null;
    switch (username) {
      case "username1":
        adminUser = properties.getProperty("username1");
        break;
      case "incorrectUsername":
        adminUser = properties.getProperty("incorrectUsername");
        break;
      case "InvalidEmail":
        adminUser = properties.getProperty("InvalidEmail");
        break;
      default:
        System.out.println("*************Please enter correct UserName**************");
        break;
    }
    return adminUser;
  }

  public String getPassword(String password) {
    String adminPassword = null;
    switch (password) {
      case "password1":
        adminPassword = properties.getProperty("password1");
        break;
      case "incorrectPassword":
        adminPassword = properties.getProperty("incorrectPassword");
        break;
      default:
        System.out.println("*************Please enter correct Password**************");
        break;
    }
    return adminPassword;
  }


  public String getpackagetype(String Package) {
    String pachagetype = null;
    switch (Package) {
      case "bronze":
    	  pachagetype = properties.getProperty("packagetype1");
        break;
      case "silver":
    	  pachagetype = properties.getProperty("packagetype2");
        break;
      case "gold":
    	  pachagetype = properties.getProperty("packagetype3");
        break;
      case "HealthCare":
    	  pachagetype = properties.getProperty("packagetype4");
        break;
      default:
        System.out.println("*************Please select correct package**************");
        break;
    }
    return pachagetype;
  }


  public String getProduct(String prod) {
	  String producttype = null;
	    switch (prod) {
	      case "badges":
	    	  producttype = properties.getProperty("productType1");
	        break;
	      case "buildYourOwn":
	    	  producttype = properties.getProperty("productType2");
	        break;
	      case "Corprate":
	    	  producttype = properties.getProperty("productType3");
	        break;
	      default:
	        System.out.println("*************Select currect prduct**************");
	        break;
	    }
	    return producttype;
	  }

public String getBrowser() {
	
	return properties.getProperty("browser");
}
public String getCardDetails() {
	return properties.getProperty("card");
}
  }

  

