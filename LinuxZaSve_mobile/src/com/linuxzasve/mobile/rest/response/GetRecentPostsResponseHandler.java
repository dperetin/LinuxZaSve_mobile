package com.linuxzasve.mobile.rest.response;

import java.util.List;

import org.apache.http.Header;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.actionbarsherlock.view.MenuItem;
import com.google.gson.Gson;
import com.linuxzasve.mobile.ArticleDisplayActivity;
import com.linuxzasve.mobile.ArticleListArrayAdapter;
import com.linuxzasve.mobile.db.LzsDbContract.LzsPost;
import com.linuxzasve.mobile.db.LzsDbHelper;
import com.linuxzasve.mobile.rest.model.LzsRestResponse;
import com.linuxzasve.mobile.rest.model.Post;
import com.loopj.android.http.JsonHttpResponseHandler;

public class GetRecentPostsResponseHandler extends JsonHttpResponseHandler {

	private final View progressSpinner;
	private final MenuItem refresh;
	private final Context context;
	private final ListView articleListView;
	private List<Post> articleList;

	public GetRecentPostsResponseHandler(final View progressSpinner, final MenuItem refresh, final Context context, final ListView articleListView, final List<Post> articleList) {
		super();
		this.progressSpinner = progressSpinner;
		this.refresh = refresh;
		this.context = context;
		this.articleListView = articleListView;
		this.articleList = articleList;
	}

	@Override
	public void onSuccess(final int statusCode, final Header[] headers, final String responseBody) {

		Gson gson = new Gson();
		LzsRestResponse lzs_feed = gson.fromJson(responseBody, LzsRestResponse.class);
		articleList = lzs_feed.getPosts();

		writePostListToDatabase(articleList);

		progressSpinner.setVisibility(View.GONE);
		if (refresh != null) {
			refresh.setActionView(null);
		}
		ArticleListArrayAdapter adapter = new ArticleListArrayAdapter(context, articleList);

		articleListView.setAdapter(adapter);

		articleListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {

				Intent i = new Intent(context, ArticleDisplayActivity.class);

				i.putExtra("naslov", articleList.get(position).getTitle());
				i.putExtra("sadrzaj", articleList.get(position).getContent());
				i.putExtra("komentari", articleList.get(position).getUrl());
				i.putExtra("origLink", articleList.get(position).getUrl());
				i.putExtra("post_id", articleList.get(position).getId());
				context.startActivity(i);

			}
		});
	}

	private void writePostListToDatabase(final List<Post> articleList2) {
		// Gets the data repository in write mode
		LzsDbHelper helper = new LzsDbHelper(context);
		SQLiteDatabase db = helper.getWritableDatabase();

		for (Post p : articleList2) {
			// // Create a new map of values, where column names are the keys
			ContentValues values = new ContentValues();
			values.put(LzsPost.COLUMN_NAME_AUTHOR_NICKNAME, p.getAuthor().getNickname());
			values.put(LzsPost.COLUMN_NAME_COMMENT_COUNT, p.getComment_count());
			values.put(LzsPost.COLUMN_NAME_CONTENT, p.getContent());
			values.put(LzsPost.COLUMN_NAME_DATE, p.getDate());
			values.put(LzsPost.COLUMN_NAME_THUMBNAIL, p.getThumbnail());
			values.put(LzsPost.COLUMN_NAME_TITLE, p.getTitle());
			values.put(LzsPost.COLUMN_NAME_URL, p.getUrl());

			// Insert the new row, returning the primary key value of the new row
			long newRowId;
			newRowId = db.insert(LzsPost.TABLE_NAME, null, values);
		}
	}
}
