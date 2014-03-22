package com.linuxzasve.mobile;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.linuxzasve.mobile.googl.GoogleUrlShortener;

public class ArticleDisplayFragment extends SherlockFragment{

	private WebView clanak;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// This fragment participates in options menu creation, so it needs to announce it. 
		setHasOptionsMenu(true);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.clanak);
		View rootView = inflater.inflate(R.layout.clanak, container, false);

		Intent intent = getActivity().getIntent();
		String message = intent.getStringExtra("naslov");
		String body = intent.getStringExtra("sadrzaj");
		String sadrzaj = "<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\" />" 
							+ "<h1>" + message + "</h1>" + body;

		clanak = (WebView) rootView.findViewById(R.id.sadrzaj_clanka);
		WebSettings settings = clanak.getSettings();
		settings.setDefaultTextEncodingName("utf-8");

		clanak.loadDataWithBaseURL("file:///android_asset/", sadrzaj, 
				"text/html", "UTF-8", null);
		return rootView;
	}
	
	private class SkratiUrl extends AsyncTask<String, Void, String> {
		
		@Override
		protected String doInBackground(String... urls) {
			String skraceniUrl = GoogleUrlShortener.ShortenUrl(urls[0]);
			return skraceniUrl;
		}

		@Override
		protected void onPostExecute(String skraceniUrl) {
			Intent intent2 = getActivity().getIntent();
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
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		
		inflater.inflate(R.menu.clanak, menu);
		
		super.onCreateOptionsMenu(menu, inflater);
	}

//	@Override
//	public void onCreateOptionsMenu(Menu menu) {
////		MenuInflater inflater = getSupportMenuInflater();
//		
////		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//		
////		return true;
//	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		Intent intent2 = getActivity().getIntent();
		String komentari = intent2.getStringExtra("komentari");
		String origLink = intent2.getStringExtra("origLink");
		String naslov = intent2.getStringExtra("naslov");

		switch (item.getItemId()) {
		
		case android.R.id.home:
			getActivity().onBackPressed();
			return true;
		
		case R.id.menu_pokazi_komentare:
			Intent intent = new Intent(getActivity(), ListaKomentara.class);

			intent.putExtra("komentari", komentari);
			intent.putExtra("naslov", naslov);
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
