package com.dineshvelhal.framework.weblib;

import java.io.File;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;



/**
 * This class provides a base for all Page Objects
 * @param runner Pass the WebRunner instance while constructing the object
 */
@RequiredArgsConstructor
@Log4j2
@ToString
public class PageBase {
	@NonNull
	@Getter(AccessLevel.PUBLIC)
	private WebRunner runner;

	public File screenshot() {
		log.traceEntry();

		log.traceExit();
		return runner.getScreenshot();
	}

	public String currentTitle() {
		log.traceEntry();


		log.traceExit();
		return runner.getTitle();
	}

	public String currentURL() {
		log.traceEntry();


		log.traceExit();
		return runner.getCurrentURL();
	}
	
	public String currentWindowHandle() {
		log.traceEntry();


		log.traceExit();
		return runner.getWindowHandle();
	}
	
	public PageBase refresh() {
		log.traceEntry();

		runner.getDriver().navigate().refresh();

		log.traceExit();
		return this;
	}
}
