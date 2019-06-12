
package com.dineshvelhal.framework.weblib;

import java.net.MalformedURLException;

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
		// set driver exe paths
		System.setProperty("webdriver.chrome.driver", "E:\\SeleniumDrivers\\chromedriver_v74.exe");
		System.setProperty("webdriver.gecko.driver", "E:\\tmp1\\geckodriver.exe");
		System.setProperty("webdriver.ie.driver", "E:\\tmp1\\IEDriverServer.exe");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testChrome() throws MalformedURLException {
		By searchBox = By.id("searchInput");
		By searchButton = By.id("searchButton");

		try {
			WebInstance inst = 
					WebInstance.builder()
					.driverName(Browser.CHROME)
					.implicitWaitSeconds(10)
					.pageLoadTimeoutSeconds(30)
					.chromeDriverPath("E:\\tmp\\chromedriver.exe")
					.build()
					.initialize();
			
			// System.out.println("webInstance: " + inst);

			WebRunner runner = new WebRunner(inst);

			runner.open("http://en.wikipedia.org")
			.sendKeys(searchBox, "Google")
			.click(searchButton)
			.quitBrowser();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/*@Test
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
	}*/
}
