package com.linuxzasve.mobile;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import android.content.Intent;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class Clanak extends SherlockActivity {
	
	private WebView clanak;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.clanak);

		Intent intent = getIntent();
		String message = intent.getStringExtra("naslov");
		String body = intent.getStringExtra("sadrzaj");
		String sadrzaj = "<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\" />" 
							+ "<h1>" + message + "</h1>" + body;

		clanak = (WebView) findViewById(R.id.sadrzaj_clanka);
		WebSettings settings = clanak.getSettings();
		settings.setDefaultTextEncodingName("utf-8");

		clanak.loadDataWithBaseURL("file:///android_asset/", sadrzaj, 
				"text/html", "UTF-8", null);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.clanak, menu);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		Intent intent2 = getIntent();
		String komentari = intent2.getStringExtra("komentari");
		String origLink = intent2.getStringExtra("origLink");
		String naslov = intent2.getStringExtra("naslov");

		switch (item.getItemId()) {
		
		case android.R.id.home:
			onBackPressed();
			return true;
		
		case R.id.menu_pokazi_komentare:
			Intent intent = new Intent(this, ListaKomentara.class);

			intent.putExtra("komentari", komentari);
			startActivity(intent);
			return true;

		case R.id.menu_share:
			Intent share = new Intent(Intent.ACTION_SEND);
			share.setType("text/plain");
			share.putExtra(Intent.EXTRA_TEXT, origLink);
			share.putExtra(Intent.EXTRA_SUBJECT, naslov);
			startActivity(Intent.createChooser(share, "Podijeli članak!"));
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}
}

