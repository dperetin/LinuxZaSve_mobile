package com.linuxzasve.mobile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class NoviKomentar extends SherlockActivity {

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.novi_komentar);
		
		/*int TIMEOUT_MS = 10000;
		
		HttpClient httpClient = new DefaultHttpClient();
		HttpConnectionParams.setConnectionTimeout(httpClient.getParams(), TIMEOUT_MS);
		HttpConnectionParams.setSoTimeout(httpClient.getParams(), TIMEOUT_MS);
		URI url;
		try {
			url = new URI("http://www.linuxzasve.com/wp-comments-post.php");
			HttpPost httpPost = new HttpPost( url); 
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();  
			nameValuePairs.add(new BasicNameValuePair("author", "Dejan"));  
			nameValuePairs.add(new BasicNameValuePair("email", "_@dejanperetin.com")); 
			nameValuePairs.add(new BasicNameValuePair("url", ""));
			nameValuePairs.add(new BasicNameValuePair("comment", "I nogom u guzicu je korak naprijed!")); 	
			nameValuePairs.add(new BasicNameValuePair("submit", "Objavi komentar")); 
			nameValuePairs.add(new BasicNameValuePair("comment_post_ID", "13998")); 
			nameValuePairs.add(new BasicNameValuePair("comment_post_ID", "13998"));
			nameValuePairs.add(new BasicNameValuePair("comment_parent", "0"));
			nameValuePairs.add(new BasicNameValuePair("akismet_comment_nonce", "17e40c1d4d"));
			// etc...
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs)); 
			HttpResponse response = httpClient.execute(httpPost);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		


	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.novi_komentar, menu);
		return true;
	}
	
	
}

