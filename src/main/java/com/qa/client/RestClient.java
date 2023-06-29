package com.qa.client;

import java.io.IOException;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.json.simple.parser.ParseException;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.qa.base.TestBase;
import com.qa.vo.BordVO;

public class RestClient extends TestBase{
	
	String key = prop.getProperty("key");
	String token = prop.getProperty("token");

	public HttpResponse<String> getResponse(String url) throws ClientProtocolException, IOException, UnirestException, ParseException{
		
		/*CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse httpResponse = httpClient.execute(httpGet);*/
		
		HttpResponse<String> httpResponse = Unirest.get(url)
				  .header("Accept", "application/json")
				  .queryString("key", key)
				  .queryString("token", token)
				  .asString();
		System.out.println("httpResponse : "+httpResponse.getBody().toString());
		
		return httpResponse;
		
		
	}
	
	/*public CloseableHttpResponse get(String url, HashMap<String, String> headerMap) throws ClientProtocolException, IOException{
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet(url); //http get request
		
		for(Map.Entry<String,String> entry : headerMap.entrySet()){
			httpget.addHeader(entry.getKey(), entry.getValue());
		}
		CloseableHttpResponse closebaleHttpResponse =  httpClient.execute(httpget); //hit the GET URL
		return closebaleHttpResponse;
			
		}*/
public HttpResponse<String> postRequest(String url,BordVO bordVO) throws ClientProtocolException, IOException, UnirestException, ParseException{
	
	HttpResponse<String> httpResponse = Unirest.post(url)
			  .queryString("name",bordVO.getName())
			  .queryString("desc",bordVO.getDesc())
			  .queryString("key", key)
			  .queryString("token", token)
			  .asString();
		
		System.out.println("httpResponse : "+httpResponse.getBody().toString());
		
		return httpResponse;
		
		
	}
}
