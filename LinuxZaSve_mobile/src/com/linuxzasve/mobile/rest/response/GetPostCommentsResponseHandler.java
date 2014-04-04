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
import android.widget.LinearLayout;
import android.widget.ListView;

import com.actionbarsherlock.view.MenuItem;
import com.google.gson.Gson;
import com.linuxzasve.mobile.ArticleDisplayActivity;
import com.linuxzasve.mobile.ArticleListArrayAdapter;
import com.linuxzasve.mobile.CommentListArrayAdapter;
import com.linuxzasve.mobile.db.LzsDbContract.LzsPost;
import com.linuxzasve.mobile.db.LzsDbHelper;
import com.linuxzasve.mobile.rest.model.LzsRestResponse;
import com.linuxzasve.mobile.rest.model.Post;
import com.loopj.android.http.JsonHttpResponseHandler;

public class GetPostCommentsResponseHandler extends JsonHttpResponseHandler {

	private final Context context;
	private ListView listView;
	private MenuItem refresh;
	private LinearLayout komentariProgressLayout;


	public GetPostCommentsResponseHandler(final Context context, ListView listView, MenuItem refresh, LinearLayout komentariProgressLayout) {
		super();

		this.context = context;
		this.listView = listView;
		this.refresh = refresh;
		this.komentariProgressLayout = komentariProgressLayout;

	}

	@Override
	public void onSuccess(final int statusCode, final Header[] headers, final String responseBody) {

		Gson gson = new Gson();
		LzsRestResponse lzs_feed = gson.fromJson(responseBody, LzsRestResponse.class);

		komentariProgressLayout.setVisibility(View.GONE);
		if (refresh != null) {
			refresh.setActionView(null);
		}
		CommentListArrayAdapter adapter = new CommentListArrayAdapter(context, lzs_feed.getPost().getComments());

		listView.setAdapter(adapter);
	}
}
