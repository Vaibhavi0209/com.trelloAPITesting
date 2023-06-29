package com.qa.tests;

import java.io.IOException;

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

public class postAPITest extends TestBase{
	
	String url;
	String bordName = "";
	RestClient restClient;
	HttpResponse<String> httpResponse;
	
	public postAPITest(){
		super();
	}
	
	@BeforeMethod
	public void setUp(){
		url = prop.getProperty("url") +""+ prop.getProperty("serviceURL");
		System.out.println("url : "+url);
		
	}
	
	@Test(priority = 0)
	public void postAPITest() throws ClientProtocolException, IOException, UnirestException, ParseException{
		restClient = new RestClient();
		
		
		BordVO bordVO = new BordVO();
		bordName = "Bord "+TestUtils.genarateRandomNumber();
		System.out.println(bordName);
		bordVO.setName(bordName);
		bordVO.setDesc(bordName + " is Generated");
		
		//object to json file:
		ObjectMapper mapper = new ObjectMapper();
		/*mapper.writeValue(new File("C:\\Users\\mygoa\\Desktop\\java\\My_Project\\git\\com.trelloAPITesting\\src\\main\\java\\com\\qa\\vo\\bord.json"), bordVO);
		
		//java object to json in String:
		String usersJsonString = mapper.writeValueAsString(bordVO);
		System.out.println("usersJsonString : "+usersJsonString);*/
		
		httpResponse = restClient.postRequest(url, bordVO);
		
		int statusCode = httpResponse.getStatus();
		System.out.println("statusCode : "+statusCode);
		
		Assert.assertEquals(statusCode, HttpStatus.SC_OK,"Status code is not 200");
		
		String responseBody = httpResponse.getBody().toString();
		System.out.println("responseJson : "+responseBody);
		JSONParser parse = new JSONParser();
		JSONObject data_obj = (JSONObject) parse.parse(responseBody);
		
		BordVO bordVO2 = mapper.readValue(responseBody, BordVO.class);
		System.out.println(bordVO2);
		
		System.out.println(bordVO2.getId());
		Assert.assertEquals(bordVO2.getName(), bordVO.getName(),"Name not matched");
		Assert.assertEquals(bordVO2.getDesc(), bordVO.getDesc(),"SDescription not matched");
		
	}

}
