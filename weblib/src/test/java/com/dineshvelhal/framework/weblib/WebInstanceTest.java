
package com.dineshvelhal.framework.weblib;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;

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
	public void testChrome() {
		By searchBox = By.id("searchInput");
		By searchButton = By.id("searchButton");

		WebInstance inst = 
				WebInstance.builder()
				.driverName("chrome")
				.headless(false)
				.implicitWaitSeconds(10)
				.pageLoadTimeoutSeconds(30)
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
	public void testFirefox() {
		By searchBox = By.id("searchInput");
		By searchButton = By.id("searchButton");

		WebInstance inst = 
				WebInstance.builder()
				.driverName("firefox")
				.headless(false)
				.implicitWaitSeconds(10)
				.pageLoadTimeoutSeconds(30)
				.firefoxDriverPath("E:\\tmp\\geckodriver.exe")
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
	
	//
}
