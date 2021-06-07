package pageObjects_WolfWorks;

import java.util.List;
import org.apache.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import ww.devicesalesrep.utilities.GenericMethods;
import ww.devicesalesrep.utilities.ReadConfig;
import ww.devicesalesrep.utilities.TestUtility;

public class WolfWorksE2E extends GenericMethods {
	ReadConfig readConfig = new ReadConfig();
	WebDriver driver;
	public static Logger logger;
	TestUtility testUtility;

	public WolfWorksE2E(WebDriver driver) throws InterruptedException {
		super(driver);
		PageFactory.initElements(driver, this);
		this.driver = driver;
		testUtility = new TestUtility(driver);
		logger = Logger.getLogger(WolfWorksE2E.class.getName());

	}

	@FindBy(how = How.XPATH, using = "(//button[text()='UPGRADE YOUR STATUS'])")
	public WebElement btnUpgradeStatus;

	@FindBy(how = How.PARTIAL_LINK_TEXT, using = "BADGES")
	public WebElement linkBadges;

	@FindBy(how = How.PARTIAL_LINK_TEXT, using = "BUILD YOUR OWN")
	public WebElement linkBuildYourOwn;

	@FindBy(how = How.PARTIAL_LINK_TEXT, using = "CORPORATE VERIFICATIONS")
	public WebElement linkCORPORATE;

	@FindBy(how = How.XPATH, using = "(//button[text()='SELECT THIS BADGE'])[1]")
	public WebElement linkBronze;

	@FindBy(how = How.XPATH, using = "(//button[text()='SELECT THIS BADGE'])[2]")
	public WebElement linkSilver;

	@FindBy(how = How.XPATH, using = "(//button[text()='SELECT THIS BADGE'])[3]")
	public WebElement linkGold;

	@FindBy(how = How.XPATH, using = "(//button[text()='SELECT THIS BADGE'])[4]")
	public WebElement linkHealthCare;

	@FindBy(how = How.XPATH, using = "(//button[text()='ADD'])[1]")
	public WebElement clkadd1;

	@FindBy(how = How.XPATH, using = "(//button[text()='ADD'])[2]")
	public WebElement clkadd2;

	@FindBy(how = How.XPATH, using = "(//button[text()='SKIP TO CHECKOUT'])")
	public WebElement bthSkipToCheckOut;

	@FindBy(how = How.XPATH, using = "(//span[@class='remove'])[1]")
	public WebElement btnRemove1;
	@FindBy(how = How.XPATH, using = "(//span[@class='remove'])[2]")
	public WebElement btnRemove;

	@FindBy(how = How.XPATH, using = "(//div[@role='alert'])")
	public WebElement alertUpdatedToastMessage;
	
	@FindBy(how = How.XPATH, using = "//button[text()='CHECK OUT']")
	public WebElement clickCheckOut;
	@FindBy(how = How.XPATH, using = "(//h2['Check out'])[1]")
	public WebElement titleCheckOut;
	
	@FindBy(how = How.XPATH, using = "//iframe[@title='Secure card payment input frame']")
	public WebElement cardDetails;
	
	@FindBy(how = How.XPATH, using = "//button[text()='COMPLETE ORDER']")
	public WebElement btnCompleteOrder;
	
	
	
	public void dashboard() throws InterruptedException{
		waitForElementToBeVisible(btnUpgradeStatus);
		Thread.sleep(5000);
		clickWebElement(btnUpgradeStatus);
		
		
	}
	public void productTypes(String product) throws InterruptedException{
		
		String type = product;
		if(type.equalsIgnoreCase("badges") ) {
			clickWebElement(linkBronze);
			
		}
		else if(type.equalsIgnoreCase("buildYourOwn")) {
			clickWebElement(linkBuildYourOwn);
		}
		else if(type.equalsIgnoreCase("corporate")) {
			clickWebElement(linkCORPORATE);
		}
		else{
			System.out.println("************Nothing is clicked**************************");
		}
		
	}
	public void selectPackage(String packagetype) throws InterruptedException {
		 
		String type = packagetype;
		if(type.equalsIgnoreCase("bronze") ) {
			clickWebElement(linkBronze);
			
		}
		else if(type.equalsIgnoreCase("silver")) {
			clickWebElement(linkSilver);
		}
		else if(type.equalsIgnoreCase("gold")) {
			clickWebElement(linkGold);
		}
		else if(type.equalsIgnoreCase("HealthCare")) {
			clickWebElement(linkHealthCare);
		}
		else{
			System.out.println("************Nothing is clicked**************************");
		}
		Thread.sleep(3000);
	}
		
	public void packageselect() throws InterruptedException {
		dashboard();
		productTypes(readConfig.getProduct("buildYourOwn"));
		selectPackage(readConfig.getpackagetype("silver"));
		clickWebElement(clkadd1);
		clickWebElement(clkadd2);
		testUtility.scrollToSpecified(bthSkipToCheckOut);
		waitForElementToBeVisible(bthSkipToCheckOut);
		clickWebElement(bthSkipToCheckOut);
		clickWebElement(btnRemove);
		clickWebElement(btnRemove1);
		Assert.assertEquals(alertUpdatedToastMessage.getText(), "You cannot modify the cart at the moment.");
		clickWebElement(clickCheckOut);
		waitForElementToBeVisible(titleCheckOut);
		
		enterText(cardDetails,readConfig.getCardDetails());
		clickWebElement(btnCompleteOrder);
		
	}

	// Your cart was updated.
	// You cannot modify the cart at the moment.

}
