package com.main.restassured.utils;

import java.util.Map;

import groovy.json.JsonException;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.RestAssuredConfig;
import io.restassured.config.SSLConfig;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestAssuredUtils {

	/**
	 * This method is used to build specifications that are required for sending
	 * request to the server. This method will take query params as input.
	 *
	 * @param baseURL
	 * @param endpointUrl
	 *            : Path of desired resource at server
	 * @param requestBody
	 *            : Input Body
	 * @param headers
	 *            : Headers that are needed to access service
	 * @param params
	 *            : query params
	 * @return RequestSpecification
	 */
	public static RequestSpecification buildRequestSpecification(String baseURL, String endpointUrl, String requestBody,
			Map<String, String> headers, Map<String, String> params) {
		RequestSpecBuilder builder = new RequestSpecBuilder();
		try {
			builder.setBaseUri(baseURL);
			builder.setBasePath(endpointUrl);
			if (requestBody != null) {
				builder.setBody(requestBody);
			}
			if (headers != null) {
				builder.addHeaders(headers);
			}
			builder.setConfig(RestAssuredConfig.config().sslConfig(new SSLConfig().relaxedHTTPSValidation()));
			builder.setUrlEncodingEnabled(false);
			if (params != null) {
				builder.addPathParams(params);
			}

		} catch (Exception e) {
			// log.error("", e);
		}
		return builder.build();
	}

	/*
	 * This method executes GET request with parameters and helps in retrieving information from
	 * server.
	 *
	 * @param baseURL: Base URL of the server
	 * 
	 * @param endpointUrl : Path of desired resource at server
	 * 
	 * @param headers : Headers that are needed to access service
	 * 
	 * @param params: Query or path parameters
	 * 
	 * @return Response
	 */
	public static Response executeGetRequestWithParameters(String baseURL, String endpointUrl,
			Map<String, String> headers, Map<String, String> params) {
		Response valResponse = null;
		try {
			RequestSpecification requestSpec = buildRequestSpecification(baseURL, endpointUrl, null, headers, params);
			valResponse = RestAssured.given().spec(requestSpec).log().uri().get();
			// Reporter.printAPICallDetails(baseURL + endpointUrl, "", headers.toString(),
			// valResponse.getHeaders().toString(), valResponse.asString());

		} catch (JsonException e) {
			// log.error("", e);
			System.out.println(e);
		}
		return valResponse;
	}

	/*
	 * This method executes GET request without parameters and helps in retrieving information from
	 * server.
	 *
	 * @param baseURL: Base URL of the server
	 * 
	 * @param endpointUrl : Path of desired resource at server
	 * 
	 * @param headers : Headers that are needed to access service
	 * 
	 * @return Response
	 */
	public static Response executeGetRequestWithoutParameters(String baseURL, String endpointUrl,
			Map<String, String> headers) {
		Response valResponse = null;
		try {
			RequestSpecification requestSpec = buildRequestSpecification(baseURL, endpointUrl, null, headers, null);
			valResponse = RestAssured.given().spec(requestSpec).log().uri().get();
			// Reporter.printAPICallDetails(baseURL + endpointUrl, "", headers.toString(),
			// valResponse.getHeaders().toString(), valResponse.asString());

		} catch (JsonException e) {
			// log.error("", e);
		}
		return valResponse;
	}

	public static int getStatusCode(Response response) {
		 try {
	            return response.getStatusCode();
	        } catch (Exception e) {
	           e.printStackTrace();
	            return 0;
	        }
	}

	 public static String getStringValueFromJSONRequest(String requestJson, String jsonpath) {
	        try {
	            JsonPath json = new JsonPath(requestJson);
	            return json.getJsonObject(jsonpath).toString();
	        } catch (Exception e) {
	           // log.error("", e);
	            return jsonpath;
	        }

	    }

}
