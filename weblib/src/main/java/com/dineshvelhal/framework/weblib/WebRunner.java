/**
 * 
 */

package com.dineshvelhal.framework.weblib;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.extern.log4j.Log4j2; 

/**
 * @author Dinesh_Velhal
 *
 */


@Log4j2
@ToString
public class WebRunner {
	@Getter(AccessLevel.PUBLIC)
	@NonNull
	private WebInstance webInstance;

	@Getter(AccessLevel.PUBLIC)
	private WebDriver driver;

	/**
	 * Explicit Wait time in seconds (will be used by smartFind method)
	 */
	private int smartWaitSeconds = 15;

	/**
	 * ImplicitlyWait time in seconds
	 */
	private int implicitWaitSeconds = 15;


	/**
	 * Page Load Timeout in seconds
	 */
	private int pageLoadTimeoutSeconds = 60;


	//************************************************************************************************
	// CONSTRUCTORS
	//************************************************************************************************

	/**
	 * @param webInstance a new instance of WebInstance class
	 */
	public WebRunner(@NonNull WebInstance webInstance) {
		//super();
		log.traceEntry("WebInstance: [{}]", webInstance);

		this.webInstance = webInstance;
		this.driver = this.webInstance.getDriver();

		log.traceExit();
	}


	//************************************************************************************************
	// ALL ACTION METHODS WHICH RETURN THE *this* OBJECT TO SUPPORT BUILDER PATTERN
	//************************************************************************************************

