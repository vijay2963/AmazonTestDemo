package pages;

import java.io.IOException;

import org.openqa.selenium.By;

import test.DemoTest;
import utility.ExcelUtils;
import utility.BaseClass;

public class SignInPage extends BaseClass{

	String skipsignIn="//*[@class = 'android.widget.Button'  and @resource-id = 'com.amazon.mShop.android.shopping:id/skip_sign_in_button']";

	/**
	 * @author Vijay Saravanan Sethu
	 * Description: To click on the sign in button.
	 * Attributes: locators, locator value, timeOut
	 *
	 */
	public void skipSignIn(){
		try {
			waitUntilElementToBeClickable(By.xpath(skipsignIn),180);
			click("xpath",skipsignIn, 180,"To click on the skip sign in button");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}