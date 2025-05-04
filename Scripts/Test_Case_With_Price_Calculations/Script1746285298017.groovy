import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import internal.GlobalVariable as GlobalVariable
import java.text.SimpleDateFormat
import java.util.Date
import org.openqa.selenium.WebElement
import org.openqa.selenium.By


// Utility: Create TestObject from XPath
TestObject createTestObjectByXPath(String xpath) {
	TestObject to = new TestObject()
	to.addProperty("xpath", ConditionType.EQUALS, xpath)
	return to
}

// Utility: Generate timestamp
String getTimestamp() {
	return new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date())
}

// Utility: Take screenshot with timestamp
void takeScreenshot(String stepName) {
	String filePath = "Screenshots/${stepName}_${getTimestamp()}.png"
	WebUI.takeScreenshot(filePath)
}

// Utility: Add delay and optional screenshot
void waitWithScreenshot(String stepName, int delayInSeconds = 2) {
	WebUI.delay(delayInSeconds)
	takeScreenshot(stepName)
}

String Username_Input = "//input[@id='user-name']"
String Password_Input = "//input[@id='password']"
String Login_Button = "//input[@id='login-button']"
String Add_To_Cart_1 = "//button[@id='add-to-cart-sauce-labs-backpack']"
String Add_To_Cart_2 = "//button[@id='add-to-cart-sauce-labs-bike-light']"
String Add_To_Cart_3 = "//button[@id='add-to-cart-sauce-labs-bolt-t-shirt']"
String Check_Cart = "//a[@class='shopping_cart_link']"
String Checkout_Button = "//button[@id='checkout']"
String Checkout_First_Name = "//input[@id='first-name']"
String Checkout_Last_Name = "//input[@id='last-name']"
String Checkout_Postal_Code = "//input[@id='postal-code']"
String Checkout_Continue_Button = "//input[@id='continue']"
String Finish_Checkout_Button = "//button[@id='finish']"
String Menu_Button = "//button[@id='react-burger-menu-btn']"
String Logout_Button = "//a[@id='logout_sidebar_link']"

String expectedText = "Your order has been dispatched, and will arrive just as fast as the pony can get there!"
// --- Test Start ---
WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl(GlobalVariable.URL)

WebUI.setText(createTestObjectByXPath(Username_Input), GlobalVariable.username)

WebUI.setText(createTestObjectByXPath(Password_Input), GlobalVariable.password)

waitWithScreenshot("Before_Login")

WebUI.click(createTestObjectByXPath(Login_Button))

WebUI.delay(2)

// Wait and handle the alert
if (WebUI.waitForAlert(5)) {
	String alertText = WebUI.getAlertText()
	WebUI.comment("Alert appeared: " + alertText)
	WebUI.dismissAlert() // Skip changing the password
}

// Define the product container object
TestObject productBlock = new TestObject().addProperty(
	"class", ConditionType.EQUALS, "inventory_item_description"
)

// Get all product elements (should be 6)
List<WebElement> products = WebUI.findWebElements(productBlock, 10)

// List to store product info maps
List<Map<String, String>> productList = []

// Loop through each product and extract details
for (WebElement product : products) {
	String name = product.findElement(By.className('inventory_item_name')).getText()
	String price = product.findElement(By.className('inventory_item_price')).getText()

	// Create a map for the current product
	Map<String, String> productMap = [
		'name'       : name,
		'price'      : price
	]

	// Add map to the list
	productList.add(productMap)
}

// Log the full product list
WebUI.comment("Collected Products: " + productList.toString())


WebUI.click(createTestObjectByXPath(Add_To_Cart_1))

WebUI.click(createTestObjectByXPath(Add_To_Cart_2))

WebUI.click(createTestObjectByXPath(Add_To_Cart_3))

waitWithScreenshot("Product_Page")

WebUI.click(createTestObjectByXPath(Check_Cart))

waitWithScreenshot("Cart_Page")

WebUI.click(createTestObjectByXPath(Checkout_Button))

waitWithScreenshot("Chekout_Page")

WebUI.setText(createTestObjectByXPath(Checkout_First_Name),'data')
WebUI.setText(createTestObjectByXPath(Checkout_Last_Name),'test')
WebUI.setText(createTestObjectByXPath(Checkout_Postal_Code),'4859')

waitWithScreenshot("Your_Information_Page")

WebUI.click(createTestObjectByXPath(Checkout_Continue_Button))

waitWithScreenshot("Checkout_Overview_Page")

WebUI.click(createTestObjectByXPath(Finish_Checkout_Button))

boolean isTextPresent = WebUI.verifyTextPresent(expectedText, false)

WebUI.comment('Text found: ' + isTextPresent)

waitWithScreenshot("Checkout_Complete_Page")

WebUI.click(createTestObjectByXPath(Menu_Button))

waitWithScreenshot("Before_Logout_Page")

WebUI.click(createTestObjectByXPath(Logout_Button))

waitWithScreenshot("After_Logout_Page")

WebUI.verifyElementPresent(createTestObjectByXPath(Login_Button), 1)

WebUI.closeBrowser()