package com.rateconversion.utils;

import java.util.HashMap;
import java.util.Map;

public class PastConversionUtils {
	
	public static String BaseUrl = "https://api.ratesapi.io";
	public static String LatestVersionEndpointUrl ="/api/{date}";


	public Map<String, String> setheadersForPastConversion() {
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Accept", "*/*");
		headers.put("Content-type", "application/json");
		return headers;
	}
	
	public Map<String, String> setParametersForPastConversion(String date){
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("date",date);
		return parameters;
	}

}
