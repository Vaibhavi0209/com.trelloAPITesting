package com.qa.utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.qa.vo.BordVO;

public class TestUtils {

	public static String getValueByJPath(JSONObject responsejson, String jpath){
		Object obj = responsejson;
		for(String s : jpath.split("/")) 
			if(!s.isEmpty()) 
				if(!(s.contains("[") || s.contains("]")))
					obj = ((JSONObject) obj).get(s);
				else if(s.contains("[") || s.contains("]"))
					obj = ((JSONArray) ((JSONObject) obj).get(s.split("\\[")[0])).get(Integer.parseInt(s.split("\\[")[1].replace("]", "")));
		return obj.toString();
	}
	
	public static int genarateRandomNumber(){
		return (int) (Math.random() * 1000);
	}
}
