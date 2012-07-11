package com.linuxzasve.mobile;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/*import lzs.app.rss.R;
import lzs.app.rss.RssFeed;*/

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.NavUtils;



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
   
        
        
        /*ImageView image;
        image = (ImageView) findViewById(R.id.imageView1);
        
        try {   
            ImageView i = (ImageView)findViewById(R.id.imageView1);
            Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL("http://www.linuxzasve.com/wp-content/themes/bigfoot/includes/timthumb.php?src=http://www.linuxzasve.com/wp-content/uploads/2012/07/thumb_mandriva.jpeg").getContent());
            i.setImageBitmap(bitmap);  
            } catch (MalformedURLException e) {   e.printStackTrace(); } 
        	  catch (IOException e) {   e.printStackTrace(); } */

       /* TextView naslov;
        naslov = (TextView) findViewById(R.id.naslov);
        naslov.setText("Dejan");*/
        
        ListView listView = (ListView) findViewById(R.id.mylist);
        
        RssFeed lzs_feed = new RssFeed("http://feeds.feedburner.com/linuxzasve");
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
        
        /*List<String> datumi = lzs_feed.getDateList();
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
            	R.layout.jedan_redak, R.id.datum, datumi);
        
        listView.setAdapter(adapter2);*/
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.lista_novosti, menu);
        return true;
    } 
}
