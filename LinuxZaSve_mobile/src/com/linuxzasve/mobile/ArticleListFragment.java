package com.linuxzasve.mobile;

import java.io.IOException;
import java.util.List;

import android.app.Activity;
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

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.google.gson.Gson;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import com.linuxzasve.mobile.rest.LzsRestApi;
import com.linuxzasve.mobile.rest.LzsRestResponse;
import com.linuxzasve.mobile.rest.Post;
import com.linuxzasve.mobile.timthumb.TimThumb;

/**
 * Fragment displays list of articles. Extends <b>SherlockFragment</b> for compatibility with android versions older than 3.0.
 * After instantiation articleListFragmentType argument must be passed.
 * @author dejan
 *
 */
public class ArticleListFragment extends SherlockFragment {
	private ListView articleListView;
	private Context context;
	private LinearLayout progressSpinner;
	private MenuItem refresh;
	
	// Default behavior is LIST. Also, type SEARCH can be set.
	private ArticleListFragmentType articleListFragmentType;
	
	private List<Post> articleList;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		// fetching arguments. This is placed here because articleListFragmentType is being used in onCreateOptionsMenu (among
		// other places) and this method is for some reason bing called before onCreateView.
		articleListFragmentType = (ArticleListFragmentType) getArguments().getSerializable("articleListFragmentType");
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// This fragment participates in options menu creation, so it needs to announce it. 
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		// inflating fragment layout
		View rootView = inflater.inflate(R.layout.fragment_article_list, container, false);

		// fetching articleList view
		articleListView = (ListView) rootView.findViewById(R.id.articleList);
		
		// fetching articleListProgressLayout and setting it to visible
		progressSpinner = (LinearLayout) rootView.findViewById(R.id.articleListProgressLayout);
		progressSpinner.setVisibility(View.VISIBLE);
		
		// storing activity to private variable to be used from inner classes
		context = getActivity();
		
		String query = null;
		if (ArticleListFragmentType.SEARCH.equals(articleListFragmentType)) {
			Intent intent = getActivity().getIntent();
			
			if (Intent.ACTION_SEARCH.equals(intent.getAction()))
				query = intent.getStringExtra(SearchManager.QUERY);
		}
		
		fetchArticles(query);

		return rootView;
	}

	@Override
	public void onCreateOptionsMenu(final Menu menu, MenuInflater inflater) {
		if (ArticleListFragmentType.LIST.equals(articleListFragmentType)) {
			// inflating menu
			inflater.inflate(R.menu.menu_fragment_article_list, menu);
			
			// storing refresh menu item to private variable for usage in inner classes
			refresh = menu.findItem(R.id.menu_refresh);
		}

		super.onCreateOptionsMenu(menu, inflater);
	}
	
	/**
	 * Instantiates AsyncTask class which fetches new posts and executes it.
	 */
	public void fetchArticles(String search) {
		if (ActivityHelper.isOnline(context)) {
			new DownloadRssFeed().execute(search);
		}
		else {
			Toast toast = Toast.makeText(context, R.string.nedostupan_internet, Toast.LENGTH_LONG);
			toast.show();
			if (progressSpinner != null) {
				progressSpinner.setVisibility(View.GONE);
			}
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {

		switch (item.getItemId()) {
		
		case android.R.id.home:
			getActivity().onBackPressed();
			return true;

		case R.id.menu_refresh:
			refresh.setActionView(R.layout.actionbar_indeterminate_progress);
			fetchArticles(null);
			return true;

		case R.id.action_search:
			getActivity().onSearchRequested();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	// --------- inner classes
	
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
				String json = null;
				
				if (articleListFragmentType == ArticleListFragmentType.LIST) {
					json = api.getRecentPosts();
				} else if (articleListFragmentType == ArticleListFragmentType.SEARCH) {
					json = api.getSearchResult(urls[0]);
				}

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
			progressSpinner.setVisibility(View.GONE);
			if (refresh != null) {
				refresh.setActionView(null);
			}
			MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(context, lzs_feed.getPosts());

			articleListView.setAdapter(adapter);

			articleListView.setOnItemClickListener(new OnItemClickListener() {

				public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {

					Intent i = new Intent(getActivity().getApplicationContext(), Clanak.class);

					i.putExtra("naslov", articleList.get(position).getTitle());
					i.putExtra("sadrzaj", articleList.get(position).getContent());
					i.putExtra("komentari", articleList.get(position).getUrl());
					i.putExtra("origLink", articleList.get(position).getUrl());
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
//		private final Context context;

		public MySimpleArrayAdapter(final Context context, final List<Post> naslovi) {
			super(context, R.layout.novosti_redak, naslovi);
//			this.context = context;
			articleList = naslovi;
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
			holder.neki_tekst.setText(articleList.get(position).getTitle());
			holder.datum.setText(articleList.get(position).getDate("dd.MM.yyyy"));
			holder.autor.setText(articleList.get(position).getAuthor().getNickname());
			holder.broj_komentara.setText(Integer.toString(articleList.get(position).getComment_count()));

			String thumbnailUrl = TimThumb.generateTimThumbUrl(articleList.get(position).getThumbnail_images().getFull().getUrl(), 256, 256, 1);
			UrlImageViewHelper.setUrlDrawable(holder.thumbnail, thumbnailUrl);

			return convertView;
		}
	}
}
