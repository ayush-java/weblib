package com.dineshvelhal.framework.weblib;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import lombok.extern.log4j.Log4j2;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.net.MalformedURLException;

import org.openqa.selenium.By;

@Log4j2
public class LocatorTest {

	static WebInstance inst;
	static WebRunner runner;

	String expCondUrl = (new File(getClass()
			.getClassLoader()
			.getResource("testapp/expected_conditions.html")
			.getFile()))
			.getAbsolutePath();

	@BeforeClass
	public void beforeClass() throws MalformedURLException {
		System.setProperty("webdriver.chrome.driver", "E:\\SeleniumDrivers\\chromedriver_v74.exe");

		inst = WebInstance.builder()
				.driverName(Browser.CHROME)
				.build()
				.initialize();

		log.info("WebInstance: {}", inst);

		runner = new WebRunner(inst);

		runner.open(expCondUrl);
	}

	@AfterClass
	public void afterClass() throws InterruptedException {
		runner.quitBrowser();
		inst = null;
		runner = null;
	}

	@Test
	public void handleAlertTest() {
		runner.getDriver().navigate().refresh();
		setMinMaxWait("3", "5");

		assertEquals(runner
				.setSmartWaitSeconds(5)
				.click(By.id("alert_trigger"))
				.acceptAlert()
				.getText(By.xpath("//*[@id=\"alert_handled\"]/span")),
				"Alert handled",
				"Assert failed for handleAlertTest.");
	}

	@Test
	public void dismissAlertTest() {
		runner.getDriver().navigate().refresh();
		setMinMaxWait("3", "5");

		assertEquals(runner
				.setSmartWaitSeconds(5)
				.click(By.id("prompt_trigger"))
				.dismissAlert()
				.getText(By.xpath("//*[@id=\"confirm_cancelled\"]/span")),
				"Cancelled",
				"Assert failed for dismissAlertTest.");
	}

	@Test
	public void acceptAlertTest() {
		runner.getDriver().navigate().refresh();
		setMinMaxWait("3", "5");

		assertEquals(runner
				.setSmartWaitSeconds(5)
				.click(By.id("prompt_trigger"))
				.acceptAlert()
				.getText(By.xpath("//*[@id=\"confirm_ok\"]/span")),
				"OK",
				"Assert failed for acceptAlertTest.");
	}

	@Test
	public void smartWaitTest() {
		runner.getDriver().navigate().refresh();
		setMinMaxWait("5", "5");

		assertEquals(runner
				.setSmartWaitSeconds(5)
				.click(By.id("prompt_trigger"))
				.acceptAlert()
				.getText(By.xpath("//*[@id=\"confirm_ok\"]/span")),
				"OK",
				"Assert failed for acceptAlertTest.");
	}


	@Test
	public void waitForValueChange() {
		runner.getDriver().navigate().refresh();
		setMinMaxWait("5", "5");

		assertTrue(runner
				.setSmartWaitSeconds(5)
				.click(By.id("text_value_trigger"))
				.waitForElementValueToContain(By.id("wait_for_value"), "Dennis Ritchie") == true);
	}
	
	@Test
	public void waitForTextChange() {
		runner.getDriver().navigate().refresh();
		setMinMaxWait("5", "5");

		assertTrue(runner
				.setSmartWaitSeconds(5)
				.click(By.id("text_value_trigger"))
				.waitForElementTextToContain(By.id("wait_for_text"), "Submit") == true);
	}
	
	@Test
	public void switchToFrameByLocator() {
		runner.getDriver().navigate().refresh();
		setMinMaxWait("3", "5");

		assertEquals(runner
				.setSmartWaitSeconds(5)
				.click(By.id("wait_for_frame"))
				.switchToFrame(By.id("frame"))
				.smartFindElement(By.id("inner_button"))
				.getText(), "Inner Button");
		
		assertEquals(runner
				.setSmartWaitSeconds(5)
				.switchToDefaultContent()
				.click(By.id("wait_for_frame"))
				.switchToFrame(By.id("frame"))
				.smartFindElement(By.id("inner_button"))
				.getText(), "Inner Button");
	}
	
	@Test
	public void switchToFrameByName() {
		runner.getDriver().navigate().refresh();
		setMinMaxWait("3", "5");

		assertEquals(runner
				.setSmartWaitSeconds(5)
				.click(By.id("wait_for_frame"))
				.switchToFrame("frm")
				.smartFindElement(By.id("inner_button"))
				.getText(), "Inner Button");
		
		assertEquals(runner
				.setSmartWaitSeconds(5)
				.switchToDefaultContent()
				.click(By.id("wait_for_frame"))
				.switchToFrame("frm")
				.smartFindElement(By.id("inner_button"))
				.getText(), "Inner Button");
	}
	
	@Test
	public void switchToFrameByNum() {
		runner.getDriver().navigate().refresh();
		setMinMaxWait("3", "5");

		assertEquals(runner
				.setSmartWaitSeconds(5)
				.click(By.id("wait_for_frame"))
				.switchToFrame(0)
				.smartFindElement(By.id("inner_button"))
				.getText(), "Inner Button");
		
		assertEquals(runner
				.setSmartWaitSeconds(5)
				.switchToDefaultContent()
				.click(By.id("wait_for_frame"))
				.switchToFrame(0)
				.smartFindElement(By.id("inner_button"))
				.getText(), "Inner Button");
	}


	private void setMinMaxWait(String min, String max) {
		runner
		.sendKeys(By.id("min_wait"), min)
		.sendKeys(By.id("max_wait"), max);
	}
}
