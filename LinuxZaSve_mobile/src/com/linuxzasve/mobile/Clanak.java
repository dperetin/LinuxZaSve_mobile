package com.linuxzasve.mobile;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class Clanak extends Activity {
	
	public static String naslov_clanka = "com.example.myapp.MESSAGE";

	public final static String link = "com.example.myapp.MESSAGE";
	private WebView clanak;
	public void sendMessage(View view) {
	    Intent intent = new Intent(this, ListaKomentara.class);
	    
	    intent.putExtra(link, naslov_clanka);
	    startActivity(intent);
	}
	
	private class DownloadRssFeed extends AsyncTask<String, Void, RssFeed> {
		@Override
		protected RssFeed doInBackground(String... urls) {
			RssFeed lzs_feed = new RssFeed(urls[0]);
			
			return lzs_feed;
		}

		@Override
		protected void onPostExecute(RssFeed lzs_feed) {
			Intent intent = getIntent();
	        String message = intent.getStringExtra(ListaNovosti.link);
			String sadrzaj = "<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\" />" + "<h1>" + message + "</h1>" + lzs_feed.getContentByTitle(message);
			String komentari_url = lzs_feed.getCommentsByTitle(message);
			naslov_clanka = komentari_url;
			clanak = (WebView) findViewById(R.id.sadrzaj_clanka);
			WebSettings settings = clanak.getSettings();
	      	settings.setDefaultTextEncodingName("utf-8");

	      	//clanak.loadData(sadrzaj, "text/html", null);
	      	clanak.loadDataWithBaseURL("file:///android_asset/", sadrzaj, "text/html", "UTF-8", null);
		}
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clanak);
        
        new DownloadRssFeed().execute("http://feeds.feedburner.com/linuxzasve");
    }
}
