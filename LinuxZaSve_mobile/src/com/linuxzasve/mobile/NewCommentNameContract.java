package com.linuxzasve.mobile;

import android.provider.BaseColumns;

public final class NewCommentNameContract {

	public NewCommentNameContract() {
	}

	public static abstract class NewCommentName implements BaseColumns {
		public static final String TABLE_NAME = "novi_komentar";
		public static final String COLUMN_NAME_IME = "ime";
	}

	private static final String TEXT_TYPE = " TEXT";
	public static final String SQL_CREATE_ENTRIES = "CREATE TABLE "
			+ NewCommentName.TABLE_NAME + " (" + NewCommentName._ID
			+ " INTEGER PRIMARY KEY," + NewCommentName.COLUMN_NAME_IME + TEXT_TYPE 
			+ " )";

	public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + NewCommentName.TABLE_NAME;
}