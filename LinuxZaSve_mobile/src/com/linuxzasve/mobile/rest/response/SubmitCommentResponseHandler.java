package com.linuxzasve.mobile.rest.response;

import org.apache.http.Header;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.google.gson.Gson;
import com.linuxzasve.mobile.db.LzsDbContract.NewCommentEmail;
import com.linuxzasve.mobile.db.LzsDbContract.NewCommentName;
import com.linuxzasve.mobile.db.LzsDbHelper;
import com.linuxzasve.mobile.rest.model.LzsRestResponse;
import com.loopj.android.http.JsonHttpResponseHandler;

public class SubmitCommentResponseHandler extends JsonHttpResponseHandler {

	Context context;

	String name;
	String email;

	private final String[] usedNames;

	// emails used in previous posts, used for email autocomplete
	private final String[] usedEmails;

	public SubmitCommentResponseHandler(final String name, final String email, final String[] usedNames, final String[] usedEmails, final Context context) {
		super();

		this.name = name;
		this.email = email;
		this.context = context;
		this.usedNames = usedNames;
		this.usedEmails = usedEmails;

	}

	@Override
	public void onSuccess(final int statusCode, final Header[] headers, final String responseBody) {

		Gson gson = new Gson();
		LzsRestResponse lzs_feed = gson.fromJson(responseBody, LzsRestResponse.class);

		if (lzs_feed.getStatus().equals("ok")) {

			Toast toast = Toast.makeText(context, "Komentar je poslan.", Toast.LENGTH_LONG);
			toast.show();

			insertEmailIfNew(email);
			insertNameIfNew(name);
		}
		else {

			Toast toast = Toast.makeText(context, "Slanje komentara nije uspjelo.", Toast.LENGTH_LONG);
			toast.show();
		}

	}

	private void insertEmailIfNew(final String email) {
		if (usedEmails.length == 0) {
			insertEmail(email);
			return;
		}
		for (String s : usedEmails) {
			if ((s != null) && s.equals(email)) {
				return;
			}
			else {
				insertEmail(email);
			}
		}
	}

	private void insertEmail(final String email) {
		LzsDbHelper mDbHelper = new LzsDbHelper(context);
		SQLiteDatabase db = mDbHelper.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(NewCommentEmail.COLUMN_NAME_EMAIL, email);

		db.insert(NewCommentEmail.TABLE_NAME, null, values);
	}

	/**
	 * Checks if name is in database, if not inserts it.
	 *
	 * @param name
	 */
	private void insertNameIfNew(final String name) {
		if (usedNames.length == 0) {
			insertName(name);
			return;
		}
		for (String s : usedNames) {
			if ((s != null) && s.equals(name)) {
				return;
			}
			else {
				insertName(name);
			}
		}
	}

	/**
	 * Inserts name in database
	 *
	 * @param name
	 */
	private void insertName(final String name) {
		LzsDbHelper mDbHelper = new LzsDbHelper(context);
		SQLiteDatabase db = mDbHelper.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(NewCommentName.COLUMN_NAME_IME, name);

		db.insert(NewCommentName.TABLE_NAME, null, values);
	}

}