	/**
	 * Opens a new browser window and navigates to the given url
	 * 
	 * @param url UR to be visited
	 * @return this instance
	 */
	public WebRunner open(String url) {
		log.traceEntry("url: [{}]", url);

		driver.manage().timeouts().implicitlyWait(getImplicitWaitSeconds(), TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(getPageLoadTimeoutSeconds(), TimeUnit.SECONDS);

		log.info("driver hashcode: [{}]", webInstance.getDriver().hashCode());
		webInstance.getDriver().get(url);

		//log.info("Test running with webInstance: [{}]", webInstance);
		log.info("Test running with webRunner: [{}]", this);

		log.traceExit();
		return this;
	}

	/**
	 * Click the web element based on the locator passed as argument
	 * 
	 * @param by locator
	 * @return this instance
	 */
	public WebRunner click(By by) {
		log.traceEntry("locator: [{}]", by.toString());

		WebElement e = smartFindElement(by);

		e.click();

		log.info("Clicked element [{}] successfully", by.toString());
		log.traceExit();
		return this;
	}

	/**
	 * Type in the web element based on the locator passed as argument (it clears the original contents of the web element)
	 * 
	 * @param by locator 
	 * @param text text to be entered
	 * @return this instance
	 */
	public WebRunner sendKeys(By by, String text) {
		log.traceEntry("locator: [{}] text: [{}]", by.toString(), text);

		WebElement e = smartFindElement(by); 

		e.clear();
		e.sendKeys(text); 

		log.traceExit();
		return this;
	}
	
	
	/**
	 * @param by locator
	 * @param chars a sequence of any number of characters (usually Keys.<somekey>, ....)
	 * @return this instance
	 */
	public WebRunner sendKeys(By by, CharSequence... chars) {
		log.traceEntry("locator: [{}] chars: [{}]", by.toString(), chars);

		WebElement e = smartFindElement(by); 

		List<CharSequence> lstChars = new ArrayList<CharSequence>();
		
		for(CharSequence c: chars) {
			lstChars.add(c);
		}
		
		e.sendKeys(Keys.chord(lstChars)); 

		log.traceExit();
		return this;
	}


	/**
	 * Select value from the dropdown using its visible text
	 * 
	 * @param by locator
	 * @param text visible text to be selected
	 * @return this instance
	 */
	public WebRunner selectByVisibleText(By by, String text) {
		log.traceEntry("locator: [{}] text: [{}]", by.toString(), text);

		WebElement e = smartFindElement(by);

		(new Select(e)).selectByVisibleText(text);

		log.traceExit();
		return this;
	}

	/**
	 * Select value from the dropdown using its index
	 * 
	 * @param by locator
	 * @param index index of the item to be selected
	 * @return this instance
	 */
	public WebRunner selectByIndex(By by, int index) {
		log.traceEntry("locator: [{}] index: [{}]", by.toString(), index);

		WebElement e = smartFindElement(by);

		(new Select(e)).selectByIndex(index);

		log.traceExit();
		return this;
	}

	/**
	 * Select value from the dropdown using its value attribute
	 * 
	 * @param by locator
	 * @param value value attribute of the item to be selected
	 * @return this instance
	 */
	public WebRunner selectByValue(By by, String value) {
		log.traceEntry("locator: [{}] value: [{}]", by.toString(), value);

		WebElement e = smartFindElement(by);

		(new Select(e)).selectByValue(value);

		log.traceExit();
		return this;
	}

	// TODO double click
	// TODO right click
	// TODO special key-strokes
	// TODO scrollPageDown
	// TODO scrollPageUp
	// TODO ScrollToBottomOfPage
	// TODO ScrollToTopOfPage

	// TODO wait for element visible
	// TODO wait for element invisible
	// TODO wait for element clickable
	// TODO wait for page load

	/**
	 * @param by locator
	 * @return Text contained in the element
	 */
	public String getText(By by) {
		log.traceEntry();

		WebElement e = this.smartFindElement(by);
		String retVal = e.getText();

		log.traceExit();
		return retVal;
	}
	
	
	/**
	 * @param by located
	 * @param attrName Name of the attribute
	 * @return attribute value
	 */
	public String getAttributeValue(By by, String attrName) {
		log.traceEntry();

		WebElement e = this.smartFindElement(by);
		String retVal = e.getAttribute(attrName);

		log.traceExit();
		return retVal;
	}
	

	/**
	 * Takes screenshot and returns the screenshot File object
	 * @return screenshot File
	 */
	public File getScreenshot() {
		log.traceEntry();

		File scr = ((TakesScreenshot)getWebInstance().getDriver()).getScreenshotAs(OutputType.FILE);

		log.traceExit("screenshot File: {}", scr.getAbsolutePath());
		return scr;

	}


	/**
	 * Provides a way to maximize browser window in a session
	 * (overrides the setting made inside the webInstance)
	 * 
	 * @return this instance
	 */
	public WebRunner maximize() {
		log.traceEntry();

		webInstance.getDriver().manage().window().maximize();

		log.traceExit();
		return this;
	}

	/**
	 * Quits the browser and ends the session
	 * @return this instance
	 */
	public WebRunner quitBrowser() {
		log.traceEntry();

		webInstance.getDriver().quit();
		log.info("Browser quit successfully [{}]", webInstance);

		log.traceExit();
		return this;
	}

	/**
	 * @param smartWaitSeconds number of seconds to wait used by smartFind
	 * @return this instance
	 */
	public WebRunner setSmartWaitSeconds(int smartWaitSeconds) {
		log.traceEntry("smartWaitSeconds: [{}]", smartWaitSeconds);

		this.smartWaitSeconds = smartWaitSeconds;

		log.traceExit();
		return this;
	}

	/**
	 * @param implicitWaitSeconds the implicitWaitSeconds to set
	 * @return this instance
	 */
	public WebRunner setImplicitWaitSeconds(int implicitWaitSeconds) {
		log.traceEntry("implicitWaitSeconds: [{}]", implicitWaitSeconds);

		this.implicitWaitSeconds = implicitWaitSeconds;
		driver.manage().timeouts().implicitlyWait(implicitWaitSeconds, TimeUnit.SECONDS);

		log.traceExit();
		return this;
	}


	/**
	 * @param pageLoadTimeoutSeconds the pageLoadTimeoutSeconds to set
	 */
	public WebRunner setPageLoadTimeoutSeconds(int pageLoadTimeoutSeconds) {
		log.traceEntry("pageLoadTimeoutSeconds: [{}]", pageLoadTimeoutSeconds);

		this.pageLoadTimeoutSeconds = pageLoadTimeoutSeconds;
		driver.manage().timeouts().pageLoadTimeout(pageLoadTimeoutSeconds, TimeUnit.SECONDS);

		log.traceExit();
		return this;
	}


	/**
	 * @return the WebDriverWait using smartWaitSeconds
	 */
	public WebDriverWait getWait() {
		log.traceEntry();

		log.info("Wait configured with seconds: [{}]", smartWaitSeconds);

		log.traceExit();

		return new WebDriverWait(getDriver(), smartWaitSeconds);
	}


	public Alert waitForAlertVisible() {
		log.traceEntry();

		Alert alert = getWait().until(ExpectedConditions.alertIsPresent());

		log.traceExit();
		return alert;
	}

	public WebRunner acceptAlert() {
		log.traceEntry();

		Alert alert = waitForAlertVisible();
		log.info("Aert Message: [{}]", alert.getText());

		alert.accept();
		log.info("Alert accepted");

		log.traceExit();
		return this;
	}

	public WebRunner dismissAlert() {
		log.traceEntry();

		Alert alert = waitForAlertVisible();
		log.info("Aert Message: [{}]", alert.getText());

		alert.dismiss();
		log.info("Alert dismissed");

		log.traceExit();
		return this;
	}


	//************************************************************************************************
	// ALL getXXX methods which return specific characteristics/attributes of/from the current page
	//************************************************************************************************
	public String getTitle() {
		log.traceEntry();

		log.traceExit();
		return getWebInstance().getDriver().getTitle();
	}

	public String getCurrentURL() {
		log.traceEntry();

		log.traceExit();
		return getWebInstance().getDriver().getCurrentUrl();
	}

	public String getSource() {
		log.traceEntry();

		log.traceExit();
		return getWebInstance().getDriver().getPageSource();
	}

	public String getWindowHandle() {
		log.traceEntry();

		log.traceExit();
		return getWebInstance().getDriver().getWindowHandle();
	}


	/**
	 * @return the smartWaitSeconds
	 */
	public int getSmartWaitSeconds() {
		log.traceEntry();

		log.traceExit();
		return smartWaitSeconds;
	}

	/**
	 * @return the pageLoadTimeoutSeconds
	 */
	public int getPageLoadTimeoutSeconds() {
		log.traceEntry();

		log.traceExit();
		return pageLoadTimeoutSeconds;
	}

	/**
	 * @return the implicitWaitSeconds
	 */
	public int getImplicitWaitSeconds() {
		log.traceEntry();

		log.traceExit();
		return implicitWaitSeconds;
	}


	//************************************************************************************************
	// ALL SUPPORT METHODS
	//************************************************************************************************

	/**
	 * Returns the WebElement by finding it using smartFind
	 * Using the explicit wait specified as argument
	 * 
	 * @param by locator
	 * @return WebElement
	 */
	public WebElement findElement(By by) {
		log.traceEntry();

		WebElement e;
		try {
			log.info("Using implicit wait: {} seconds", this.implicitWaitSeconds);

			e = driver.findElement(by);
			log.info("Found the element [{}]", by.toString());
		} catch (Exception e1) {
			log.error("Error in finding element [{}] - ERROR [{}]", by.toString(), e1.getMessage());
			throw e1;
		}

		log.traceExit();
		return e;
	}

	/**
	 * Finds the element by waiting for given number of seconds for the 
	 * element to be clickable
	 * 
	 * @param by locator
	 * @param seconds Wait in seconds
	 * @return WebElement
	 */
	public WebElement smartFindElement(By by, int seconds) {
		log.traceEntry();

		log.info("{}, wait seconds: {}", by, seconds);

		WebElement e;

		log.info("Using non-default smart wait: {} seconds", seconds);

		try {
			WebDriverWait wait = new WebDriverWait(driver, seconds);
			e = wait.until(ExpectedConditions.elementToBeClickable(by));
			log.info("Found the element [{}]", by);
		} catch (Exception ex) {
			log.error("Error in finding element [{}] - ERROR [{}]", by.toString(), ex.getMessage());
			throw ex;
		}

		log.traceExit();
		return e;
	}


	/**
	 * Finds the element by waiting for smartwaitSeconds for the 
	 * element to be clickable
	 * 
	 * @param by locator
	 * @return WebElement
	 */
	public WebElement smartFindElement(By by) {
		log.traceEntry("by = {}", by);

		WebElement e;

		//int seconds = getSmartWaitSeconds();
		//log.info("Using default wait: {} seconds", seconds);

		try {
			WebDriverWait wait = this.getWait();
			e = wait.until(ExpectedConditions.elementToBeClickable(by));
			log.info("Found the element [{}]", by);
		}
		catch (Exception ex) {
			log.error("Error in finding element [{}] - ERROR [{}]", by.toString(), ex.getMessage());
			throw ex;
		}

		log.traceExit();
		return e;
	}


	// TODO fluent findElement needed
}
