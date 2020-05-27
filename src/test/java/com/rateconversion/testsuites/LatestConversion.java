package com.rateconversion.testsuites;

import java.util.ArrayList;

import org.junit.Assert;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.GherkinKeyword;
import com.aventstack.extentreports.gherkin.model.Feature;
import com.aventstack.extentreports.gherkin.model.Scenario;
import com.main.common.utils.ExcelCustomUtils;
import com.main.runnerutils.RunCucumberTest;
import com.rateconversion.services.LatestConversionService;
import com.rateconversion.utils.LatestConversionUtils;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.Response;

public class LatestConversion {

	String testdata = "./src/test/resources/TestData.xlsx";
	ExtentTest feature = null;
	ExtentTest logInfo = null;
	Response response = null;
	LatestConversionService latestConversionService;
	String baseUrl = null;
	String latestConversionEndPointUrl = null;

	public LatestConversion() {

		latestConversionService = new LatestConversionService();
		feature = RunCucumberTest.reporter.createTest(Feature.class, "Conversion Rate for the Past dates");
		feature = feature.createNode(Scenario.class, "PASSED test case");
	}

	@Given("^I set endpoint for GET Latest Rate conversion$")
	public void I_set_endpoint_for_GET_Latest_Rate_conversion() throws ClassNotFoundException {
		logInfo = feature.createNode(new GherkinKeyword("Given"), "I set endpoint for GET Latest Rate conversion");
		baseUrl = LatestConversionUtils.BaseUrl;
		latestConversionEndPointUrl = LatestConversionUtils.LatestVersionEndpointUrl;

	}

	@When("^I send GET HTTP Request$")
	public void i_retrieve_latest_foreign_exchange_rates_resource() throws Throwable {
		logInfo = feature.createNode(new GherkinKeyword("When"), "I send GET HTTP Request");
		System.out.println("Into the When block...");
		logInfo.info("running given step");
		response = latestConversionService.getLatestConversionRates(baseUrl, latestConversionEndPointUrl);
		logInfo.info("Response of the service is :: " + response);
	}

	@Then("^I get a OK response with status (.+)$")
	public void i_get_a_ok_response_with_status(int statuscode) throws Throwable {
		logInfo = feature.createNode(new GherkinKeyword("Then"), "I get OK response with status " + statuscode);
		logInfo.info("Expected Status :: " + statuscode + " Actual status code :: "
				+ latestConversionService.getStatusCode(response));
		Assert.assertEquals("HTTP code does not matched with expected one...", statuscode,
				latestConversionService.getStatusCode(response));
	}

	@And("^validating response for (.+) (.+)$")
	public void validating_response(String country, String currency) throws ClassNotFoundException {
		logInfo = feature.createNode(new GherkinKeyword("And"),
				"validating response of " + country + " :: " + currency);
		System.out.println("rates." + country);

		latestConversionService.validateRateConversionForCountry(response.asString(), country, currency);

	}

	@Then("^validate response for multiple countries currency$")
	public void validate_response_for_multiple_countries_currency() throws ClassNotFoundException {
		logInfo = feature.createNode(new GherkinKeyword("And"), "validating response for multiple countries currency");
		ArrayList<ArrayList<String>> DBrow = null;

		DBrow = ExcelCustomUtils.getExcelRows(testdata, 0);
		for (int i = 1; i < DBrow.size(); i++) {
			System.out.println("checking data from excel col1::" + DBrow.get(i).get(0).toString() + " col2:: "
					+ DBrow.get(i).get(1).toString());
			boolean flag = latestConversionService.validateRateConversionForCountry(response.asString(),
					DBrow.get(i).get(0).toString(), DBrow.get(i).get(1).toString());
			if (flag)
				logInfo.pass("currency for country :: " + DBrow.get(i).get(0).toString()
						+ " is matched and currency is :: " + DBrow.get(i).get(1).toString());
			else
				logInfo.fail("currency for country :: " + DBrow.get(i).get(0).toString()
						+ " does not matched and currency is :: " + DBrow.get(i).get(1).toString());
		}

	}

}
