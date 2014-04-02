package com.linuxzasve.mobile.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LzsDbHelper extends SQLiteOpenHelper {

	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "lzs.db";

	public LzsDbHelper(final Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(final SQLiteDatabase db) {
		db.execSQL(LzsDbContract.SQL_EMAIL_CREATE_ENTRIES);
		db.execSQL(LzsDbContract.SQL_NAME_CREATE_ENTRIES);
		db.execSQL(LzsDbContract.SQL_POST_CREATE_ENTRIES);
	}

	@Override
	public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
		db.execSQL(LzsDbContract.SQL_EMAIL_DELETE_ENTRIES);
		db.execSQL(LzsDbContract.SQL_NAME_DELETE_ENTRIES);
		db.execSQL(LzsDbContract.SQL_POST_DELETE_ENTRIES);
		onCreate(db);
	}

	@Override
	public void onDowngrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
		onUpgrade(db, oldVersion, newVersion);
	}
}
