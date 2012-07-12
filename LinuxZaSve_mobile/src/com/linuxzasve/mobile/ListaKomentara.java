package com.linuxzasve.mobile;

import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
			TextView neki_tekst = (TextView) rowView.findViewById(R.id.tekst_komentar );
			TextView datum = (TextView) rowView.findViewById(R.id.datum_komentar);
			TextView autor = (TextView) rowView.findViewById(R.id.autor_komentar);
			
			//neki_tekst.setText(values.get(position).getContent());
			datum.setText(values.get(values.size() - position - 1).hrvatskiDatum());
			autor.setText(values.get(values.size() - position - 1).getCreator());
			

	      	neki_tekst.setText(Html.fromHtml(values.get(values.size() - position - 1).getContent()));
	

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

        MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(this, lzs_feed.lista_postova);

        listView.setAdapter(adapter); 
    }
}

