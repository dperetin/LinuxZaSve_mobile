package com.linuxzasve.mobile;

import java.io.IOException;
import java.util.List;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import com.linuxzasve.mobile.emote.EmoticonDrawables;
import com.linuxzasve.mobile.wp_comment.Komentar;
import com.linuxzasve.mobile.wp_comment.WordpressCommentParser;

import android.graphics.drawable.BitmapDrawable;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.Html.ImageGetter;
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
	String post_id;
	String akismet;
	private MenuItem refresh;
	
	
	public class MySimpleArrayAdapter extends ArrayAdapter<Komentar> {
		private final Context context;
		private final List<Komentar> values;

		public MySimpleArrayAdapter(Context context, List<Komentar> naslovi) {
			super(context, R.layout.komentar_redak, naslovi);
			this.context = context;
			this.values = naslovi;

			post_id = naslovi.get(0).getCommentPostId();
			akismet = naslovi.get(0).getAkismetCommentNounce();

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
			
//			neki_tekst.setText(Html.fromHtml(values.get(values.size() - position - 1).getContent()));
			
			neki_tekst.setText(Html.fromHtml(
					values.get(values.size() - position - 1).getContent(),
					new ImageGetter() {
						@Override
						public Drawable getDrawable(String source) {
							
							/* Dohvacam ID slike. Ako nema takovg smajla, vracam null */
							Integer id = EmoticonDrawables.getDrawableId(source);
							
							if (id == null) {
								return null;
							}

							Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), id);

							Drawable d = new BitmapDrawable(context.getResources(), bitmap);
							int dWidth = d.getIntrinsicWidth();
							int dHeight = d.getIntrinsicHeight();

							d.setBounds(0, -dHeight, dWidth, 0);
							return d;
						}
					}, null));
			
			UrlImageViewHelper
			.setUrlDrawable(thumbnail, values.get(values.size() - position - 1)
					.getThumbnail());
			
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
		
		ActionBar ab = getSupportActionBar();
		String naslov = intent.getStringExtra("naslov");
		ab.setSubtitle("Komentari na članak: " + naslov);
		
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
			
		case R.id.menu_new_comment:
			Intent intent = new Intent(this, NoviKomentar.class);
			
			intent.putExtra("post_id", post_id);
			intent.putExtra("akismet", akismet);
			intent.putExtra("orig_url", message);
			startActivity(intent);
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

