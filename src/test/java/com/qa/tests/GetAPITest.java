package com.qa.tests;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.util.TestUtil;

public class GetAPITest extends TestBase {
	
	TestBase testBase;
	String ServiceURL;
	String APIURL;
	String URL;
	RestClient restClient;
	CloseableHttpResponse httpResponse;
	
	public GetAPITest() {
		super();
	}
	
	@BeforeMethod
	public void setUp() {
		
		//testBase = new TestBase();
		ServiceURL = prop.getProperty("serviceURL");
		APIURL = prop.getProperty("URL");
		URL = APIURL + ServiceURL;
	}
	
	
	@Test(priority=1, enabled=false)
	public void getAPITestwithoutHeaders() throws ParseException, IOException {
		System.out.println("---------------- getAPITestwithoutHeaders --------------");
		restClient = new RestClient();
		try {
			httpResponse  = restClient.get(URL);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Status Code
		System.out.println("getStatusLine () " + httpResponse.getStatusLine().toString());
		System.out.println("getReasonPhrase  " +  httpResponse.getStatusLine().getReasonPhrase());
		System.out.println("getProtocolVersion "+httpResponse.getStatusLine().getProtocolVersion().toString());
		int statusCode = httpResponse.getStatusLine().getStatusCode();
		System.out.println("Status code :"+statusCode);
		
		Assert.assertEquals(statusCode,  RESPONSE_STATUS_CODE_200, "Status is not 200");
		
		// JSON String
		String responseString = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
		JSONObject responseJSON= new JSONObject(responseString);
		System.out.println("Response JSON from API : "+ responseJSON);
		
		//Single value assertion
		//per_page value
		String perPageValue = TestUtil.getValueByJPath(responseJSON, "/per_page");
		System.out.println("Value of per page is "+ perPageValue);
		Assert.assertEquals(Integer.parseInt(perPageValue), 3);
		
		//total value
		String totalValue = TestUtil.getValueByJPath(responseJSON, "/total");
		System.out.println("Value of total is "+ totalValue);
		Assert.assertEquals(Integer.parseInt(totalValue), 12);
		
		//get the values from JSON array for the 1st array
		
		String lastName = TestUtil.getValueByJPath(responseJSON, "/data[1]/last_name");
		String id = TestUtil.getValueByJPath(responseJSON, "/data[1]/id");
		String avatar = TestUtil.getValueByJPath(responseJSON, "/data[1]/avatar");
		String firstName = TestUtil.getValueByJPath(responseJSON, "/data[1]/first_name");
		
		System.out.println("Last Name "+lastName);
		System.out.println("ID "+id);
		System.out.println("Avatar "+avatar);
		System.out.println("First Name "+firstName);
		
		// All headers 
		Header[] hdrArray = httpResponse.getAllHeaders();  // to get all the headers
		HashMap<String, String> allheaders = new HashMap<String, String>();
		for (Header hdr : hdrArray) {
			allheaders.put(hdr.getName(), hdr.getValue());
		}
		System.out.println("Headers Array " + allheaders);
		System.out.println("---------------- getAPITestwithoutHeaders --------------");
		
	}

	@Test(priority=1)
	public void getAPITestWithHeaders() throws ParseException, IOException {
		System.out.println("---------------- getAPITestWithHeaders --------------");
		
		restClient = new RestClient();
		
		HashMap<String, String> headermap = new HashMap<String, String>();
		headermap.put("Content-Type", "application/json");

//      Incase in the project we pass userid and password		
//		headermap.put("UserName", "test@test.com");
//		headermap.put("Password", "password@123");
//		headermap.put("Token", "12345-2323-wwew");

		try {
			httpResponse  = restClient.get(URL, headermap);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Status Code
		int statusCode = httpResponse.getStatusLine().getStatusCode();
		System.out.println("Status code :"+statusCode);
		
		Assert.assertEquals(statusCode,  RESPONSE_STATUS_CODE_200, "Status is not 200");
		
		// JSON String
		String responseString = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
		JSONObject responseJSON= new JSONObject(responseString);
		System.out.println("Response JSON from API : "+ responseJSON);
		
		
		//Single value assertion
		//per_page value
		String perPageValue = TestUtil.getValueByJPath(responseJSON, "/per_page");
		System.out.println("Value of per page is "+ perPageValue);
		Assert.assertEquals(Integer.parseInt(perPageValue), 3);
		
		//total value
		String totalValue = TestUtil.getValueByJPath(responseJSON, "/total");
		System.out.println("Value of total is "+ totalValue);
		Assert.assertEquals(Integer.parseInt(totalValue), 12);
		
		//get the values from JSON array
		
		String lastName = TestUtil.getValueByJPath(responseJSON, "/data[0]/last_name");
		String id = TestUtil.getValueByJPath(responseJSON, "/data[0]/id");
		String avatar = TestUtil.getValueByJPath(responseJSON, "/data[0]/avatar");
		String firstName = TestUtil.getValueByJPath(responseJSON, "/data[0]/first_name");
		
		System.out.println("Last Name "+lastName);
		System.out.println("ID "+id);
		System.out.println("Avatar "+avatar);
		System.out.println("First Name "+firstName);
		
		// All headers 
		Header[] hdrArray = httpResponse.getAllHeaders();  // to get all the headers
		HashMap<String, String> allheaders = new HashMap<String, String>();
		for (Header hdr : hdrArray) {
			allheaders.put(hdr.getName(), hdr.getValue());
		}
		System.out.println("Headers Array " + allheaders);
		System.out.println("---------------- getAPITestWithHeaders --------------");
	}
}
