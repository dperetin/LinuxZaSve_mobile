package com.linuxzasve.mobile;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class Clanak extends SherlockActivity {
	
	public static String naslov_clanka = "com.example.myapp.MESSAGE";
	private Clanak ovaAct;
	public final static String link = "com.example.myapp.MESSAGE";
	private WebView clanak;
	private ProgressDialog pDialog;
	private class DownloadRssFeed extends AsyncTask<String, Void, RssFeed> {
		@Override
		protected void onPreExecute() {
	        pDialog = ProgressDialog.show(ovaAct,"Pričekajte trenutak ...", "Dohvaćam članak ...", true);
	    }
		
		@Override
		protected RssFeed doInBackground(String... urls) {
			RssFeed lzs_feed = new RssFeed(urls[0]);
			
			return lzs_feed;
		}

		@Override
		protected void onPostExecute(RssFeed lzs_feed) {
			pDialog.dismiss();
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
        ovaAct = this;
        new DownloadRssFeed().execute("http://feeds.feedburner.com/linuxzasve");
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.clanak, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_pokazi_komentare:
                Intent intent = new Intent(this, ListaKomentara.class);
        	    
        	    intent.putExtra(link, naslov_clanka);
        	    startActivity(intent);
                return true;
            case R.id.menu_share:
            	Intent intent2 = getIntent();
    	        String message = intent2.getStringExtra(ListaNovosti.link);
    	        
            	Intent share = new Intent(Intent.ACTION_SEND);
            	share.setType("text/plain");
            	share.putExtra(Intent.EXTRA_TEXT, message);
            	startActivity(Intent.createChooser(share, "Podijeli članak!"));
            	return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
