package com.linuxzasve.mobile.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.linuxzasve.mobile.ActivityHelper;
import com.linuxzasve.mobile.adapters.ArticleListArrayAdapter;
import com.linuxzasve.mobile.R;
import com.linuxzasve.mobile.rest.LzsRestGateway;
import com.linuxzasve.mobile.rest.model.LzsRestResponse;
import com.linuxzasve.mobile.rest.model.Post;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * This fragment is responsible for fetching and displaying article list.
 *
 * @author dperetin
 */
public class ArticleListFragment extends Fragment {
    private GridView articleListView;
    private LinearLayout progressSpinner;
    private MenuItem refresh;
    private Context context;

    private ArticleFragmentListener articleFragmentListener;

    // Default behavior is LIST. Also, type SEARCH can be set.
    private ArticleListFragmentType articleListFragmentType;

    public static final String ARTICLE_LIST_FRAGMENT_TYPE = "articleListFragmentType";
    public static final String ARTICLE_LIST = "article_list";

    private ArrayList<Post> articleList;

    @Override
    public void onResume() {
        super.onResume();

        articleFragmentListener.setArticleFragmentActionBarTitle();
    }

    @Override
    public void onAttach(final Activity activity) {
        super.onAttach(activity);

        context = activity;

        try {
            articleFragmentListener = (ArticleFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement ArticleFragmentListener");
        }
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        if (getArguments() != null) {
            articleList = getArguments().getParcelableArrayList(ARTICLE_LIST);
        }

        if (getArguments() != null) {

            articleListFragmentType =
                    (ArticleListFragmentType) getArguments().getSerializable(ARTICLE_LIST_FRAGMENT_TYPE);
        } else {

            articleListFragmentType = ArticleListFragmentType.LIST;
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.article_list_fragment, container, false);

        articleListView = (GridView) rootView.findViewById(R.id.articleList);

        progressSpinner = (LinearLayout) rootView.findViewById(R.id.articleListProgressLayout);
        progressSpinner.setVisibility(View.VISIBLE);

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
            inflater.inflate(R.menu.article_list_menu, menu);

            refresh = menu.findItem(R.id.menu_refresh_item);
        }

        super.onCreateOptionsMenu(menu, inflater);
    }

    private void fetchArticles(final String search) {

        if (ActivityHelper.isOnline(getActivity())) {

            LzsRestGateway g = new LzsRestGateway();


            Callback<LzsRestResponse> callback = new Callback<LzsRestResponse>() {
                @Override
                public void success(LzsRestResponse lzsRecentPosts, Response response) {
                        articleList = lzsRecentPosts.getPosts();

                        progressSpinner.setVisibility(View.GONE);

                        if (refresh != null) {
                            refresh.setActionView(null);
                        }

                        ArticleListArrayAdapter adapter = new ArticleListArrayAdapter(context, articleList);

                        articleListView.setAdapter(adapter);

                        articleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(final AdapterView<?> parent, final View view,
                                                    final int position, final long id) {

                                articleFragmentListener.onListItemSelected(articleList.get(position));
                            }
                        });
                }

                @Override
                public void failure(RetrofitError error) {
                    progressSpinner.setVisibility(View.GONE);

                    if (refresh != null) {
                        refresh.setActionView(null);
                    }

                    Toast toast =
                            Toast.makeText(getActivity(), R.string.article_fetch_fail, Toast.LENGTH_LONG);
                    toast.show();
                }
            };

            if (ArticleListFragmentType.LIST.equals(articleListFragmentType)) {
                g.getRecentPosts(callback);
            } else if (ArticleListFragmentType.SEARCH.equals(articleListFragmentType)) {
                g.getSearchResult(search, callback);
            }
        } else {

            Toast toast =
                    Toast.makeText(getActivity(), R.string.network_not_available, Toast.LENGTH_LONG);
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
                articleFragmentListener.onArticleFragmentUpNavPressed();
                return true;

            case R.id.menu_refresh_item:
                refresh.setActionView(R.layout.actionbar_indeterminate_progress);
                fetchArticles(null);
                return true;

            case R.id.menu_search_item:
                articleFragmentListener.onSearchButtonPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putParcelableArrayList(ARTICLE_LIST, articleList);
    }

    public interface ArticleFragmentListener {
        public void onArticleFragmentUpNavPressed();

        public void setArticleFragmentActionBarTitle();

        public void onListItemSelected(Post post);

        public void onSearchButtonPressed();
    }
}
