package com.linuxzasve.mobile;

import android.content.Intent;
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
import com.linuxzasve.mobile.rest.LzsRestGateway;
import com.linuxzasve.mobile.rest.response.GetPostCommentsResponseHandler;

public class ListaKomentara extends SherlockActivity {

	private ListView listView;
	LinearLayout komentariProgressLayout;
	String message;
	Integer post_id;
	String akismet;
	private MenuItem refresh;

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lista_komentara);
		listView = (ListView)findViewById(R.id.komentari);
		komentariProgressLayout = (LinearLayout)findViewById(R.id.komentariProgressLayout);
		Intent intent = getIntent();
		message = intent.getStringExtra("komentari");
		post_id = intent.getIntExtra("post_id", 0);

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
				intent.putExtra("post_id", String.valueOf(post_id));
				startActivity(intent);
				return true;

			default:
				return super.onOptionsItemSelected(item);
		}
	}

	public void fetchArticles() {
		if (ActivityHelper.isOnline(this)) {
			LzsRestGateway g = new LzsRestGateway();
			GetPostCommentsResponseHandler responseHandler = new GetPostCommentsResponseHandler(this, listView, refresh, komentariProgressLayout);

			g.getCommentsForPost(post_id, responseHandler);
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
