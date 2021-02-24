package pages;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import utility.BaseClass;
import utility.ExcelUtils;

public class CartPage extends BaseClass{

	String mincartPopUp="//android.view.View[@content-desc='Cart']/android.view.View/android.widget.Button";
	String assertionProductTitle="//android.view.View[@content-desc='Samsung 163 cm (65 inches) 4K Ultra HD Smart LED TV UA65TU8000KX...']/android.widget.TextView";

	/**
	 * @author Vijay Saravanan Sethu
	 * Description: To click on the mini pop up cart button
	 * Attributes: locators, locator value, timeOut
	 *
	 */
	public void Cart(){

		try {
			// 
			waitUntilElementPresent( "xpath", mincartPopUp,180);
			click("xpath", mincartPopUp,180,"To click on the mini pop up cart button");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author Vijay Saravanan Sethu
	 * Description: Asserting the products which is added on the cart page
	 * Attributes: locators, locator value, timeOut
	 *
	 */
	public void cartProductsAssertion(){
		try {
			waitUntilElementPresent( "xpath", assertionProductTitle,180);

			String ProductTitle= getText("xpath", assertionProductTitle, 180,"To retrieve the text");

			waitUntilElementPresent( "xpath", assertionProductTitle,180);

			String assertion = ProductTitle.replace("UA65TU8000KX...", "UA65TU8000KXXL (Black) (2020 Model)");

			stringComparison(details.get(0),ProductTitle.replace("UA65TU8000KX...", "UA65TU8000KXXL (Black) (2020 Model)"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
