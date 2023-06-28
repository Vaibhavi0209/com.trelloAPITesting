package com.qa.tests;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
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

public class GetApiTest extends TestBase{

	String url;
	String bordId = "649c4944abcfa2fd17116c2f";
	RestClient restClient;
	HttpResponse<String> httpResponse;
	//CloseableHttpResponse closebaleHttpResponse;
	
	public GetApiTest(){
		super();
	}
	
	@BeforeMethod
	public void setUp(){
		url = prop.getProperty("url") +""+ prop.getProperty("serviceURL")+""+bordId;
		System.out.println("url : "+url);
		
	}
	
	@Test
	public void getAPITest()throws ClientProtocolException, IOException, UnirestException, ParseException {
		restClient = new RestClient();
		httpResponse = restClient.getResponse(url);
		
		int statusCode = httpResponse.getStatus();
		System.out.println("statusCode : "+statusCode);
		Assert.assertEquals(statusCode, HttpStatus.SC_OK,"Status code is not 200");
		
		String responseBody = httpResponse.getBody().toString();
		System.out.println("responseJson : "+responseBody);
		
		JSONParser parse = new JSONParser();
		JSONObject data_obj = (JSONObject) parse.parse(responseBody);
		/*Set<String> keySet= data_obj.keySet();
		for (String key : keySet) {
			System.out.println(key +" = "+data_obj.get(key));
		}*/
		
		Assert.assertEquals(data_obj.get("name"), "Bord 380","Bord name is not correct");
		Assert.assertEquals(data_obj.get("id"), bordId,"Wrong Bord id");
		Assert.assertEquals(data_obj.get("desc"), "This is Demo bord for Postman","Wrong Description");
		Assert.assertEquals(data_obj.get("pinned"), false,"Bord pinned status true");
		Assert.assertEquals(data_obj.get("url"), "https://trello.com/b/JamlsSAq/bord-380","Wrong Bord URL");
		
		//String permissionLevel = TestUtils.getValueByJPath(data_obj, "/prefs/permissionLevel");
		Map prefs = (Map) data_obj.get("prefs");
		Assert.assertEquals(prefs.get("permissionLevel"), "private","permission Level is not private");
		HashMap<String, List<String>> headerMap = httpResponse.getHeaders();
		System.out.println("Header: "+headerMap);
		
		Assert.assertEquals(headerMap.get("Access-Control-Allow-Origin").get(0), "*","Access-Control-Allow-Origin is not *");
	}
	
	/*@Test(priority=2)
	public void getAPITestWithHeaders() throws ClientProtocolException, IOException{
		restClient = new RestClient();
		
		HashMap<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("Accept", "application/json");
		headerMap.put("key", "f33c7f4c22c071213261107fcee3e407");
		headerMap.put("token", "ATTA35090ad6555e9a1542c3cd4e209c544f27660776d4e0f5404a5b51666c9197ee994B0D40");

		
		closebaleHttpResponse = restClient.get(url,headerMap);
		
		//a. Status Code:
		int statusCode = closebaleHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status Code--->"+ statusCode);
		
		Assert.assertEquals(statusCode, HttpStatus.SC_OK, "Status code is not 200");

		//b. Json String:
		String responseString = EntityUtils.toString(closebaleHttpResponse.getEntity(), "UTF-8");
		
		//JSONObject responseJson = new JSONObject(responseString);
		System.out.println("Response JSON from API---> "+ responseString);
		
		
		
		//c. All Headers
		Header[] headersArray =  closebaleHttpResponse.getAllHeaders();
		HashMap<String, String> allHeaders = new HashMap<String, String>();	
		for(Header header : headersArray){
			allHeaders.put(header.getName(), header.getValue());
		}	
		System.out.println("Headers Array-->"+allHeaders);
		
		
		
	}*/
}
