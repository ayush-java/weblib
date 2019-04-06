
package com.dineshvelhal.framework.weblib;

import java.io.File;
import java.net.MalformedURLException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;

/**
 * @author Dinesh_Velhal (dinesh.velhal at gmail.com)
 *
 */

public class WebInstanceTest {
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testChrome() throws MalformedURLException {
		By searchBox = By.id("searchInput");
		By searchButton = By.id("searchButton");

		WebInstance inst = 
				WebInstance.builder()
				.driverName(Browser.CHROME)
				.headless(false)
				.implicitWaitSeconds(10)
				.pageLoadTimeoutSeconds(30)
				.smartFind(true)
				.chromeDriverPath("E:\\tmp\\chromedriver.exe")
				.build()
				.initialize();

		WebRunner.builder()
		.webinstance(inst)
		.build()
		.open("http://en.wikipedia.org")
		.sendKeys(searchBox, "Google")
		.click(searchButton)
		.quitBrowser();
	}
	
	@Test
	public void testFirefox() throws MalformedURLException {
		By searchBox = By.id("searchInput");
		By searchButton = By.id("searchButton");

		WebInstance inst = 
				WebInstance.builder()
				.driverName(Browser.FIREFOX)
				.headless(false)
				.implicitWaitSeconds(10)
				.pageLoadTimeoutSeconds(30)
				.firefoxDriverPath("E:\\tmp\\geckodriver.exe")
				.build()
				.initialize();

		WebRunner wr =  WebRunner.builder()
		.webinstance(inst)
		.build()
		.open("http://en.wikipedia.org")
		.sendKeys(searchBox, "Google")
		.click(searchButton);
		
		File f = wr.getScreenshot();
		wr.quitBrowser();
	}
}


/*
 * 
 * 
package com.dineshvelhal.framework.weblib;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class DropMeDocker {

	static Capabilities chromeCapabilities = DesiredCapabilities.chrome();
	static Capabilities firefoxCapabilities = DesiredCapabilities.firefox();

	public static void main(String[] args) throws MalformedURLException {
		WebDriver chrome = new RemoteWebDriver(new URL("http://192.168.99.100:4444/wd/hub"), chromeCapabilities);
		WebDriver firefox = new RemoteWebDriver(new URL("http://192.168.99.100:4444/wd/hub"), firefoxCapabilities);

		// run against chrome
		chrome.get("https://www.google.com");
		System.out.println(chrome.getTitle());

		// run against firefox
		firefox.get("https://www.google.com");
		System.out.println(firefox.getTitle());

		chrome.quit();
		firefox.quit();
	}
}

 * 
 */
