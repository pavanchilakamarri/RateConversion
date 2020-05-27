package com.rateconversion.services;

import com.main.restassured.utils.RestAssuredUtils;
import com.rateconversion.utils.LatestConversionUtils;

import io.restassured.response.Response;


public class LatestConversionService {

	public LatestConversionUtils latestConversionUtils;
	public LatestConversionService() {

		latestConversionUtils=new LatestConversionUtils();
	}
	
	public Response getLatestConversionRates(String baseUrl, String endPointUrl) {
		Response res = RestAssuredUtils.executeGetRequestWithoutParameters(baseUrl,endPointUrl, latestConversionUtils.setheadersForCorpDeptSnYr());
		System.out.println("Latest Conversion Rates::" + res.getBody().asString());
		return res;
	}
	
	public int getStatusCode(Response response) {
		int res=RestAssuredUtils.getStatusCode(response);
		return res;
	}

	public boolean validateRateConversionForCountry(String response, String country, String currency) {
		String jsonpath = "rates." + country;
		if(RestAssuredUtils.getStringValueFromJSONRequest(response, jsonpath).equals(currency))
		return true;
		else
			return false;
			
			


	}
	
}
