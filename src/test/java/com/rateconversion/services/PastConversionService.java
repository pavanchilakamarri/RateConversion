package com.rateconversion.services;

import java.time.LocalDate;
import com.main.restassured.utils.RestAssuredUtils;
import com.rateconversion.utils.PastConversionUtils;

import io.restassured.response.Response;

public class PastConversionService {

	public PastConversionUtils pastConversionUtils;

	public PastConversionService() {

		pastConversionUtils = new PastConversionUtils();
	}

	public Response getPastConversionRates(String baseUrl, String endPointUrl, String date) {
		Response res = RestAssuredUtils.executeGetRequestWithParameters(baseUrl, endPointUrl,
				pastConversionUtils.setheadersForPastConversion(),pastConversionUtils.setParametersForPastConversion(date));
		System.out.println("Latest Conversion Rates::" + res.getBody().asString());
		return res;
	}

	public int getStatusCode(Response response) {
		int res = RestAssuredUtils.getStatusCode(response);
		return res;
	}

	public boolean validateRateConversionForCountry(String response, String country, String currency) {
		String jsonpath = "rates." + country;
		if (RestAssuredUtils.getStringValueFromJSONRequest(response, jsonpath).equals(currency))
			return true;
		else
			return false;

	}
	
	public LocalDate getCurrentDate() {
		
		return java.time.LocalDate.now();		
	}

}
