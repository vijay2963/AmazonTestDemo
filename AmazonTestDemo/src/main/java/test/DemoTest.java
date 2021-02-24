package test;
import java.io.IOException;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.ProductPage;
import pages.SignInPage;
import utility.BaseClass;
import utility.ExcelUtils;

/**
 * @author Vijay Saravanan Sethu
 * Description: Before Test to initialize extent report & desiredCapabilities
 *
 */

public class DemoTest extends BaseClass {

	@BeforeTest()
	public void beforeRun( ) {
		extentReport();
		desiredCapabilities();
	}

	/**
	 * @author Vijay Saravanan Sethu
	 * Description: test method has all the test steps to run the mobile app
	 *
	 */
	@Test
	public void test() {
		test=extent.createTest("Amazon Appium Test");
		ExcelUtils excelReader = new ExcelUtils();
		new SignInPage().skipSignIn();
		new ProductPage().searchProduct(excelReader);
		new ProductPage().clickAutoSuggestedProduct(excelReader);
		new ProductPage().clickTheProduct(excelReader);
		new ProductPage().storeTheTitleOfTheProduct(excelReader);
		new ProductPage().toScrollThePage();
		new ProductPage().toClickOnTheCart();
		new CartPage().Cart();
		new CartPage().cartProductsAssertion();
	}

	/**
	 * @author Vijay Saravanan Sethu
	 * Description: tearDown method used to log the failure and to generate the extent report in html 
	 * format
	 *
	 */
	@AfterMethod()
	public void tearDown(ITestResult result) {
		if(ITestResult.FAILURE==result.getStatus()) {

			test.fail(result.getThrowable().getMessage());

		}
		flush();
	}
}