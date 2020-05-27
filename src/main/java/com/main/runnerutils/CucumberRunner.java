package com.main.runnerutils;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Before;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.main.configurations.Config;
import com.main.configurations.CucumberConfig;

import cucumber.api.Scenario;
import cucumber.runtime.Runtime;
import cucumber.runtime.RuntimeOptions;
import cucumber.runtime.formatter.PluginFactory;
import cucumber.runtime.io.MultiLoader;
import cucumber.runtime.io.ResourceLoaderClassFinder;

public class CucumberRunner {
	public static long startTime = 0;
	public static String testOutputFolder = "";
	public static String fileSeparator = System.getProperty("file.separator");
	public static ExtentHtmlReporter header;
	public static ExtentReports reporter;
	public static String scenarioName;

	@Before
	public void setUp(Scenario scenario) {
		System.out.println("setUp class");
		scenarioName=scenario.getName();
	}
	
	public static void beforeAll() {
		System.out.println("Before all");
		startTime = System.currentTimeMillis();
		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy_hh-mm-ss_a");
		Calendar cal = Calendar.getInstance();
		String timeStamp = dateFormat.format(cal.getTime());
		testOutputFolder = "." + fileSeparator + "Reports" + fileSeparator + "test-output-" + timeStamp;
		System.out.println(testOutputFolder);
		File reportsFolder = new File(testOutputFolder);
		reportsFolder.mkdir();
		header = new ExtentHtmlReporter(reportsFolder + "/index.html");
		header.config().setReportName(Config.getInstance().getTestConfig().getProjectName());
		reporter = new ExtentReports();
		reporter.attachReporter(header);
		

	}

	public void run(String tags, String features, String glue) {

		CucumberConfig cucumberConfig = Config.getInstance().getCucumberConfig();
		List<String> arguments = new ArrayList<>();
		for (String feature : features.split(",")) {
			arguments.add(feature);
		}
		if (tags != null && !tags.equalsIgnoreCase("null")) {
			arguments.add("--tags");
			arguments.add(tags);
		}
		arguments.add("--plugin");
		arguments.add("json:target/Destination/cucumber.json");
		arguments.add("--plugin");
		arguments.add("rerun:target/Destination/rerun.txt");
		if (!cucumberConfig.getPlugins().isEmpty()) {
			for (String plugin : cucumberConfig.getPlugins().split(",")) {
				arguments.add("--plugin");
				arguments.add(plugin);
			}
		}
		glue = glue + ",org.gaptech.platform.cucumberutils";
		String[] gluePackages = glue.split(",");
		for (String packages : gluePackages) {
			if (!packages.contains("none")) {
				arguments.add("--glue");
				arguments.add(packages);
			}
		}
		if (cucumberConfig.getStrict()) {
			arguments.add("--strict");
		}

		if (cucumberConfig.getMonochrome()) {
			arguments.add("--monochrome");
		}
		if (cucumberConfig.getDryRun()) {
			arguments.add("--dry-run");
		}

		final String[] argv = arguments.toArray(new String[0]);
		try {
			executeCucumberTests(argv);
		} catch (Exception e) {
			// log.error("", e);
		}

	}

	public byte executeCucumberTests(final String[] argv) {
		Runtime runtime = null;
		try {
			RuntimeOptions runtimeOptions = new RuntimeOptions(new ArrayList(Arrays.asList(argv)));

			MultiLoader resourceLoader = new MultiLoader(CucumberRunner.class.getClassLoader());
			ResourceLoaderClassFinder classFinder = new ResourceLoaderClassFinder(resourceLoader,
					CucumberRunner.class.getClassLoader());
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			/*EventBus eventBus = new TimeServiceEventBus(TimeService.SYSTEM);
			FeatureLoader featureLoader = new FeatureLoader(resourceLoader);
			BackendSupplier backendSupplier = new BackendModuleBackendSupplier(resourceLoader, classFinder,
					runtimeOptions);
			FeatureSupplier featureSupplier = new FeaturePathFeatureSupplier(featureLoader, runtimeOptions);
			Filters filters = new Filters(runtimeOptions);
			RunnerSupplier runnerSupplier = new ThreadLocalRunnerSupplier(runtimeOptions, eventBus, backendSupplier);
			ExecutorService executor = Executors.newFixedThreadPool(runtimeOptions.getThreads());
			Plugins plugins = new Plugins(classLoader, new PluginFactory(), eventBus, runtimeOptions);
			runtime = new Runtime(plugins, runtimeOptions, eventBus, filters, runnerSupplier, featureSupplier,
					executor);
			runtime.run();*/

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return runtime.exitStatus();
	}

	public static void afterAll() {
		System.out.println("After all");
		reporter.flush();
	}

}
