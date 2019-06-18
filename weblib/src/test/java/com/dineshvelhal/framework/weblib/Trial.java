package com.dineshvelhal.framework.weblib;

import org.testng.annotations.Test;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterTest;

public class Trial {
	WebDriver driver;
	
	String expCondUrl = (new File(getClass()
			.getClassLoader()
			.getResource("keyboard_events.html")
			.getFile()))
			.getAbsolutePath();
	
	@Test
	public void f() throws InterruptedException {
		driver.get(expCondUrl);
		
		WebElement area = driver.findElement(By.id("area"));
		
		area.sendKeys("Dinesh");
		
		//area.sendKeys(Keys.chord(Keys.CONTROL, "a"));
		List<CharSequence> chars = new ArrayList<CharSequence>();
		chars.add(Keys.CONTROL);
		chars.add("a");
		
		area.sendKeys(Keys.chord(chars));
	}
	
	
	@BeforeTest
	public void beforeTest() throws MalformedURLException {
		System.setProperty("webdriver.chrome.driver", "E:\\SeleniumDrivers\\chromedriver_v74.exe");

		driver = WebInstance.builder()
				.driverName(Browser.CHROME)
				.build()
				.initialize().getDriver();
	}

	@AfterTest
	public void afterTest() throws InterruptedException {
		Thread.sleep(5000);
		driver.quit();
	}

}
