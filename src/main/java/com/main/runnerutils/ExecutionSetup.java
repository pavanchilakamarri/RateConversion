package com.main.runnerutils;

import static org.junit.Assert.assertNotNull;
import static org.testng.Assert.assertNotNull;

import com.main.configurations.Config;
import com.main.configurations.CucumberConfig;
import com.main.configurations.TestConfig;

public class ExecutionSetup {

	public static Config config;
	public static String tags;
	public static String type;
	public static String execution;
	public static int retryCount;
	public static String projectName;
	public static boolean isWebTest = false;
	public static boolean isLocalExecution = false;
	public static boolean isCucumberTest = false;

	public static void main(String arg[]) {

		config = Config.getInstance();
		/*
		 * This condition is used whenever we want to run our test cases from external
		 * source
		 */
		if (arg.length != 0) {
			System.out.println("This block is for running code from Jenkins by picking some values...");
		}

		TestConfig testConfig = config.getTestConfig();
		assertNotNull(testConfig, "Test property file is not defined... Please define file before running it");
		tags = config.getTestConfig().getTags().toLowerCase();
		type = config.getTestConfig().getType().toLowerCase();
		execution = config.getTestConfig().getExecution();
		projectName = config.getTestConfig().getProjectName().toUpperCase();
		retryCount = config.getTestConfig().getRetryCount();

		/*
		 * This condition is for web based application
		 */
		if (isWebTest && execution.equalsIgnoreCase("local")) {

		}

		if (execution.equalsIgnoreCase("local")) {
			System.out.println(execution);
			isLocalExecution = true;
		} else if (execution.equalsIgnoreCase("remote")) {
			// This is for execution of test cases from remote
		} else {
			throw new RuntimeException(
					"Execution set-up value is mentioned with wrong value. Values supported are :: local, remote ");
		}

		if (type.equalsIgnoreCase("junit")) {
			//This is for running Junit test cases 
		}else if (type.equalsIgnoreCase("cucumber")) {
			isCucumberTest=true;
			if(isWebTest) {
				
			}else {
				CucumberConfig cucumberConfig=config.getCucumberConfig();
				CucumberRunner cucumberRunner=new CucumberRunner();
				System.out.println(cucumberConfig.getFeatures().toString());
				CucumberRunner.beforeAll();
				cucumberRunner.run(tags, cucumberConfig.getFeatures(), cucumberConfig.getGlue());
				CucumberRunner.afterAll();
				assertNotNull(cucumberConfig,"Cucumber properties not mentioned in yaml file.");
				
			}
		}

		
		
	}
}
