package com.linuxzasve.mobile;

import java.io.IOException;
import java.util.List;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class ListaKomentara extends SherlockActivity {
	
	private ListView listView;
	private ListaKomentara ovaAct;
	LinearLayout komentariProgressLayout;
	String message;
	private MenuItem refresh;
	
	public class MySimpleArrayAdapter extends ArrayAdapter<Komentar> {
		private final Context context;
		private final List<Komentar> values;

		public MySimpleArrayAdapter(Context context, List<Komentar> naslovi) {
			super(context, R.layout.komentar_redak, naslovi);
			this.context = context;
			this.values = naslovi;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View rowView = inflater.inflate(R.layout.komentar_redak, parent, false);
			TextView neki_tekst = (TextView) rowView.findViewById(R.id.tekst_komentar );
			neki_tekst.setMovementMethod(LinkMovementMethod.getInstance());
			TextView datum = (TextView) rowView.findViewById(R.id.datum_komentar);
			TextView autor = (TextView) rowView.findViewById(R.id.autor_komentar);
			ImageView thumbnail = (ImageView) rowView.findViewById(R.id.komentarThumbnail);
//			datum.setText(values.get(values.size() - position - 1).datumDdmmyyy());
			datum.setText(values.get(values.size() - position - 1).getPublishDate());
			autor.setText(values.get(values.size() - position - 1).getCreator());
			
			neki_tekst.setText(Html.fromHtml(values.get(values.size() - position - 1).getContent()));
			
			UrlImageViewHelper
			.setUrlDrawable(thumbnail, values.get(values.size() - position - 1)
					.getThumbnail(), R.drawable.placeholder);
			
			return rowView;
		}
	} 
	
	private class DownloadRssFeed extends AsyncTask<String, Void, WordpressCommentParser> {
		@Override
		protected void onPreExecute() {
		}
		
		@Override
		protected WordpressCommentParser doInBackground(String... urls) {
			WordpressCommentParser lzs_feed=null;
			try {
				lzs_feed = new WordpressCommentParser(urls[0]);
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return lzs_feed;
		}

		@Override
		protected void onPostExecute(WordpressCommentParser lzs_feed) {
			komentariProgressLayout.setVisibility(View.GONE);
			refresh.setActionView(null);
			
			MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(ovaAct, lzs_feed.getPosts());

			listView.setAdapter(adapter); 
		}
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lista_komentara);
		ovaAct = this;
		listView = (ListView) findViewById(R.id.komentari);
		komentariProgressLayout = (LinearLayout) findViewById(R.id.komentariProgressLayout);
		Intent intent = getIntent();
		message = intent.getStringExtra("komentari");
		komentariProgressLayout.setVisibility(View.VISIBLE);
		fetchArticles();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.lista_komentara, menu);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		refresh = menu.findItem(R.id.menu_refresh);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			return true;
			
		case R.id.menu_refresh:
			refresh.setActionView(R.layout.actionbar_indeterminate_progress);
			fetchArticles();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}
	public void fetchArticles() {
		if (ActivityHelper.isOnline(this)) {
			new DownloadRssFeed().execute(message);
		} else {
			Toast toast = Toast.makeText(getBaseContext(), R.string.nedostupan_internet, Toast.LENGTH_LONG);
			toast.show();
			if (komentariProgressLayout != null)
				komentariProgressLayout.setVisibility(View.GONE);
			
			if (refresh != null)
				refresh.setActionView(null);
		}
	}
}

