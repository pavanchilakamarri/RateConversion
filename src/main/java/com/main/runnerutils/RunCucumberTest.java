package com.main.runnerutils;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.gherkin.model.Scenario;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.cucumber.listener.Reporter;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = "./src/test/resources/Features", glue = "com.rateconversion.testsuites", plugin = {
		"com.cucumber.listener.ExtentCucumberFormatter:Reports/report.html" },tags= {"@All"}, monochrome = true)
public class RunCucumberTest {
	public static long startTime = 0;
	public static String testOutputFolder = "";
	public static String fileSeparator = System.getProperty("file.separator");
	public static ExtentHtmlReporter header;
	public static ExtentReports reporter;
	public static Scenario scenarioName;

	@BeforeClass
	public static void setUp() {
		
		System.out.println("Before all");
		startTime = System.currentTimeMillis();
		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy_hh-mm-ss_a");
		Calendar cal = Calendar.getInstance();
		String timeStamp = dateFormat.format(cal.getTime());
		testOutputFolder = "." + fileSeparator + "Reports" + fileSeparator + "test-output-" + timeStamp;
		System.out.println(testOutputFolder);
		File reportsFolder = new File(testOutputFolder);
		reportsFolder.mkdir();
		header = new ExtentHtmlReporter(reportsFolder + "/report.html");
		header.config().setReportName("");
		reporter = new ExtentReports();
		reporter.attachReporter(header);

	}

		
	@AfterClass
	public static void afterAll() {
		System.out.println("After all");
		Reporter.loadXMLConfig(new File("./src/main/resources/extent-config.xml"));
		reporter.flush();
	}

}
