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
import org.openqa.selenium.Keys;

@Log4j2
public class KeyboardTest {
	static WebInstance inst;
	static WebRunner runner;

	String expCondUrl = (new File(getClass()
			.getClassLoader()
			.getResource("testapp/keyboard_mouse_events.html")
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
	public void modifierKeyTest() {
		runner.sendKeys(areaLoc, "Dinesh");
		runner.sendKeys(By.id("area"), Keys.CONTROL, "a");
		assertTrue(runner.smartFindElement(By.id("ctrl")).isDisplayed(), "Ctrl key press was not detected");

		runner.sendKeys(By.id("area"), Keys.CONTROL, Keys.ALT, Keys.SHIFT, Keys.META, "a");
		assertTrue(runner.smartFindElement(By.id("ctrl")).isDisplayed() 
				&& runner.smartFindElement(By.id("alt")).isDisplayed() 
				&& runner.smartFindElement(By.id("shift")).isDisplayed()
				&& runner.smartFindElement(By.id("meta")).isDisplayed(), "Ctrl, Alt, Meta or Shift keypress was not detected");
	}
	
	@Test
	public void specialKeyPressTest() {
		assertEquals(runner
				.sendKeys(By.id("area"), Keys.ENTER)
				.smartFindElement(By.id("key"))
				.getText(), "Enter", "Enter Key Press was not detected");
		
		assertEquals(runner
				.sendKeys(By.id("area"), Keys.PAGE_DOWN)
				.smartFindElement(By.id("key"))
				.getText(), "PageDown", "PageDown Key Press was not detected");
		
		assertEquals(runner
				.sendKeys(By.id("area"), "d")
				.smartFindElement(By.id("code"))
				.getText(), "KeyD", "Key d Press was not detected");
	}
}
