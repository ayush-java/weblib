package com.dineshvelhal.framework.weblib;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;

import java.io.File;
import java.net.MalformedURLException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;

public class Trial {
	WebDriver driver;
	
	String expCondUrl = (new File(getClass()
			.getClassLoader()
			.getResource("expected_conditions.html")
			.getFile()))
			.getAbsolutePath();
	
	@Test
	public void f() throws InterruptedException {
		driver.get(expCondUrl);
		
		driver.findElement(By.id("alert_trigger")).click();
		
		WebDriverWait wait = new WebDriverWait(driver, 10);
		
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		System.out.println("alert = " + alert.getText());
		alert.accept();
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
