package com.linuxzasve.mobile;

import java.io.IOException;
import java.util.List;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.google.gson.Gson;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import com.linuxzasve.mobile.rest.LzsRestApi;
import com.linuxzasve.mobile.rest.LzsRestResponse;
import com.linuxzasve.mobile.rest.Post;
import com.linuxzasve.mobile.timthumb.TimThumb;

public class SearchActivity extends SherlockActivity {

	private ListView listaClanaka;
	private SearchActivity ovaAct;
	public static List<Post> values;
	LinearLayout novostiProgressLayout;

	private class DownloadRssFeed extends AsyncTask<String, Void, LzsRestResponse> {
		@Override
		protected void onPreExecute() {
		}

		@Override
		protected LzsRestResponse doInBackground(final String... urls) {
			LzsRestResponse obj2 = null;
			LzsRestApi api = null;
			try {
				api = new LzsRestApi();

				String json = api.getSearchResult(urls[0]);

				Gson gson = new Gson();
				obj2 = gson.fromJson(json, LzsRestResponse.class);
			}
			catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return obj2;
		}

		@Override
		protected void onPostExecute(final LzsRestResponse lzs_feed) {
			novostiProgressLayout.setVisibility(View.GONE);
			MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(ovaAct, lzs_feed.getPosts());

			listaClanaka.setAdapter(adapter);

			listaClanaka.setOnItemClickListener(new OnItemClickListener() {

				public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {

					Intent i = new Intent(getApplicationContext(), Clanak.class);

					i.putExtra("naslov", values.get(position).getTitle());
					i.putExtra("sadrzaj", values.get(position).getContent());
					i.putExtra("komentari", values.get(position).getUrl());
					i.putExtra("origLink", values.get(position).getUrl());
					startActivity(i);
				}
			});
		}
	}

	static class ViewHolder {
		TextView neki_tekst;
		TextView datum;
		TextView autor;
		TextView broj_komentara;
		ImageView thumbnail;
		boolean isSet = false;
	}

	public class MySimpleArrayAdapter extends ArrayAdapter<Post> {
		private final Context context;

		public MySimpleArrayAdapter(final Context context, final List<Post> naslovi) {
			super(context, R.layout.novosti_redak, naslovi);
			this.context = context;
			values = naslovi;
		}

		@Override
		public View getView(final int position, View convertView, final ViewGroup parent) {
			ViewHolder holder;

			if (convertView == null) {
				LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = inflater.inflate(R.layout.novosti_redak, parent, false);
				holder = new ViewHolder();

				holder.neki_tekst = (TextView)convertView.findViewById(R.id.neki_tekst);
				holder.datum = (TextView)convertView.findViewById(R.id.datum);
				holder.autor = (TextView)convertView.findViewById(R.id.autor);
				holder.broj_komentara = (TextView)convertView.findViewById(R.id.broj_komentara);
				holder.thumbnail = (ImageView)convertView.findViewById(R.id.thumbnail);

				convertView.setTag(holder);
			}
			else {
				holder = (ViewHolder)convertView.getTag();
			}
			holder.neki_tekst.setText(values.get(position).getTitle());
			holder.datum.setText(values.get(position).getDate());
			holder.autor.setText(values.get(position).getAuthor().getNickname());
			holder.broj_komentara.setText(Integer.toString(values.get(position).getComment_count()));

			if (values.get(position).getThumbnail_images() != null 
					&& values.get(position).getThumbnail_images().getFull() != null 
					&& values.get(position).getThumbnail_images().getFull().getUrl() != null) {
				String thumbnailUrl = TimThumb.generateTimThumbUrl(values.get(position).getThumbnail_images().getFull().getUrl(), 256, 256, 1);
				UrlImageViewHelper.setUrlDrawable(holder.thumbnail, thumbnailUrl);
			}

			return convertView;
		}
	}

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lista_novosti);

		listaClanaka = (ListView)findViewById(R.id.mylist);
		novostiProgressLayout = (LinearLayout)findViewById(R.id.novostiProgressLayout);
		ovaAct = this;
		novostiProgressLayout.setVisibility(View.VISIBLE);

		Intent intent = getIntent();
		String query = null;
	    if (Intent.ACTION_SEARCH.equals(intent.getAction()))
	      query = intent.getStringExtra(SearchManager.QUERY);
		
	    ActionBar ab = getSupportActionBar();
	    ab.setSubtitle("Tražim: " + query); 
	    
		fetchArticles(query);

	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {

		switch (item.getItemId()) {

			case android.R.id.home:
				onBackPressed();
				return true;

			default:
				return super.onOptionsItemSelected(item);
		}
	}

	public void fetchArticles(String search) {
		if (ActivityHelper.isOnline(this)) {
			new DownloadRssFeed().execute(search);
		}
		else {
			Toast toast = Toast.makeText(getBaseContext(), R.string.nedostupan_internet, Toast.LENGTH_LONG);
			toast.show();
			if (novostiProgressLayout != null) {
				novostiProgressLayout.setVisibility(View.GONE);
			}
		}
	}
}
