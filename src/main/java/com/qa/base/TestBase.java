package com.qa.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class TestBase {

	public static Properties prop;
	
	public TestBase() {

		try {
			
			File f = new File(System.getProperty("user.dir")+"/src/main/java/com/qa/config/config.properties");
			FileInputStream fis = new FileInputStream(f);
			
			prop = new Properties();
			prop.load(fis);
			
			System.out.println(prop.getProperty("url"));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
