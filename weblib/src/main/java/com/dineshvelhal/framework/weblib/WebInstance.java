package com.dineshvelhal.framework.weblib;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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
	final Browser driverName = Browser.CHROME;


	/**
	 * ImplicitlyWait time in seconds
	 */
	@Getter(AccessLevel.PUBLIC)
	@Builder.Default 
	final int implicitWaitSeconds = 15;

	/**
	 * Explicit Wait time in seconds (will be used by smartFind method
	 */
	@Getter(AccessLevel.PUBLIC)
	@Builder.Default 
	final int explicitWaitSeconds = 15;


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
	private String chromeDriverPath = "";

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


	/**
	 * Whether smart findElement is enabled or not (Default = False)
	 */
	@Getter(AccessLevel.PUBLIC)
	@Builder.Default
	private boolean smartFind = false;


	/**
	 * Whether Grid based remote execution is needed
	 */
	@Getter(AccessLevel.PUBLIC)
	@Builder.Default
	private boolean remote = false;

	@Getter(AccessLevel.PUBLIC)
	@Builder.Default
	private String gridURL = "";
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
	 * @throws MalformedURLException 
	 */
	WebInstance initialize() throws MalformedURLException {
		log.traceEntry();

		// Raise warning messages (and optionally runtime errors) if the parameters' values are invalid)
		this.validateInputs();

		// set driver exe paths
		System.setProperty("webdriver.chrome.driver", this.getChromeDriverPath());
		System.setProperty("webdriver.gecko.driver", this.getFirefoxDriverPath());
		System.setProperty("webdriver.ie.driver", this.getIEDriverPath());


		log.info("Browser: [{}]", this.driverName);

		boolean remoteChromeBrowser = 			isRemote() && getDriverName() == Browser.CHROME && isHeadless() == false;
		boolean remoteChromeBrowserHeadless = 	isRemote() && getDriverName() == Browser.CHROME && isHeadless();
		boolean localChromeBrowser = 			isRemote() == false && getDriverName() == Browser.CHROME && isHeadless() == false;
		boolean localChromeBrowserHeadless = 	isRemote() == false && getDriverName() == Browser.CHROME && isHeadless();

		boolean remoteFirefoxBrowser = 			isRemote() && getDriverName() == Browser.FIREFOX && isHeadless() == false;
		boolean remoteFirefoxBrowserHeadless = 	isRemote() && getDriverName() == Browser.FIREFOX && isHeadless();
		boolean localFirefoxBrowser = 			isRemote() == false && getDriverName() == Browser.FIREFOX && isHeadless() == false;
		boolean localFirefoxBrowserHeadless = 	isRemote() == false && getDriverName() == Browser.FIREFOX && isHeadless();

		boolean localIEBrowser = getDriverName() == Browser.IE;

		if(remoteChromeBrowser) {
			if (getGridURL().equalsIgnoreCase("")) {
				throw new UnsupportedOperationException("Grid URL is needed to carry out grid based execution");
			}
			Capabilities chromeCapabilities = DesiredCapabilities.chrome();
			driver = new RemoteWebDriver(new URL(getGridURL()), chromeCapabilities);
			log.info("driver hashcode: [{}]", driver.hashCode());

		}else if(remoteChromeBrowserHeadless) {
			if (getGridURL().equalsIgnoreCase("")) {
				throw new UnsupportedOperationException("Grid URL is needed to carry out grid based execution");
			}
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--headless");
			driver = new RemoteWebDriver(new URL(getGridURL()), options);
			log.info("driver hashcode: [{}]", driver.hashCode());

		}else if(localChromeBrowser) {
			ChromeOptions options = new ChromeOptions();
			driver = new ChromeDriver(options);
			log.info("driver hashcode: [{}]", driver.hashCode());

		}else if(localChromeBrowserHeadless) {
			ChromeOptions co = new ChromeOptions();
			co.addArguments("--headless");
			driver = new ChromeDriver(co);
			log.info("driver hashcode: [{}]", driver.hashCode());
		}else if(remoteFirefoxBrowser) {
			Capabilities firefoxCapabilities = DesiredCapabilities.firefox();
			driver = new RemoteWebDriver(new URL(getGridURL()), firefoxCapabilities);
			log.info("driver hashcode: [{}]", driver.hashCode());
			
		}else if(remoteFirefoxBrowserHeadless) {
			FirefoxOptions firefoxOptions = new FirefoxOptions();

			FirefoxBinary firefoxBinary = new FirefoxBinary();
			firefoxBinary.addCommandLineOptions("--headless");
			firefoxOptions.setBinary(firefoxBinary);
			
			driver = new RemoteWebDriver(new URL(getGridURL()), firefoxOptions);
			log.info("driver hashcode: [{}]", driver.hashCode());
			
			
		}else if(localFirefoxBrowser) {
			FirefoxOptions firefoxOptions = new FirefoxOptions();
			driver = new FirefoxDriver(firefoxOptions);
			log.info("driver hashcode: [{}]", driver.hashCode());

		}else if (localFirefoxBrowserHeadless) {
			FirefoxOptions firefoxOptions = new FirefoxOptions();

			FirefoxBinary firefoxBinary = new FirefoxBinary();
			firefoxBinary.addCommandLineOptions("--headless");
			firefoxOptions.setBinary(firefoxBinary);
			driver = new FirefoxDriver(firefoxOptions);
			log.info("driver hashcode: [{}]", driver.hashCode());

		}else if(localIEBrowser) {
			if(isRemote()) {
				log.warn("Remote execution is currently not supported with IE browser");
				throw new UnsupportedOperationException("Remote execution is currently not supported with IE browser");
			}
			driver = new InternetExplorerDriver();
			log.info("driver hashcode: [{}]", driver.hashCode());

		}else {
			throw new UnsupportedOperationException("This browser type is either not valid or isn't currently supported");
		}

		/*switch(this.driverName) {
		case CHROME:
			ChromeOptions co = new ChromeOptions();
			if(this.isHeadless()) {
				co.addArguments("--headless");
			}

			// TODO add support for more capabilities in future if needed

			driver = new ChromeDriver(co);
			log.info("driver hashcode: [{}]", driver.hashCode());

			break;

		case FIREFOX:
			FirefoxOptions firefoxOptions = new FirefoxOptions();

			if(this.isHeadless()) {

				FirefoxBinary firefoxBinary = new FirefoxBinary();
				firefoxBinary.addCommandLineOptions("--headless");
				firefoxOptions.setBinary(firefoxBinary);
			}

			// TODO add support for more capabilities in future if needed

			driver = new FirefoxDriver(firefoxOptions);

			break;

		case IE:
			// TODO add support for capabilities in future if needed
			log.warn("Headless mode is ignored for IE");
			driver = new InternetExplorerDriver();
			break;

		default:
			throw new UnsupportedOperationException("This browser type is either not valid or isn't currently supported");
		}*/

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

		log.traceEntry();
	}


	/**
	 * Returns the WebElement by finding it using smartFind or normal findElement.
	 * Using the explicit wait specified as argument
	 * 
	 * @param by locator
	 * @param seconds Wait in seconds
	 * @return WebElement
	 */
	public WebElement smartFindElement(By by, int seconds) {
		log.traceEntry("by = {}, seconds: {}", by, seconds);

		WebElement e = null;

		if(isSmartFind()) {
			log.info("Smart findElement is true");
			log.info("Using explicit wait: {} seconds", seconds);

			WebDriverWait wait = new WebDriverWait(getDriver(), seconds);
			e = wait.until(ExpectedConditions.elementToBeClickable(by));
		}else {
			log.info("Smart findElement is false. Good old findElement will be used.");
			e = getDriver().findElement(by);
		}

		log.traceExit();
		return e;
	}


	/**
	 * Returns the WebElement by finding it using smartFind or normal findElement.
	 * Using the explicit wait specified in the field getExplicitWait
	 * 
	 * @param by locator
	 * @param seconds Wait in seconds
	 * @return WebElement
	 */
	public WebElement smartFindElement(By by) {
		log.traceEntry("by = {}", by);

		WebElement e = smartFindElement(by, getExplicitWaitSeconds());

		log.traceExit();
		return e;
	}

	///////////////////////////////////////////////////////////////////////////
	// TODO proxy support
	// TODO BrowserStack & SauceLabs support
	// TODO Set custom browser profile
	// TODO Set Grid profile
	// TODO Cookie management
	// TODO Window Size and position
}
