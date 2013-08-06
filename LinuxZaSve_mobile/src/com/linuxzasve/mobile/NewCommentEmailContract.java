package com.linuxzasve.mobile;

import android.provider.BaseColumns;

public final class NewCommentEmailContract {

	public NewCommentEmailContract() {
	}

	public static abstract class NewCommentEmail implements BaseColumns {
		public static final String TABLE_NAME = "novi_komentar_email";
		public static final String COLUMN_NAME_EMAIL = "email";
	}

	private static final String TEXT_TYPE = " TEXT";
	public static final String SQL_CREATE_ENTRIES = "CREATE TABLE "
			+ NewCommentEmail.TABLE_NAME + " (" + NewCommentEmail._ID
			+ " INTEGER PRIMARY KEY," + NewCommentEmail.COLUMN_NAME_EMAIL + TEXT_TYPE 
			+ " )";

	public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + NewCommentEmail.TABLE_NAME;
}