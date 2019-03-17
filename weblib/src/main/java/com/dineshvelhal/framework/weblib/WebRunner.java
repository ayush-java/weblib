/**
 * 
 */
package com.dineshvelhal.framework.weblib;

import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2; 

/**
 * @author Dinesh_Velhal
 *
 */
@Builder
@Getter
@Log4j2
@ToString
public class WebRunner {
	private WebInstance webinstance;
	//private WebDriver driver;

	/**
	 * 
	 * @param inst
	 */
/*	public WebRunner(WebInstance inst) {
		super();
		log.traceEntry("WebInstance: [{}]", inst.toString());
		
		this.webin = inst;
		this.driver = inst.getDriver();
		
		log.traceExit();
	}*/
	
	/**
	 * Opens a new browser window and navigates to the given url
	 * 
	 * @param url UR to be visited
	 * @return this instance
	 */
	public WebRunner open(String url) {
		log.traceEntry("url: [{}]", url);
		
		log.info("driver hashcode: [{}]", webinstance.getDriver().hashCode());
		webinstance.getDriver().get(url);
		
		if (webinstance.isMaximizeWindow()) {
			webinstance.getDriver().manage().window().maximize();
		}
		
		log.info("Test running with webinstance: [{}]", webinstance);
		
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
		
		WebElement e = webinstance.smartFindElement(by);
		
		e.click();
		
		log.traceExit();
		return this;
	}
	
	/**
	 * Type in the web element based on the locator passed as argument
	 * 
	 * @param by locator 
	 * @param text text to be entered
	 * @return this instance
	 */
	public WebRunner sendKeys(By by, String text) {
		log.traceEntry("locator: [{}] text: [{}]", by.toString(), text);
		
		WebElement e = webinstance.smartFindElement(by);
		
		e.sendKeys(text);
		
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
		
		WebElement e = webinstance.smartFindElement(by);
		
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
		
		WebElement e = webinstance.smartFindElement(by);
		
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
		
		WebElement e = webinstance.smartFindElement(by);
		
		(new Select(e)).selectByValue(value);
		
		log.traceExit();
		return this;
	}
	
	// TODO double click
	// TODO right click
	// TODO special key-strokes
	
	// TODO get inner text
	// TODO get specific attribute value
	// TODO wait for element visible
	// TODO wait for element invisible
	// TODO wait for element clickable
	
	
	
	/**
	 * Takes screenshot and returns the screenshot File object
	 * @return screenshot File
	 */
	public File getScreenshot() {
		log.traceEntry();
		
		File scr = ((TakesScreenshot)getWebinstance().getDriver()).getScreenshotAs(OutputType.FILE);
		
		log.traceExit("screenshot File: {}", scr.getAbsolutePath());
		return scr;
		
	}
	
	
	/**
	 * Provides a way to specifically maximize browser window in a specific session
	 * (overrides the setting made inside the webinstance)
	 * 
	 * @return this instance
	 */
	public WebRunner maximize() {
		log.traceEntry();
		
		webinstance.getDriver().manage().window().maximize();
		
		log.traceExit();
		return this;
	}
	
	/**
	 * Quits the browser and ends the session
	 * @return this instance
	 */
	public WebRunner quitBrowser() {
		log.traceEntry();
		
		webinstance.getDriver().quit();
		
		log.traceExit();
		return this;
	}
}
