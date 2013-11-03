package com.linuxzasve.mobile;

import java.io.IOException;
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

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.linuxzasve.mobile.db.NewCommentEmailDbHelper;
import com.linuxzasve.mobile.db.NewCommentNameDbHelper;
import com.linuxzasve.mobile.db.NewCommentEmailContract.NewCommentEmail;
import com.linuxzasve.mobile.db.NewCommentNameContract.NewCommentName;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
	private static final Integer RETURN_NOK = Integer.valueOf(500);
	
//	names used in previous posts, used for name autocomplete
	private String[] usedNames;
	
//	emails used in previous posts, used for email autocomplete
	private String[] usedEmails;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.novi_komentar);
		
		
		
		Intent intent = getIntent();
		post_id = intent.getStringExtra("post_id");
		akismet = intent.getStringExtra("akismet");
		orig_url = intent.getStringExtra("orig_url");
		
		usedNames = getUsedNames();
		usedEmails = getUsedEmails();

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.novi_komentar, menu);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, usedNames);
		AutoCompleteTextView et1 = (AutoCompleteTextView) findViewById(R.id.novi_komentar_name);
		et1.setAdapter(adapter);
		
		ArrayAdapter<String> adapterEmails = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, usedEmails);
		AutoCompleteTextView et2 = (AutoCompleteTextView) findViewById(R.id.novi_komentar_email);
		et2.setAdapter(adapterEmails);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			return true;
			
		case R.id.menu_send:
			AutoCompleteTextView et1 = (AutoCompleteTextView) findViewById(R.id.novi_komentar_name);
			name = et1.getText().toString();
			
			AutoCompleteTextView et2 = (AutoCompleteTextView) findViewById(R.id.novi_komentar_email);
			email = et2.getText().toString();
			
			EditText et3 = (EditText) findViewById(R.id.novi_komentar_url);
			url = et3.getText().toString();
			
			EditText et4 = (EditText) findViewById(R.id.novi_komentar_tekst);
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
	
	/**
	 * 
	 * @return
	 */
	private String[] getUsedNames() {
		String[] allNames;
		NewCommentNameDbHelper mDbHelper = new NewCommentNameDbHelper(getBaseContext());
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
			
		String[] projection = {NewCommentName._ID, NewCommentName.COLUMN_NAME_IME};
		
		List<String> popisImena = new ArrayList<String>();
		
		Cursor c = db.query(NewCommentName.TABLE_NAME, projection, null, null, null, null, null);
		
		while(c.moveToNext()) {
			popisImena.add(c.getString(c.getColumnIndex(NewCommentName.COLUMN_NAME_IME)));
		}
		allNames = new String[popisImena.size()];
		allNames = popisImena.toArray(allNames);
		
		return allNames;
	}
	
	/**
	 * 	Populates email autocomplete field
	 */
	private String[] getUsedEmails() {
		String[] allEmails;
		NewCommentEmailDbHelper mDbHelper = new NewCommentEmailDbHelper(getBaseContext());
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
			
		String[] projection = {NewCommentEmail._ID, NewCommentEmail.COLUMN_NAME_EMAIL};
		
		List<String> popisImena = new ArrayList<String>();
		
		Cursor c = db.query(NewCommentEmail.TABLE_NAME, projection, null, null, null, null, null);
		
		while(c.moveToNext()) {
			popisImena.add(c.getString(c.getColumnIndex(NewCommentEmail.COLUMN_NAME_EMAIL)));
		}
		allEmails = new String[popisImena.size()];
		allEmails = popisImena.toArray(allEmails);
		
		return allEmails;
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

		        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));

		        response = httpclient.execute(httppost);
		        
		    } 
		        catch (ClientProtocolException e) {

		    } 
		    catch (IOException e) {

		    }

		    if (!Integer.valueOf(response.getStatusLine().getStatusCode()).equals(RETURN_NOK)) {
		    	insertEmailIfNew(urls[1]);
		    	insertNameIfNew(urls[0]);
		    }
		    
		    return Integer.valueOf(response.getStatusLine().getStatusCode());
	    }

		protected void onPostExecute(Integer result) {
			if (!result.equals(RETURN_NOK)) {
				
				Toast toast = Toast.makeText(getBaseContext(), "Komentar je poslan.", Toast.LENGTH_LONG);
				toast.show();
			} 
			else {

				Toast toast = Toast.makeText(getBaseContext(),
						"Slanje komentara nije uspjelo.", Toast.LENGTH_LONG);
				toast.show();
			}
		}

		private void insertEmailIfNew(String email) {
			if(usedEmails.length == 0) {
				insertEmail(email);
				return;
			}
			for(String s : usedEmails) {
				if (s != null && s.equals(email)) {
					return;
				} else {
					insertEmail(email);
				}
			}
		}

		private void insertEmail(String email) {
			NewCommentEmailDbHelper mDbHelper = new NewCommentEmailDbHelper(getBaseContext());
			SQLiteDatabase db = mDbHelper.getWritableDatabase();

			ContentValues values = new ContentValues();
			values.put(NewCommentEmail.COLUMN_NAME_EMAIL, email);

			db.insert(NewCommentEmail.TABLE_NAME, null, values);
		}

		/**
		 * Checks if name is in database, if not inserts it.
		 * @param name
		 */
		private void insertNameIfNew(String name) {
			if(usedNames.length == 0) {
				insertName(name);
				return;
			}
			for(String s : usedNames) {
				if (s != null && s.equals(name)) {
					return;
				} else {
					insertName(name);
				}
			}	
		}

		/**
		 * Inserts name in database
		 * @param name
		 */
		private void insertName(String name) {
			NewCommentNameDbHelper mDbHelper = new NewCommentNameDbHelper(getBaseContext());
			SQLiteDatabase db = mDbHelper.getWritableDatabase();

			ContentValues values = new ContentValues();
			values.put(NewCommentName.COLUMN_NAME_IME, name);

			db.insert(NewCommentName.TABLE_NAME, null, values);
		}
	}
}

