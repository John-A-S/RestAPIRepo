package com.qa.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class RestClient {
	
	//1. GET Method without headers
	public CloseableHttpResponse get(String URL) throws ClientProtocolException, IOException {
		CloseableHttpClient httpClient =  HttpClients.createDefault();  	// create client connection 
		HttpGet httpget = new HttpGet(URL); 								// http get request
		CloseableHttpResponse httpResponse = httpClient.execute(httpget); 	// hit the URL
		
		return httpResponse;
	}

	//1. GET Method with headers
	public CloseableHttpResponse get(String URL, HashMap<String, String> headermap) throws ClientProtocolException, IOException {
		CloseableHttpClient httpClient =  HttpClients.createDefault();  	// create client connection 
		HttpGet httpget = new HttpGet(URL); 								// http get request
		CloseableHttpResponse httpResponse = httpClient.execute(httpget); 	// hit the URL
		
		for (Map.Entry<String, String> entry : headermap.entrySet()){
			httpget.addHeader(entry.getKey(), entry.getValue());
		}
		
		return httpResponse;
	}

	//2.  POST Method
	public CloseableHttpResponse post(String URL, String entityString, HashMap<String, String> headermap) throws ClientProtocolException, IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault(); 		// create client connection
		HttpPost httppost = new HttpPost(URL); 								// http post request
		httppost.setEntity(new StringEntity(entityString)); 				// for Payload 
		
		//for headers:
		for (Map.Entry<String, String> entry : headermap.entrySet()) {
			httppost.addHeader(entry.getKey(), entry.getValue());
		}
		CloseableHttpResponse closeableHttpResponse = httpClient.execute(httppost);
		return closeableHttpResponse;
		
	}
}