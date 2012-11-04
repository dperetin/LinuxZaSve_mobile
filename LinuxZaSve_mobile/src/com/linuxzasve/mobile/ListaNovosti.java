package com.linuxzasve.mobile;

import java.util.List;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ListaNovosti extends SherlockActivity {
	
	private ListView listaClanaka;
	private ListaNovosti ovaAct;
	private ProgressDialog pDialog;
	public static List<LzsRssPost> values;
	
	private class DownloadRssFeed extends AsyncTask<String, Void, RssFeed> {
		@Override
		protected void onPreExecute() {
			pDialog = ProgressDialog.show(ovaAct, "Pričekajte trenutak ...", 
						"Dohvaćam popis članaka ...", true);
		}
		
		@Override
		protected RssFeed doInBackground(String... urls) {
			RssFeed lzs_feed = new RssFeed(urls[0]);
	
			return lzs_feed;
		}

		@Override
		protected void onPostExecute(RssFeed lzs_feed) {
			pDialog.dismiss();

			MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(ovaAct, 
					lzs_feed.getPosts());

			listaClanaka.setAdapter(adapter);

			listaClanaka.setOnItemClickListener(new OnItemClickListener(){
			
				public void onItemClick(AdapterView<?> parent, View view, 
						int position, long id) {
				
				Intent i = new Intent(getApplicationContext(), Clanak.class);
				
				i.putExtra("naslov", values.get(position).getTitle());
				i.putExtra("sadrzaj", values.get(position).getContent());
				i.putExtra("komentari", values.get(position).getCommentRss());
				i.putExtra("origLink", values.get(position).getOrigLink());
				startActivity(i);
				}
			});
		}
	}

	
	public class MySimpleArrayAdapter extends ArrayAdapter<LzsRssPost> {
		private final Context context;
		

		public MySimpleArrayAdapter(Context context, List<LzsRssPost> naslovi) {
			super(context, R.layout.novosti_redak, naslovi);
			this.context = context;
			values = naslovi;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				LayoutInflater inflater = (LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = inflater.inflate(R.layout.novosti_redak, parent, false);
			}
			TextView neki_tekst = (TextView) convertView.findViewById(R.id.neki_tekst );
			TextView datum = (TextView) convertView.findViewById(R.id.datum);
			TextView autor = (TextView) convertView.findViewById(R.id.autor);
			
			neki_tekst.setText(values.get(position).getTitle());
			datum.setText(values.get(position).datumDdmmyyy());
			autor.setText(values.get(position).getCreator());
			
			Typeface tf = Typeface.createFromAsset(getAssets(),
			        "Roboto-Regular.ttf");
			neki_tekst.setTypeface(tf);
			datum.setTypeface(tf);
			autor.setTypeface(tf);

			return convertView;
		}
	} 

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lista_novosti);

		listaClanaka = (ListView) findViewById(R.id.mylist);
		ovaAct = this;
		new DownloadRssFeed().execute("http://feeds.feedburner.com/linuxzasve");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.lista_novosti, menu);
		return true;
	}
}
