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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.utils.TestUtils;
import com.qa.vo.BordVO;

public class PutAPITest extends TestBase{

	String url;
	String bordId = "649da6ed8275f4bc91d82606";
	RestClient restClient;
	HttpResponse<String> httpResponse;
	BordVO bordVO;
	
	public PutAPITest(){
		super();
	}
	
	@BeforeMethod
	public void setUp(){
		System.out.println("****************** PutAPITest *****************");
		url = prop.getProperty("url") +""+ prop.getProperty("serviceURL")+""+bordId;
		System.out.println("url : "+url);
		
	}
	
	@Test(priority=2)
	public void getAPITest()throws ClientProtocolException, IOException, UnirestException, ParseException {
		restClient = new RestClient();
		
		httpResponse = restClient.getResponse(url);
		
		int getstatusCode = httpResponse.getStatus();
		System.out.println("getstatusCode : "+getstatusCode);
		Assert.assertEquals(getstatusCode, HttpStatus.SC_OK,"Status code is not 200");
		
		String responseBody = httpResponse.getBody().toString();
		System.out.println("responseJson : "+responseBody);
		
		ObjectMapper mapper = new ObjectMapper();
		bordVO = mapper.readValue(responseBody, BordVO.class);
		System.out.println(bordVO);
		
		String bordName = "Bord "+TestUtils.genarateRandomNumber();
		System.out.println(bordName);
		bordVO.setName(bordName);
		
		url = prop.getProperty("url") +""+ prop.getProperty("serviceURL")+""+bordVO.getId();
		httpResponse = restClient.putRequest(url, bordVO);
		
		int putstatusCode = httpResponse.getStatus();
		System.out.println("putstatusCode : "+putstatusCode);
		
		Assert.assertEquals(putstatusCode, HttpStatus.SC_OK,"Status code is not 200");
		
		String putresponseBody = httpResponse.getBody().toString();
		System.out.println("putresponse Json : "+putresponseBody);
		
		BordVO bordVO2 = mapper.readValue(responseBody, BordVO.class);
		System.out.println(bordVO2);
		
		System.out.println(bordVO2.getId());
		Assert.assertEquals(bordVO2.getId(), bordVO.getId(),"id not matched");
	}
}
