package com.qa.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class TestBase {
	
	public int RESPONSE_STATUS_CODE_200 = 200; // OK
	public int RESPONSE_STATUS_CODE_201 = 201; // CREATED SUCCESSFULLY (POST) 
	public int RESPONSE_STATUS_CODE_400 = 400; // BAD REQUEST
	public int RESPONSE_STATUS_CODE_401 = 401; // UNAUTHORISED ACCESS
	public int RESPONSE_STATUS_CODE_404 = 404; // NOT FOUND
	public int RESPONSE_STATUS_CODE_407 = 407; // PROXY AUTHENTICATION REQUIRED
	public int RESPONSE_STATUS_CODE_500 = 500; // SERVER INTERNAL ERROR
	
	public Properties prop;
	
	public TestBase(){
		prop = new Properties();
		FileInputStream ip;
		try {							
			ip = new FileInputStream(System.getProperty("user.dir")+ "\\src\\main\\java\\com\\qa\\config\\config.properties");
			prop.load(ip);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
