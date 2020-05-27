package com.rateconversion.utils;

import java.util.HashMap;
import java.util.Map;

public class LatestConversionUtils {
	
	public static String BaseUrl = "https://api.ratesapi.io/api";
	public static String LatestVersionEndpointUrl ="/latest";


	public Map<String, String> setheadersForCorpDeptSnYr() {
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Accept", "*/*");
		headers.put("Content-type", "application/json");
		return headers;
	}
	

}
