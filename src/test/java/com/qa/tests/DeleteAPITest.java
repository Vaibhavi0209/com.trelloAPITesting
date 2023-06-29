package com.qa.tests;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.qa.base.TestBase;
import com.qa.client.RestClient;

public class DeleteAPITest extends TestBase{

	String url;
	String bordId = "649c4944abcfa2fd17116c2f";
	RestClient restClient;
	HttpResponse<String> httpResponse;
	
	public DeleteAPITest(){
		super();
	}
	
	@BeforeMethod
	public void setUp(){
		System.out.println("****************** DeleteAPITest *****************");
		url = prop.getProperty("url") +""+ prop.getProperty("serviceURL")+""+bordId;
		System.out.println("url : "+url);
		
	}
	
	//@Test(priority=3)
	public void getAPITest()throws ClientProtocolException, IOException, UnirestException, ParseException {
		restClient = new RestClient();
		httpResponse = restClient.deleteRequest(url);
		
		int statusCode = httpResponse.getStatus();
		System.out.println("statusCode : "+statusCode);
		Assert.assertEquals(statusCode, HttpStatus.SC_OK,"Status code is not 200");
		
		String responseBody = httpResponse.getBody().toString();
		System.out.println("responseJson : "+responseBody);
		
	}
}
