package com.linuxzasve.mobile;

import java.util.List;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class ListaKomentara extends SherlockActivity {
	
	private ListView listView;
	private ListaKomentara ovaAct;
	private ProgressDialog pDialog;
	
	public class MySimpleArrayAdapter extends ArrayAdapter<LzsRssPost> {
		private final Context context;
		private final List<LzsRssPost> values;

		public MySimpleArrayAdapter(Context context, List<LzsRssPost> naslovi) {
			super(context, R.layout.komentar_redak, naslovi);
			this.context = context;
			this.values = naslovi;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View rowView = inflater.inflate(R.layout.komentar_redak, parent, false);
			TextView neki_tekst = (TextView) rowView.findViewById(R.id.tekst_komentar );
			TextView datum = (TextView) rowView.findViewById(R.id.datum_komentar);
			TextView autor = (TextView) rowView.findViewById(R.id.autor_komentar);
			
			datum.setText(values.get(values.size() - position - 1).datumDdmmyyy());
			autor.setText(values.get(values.size() - position - 1).getCreator());
			
			neki_tekst.setText(Html.fromHtml(
					values.get(values.size() - position - 1).getContent())
					);
			
			return rowView;
		}
	} 
	
	private class DownloadRssFeed extends AsyncTask<String, Void, RssFeed> {
		@Override
		protected void onPreExecute() {
			pDialog = ProgressDialog.show(ovaAct, "Pričekajte trenutak ...", 
					"Dohvaćam komentare ...", true);
		}
		
		@Override
		protected RssFeed doInBackground(String... urls) {
			RssFeed lzs_feed = new RssFeed(urls[0]);
			
			return lzs_feed;
		}

		@Override
		protected void onPostExecute(RssFeed lzs_feed) {
			
			pDialog.dismiss();
			
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
		
		Intent intent = getIntent();
		String message = intent.getStringExtra("komentari");
		
		new DownloadRssFeed().execute(message);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.lista_komentara, menu);
		return true;
	}
}

