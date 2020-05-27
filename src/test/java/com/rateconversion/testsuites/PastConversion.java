package com.rateconversion.testsuites;

import java.time.LocalDate;
import org.junit.Assert;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.GherkinKeyword;
import com.aventstack.extentreports.gherkin.model.Feature;
import com.aventstack.extentreports.gherkin.model.Scenario;
import com.main.runnerutils.RunCucumberTest;
import com.rateconversion.services.PastConversionService;
import com.rateconversion.utils.PastConversionUtils;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.Response;

public class PastConversion {

	String testdata = "./src/test/resources/TestData.xlsx";
	ExtentTest feature = null;
	ExtentTest logInfo = null;
	Response response = null;
	PastConversionService pastConversionService;
	String baseUrl = null;
	String pastConversionEndPointUrl = null;

	public PastConversion() {

		pastConversionService = new PastConversionService();
		feature = RunCucumberTest.reporter.createTest(Feature.class, "Conversion Rate for the Past dates");
		feature = feature.createNode(Scenario.class, "PASSED test case");
	}

	@Given("^I set endpoint for GET past Rate conversion$")
	public void I_set_endpoint_for_GET_Latest_Rate_conversion() throws ClassNotFoundException {
		logInfo = feature.createNode(new GherkinKeyword("Given"), "I set endpoint for GET past Rate conversion");
		baseUrl = PastConversionUtils.BaseUrl;
		pastConversionEndPointUrl = PastConversionUtils.LatestVersionEndpointUrl;

	}

	@When("^I send GET HTTP Request with (.+)$")
	public void i_retrieve_latest_foreign_exchange_rates_resource(String date) throws Throwable {
		logInfo = feature.createNode(new GherkinKeyword("When"), "I send GET HTTP Request with " + date);
		LocalDate dt = pastConversionService.getCurrentDate();
		LocalDate date1 = LocalDate.parse(date);
		if (date1.compareTo(dt) > 0) {
			logInfo.fail("Date cannot be greater than current date");
			Assert.assertTrue(false);
		}

		System.out.println("Into the When block...");
		logInfo.info("running given step");
		response = pastConversionService.getPastConversionRates(baseUrl, pastConversionEndPointUrl, date);
		logInfo.info("Response of the service is :: " + response.asString());
	}

	@Then("^I get OK response with status (.+)$")
	public void i_get_a_ok_response_with_status(int statuscode) throws Throwable {

		logInfo = feature.createNode(new GherkinKeyword("Then"), "I get OK response with status " + statuscode);

		logInfo.info("Expected Status :: " + statuscode + " Actual status code :: "
				+ pastConversionService.getStatusCode(response));
		Assert.assertEquals("HTTP code does not matched with expected one...", statuscode,
				pastConversionService.getStatusCode(response));
	}

	@And("^validating response of (.+) (.+)$")
	public void validating_response(String country, String currency) throws ClassNotFoundException {
		logInfo = feature.createNode(new GherkinKeyword("And"),	"validating response of " + country + " :: " + currency);

		System.out.println("rates." + country);

		boolean flag = pastConversionService.validateRateConversionForCountry(response.asString(), country, currency);
		if (flag)
			logInfo.pass("currency for country is matched");
		else
			logInfo.fail("currency for country does not matched");

	}
}
