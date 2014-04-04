package com.linuxzasve.mobile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

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

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.google.gson.Gson;
import com.linuxzasve.mobile.db.LzsDbContract.NewCommentEmail;
import com.linuxzasve.mobile.db.LzsDbContract.NewCommentName;
import com.linuxzasve.mobile.db.LzsDbHelper;
import com.linuxzasve.mobile.rest.LzsRestGateway;
import com.linuxzasve.mobile.rest.model.LzsRestResponse;
import com.linuxzasve.mobile.rest.response.SubmitCommentResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

public class NoviKomentar extends SherlockActivity {
	private String name;
	private String email;
	private String text;
	private String post_id;

	// names used in previous posts, used for name autocomplete
	private String[] usedNames;

	// emails used in previous posts, used for email autocomplete
	private String[] usedEmails;

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.novi_komentar);

		Intent intent = getIntent();
		post_id = intent.getStringExtra("post_id");

		usedNames = getUsedNames();
		usedEmails = getUsedEmails();
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.novi_komentar, menu);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, usedNames);
		AutoCompleteTextView et1 = (AutoCompleteTextView)findViewById(R.id.novi_komentar_name);
		et1.setAdapter(adapter);

		ArrayAdapter<String> adapterEmails = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, usedEmails);
		AutoCompleteTextView et2 = (AutoCompleteTextView)findViewById(R.id.novi_komentar_email);
		et2.setAdapter(adapterEmails);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {

		switch (item.getItemId()) {
			case android.R.id.home:
				onBackPressed();
				return true;

			case R.id.menu_send:
				AutoCompleteTextView et1 = (AutoCompleteTextView)findViewById(R.id.novi_komentar_name);
				name = et1.getText().toString();

				AutoCompleteTextView et2 = (AutoCompleteTextView)findViewById(R.id.novi_komentar_email);
				email = et2.getText().toString();

				EditText et4 = (EditText)findViewById(R.id.novi_komentar_tekst);
				text = et4.getText().toString();

				if ((email == null) || (name == null) || email.equals("") || name.equals("")) {
					Toast toast = Toast.makeText(getBaseContext(), "Nisu popunjena sva obavezna polja.", Toast.LENGTH_SHORT);
					toast.show();
				}
				else {
					LzsRestGateway g = new LzsRestGateway();
					g.submitComment(post_id, name, email, text, new SubmitCommentResponseHandler(name, email, usedNames, usedEmails, this));
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
		LzsDbHelper mDbHelper = new LzsDbHelper(getBaseContext());
		SQLiteDatabase db = mDbHelper.getReadableDatabase();

		String[] projection = { NewCommentName._ID, NewCommentName.COLUMN_NAME_IME };

		List<String> popisImena = new ArrayList<String>();

		Cursor c = db.query(NewCommentName.TABLE_NAME, projection, null, null, null, null, null);

		while (c.moveToNext()) {
			popisImena.add(c.getString(c.getColumnIndex(NewCommentName.COLUMN_NAME_IME)));
		}
		allNames = new String[popisImena.size()];
		allNames = popisImena.toArray(allNames);

		return allNames;
	}

	/**
	 * Populates email autocomplete field
	 */
	private String[] getUsedEmails() {
		String[] allEmails;
		LzsDbHelper mDbHelper = new LzsDbHelper(getBaseContext());
		SQLiteDatabase db = mDbHelper.getReadableDatabase();

		String[] projection = { NewCommentEmail._ID, NewCommentEmail.COLUMN_NAME_EMAIL };

		List<String> popisImena = new ArrayList<String>();

		Cursor c = db.query(NewCommentEmail.TABLE_NAME, projection, null, null, null, null, null);

		while (c.moveToNext()) {
			popisImena.add(c.getString(c.getColumnIndex(NewCommentEmail.COLUMN_NAME_EMAIL)));
		}
		allEmails = new String[popisImena.size()];
		allEmails = popisImena.toArray(allEmails);

		return allEmails;
	}
}
