package com.dineshvelhal.framework.weblib;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;


/**
 * @author Dinesh_Velhal
 *
 */
@Builder
@Log4j2
@ToString
public class WebInstance {

	@Getter(AccessLevel.PACKAGE)
	@Builder.Default 
	private WebDriver driver = null;

	/**
	 * The name of the browser being used; should be one of 
	 * {android, chrome, firefox, htmlunit, ie, 
	 * iPhone, iPad, opera, safari}
	 * @see https://github.com/SeleniumHQ/selenium/wiki/DesiredCapabilities
	 * 
	 * In addition, you can also use {remote}
	 * 
	 * It is used to instantiate the WebDriver object
	 */
	@Getter(AccessLevel.PUBLIC)
	@Builder.Default 
	String driverName = "chrome";


	/**
	 * ImplicitlyWait time in seconds
	 */
	@Getter(AccessLevel.PUBLIC)
	@Builder.Default 
	final int implicitWaitSeconds = 15;


	/**
	 * Page Load Timeout in seconds
	 */
	@Getter(AccessLevel.PUBLIC)
	@Builder.Default 
	final int pageLoadTimeoutSeconds = 60;


	/**
	 * Maximizes the browser window if not already maximized
	 */
	@Getter(AccessLevel.PUBLIC)
	@Builder.Default 
	final boolean maximizeWindow = true;


	/**
	 * Browser runs in headless mode if this field is set to true
	 */
	@Getter(AccessLevel.PUBLIC)
	@Builder.Default 
	final boolean headless = false;
	
	/**
	 * path of the chrome driver executable
	 */
	@Getter(AccessLevel.PUBLIC)
	@Builder.Default 
	final String chromeDriverPath = "";
	
	/**
	 * path of the firefox driver executable
	 */
	@Getter(AccessLevel.PUBLIC)
	@Builder.Default 
	private String firefoxDriverPath = "";
	
	/**
	 * path of the IE driver executable
	 */
	@Getter(AccessLevel.PUBLIC)
	@Builder.Default 
	private String IEDriverPath = "";


	///////////////////////////////////////////////////////////////////////////

	/**
	 * Initializes WebDriver using all the properties set before this method call
	 * 
	 * The name of the browser being used; should be one of 
	 * {android, chrome, firefox, htmlunit, ie, 
	 * iPhone, iPad, opera, safari}
	 * @see https://github.com/SeleniumHQ/selenium/wiki/DesiredCapabilities
	 * 
	 * @return An initialized instance of WebInstance
	 */
	WebInstance initialize() {
		log.traceEntry();
		
		// Raise warning messages (and optionally runtime errors) if the parameters' values are invalid)
		this.validateInputs();
		
		// set driver exe paths
		System.setProperty("webdriver.chrome.driver", this.getChromeDriverPath());
		System.setProperty("webdriver.gecko.driver", this.getFirefoxDriverPath());
		System.setProperty("webdriver.ie.driver", this.getIEDriverPath());
		
		
		log.info("Browser: [{}]", this.driverName);
		switch(this.driverName.toUpperCase()) {
		case "CHROME":
			ChromeOptions co = new ChromeOptions();
			if(this.isHeadless()) {
				co.addArguments("--headless");
			}
			
			// TODO add support for more capabilities in future if needed
			
			driver = new ChromeDriver(co);
			log.info("driver hashcode: [{}]", driver.hashCode());
			
			break;
			
		case "FIREFOX":
			FirefoxOptions firefoxOptions = new FirefoxOptions();
			
			if(this.isHeadless()) {
				
				FirefoxBinary firefoxBinary = new FirefoxBinary();
			    firefoxBinary.addCommandLineOptions("--headless");
			    firefoxOptions.setBinary(firefoxBinary);
			}
			
			// TODO add support for more capabilities in future if needed
			
			driver = new FirefoxDriver(firefoxOptions);
			
			break;
			
		case "IE":
			// TODO add support for capabilities in future if needed
			driver = new InternetExplorerDriver();
			break;
			
		default:
			throw new UnsupportedOperationException("This browser type is either not valid or isn't currently supported");
		}

		driver.manage().timeouts().implicitlyWait(getImplicitWaitSeconds(), TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(getPageLoadTimeoutSeconds(), TimeUnit.SECONDS);
		
		log.traceExit();
		return this;
	}



	/**
	 * Raise warning messages (and optionally runtime errors) 
	 * if the parameters' values are invalid)
	 */
	private void validateInputs() {
		log.traceEntry();
		
		//Check validity of headless mode
		if(!this.getDriverName().equalsIgnoreCase("CHROME") 
				&& !this.getDriverName().equalsIgnoreCase("FIREFOX") 
				&& this.isHeadless()) {
			log.warn("Headless mode is ignored for browser: [{}]", this.getDriverName());
		}else {
			log.info("Headless mode: [{}] for browser: [{}]", this.isHeadless(), this.getDriverName());
		}
		
		log.traceEntry();
	}

	///////////////////////////////////////////////////////////////////////////
	// TODO proxy support
	// TODO BrowserStack & SauceLabs support
	// TODO Set custom browser profile
	// TODO Set Grid profile
	// TODO Cookie management
	// TODO Window Size and position
}
