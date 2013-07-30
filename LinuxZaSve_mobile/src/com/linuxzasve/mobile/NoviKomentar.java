package com.linuxzasve.mobile;

import java.io.IOException;
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
import org.jsoup.helper.StringUtil;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

public class NoviKomentar extends SherlockActivity {
	private String name;
	private String email;
	private String url;
	private String text;
	private String post_id;
	private String akismet;
	private String orig_url;
	private static final Integer RETURN_NOK = new Integer(500);
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.novi_komentar);
		
		
		
		Intent intent = getIntent();
		post_id = intent.getStringExtra("post_id");
		akismet = intent.getStringExtra("akismet");
		orig_url = intent.getStringExtra("orig_url");

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.novi_komentar, menu);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			return true;
			
		case R.id.menu_send:
			EditText et1 = (EditText) findViewById(R.id.editText1);
			name = et1.getText().toString();
			
			EditText et2 = (EditText) findViewById(R.id.editText2);
			email = et2.getText().toString();
			
			EditText et3 = (EditText) findViewById(R.id.editText3);
			url = et3.getText().toString();
			
			EditText et4 = (EditText) findViewById(R.id.editText4);
			text = et4.getText().toString();
			
			if (email == null || name == null || email.equals("") || name.equals("") ) {
				Toast toast = Toast.makeText(getBaseContext(), "Nisu popunjena sva obavezna polja.", Toast.LENGTH_SHORT);
				toast.show();
			} else {
				new CommentPoster().execute(name, email, url, text, post_id, akismet, orig_url);
				onBackPressed();
			}
			
			
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	private class CommentPoster extends AsyncTask<String, Integer, Integer> {
	    protected Integer doInBackground(String... urls) {
	    	HttpResponse response = null;
	    	HttpClient httpclient = new DefaultHttpClient();
		    HttpPost httppost = new HttpPost("http://www.linuxzasve.com/wp-comments-post.php");
		    httppost.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		    httppost.setHeader("Accept-Encoding", "gzip, deflate");
		    httppost.setHeader("Accept-Language", "en-US,en;q=0.5");
		    httppost.setHeader("Connection", "keep-alive");
		    httppost.setHeader("Host", "www.linuxzasve.com");
		    httppost.setHeader("Referer", urls[6]);
		    httppost.setHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:21.0) Gecko/20100101 Firefox/21.0");

		    try {
		        // Add your data
		        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(9);
		        
		        
		        nameValuePairs.add(new BasicNameValuePair("author", urls[0]));
		        nameValuePairs.add(new BasicNameValuePair("email", urls[1]));
		        nameValuePairs.add(new BasicNameValuePair("url", urls[2]));
		        nameValuePairs.add(new BasicNameValuePair("comment", urls[3]));
		        
		        nameValuePairs.add(new BasicNameValuePair("submit", "Objavi komentar"));
		        nameValuePairs.add(new BasicNameValuePair("comment_post_ID", urls[4]));
		        nameValuePairs.add(new BasicNameValuePair("comment_post_ID", urls[4]));
		        nameValuePairs.add(new BasicNameValuePair("comment_parent", "0"));
		        nameValuePairs.add(new BasicNameValuePair("akismet_comment_nonce", urls[5]));

		        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

		        response = httpclient.execute(httppost);
		        
		    } catch (ClientProtocolException e) {

		    } catch (IOException e) {

		    }
		    
		    return new Integer(response.getStatusLine().getStatusCode());
	    }

	    protected void onPostExecute(Integer result) {
	    	if (!result.equals(RETURN_NOK)) {
	    		Toast toast = Toast.makeText(getBaseContext(), "Komentar je poslan.", Toast.LENGTH_LONG);
				toast.show();
	    	}
	    	else {
	    		Toast toast = Toast.makeText(getBaseContext(), "Slanje komentara nije uspjelo.", Toast.LENGTH_LONG);
				toast.show();
	    	}
	    	
	    }
	}
}

