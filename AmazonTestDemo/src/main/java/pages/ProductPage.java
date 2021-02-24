package pages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;

import io.appium.java_client.MobileElement;
import utility.ExcelUtils;
import utility.BaseClass;

public class ProductPage extends BaseClass {

	String searchBox="com.amazon.mShop.android.shopping:id/rs_search_src_text";
	String autoSuggestedProductList="com.amazon.mShop.android.shopping:id/iss_search_dropdown_item_text";
	String clickProduct="com.amazon.mShop.android.shopping:id/item_title";
	String outSideTouch="//*[@class = 'android.view.View'  and @resource-id = 'com.amazon.mShop.android.shopping:id/touch_outside']";
	String storeTitleOfTheProduct="//*[@text = 'Samsung 163 cm (65 inches) 4K Ultra HD Smart LED TV UA65TU8000KXXL (Black) (2020 Model)' and @class = 'android.view.View']";
	String cartClick="//*[@class = 'android.widget.Button' and @resource-id = 'add-to-cart-button']";

	/**
	 * @author Vijay Saravanan Sethu
	 * Description: Entering the value in the text box and searching for the suitable product.
	 * Attributes: locators, locator value, timeOut
	 *
	 */
	public void searchProduct(ExcelUtils excelReader) {

		try {
			click("id", searchBox,180,"To click on the search text box");
			Thread.sleep(4000);
			try {
				// 
				sendKeys("id", searchBox,excelReader.getCellData("Sheet1", "InputData", 2),180,"To search on the suitable product"+excelReader.getCellData("Sheet1", "ProductName", 2));

			} catch (StaleElementReferenceException e) {
				Thread.sleep(2000);
				sendKeys("id", searchBox, excelReader.getCellData("Sheet1", "InputData", 2),180,"To search on the suitable product"+excelReader.getCellData("Sheet1", "ProductName", 2));
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	/**
	 * @author Vijay Saravanan Sethu
	 * Description: To click on the auto suggested product.
	 * Attributes: locators, locator value, timeOut
	 *
	 */
	public void clickAutoSuggestedProduct(ExcelUtils excelReader)  {
		waitUntilElementPresent(By.id(autoSuggestedProductList), 180);
		storeDropDownClick("id",autoSuggestedProductList,excelReader.getCellData("Sheet1", "ProductName", 3));
	}

	/**
	 * @author Vijay Saravanan Sethu
	 * Description: To click on the product visible to the user.
	 * Attributes: locators, locator value, timeOut
	 *
	 */
	public void clickTheProduct(ExcelUtils excelReader){

		try {
			// To store all the product in the list
			waitUntilElementPresent(By.id(clickProduct), 180);
			storeDropDownClick("id",clickProduct,excelReader.getCellData("Sheet1", "ProductName", 4));

			// To click on outside the pop up to avoid the pop up
			waitUntilElementToBeClickable(By.xpath(outSideTouch),180); 
			MobileElement elementTap = driver.findElementByXPath(outSideTouch); 
			tap(elementTap,"To click on outside the pop up to avoid the pop up");
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}

	/**
	 * @author Vijay Saravanan Sethu
	 * Description: To store the title of the product.
	 * Attributes: locators, locator value, timeOut
	 *
	 */
	public void storeTheTitleOfTheProduct(ExcelUtils excelReader)  {
		try {
			// To store the product name in the list
			waitUntilElementPresent(By.xpath(storeTitleOfTheProduct), 180);
			storeTheText("xpath",storeTitleOfTheProduct,excelReader.getCellData("Sheet1", "ProductName", 4));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author Vijay Saravanan Sethu
	 * Description: To scroll the page in vertical.
	 * Attributes: locators, locator value, timeOut
	 *
	 */
	public void toScrollThePage()  {
		swipeToElement(cartClick);

	}

	/**
	 * @author Vijay Saravanan Sethu
	 * Description: To click on the cart.
	 * Attributes: locators, locator value, timeOut
	 *
	 */
	public void toClickOnTheCart()  {
		try {
			click("xpath", cartClick, 180,"To click on the add to cart button");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
