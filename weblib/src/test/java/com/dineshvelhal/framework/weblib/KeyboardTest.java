package com.dineshvelhal.framework.weblib;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import lombok.extern.log4j.Log4j2;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.net.MalformedURLException;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

@Log4j2
public class KeyboardTest {
	static WebInstance inst;
	static WebRunner runner;

	String expCondUrl = (new File(getClass()
			.getClassLoader()
			.getResource("keyboard_events.html")
			.getFile()))
			.getAbsolutePath();

	By areaLoc = By.id("area");

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
	public void specialKeyTests() {
		runner.sendKeys(areaLoc, "Dinesh");
		runner.sendKeys(By.id("area"), Keys.CONTROL, "a");
		assertTrue(runner.smartFindElement(By.id("ctrl")).isDisplayed(), "Ctrl key press was not detected");

		runner.sendKeys(By.id("area"), Keys.CONTROL, Keys.ALT, Keys.SHIFT, "a");
		assertTrue(runner.smartFindElement(By.id("ctrl")).isDisplayed() 
				&& runner.smartFindElement(By.id("alt")).isDisplayed() 
				&& runner.smartFindElement(By.id("shift")).isDisplayed(), "Ctrl, Alt or Shift keypress was not detected");
	}
}
