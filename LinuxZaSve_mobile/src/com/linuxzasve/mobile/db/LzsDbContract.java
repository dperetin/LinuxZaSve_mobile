package com.linuxzasve.mobile.db;

import android.provider.BaseColumns;

/**
 *
 * @author dejan
 *
 */
public class LzsDbContract {

	public LzsDbContract() {
	}

	private static final String TEXT_TYPE = " TEXT";
	private static final String INTEGER_TYPE = " INTEGER";
	private static final String SEPARATOR = ", ";

	/**
	 *
	 * @author dejan
	 *
	 */
	public static abstract class NewCommentEmail implements BaseColumns {
		public static final String TABLE_NAME = "novi_komentar_email";
		public static final String COLUMN_NAME_EMAIL = "email";
	}

	public static final String SQL_EMAIL_CREATE_ENTRIES = "CREATE TABLE " + NewCommentEmail.TABLE_NAME + " (" + NewCommentEmail._ID + " INTEGER PRIMARY KEY," + NewCommentEmail.COLUMN_NAME_EMAIL + TEXT_TYPE + " )";

	public static final String SQL_EMAIL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + NewCommentEmail.TABLE_NAME;

	/**
	 *
	 * @author dejan
	 *
	 */
	public static abstract class NewCommentName implements BaseColumns {
		public static final String TABLE_NAME = "novi_komentar";
		public static final String COLUMN_NAME_IME = "ime";
	}

	public static final String SQL_NAME_CREATE_ENTRIES = "CREATE TABLE " + NewCommentName.TABLE_NAME + " (" + NewCommentName._ID + " INTEGER PRIMARY KEY," + NewCommentName.COLUMN_NAME_IME + TEXT_TYPE + " )";

	public static final String SQL_NAME_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + NewCommentName.TABLE_NAME;

	/**
	 *
	 * @author dejan
	 *
	 */
	public static abstract class LzsPost implements BaseColumns {
		public static final String TABLE_NAME = "post";
		public static final String COLUMN_NAME_URL = "url";
		public static final String COLUMN_NAME_TITLE = "title";
		public static final String COLUMN_NAME_CONTENT = "content";
		public static final String COLUMN_NAME_DATE = "date";
		public static final String COLUMN_NAME_AUTHOR_NICKNAME = "author_nickname";
		public static final String COLUMN_NAME_COMMENT_COUNT = "comment_count";
		public static final String COLUMN_NAME_THUMBNAIL = "thumbnail";

	}

	public static final String SQL_POST_CREATE_ENTRIES = "CREATE TABLE " + LzsPost.TABLE_NAME + " (" + LzsPost._ID + " INTEGER PRIMARY KEY," + LzsPost.COLUMN_NAME_URL + TEXT_TYPE + SEPARATOR + LzsPost.COLUMN_NAME_TITLE + TEXT_TYPE + SEPARATOR
			+ LzsPost.COLUMN_NAME_CONTENT + TEXT_TYPE + SEPARATOR + LzsPost.COLUMN_NAME_DATE + TEXT_TYPE + SEPARATOR + LzsPost.COLUMN_NAME_AUTHOR_NICKNAME + TEXT_TYPE + SEPARATOR + LzsPost.COLUMN_NAME_COMMENT_COUNT + INTEGER_TYPE + SEPARATOR
			+ LzsPost.COLUMN_NAME_THUMBNAIL + TEXT_TYPE + " )";

	public static final String SQL_POST_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + LzsPost.TABLE_NAME;

}
