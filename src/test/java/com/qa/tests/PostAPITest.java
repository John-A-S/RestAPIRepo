package com.qa.tests;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.data.Users;

public class PostAPITest extends TestBase{
	
	TestBase testBase;
	String ServiceURL;
	String APIURL;
	String URL;
	RestClient restClient;
	CloseableHttpResponse httpResponse;
	
	
	@BeforeMethod
	public void setUp() {
		testBase = new TestBase();
		ServiceURL = prop.getProperty("serviceURL");
		APIURL = prop.getProperty("URL");
		URL = APIURL + ServiceURL;
	}
	
	@Test
	public void postAPITest() throws JsonGenerationException, JsonMappingException, IOException {
		
		System.out.println("---------------- postAPITest --------------");
		restClient = new RestClient();
		try {
			httpResponse  = restClient.get(URL);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		HashMap<String, String> headermap = new HashMap<String, String>();
		headermap.put("Content-Type", "application/json");

		//jackson API to convert the java object to JSON
		
		ObjectMapper mapper = new ObjectMapper();
		Users user = new Users("Tommy", "Leader"); //expected users object
		
		// convert JAVA object to JSON File
		mapper.writeValue(new File("C:\\Eclipse\\Eclipse-Workspace\\RestAPI\\src\\main\\java\\com\\qa\\data\\Users.json"), 
				user);
		
		// convert to Json in string  - MARSHALLING
		String usersJsonString =  mapper.writeValueAsString(user);
		System.out.println(usersJsonString);
		
		httpResponse = restClient.post(URL, usersJsonString, headermap); //call the API
		
		// Status Code
		int statusCode = httpResponse.getStatusLine().getStatusCode();
		System.out.println("Status code :"+statusCode);
		Assert.assertEquals(statusCode,  RESPONSE_STATUS_CODE_201, "Status is not 201");
		
		// JSON String
		String responseString = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
		JSONObject responseJSON= new JSONObject(responseString);
		System.out.println("Response JSON from API : "+ responseJSON);
		
		// converting JSON to Java object - UNMARSHALLING
		Users usrResObj = mapper.readValue(responseString, Users.class);  // actual users object
		System.out.println(usrResObj);
		
		Assert.assertTrue(user.getName().equals(usrResObj.getName()));
		Assert.assertTrue(user.getJob().equals(usrResObj.getJob()));
		
		System.out.println("ID : "+ usrResObj.getId());
		System.out.println("Created at: " + usrResObj.getCreatedAt());
	}

}
