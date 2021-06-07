package ww.devicesalesrep.utilities;

import java.util.List;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class GenericMethods {
	public static Logger logger;
	public WebDriver driver;
	public WebDriverWait wait;

	public GenericMethods(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver, TestUtility.waittime);
		// logger = Logger.getLogger("orx_SalesRep");
		logger = Logger.getLogger(GenericMethods.class.getName());

		PropertyConfigurator.configure("Log4j.properties");

	}

	public List<WebElement> getWebElements(By locator) {

		logger.info("getWebElementsList: " + locator.toString());
		List<WebElement> objElements = null;

		try {
			objElements = driver.findElements(locator);
		} catch (Exception e) {
			logger.info("getWebElementsList: " + e.getMessage());
		}

		return objElements;
	}

	public boolean waitForElementToBeVisible(WebElement btnContinue) throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(driver, TestUtility.waittime);
		try {
			wait.until(ExpectedConditions.visibilityOf((WebElement) btnContinue));
			return true;
		} catch (Exception e) {
			logger.info("Elememnt is not visible: " + e.getMessage());
			return false;
		}

	}

	public boolean waitForListOfElementsToBeVisible(List<WebElement> webelement) throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(driver, TestUtility.waittime);
		try {
			wait.until(ExpectedConditions.visibilityOf((WebElement) webelement));
			return true;
		} catch (Exception e) {
			logger.info("Elememnt is not visible: " + e.getMessage());
			return false;
		}

	}

	public void enterText(WebElement txtemail, String strInputText) throws InterruptedException {

		logger.info("enterText: " + txtemail.toString() + ", " + strInputText);
		if (waitForElementToBeVisible(txtemail)) {
			((WebElement) txtemail).sendKeys(strInputText);
		}
	}

	public void clickWebElement(WebElement btnContinue) throws InterruptedException {

		logger.info("clickWebElement: " + btnContinue.toString());
		if (waitForElementToBeVisible(btnContinue)) {
			((WebElement) btnContinue).click();
		}
	}

}
