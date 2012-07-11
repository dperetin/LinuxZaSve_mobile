package com.linuxzasve.mobile;

import java.util.List;

import com.linuxzasve.mobile.ListaNovosti.MySimpleArrayAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ListaKomentara extends Activity {
	
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
			WebView neki_tekst = (WebView) rowView.findViewById(R.id.teks_komentar );
			TextView datum = (TextView) rowView.findViewById(R.id.datum_komentar);
			TextView autor = (TextView) rowView.findViewById(R.id.autor_komentar);
			
			//neki_tekst.setText(values.get(position).getContent());
			datum.setText(values.get(values.size() - position - 1).hrvatskiDatum());
			autor.setText(values.get(values.size() - position - 1).getCreator());
			
			WebSettings settings = neki_tekst.getSettings();
	      	settings.setDefaultTextEncodingName("utf-8");
	      	neki_tekst.getSettings().setJavaScriptEnabled(true);
	      	neki_tekst.getSettings().setBuiltInZoomControls(true);
	      	
	            // displaying selected product name
	      	neki_tekst.loadData(values.get(values.size() - position - 1).getContent(), "text/html", null);
	

			return rowView;
		}
	} 
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_komentara);
        
        Intent intent = getIntent();
        String message = intent.getStringExtra(Clanak.link);
        
        ListView listView = (ListView) findViewById(R.id.komentari);
        
        RssFeed lzs_feed = new RssFeed(message);
        List<String> display_string = lzs_feed.getTitleList();

        
        /*String[] display_string = new String[] { "Android", "iPhone", "WindowsMobile",
        		"Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
        		"Linux", "OS/2" };*/
        
        // First paramenter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the View to which the data is written
        // Forth - the Array of data
        MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(this, lzs_feed.lista_postova);

        // Assign adapter to ListView
        listView.setAdapter(adapter);
        
        
        
        
        
    }
}

