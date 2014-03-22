package com.linuxzasve.mobile.rest.response;

import java.util.List;

import org.apache.http.Header;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.actionbarsherlock.view.MenuItem;
import com.google.gson.Gson;
import com.linuxzasve.mobile.ArticleDisplayActivity;
import com.linuxzasve.mobile.MySimpleArrayAdapter;
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

		progressSpinner.setVisibility(View.GONE);
		if (refresh != null) {
			refresh.setActionView(null);
		}
		MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(context, articleList);

		articleListView.setAdapter(adapter);

		articleListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {

				Intent i = new Intent(context, ArticleDisplayActivity.class);

				i.putExtra("naslov", articleList.get(position).getTitle());
				i.putExtra("sadrzaj", articleList.get(position).getContent());
				i.putExtra("komentari", articleList.get(position).getUrl());
				i.putExtra("origLink", articleList.get(position).getUrl());
				context.startActivity(i);

			}
		});
	}
}
