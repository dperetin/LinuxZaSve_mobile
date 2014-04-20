package com.linuxzasve.mobile.rest.response;

import org.apache.http.Header;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.actionbarsherlock.view.MenuItem;
import com.google.gson.Gson;
import com.linuxzasve.mobile.CommentListArrayAdapter;
import com.linuxzasve.mobile.rest.model.LzsRestResponse;
import com.loopj.android.http.JsonHttpResponseHandler;

public class GetPostCommentsResponseHandler extends JsonHttpResponseHandler {

	private final Context context;
	private final ListView listView;
	private final MenuItem refresh;
	private final LinearLayout komentariProgressLayout;

	public GetPostCommentsResponseHandler(final Context context, final ListView listView, final MenuItem refresh, final LinearLayout komentariProgressLayout) {
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
