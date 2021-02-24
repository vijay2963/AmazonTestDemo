package utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;

public class BaseClass  {

	public static List<String> details = new ArrayList<String>();
	public static List<String> productCartdetails = new ArrayList<String>();
	public static String ProductPageTile;
	public static AndroidDriver<MobileElement> driver;
	public static DesiredCapabilities capabilities;
	public static Properties prop;
	public static ExtentHtmlReporter htmlReporter;
	public static ExtentReports extent;
	public static ExtentTest test;


	/**
	 * @author Vijay Saravanan Sethu
	 * Description: Base class has a constructor and initializes the object.
	 * 
	 */
	public BaseClass() {

		capabilities = new DesiredCapabilities();
		prop=new Properties();
	}


	/**
	 * @author Vijay Saravanan Sethu
	 * Description: To initialize the desired capabilities key and value pair to initialize the appium driver.
	 * 
	 */
	public static AndroidDriver<MobileElement> desiredCapabilities() {
		prop=loadPropertyFile(System.getProperty("user.dir")+"\\src\\main\\resources\\configFiles\\config.properties");
		capabilities.setCapability("deviceName", prop.getProperty("deviceName"));
		capabilities.setCapability("udid", prop.getProperty("udid"));
		capabilities.setCapability("automationName", prop.getProperty("automationName"));
		capabilities.setCapability("platformVersion", prop.getProperty("platformVersion"));
		capabilities.setCapability("platformName", prop.getProperty("platformName"));
		capabilities.setCapability("app", prop.getProperty("app"));

		try {
			driver = new AndroidDriver<MobileElement>(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
			driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} 
		return driver;
	}

	/**
	 * @author Vijay Saravanan Sethu
	 * Description: To store the list of products in the list and click on the selected element.
	 * Attribute: elements to store all the products, locator type and input the data
	 */
	public static void storeDropDownClick(String elementType, String locator, String input) {
		List<MobileElement> elements;
		// To click on the suitable product after the product is displayed on the screen
		elements = driver.findElementsById(locator);
		if(elementType.equalsIgnoreCase("id")) {
			for (int i = 0; i < elements.size(); i++) {
				System.out.println(elements.get(i).getText());
				if (elements.get(i).getText().equalsIgnoreCase(input)) {
					elements.get(i).click();
					break;
				}
			}
		}
		// To click on the suitable product after the product is displayed on the screen
		else if(elementType.equalsIgnoreCase("xpath")) {
			elements = driver.findElementsById(locator);
			for (int i = 0; i < elements.size(); i++) {
				System.out.println(elements.get(i).getText());
				if (elements.get(i).getText().equalsIgnoreCase(input)) {
					elements.get(i).click();
					break;
				}
			}
		}
	}

	/**
	 * @author Vijay Saravanan Sethu
	 * Description: To store the text from the list.
	 * Attribute: elements to store all the products, locator type and input the data
	 */
	public static List<String> storeTheText(String elementType, String locator,String input) {
		List<MobileElement> elements;
		if(elementType.equalsIgnoreCase("id")) {
			elements = driver.findElementsById(locator);

			for (int i = 0; i < elements.size(); i++) {
				System.out.println(elements.get(i).getText());
				if (elements.get(i).getText().equalsIgnoreCase(input)) {
					String description = elements.get(i).getText();
					details.add(description);
					break;
				}
			}
		}
		else if(elementType.equalsIgnoreCase("xpath")) {

			elements = driver.findElementsByXPath(locator);

			for (int i = 0; i < elements.size(); i++) {
				System.out.println(elements.get(i).getText());
				if (elements.get(i).getText().equalsIgnoreCase(input)) {
					String description = elements.get(i).getText();
					details.add(description);
					break;
				}
			}
		}
		return details;
	}
	/**
	 * @author Vijay Saravanan Sethu
	 * Description: To compare the strings.
	 * Attribute: actual , expected which compares both the strings
	 */
	public static void stringComparison(String actual, String expected) {

		if (actual.contains(actual)) {
			Assert.assertEquals(actual,expected);
			test.log(Status.PASS, actual +","+expected+" "+ "string equals as expected");
			try {
				test.addScreenCaptureFromPath(captureScreenShot(driver));
			} catch (IOException e) {
				System.out.println("Failure");
				test.log(Status.FAIL, "Unable to comapare the string");
				try {
					test.addScreenCaptureFromPath(captureScreenShot(driver));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}

		} else {
			System.out.println("Failure");
			test.log(Status.FAIL, "Unable to comapare the string");
			try {
				test.addScreenCaptureFromPath(captureScreenShot(driver));
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			Assert.fail();
		}
	}

	/**
	 * @author Vijay Saravanan Sethu
	 * Description: To initialize the variables for extent reports.
	 * 
	 */
	public static void extentReport() {
		try {
			htmlReporter=new ExtentHtmlReporter(System.getProperty("user.dir")+"\\reports\\myreport.html");
			htmlReporter.config().setDocumentTitle("Appium Automation Report");
			htmlReporter.config().setReportName("Functional Testing the mobile app");
			htmlReporter.config().setTheme(Theme.STANDARD);
			extent=new ExtentReports();
			extent.attachReporter(htmlReporter);
			extent.setSystemInfo("OS", "Android");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * @author Vijay Saravanan Sethu
	 * Description: To exit the driver and to generate the html report.
	 * 
	 */
	public static void flush() {
		try {
			extent.flush();
			Thread.sleep(2000);
			driver.quit();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author Vijay Saravanan Sethu
	 * Description: To load the file using File InputStream.
	 * Attribute: Path-To share the file path 
	 */
	public static Properties loadPropertyFile(String path){
		try
		{
			FileInputStream fs=new FileInputStream(path);
			prop.load(fs);
		}
		catch (IOException e) {
			e.printStackTrace();	
		}
		return prop;
	}

	/**
	 * @author Vijay Saravanan Sethu
	 * Description: To Wait the untill the element is clickable.
	 * Attribute: Timeout second, locator type, locator
	 */
	public static void waitUntilElementToBeClickable(long timeoutSeconds,String elementType, String identifier) {
		try {
			Wait<AndroidDriver<MobileElement>> wait = new FluentWait<>(driver)
					.withTimeout(timeoutSeconds, TimeUnit.SECONDS)
					.pollingEvery(5, TimeUnit.SECONDS)
					.ignoring(NoSuchElementException.class);
			if(elementType.equalsIgnoreCase("id")) {
				wait.until(ExpectedConditions.elementToBeClickable(By.id(identifier)));
				test.log(Status.PASS, "To wait untill the element to be clickable");
				test.addScreenCaptureFromPath(captureScreenShot(driver));
			}
			else if(elementType.equalsIgnoreCase("xpath")) {
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath(identifier)));
				test.log(Status.PASS, "To wait untill the element to be clickable");
				test.addScreenCaptureFromPath(captureScreenShot(driver));
			}
		} catch (Exception e) {
			test.log(Status.FAIL, "Unable to click on the element");
			try {
				test.addScreenCaptureFromPath(captureScreenShot(driver));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

	/**
	 * @author Vijay Saravanan Sethu
	 * Description: To Wait the untill the element is present.
	 * Attribute: Timeout second, locator type, locator
	 */
	public static void waitUntilElementPresent(String elementType,String identifier, long timeoutSeconds) {
		try {
			Wait<AndroidDriver<MobileElement>> wait = new FluentWait<>(driver)
					.withTimeout(timeoutSeconds, TimeUnit.SECONDS)
					.pollingEvery(5, TimeUnit.SECONDS).
					ignoring(NoSuchElementException.class);
			if(elementType.equalsIgnoreCase("id")) {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(identifier)));
				test.log(Status.PASS, "To wait untill the element is present");
				test.addScreenCaptureFromPath(captureScreenShot(driver));
			}
			else if(elementType.equalsIgnoreCase("xpath")) {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(identifier)));	
				test.log(Status.PASS, "To wait untill the element is present");
				test.addScreenCaptureFromPath(captureScreenShot(driver));
			}
		} catch (Exception e) {
			test.log(Status.FAIL, "Unable to find the element");
			try {
				test.addScreenCaptureFromPath(captureScreenShot(driver));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

	/**
	 * @author Vijay Saravanan Sethu
	 * Description: To Wait the untill the element is present.
	 * Attribute: Timeout second, locator type, locator
	 */
	public static void waitUntilElementPresent( By by,long timeoutSeconds ) {
		try {
			Wait<AndroidDriver<MobileElement>> wait = new FluentWait<>(driver)
					.withTimeout(timeoutSeconds, TimeUnit.SECONDS)
					.pollingEvery(5, TimeUnit.SECONDS).
					ignoring(NoSuchElementException.class);
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			test.log(Status.PASS, "To wait untill the element is present");
			test.addScreenCaptureFromPath(captureScreenShot(driver));
		} catch (Exception e) {
			test.log(Status.FAIL, "Unable to wait until the element is present");
			try {
				test.addScreenCaptureFromPath(captureScreenShot(driver));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

	/**
	 * @author Vijay Saravanan Sethu
	 * Description: To Wait the untill the element is clickable.
	 * Attribute: Timeout second, locator type, locator
	 */
	public static void waitUntilElementToBeClickable(By by,long timeoutSeconds ) {
		try {
			Wait<AndroidDriver<MobileElement>> wait = new FluentWait<>(driver)
					.withTimeout(timeoutSeconds, TimeUnit.SECONDS)
					.pollingEvery(5, TimeUnit.SECONDS)
					.ignoring(NoSuchElementException.class);
			wait.until(ExpectedConditions.elementToBeClickable(by));
			test.log(Status.PASS, "To wait untill the element to be clickable");
			test.addScreenCaptureFromPath(captureScreenShot(driver));
		} catch (Exception e) {
			test.log(Status.FAIL, "Unable to wait untill the element clickable");
			try {
				test.addScreenCaptureFromPath(captureScreenShot(driver));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

	/**
	 * @author Vijay Saravanan Sethu
	 * Description: To tap the element.
	 * Attribute: mobileElement, testStep
	 */
	public static void tap(MobileElement elementTap, String teststep)  {
		try {
			TouchAction touchAction = new TouchAction(driver)
					.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(5)))
					.tap(ElementOption.element(elementTap))
					.perform();
			test.log(Status.PASS, teststep);
			test.addScreenCaptureFromPath(captureScreenShot(driver));

		} catch (Exception e) {
			test.log(Status.FAIL, teststep);
			e.printStackTrace();
			try {
				test.addScreenCaptureFromPath(captureScreenShot(driver));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * @author Vijay Saravanan Sethu
	 * Description: To scroll the element in vertical.
	 * 
	 */
	public static void scrollvertical() {
		try {
			Dimension screenSize = driver.manage().window().getSize();
			int startx = (int) (screenSize.width / 2);
			int endy = (int) (screenSize.height * 0.9);
			int starty = (int) (screenSize.height * 0.4);

			TouchAction touchAction = new TouchAction(driver)
					.press(PointOption.point(startx, endy))
					.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(5)))
					.moveTo(PointOption.point(startx, starty))
					.release().perform();
			test.log(Status.PASS, "Able to scroll the page in vertical");
			test.addScreenCaptureFromPath(captureScreenShot(driver));
		} catch (Exception e) {
			test.log(Status.FAIL, "Unable to scroll vertical");
			try {
				test.addScreenCaptureFromPath(captureScreenShot(driver));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

	/**
	 * @author Vijay Saravanan Sethu
	 * Description: To swipe the element.
	 * Attribute: locator
	 */
	public void swipeToElement(String property) {
		int count=0;
		while (true) {

			if (verifyElement(property)) {
				break;
			}

			scrollvertical();
			count++;

			if(count>10) {
				Assert.assertTrue(false, "Element Not Visible");
			}
		}
	}

	/**
	 * @author Vijay Saravanan Sethu
	 * Description: To verify the element.
	 * Attribute: locator
	 */
	public boolean verifyElement(String property) {
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		boolean present = true;
		try {
			driver.findElement(By.xpath(property));
			return present;

		} catch (Exception e) {
			present = false;
			return present;
		}
	}

	/**
	 * @author Vijay Saravanan Sethu
	 * Description: To click on the element.
	 * Attribute: locator, locator type,timeoutSeconds, testStep
	 */
	public static void click(String elementType,String identifier, long timeoutSeconds,String teststep) 
	{
		try
		{
			if(elementType.equalsIgnoreCase("id")) {
				waitUntilElementToBeClickable(timeoutSeconds , elementType, identifier);
				driver.findElement(By.id(identifier)).click();
				test.log(Status.PASS, teststep);
				test.addScreenCaptureFromPath(captureScreenShot(driver));
			}
			else if(elementType.equalsIgnoreCase("xpath")) {
				waitUntilElementToBeClickable(timeoutSeconds, elementType,identifier);
				driver.findElement(By.xpath(identifier)).click();
				test.log(Status.PASS, teststep);
				try {
					test.addScreenCaptureFromPath(captureScreenShot(driver));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		catch (Exception e) {
			test.log(Status.FAIL, teststep);
			try {
				test.addScreenCaptureFromPath(captureScreenShot(driver));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();	
		}
	}

	/**
	 * @author Vijay Saravanan Sethu
	 * Description: To click on the element.
	 * Attribute: locator, locator type,timeoutSeconds, testStep
	 */
	public static void clickImplicitWait(String elementType,String identifier, long timeoutSeconds,String teststep) 
	{
		try
		{
			if(elementType.equalsIgnoreCase("id")) {
				driver.manage().timeouts().implicitlyWait(timeoutSeconds, TimeUnit.SECONDS);
				driver.findElement(By.id(identifier)).click();
				test.log(Status.PASS, teststep);
				test.addScreenCaptureFromPath(captureScreenShot(driver));
			}
			else if(elementType.equalsIgnoreCase("xpath")) {
				driver.manage().timeouts().implicitlyWait(timeoutSeconds, TimeUnit.SECONDS);
				driver.findElement(By.xpath(identifier)).click();
				test.log(Status.PASS, teststep);
				test.addScreenCaptureFromPath(captureScreenShot(driver));
			}
		}
		catch (Exception e) {
			test.log(Status.FAIL, teststep);
			try {
				test.addScreenCaptureFromPath(captureScreenShot(driver));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();	
		}	
	}

	/**
	 * @author Vijay Saravanan Sethu
	 * Description: To enter the input in the text box.
	 * Attribute: locator, locator type, inputData, timeoutSeconds, testStep
	 */
	public static void sendKeys(String elementType,String identifier,String input,long timeoutSeconds,String teststep) 
	{
		try
		{
			if(elementType.equalsIgnoreCase("id")) {
				waitUntilElementPresent(elementType,identifier,timeoutSeconds);
				driver.findElement(By.id(identifier)).sendKeys(input);
				test.log(Status.PASS, teststep);
				test.addScreenCaptureFromPath(captureScreenShot(driver));

			}
			else if(elementType.equalsIgnoreCase("xpath")) {
				waitUntilElementPresent(elementType,identifier,timeoutSeconds );
				driver.findElement(By.xpath(identifier)).sendKeys(input);
				test.log(Status.PASS, teststep);
				test.addScreenCaptureFromPath(captureScreenShot(driver));
			}
		}
		catch (Exception e) {
			test.log(Status.FAIL, teststep);
			e.printStackTrace();	
		}
	}

	/**
	 * @author Vijay Saravanan Sethu
	 * Description: To get the text from the element.
	 * Attribute: locator, locator type, inputData, timeoutSeconds, testStep
	 */
	public String getText(String elementType,String identifier,long timeoutSeconds,String teststep) 
	{
		String text="";
		try
		{
			if(elementType.equalsIgnoreCase("id")) {
				waitUntilElementPresent(elementType,identifier,timeoutSeconds);
				text=	driver.findElement(By.id(identifier)).getText();
				test.log(Status.PASS, teststep);
				test.addScreenCaptureFromPath(captureScreenShot(driver));

			}
			else if(elementType.equalsIgnoreCase("xpath")) {
				waitUntilElementPresent(elementType,identifier,timeoutSeconds );
				text=	driver.findElement(By.xpath(identifier)).getText();
				test.log(Status.PASS, teststep);
				test.addScreenCaptureFromPath(captureScreenShot(driver));

			}
		}
		catch (Exception e) {
			test.log(Status.FAIL, teststep);
			try {
				test.addScreenCaptureFromPath(captureScreenShot(driver));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return text;
	}

	/**
	 * @author Vijay Saravanan Sethu
	 * Description: To capture the screenshot.
	 * Attribute: driver- To initialize the driver
	 */
	public static String captureScreenShot(AndroidDriver<MobileElement> driver )  {
		String destination="";
		try {
			TakesScreenshot screenshot= (TakesScreenshot)driver;
			File source=screenshot.getScreenshotAs(OutputType.FILE);
			destination=System.getProperty("user.dir")+"\\screenshots\\"+System.currentTimeMillis()+".jpeg";
			File target= new File(destination);
			FileUtils.copyFile(source, target);
			return destination;
		} catch (WebDriverException e) {
			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		return destination;
	}

	/**
	 * @author Vijay Saravanan Sethu
	 * Description: To mark the test step as pass,if the locator connected on the element .
	 * 
	 */
	public void extentReportPass(String input){
		String failScreenShot=  captureScreenShot(driver);
		try {
			test.pass(input, MediaEntityBuilder.createScreenCaptureFromPath(failScreenShot).build());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author Vijay Saravanan Sethu
	 * Description: To mark the test step as fail,if the locator is not connected on the element .
	 *
	 */
	public void extentReportFail(String input){
		String failScreenShot=  captureScreenShot(driver);
		try {
			test.fail(input, MediaEntityBuilder.createScreenCaptureFromPath(failScreenShot).build());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
