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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ListaNovosti extends SherlockActivity {
	
	// ovaj view je odvojen da mu se moze pristupiti iz background dretve
	private ListView listaClanaka;
	private ListaNovosti ovaAct;
	public final static String link = "com.example.myapp.MESSAGE";
	private ProgressDialog pDialog;
	private class DownloadRssFeed extends AsyncTask<String, Void, RssFeed> {
		@Override
		protected void onPreExecute() {
	        pDialog = ProgressDialog.show(ovaAct,"Pričekajte trenutak ...", "Dohvaćam popis članaka ...", true);
	    }
		
		@Override
		protected RssFeed doInBackground(String... urls) {

			RssFeed lzs_feed = new RssFeed(urls[0]);

			return lzs_feed;
		}

		@Override
		protected void onPostExecute(RssFeed lzs_feed) {
			pDialog.dismiss();

			MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(ovaAct, lzs_feed.lista_postova);

	        // Assign adapter to ListView
			listaClanaka.setAdapter(adapter);
	        
			listaClanaka.setOnItemClickListener(new OnItemClickListener(){
	        	
	        	public void onItemClick(AdapterView<?> parent, View view,
	        			int position, long id) {
	        		
	        		Intent i = new Intent(getApplicationContext(), Clanak.class);
	        		
	        		TextView neki_tekst = (TextView) view.findViewById(R.id.neki_tekst );
	        		
	        		i.putExtra(link, neki_tekst.getText());
	        		startActivity(i);	
	        		}
	        });
		}
	}

	
	public class MySimpleArrayAdapter extends ArrayAdapter<LzsRssPost> {
		private final Context context;
		private final List<LzsRssPost> values;

		public MySimpleArrayAdapter(Context context, List<LzsRssPost> naslovi) {
			super(context, R.layout.novosti_redak, naslovi);
			this.context = context;
			this.values = naslovi;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View rowView = inflater.inflate(R.layout.novosti_redak, parent, false);
			TextView neki_tekst = (TextView) rowView.findViewById(R.id.neki_tekst );
			TextView datum = (TextView) rowView.findViewById(R.id.datum);
			TextView autor = (TextView) rowView.findViewById(R.id.autor);
			
			neki_tekst.setText(values.get(position).getTitle());
			datum.setText(values.get(position).hrvatskiDatum());
			autor.setText(values.get(position).getCreator());

			return rowView;
		}
	} 
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_novosti);
        
        listaClanaka = (ListView) findViewById(R.id.mylist);
        ovaAct = this;
        //RssFeed lzs_feed = new RssFeed("http://feeds.feedburner.com/linuxzasve");
        new DownloadRssFeed().execute("http://feeds.feedburner.com/linuxzasve");

        

    } 
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.lista_novosti, menu);
        return true;
    }
}
