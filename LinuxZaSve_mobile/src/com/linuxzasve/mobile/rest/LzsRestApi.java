package com.linuxzasve.mobile.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
//import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;


public class LzsRestApi {

	String baseUrl = "http://www.linuxzasve.com/api/";
	
	public String getRecentPosts() throws IOException {
		
		String jsonResult = getContent(baseUrl + "get_recent_posts/");
		
		return jsonResult;
	}
	
	private String getContent(String url) throws IOException {
		
		HttpResponse response = null;
		try {        
		        HttpClient client = new DefaultHttpClient();
		        HttpGet request = new HttpGet();
		        request.setURI(new URI(url));
		        response = client.execute(request);
		    } catch (URISyntaxException e) {
		        e.printStackTrace();
		    } catch (ClientProtocolException e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		    } catch (IOException e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		    }   
		    return getStringFromInputStream(response.getEntity().getContent());
		
	}
	
	private static String getStringFromInputStream(InputStream is) {
		 
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
 
		String line;
		try {
 
			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
 
		return sb.toString();
 
	}
}