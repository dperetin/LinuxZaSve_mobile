package com.linuxzasve.mobile;

import java.util.List;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ListaNovosti extends Activity {
	
	public final static String link = "com.example.myapp.MESSAGE";
	
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
        
        ListView listView = (ListView) findViewById(R.id.mylist);
        
        RssFeed lzs_feed = new RssFeed("http://feeds.feedburner.com/linuxzasve");

        MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(this, lzs_feed.lista_postova);

        // Assign adapter to ListView
        listView.setAdapter(adapter);
        
        listView.setOnItemClickListener(new OnItemClickListener(){
        	@Override
        	public void onItemClick(AdapterView<?> parent, View view,
        			int position, long id) {
        		
        		Intent i = new Intent(getApplicationContext(), Clanak.class);
        		
        		TextView neki_tekst = (TextView) view.findViewById(R.id.neki_tekst );
        		
        		i.putExtra(link, neki_tekst.getText());
        		startActivity(i);	
        		}
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.lista_novosti, menu);
        return true;
    } 
}
