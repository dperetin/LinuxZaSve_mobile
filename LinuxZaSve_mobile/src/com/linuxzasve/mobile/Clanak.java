package com.linuxzasve.mobile;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.linuxzasve.mobile.googl.GoogleUrlShortener;

import android.content.Intent;
import android.os.AsyncTask;
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
	
	private class SkratiUrl extends AsyncTask<String, Void, String> {
		
		@Override
		protected String doInBackground(String... urls) {
			String skraceniUrl = GoogleUrlShortener.ShortenUrl(urls[0]);
			return skraceniUrl;
		}

		@Override
		protected void onPostExecute(String skraceniUrl) {
			Intent intent2 = getIntent();
			String naslov = intent2.getStringExtra("naslov");
			Intent share = new Intent(Intent.ACTION_SEND);
			share.setType("text/plain");
			
			
			// kreiram naslov za dijeljenje
			String shareNaslov = "LZS | " + naslov; 
			
			// kreiram tekst za dijeljenje
			String shareText = "Linux za sve | " + naslov + " " + skraceniUrl;
			
			
			share.putExtra(Intent.EXTRA_TEXT, shareText);
			share.putExtra(Intent.EXTRA_SUBJECT, shareNaslov);
			startActivity(Intent.createChooser(share, "Podijeli članak!"));
			
		}
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
			new SkratiUrl().execute(origLink);

			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}
}

