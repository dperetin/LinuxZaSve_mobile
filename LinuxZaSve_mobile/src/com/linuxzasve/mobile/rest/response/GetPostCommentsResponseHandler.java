package com.linuxzasve.mobile.rest.response;

import org.apache.http.Header;

import android.content.Context;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.linuxzasve.mobile.adapters.CommentListArrayAdapter;
import com.linuxzasve.mobile.rest.model.LzsRestResponse;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.io.UnsupportedEncodingException;

public class GetPostCommentsResponseHandler extends AsyncHttpResponseHandler {

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
	public void onSuccess(final int statusCode, final Header[] headers, final byte[] bytes) {

        String responseBody = null;

        try {
            responseBody = new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
		LzsRestResponse lzs_feed = gson.fromJson(responseBody, LzsRestResponse.class);

		komentariProgressLayout.setVisibility(View.GONE);
		if (refresh != null) {
			refresh.setActionView(null);
		}
		CommentListArrayAdapter adapter = new CommentListArrayAdapter(context, lzs_feed.getPost().getComments());

		listView.setAdapter(adapter);
	}

    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

    }
}
