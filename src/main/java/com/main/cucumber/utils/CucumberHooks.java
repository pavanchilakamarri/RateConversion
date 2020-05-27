package com.main.cucumber.utils;

import org.junit.AfterClass;
import org.junit.BeforeClass;

public class CucumberHooks {
	
	@BeforeClass
	public void test() {
		System.out.println("testing");
	}

	@AfterClass
	 public static void writeExtentReport() {
	// Reporter.loadXMLConfig(new File("./src/main/resources/extent-config.xml"));
	 }

}
