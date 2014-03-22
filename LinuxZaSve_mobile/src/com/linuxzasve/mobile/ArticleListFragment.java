package com.linuxzasve.mobile;

import java.util.List;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.linuxzasve.mobile.rest.LzsRestGateway;
import com.linuxzasve.mobile.rest.model.Post;
import com.linuxzasve.mobile.rest.response.GetRecentPostsResponseHandler;

/**
 * Fragment displays list of articles. Extends <b>SherlockFragment</b> for compatibility with android versions older
 * than 3.0. After instantiation articleListFragmentType argument must be passed.
 *
 * @author dejan
 *
 */
public class ArticleListFragment extends SherlockFragment {
	private ListView articleListView;
	private Context context;
	private LinearLayout progressSpinner;
	private MenuItem refresh;
	SherlockFragment fragment;

	// Default behavior is LIST. Also, type SEARCH can be set.
	private ArticleListFragmentType articleListFragmentType;

	private List<Post> articleList;

	@Override
	public void onAttach(final Activity activity) {
		super.onAttach(activity);

		// fetching arguments. This is placed here because articleListFragmentType is being used in onCreateOptionsMenu
		// (among other places) and this method is for some reason being called before onCreateView.
		if (getArguments() != null) {
			articleListFragmentType = (ArticleListFragmentType)getArguments().getSerializable("articleListFragmentType");
		}
		else {
			articleListFragmentType = ArticleListFragmentType.LIST;
		}
	}

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// This fragment participates in options menu creation, so it needs to announce it.
		setHasOptionsMenu(true);
		fragment = this;
	}

	@Override
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {

		// inflating fragment layout
		View rootView = inflater.inflate(R.layout.fragment_article_list, container, false);

		// fetching articleList view
		articleListView = (ListView)rootView.findViewById(R.id.articleList);

		// fetching articleListProgressLayout and setting it to visible
		progressSpinner = (LinearLayout)rootView.findViewById(R.id.articleListProgressLayout);
		progressSpinner.setVisibility(View.VISIBLE);

		// storing activity to private variable to be used from inner classes
		context = getActivity();

		String query = null;
		if (ArticleListFragmentType.SEARCH.equals(articleListFragmentType)) {
			Intent intent = getActivity().getIntent();

			if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
				query = intent.getStringExtra(SearchManager.QUERY);
			}
		}

		fetchArticles(query);

		return rootView;
	}

	@Override
	public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
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
	public void fetchArticles(final String search) {
		if (ActivityHelper.isOnline(context)) {
			LzsRestGateway g = new LzsRestGateway();
			GetRecentPostsResponseHandler responseHandler = new GetRecentPostsResponseHandler(progressSpinner, refresh, context, articleListView, articleList);

			if (ArticleListFragmentType.LIST.equals(articleListFragmentType)) {
				g.getRecentPosts(responseHandler);
			} else if (ArticleListFragmentType.SEARCH.equals(articleListFragmentType)) {
				g.getSearchResult(search, responseHandler);
			}

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
}
