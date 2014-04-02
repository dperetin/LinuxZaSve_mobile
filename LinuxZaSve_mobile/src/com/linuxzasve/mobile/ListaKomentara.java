package com.linuxzasve.mobile;

import java.io.IOException;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.linuxzasve.mobile.wp_comment.WordpressCommentParser;

public class ListaKomentara extends SherlockActivity {

	private ListView listView;
	private ListaKomentara ovaAct;
	LinearLayout komentariProgressLayout;
	String message;
	String post_id;
	String akismet;
	private MenuItem refresh;

	private class DownloadRssFeed extends AsyncTask<String, Void, WordpressCommentParser> {
		@Override
		protected void onPreExecute() {
		}

		@Override
		protected WordpressCommentParser doInBackground(final String... urls) {
			WordpressCommentParser lzs_feed = null;
			try {
				lzs_feed = new WordpressCommentParser(urls[0]);
			}
			catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return lzs_feed;
		}

		@Override
		protected void onPostExecute(final WordpressCommentParser lzs_feed) {
			komentariProgressLayout.setVisibility(View.GONE);
			refresh.setActionView(null);

			CommentListArrayAdapter adapter = new CommentListArrayAdapter(ovaAct, lzs_feed.getPosts(), post_id, akismet);

			listView.setAdapter(adapter);
		}
	}

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lista_komentara);
		ovaAct = this;
		listView = (ListView)findViewById(R.id.komentari);
		komentariProgressLayout = (LinearLayout)findViewById(R.id.komentariProgressLayout);
		Intent intent = getIntent();
		message = intent.getStringExtra("komentari");
		komentariProgressLayout.setVisibility(View.VISIBLE);

		ActionBar ab = getSupportActionBar();
		String naslov = intent.getStringExtra("naslov");
		ab.setSubtitle("Komentari na članak: " + naslov);

		fetchArticles();
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.lista_komentara, menu);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		refresh = menu.findItem(R.id.menu_refresh);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {

		switch (item.getItemId()) {
			case android.R.id.home:
				onBackPressed();
				return true;

			case R.id.menu_refresh:
				refresh.setActionView(R.layout.actionbar_indeterminate_progress);
				fetchArticles();
				return true;

			case R.id.menu_new_comment:
				Intent intent = new Intent(this, NoviKomentar.class);

				intent.putExtra("post_id", post_id);
				intent.putExtra("akismet", akismet);
				intent.putExtra("orig_url", message);
				startActivity(intent);
				return true;

			default:
				return super.onOptionsItemSelected(item);
		}
	}

	public void fetchArticles() {
		if (ActivityHelper.isOnline(this)) {
			new DownloadRssFeed().execute(message);
		}
		else {
			Toast toast = Toast.makeText(getBaseContext(), R.string.nedostupan_internet, Toast.LENGTH_LONG);
			toast.show();
			if (komentariProgressLayout != null) {
				komentariProgressLayout.setVisibility(View.GONE);
			}

			if (refresh != null) {
				refresh.setActionView(null);
			}
		}
	}
}
