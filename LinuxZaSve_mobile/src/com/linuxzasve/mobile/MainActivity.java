package com.linuxzasve.mobile;

import android.app.ActionBar;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;

import com.linuxzasve.mobile.fragments.ArticleDisplayFragment;
import com.linuxzasve.mobile.fragments.ArticleListFragment;
import com.linuxzasve.mobile.fragments.ArticleListFragmentType;
import com.linuxzasve.mobile.fragments.CommentEditFragment;
import com.linuxzasve.mobile.fragments.CommentListFragment;
import com.linuxzasve.mobile.rest.model.Post;

/**
 * Main activity. This activity handles all fragment for this app.
 *
 * @author dperetin
 */
public class MainActivity extends Activity implements
        ArticleListFragment.ArticleFragmentListener,
        ArticleDisplayFragment.ArticleDisplayFragmentListener,
        CommentListFragment.CommentListFragmentListener,
        CommentEditFragment.CommentEditFragmentListener {

    // holds action from intent
    private String intentAction;

    // if activity was started for search, hold term being searched for
    private String searchQuery;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        intentAction = intent.getAction();

        if (savedInstanceState == null) {
            ArticleListFragment articleListFragment = new ArticleListFragment();

            Bundle bundle = new Bundle();

            if (Intent.ACTION_SEARCH.equals(intentAction)) {
                searchQuery = intent.getStringExtra(SearchManager.QUERY);
                bundle.putSerializable(ArticleListFragment.ARTICLE_LIST_FRAGMENT_TYPE, ArticleListFragmentType.SEARCH);
            }
            else {
                bundle.putSerializable(ArticleListFragment.ARTICLE_LIST_FRAGMENT_TYPE, ArticleListFragmentType.LIST);
            }

            articleListFragment.setArguments(bundle);

            getFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, articleListFragment)
                .commit();
        }
    }

    /** article list fragment listeners **/

    @Override
    public void onArticleFragmentUpNavPressed() {
        onBackPressed();
    }

    @Override
    public void setArticleFragmentActionBarTitle() {
        ActionBar actionBar = getActionBar();

        if (actionBar != null) {
            if (Intent.ACTION_SEARCH.equals(intentAction)) {
                actionBar.setSubtitle(getResources().getString(R.string.action_bar_subtitle_article_search,
                        searchQuery));

                actionBar.setHomeButtonEnabled(true);
                actionBar.setDisplayHomeAsUpEnabled(true);
            } else {
                actionBar.setSubtitle(getResources().getString(R.string.action_bar_subtitle_article_list));

                actionBar.setHomeButtonEnabled(false);
                actionBar.setDisplayHomeAsUpEnabled(false);
            }


        }
    }

    @Override
    public void onListItemSelected(Post post) {
        ArticleDisplayFragment articleDisplayFragment = new ArticleDisplayFragment();

        Bundle bundle = new Bundle();
        bundle.putString(ArticleDisplayFragment.BUNDLE_POST_CONTENT, post.getContent());
        bundle.putString(ArticleDisplayFragment.BUNDLE_POST_TITLE, post.getTitle());
        bundle.putInt(ArticleDisplayFragment.BUNDLE_POST_ID, post.getId());
        bundle.putString(ArticleDisplayFragment.BUNDLE_POST_ORIGINAL_LINK, post.getUrl());

        articleDisplayFragment.setArguments(bundle);

        getFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, articleDisplayFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onSearchButtonPressed() {
        onSearchRequested();
    }

    // article display listeners

    @Override
    public void onArticleDisplayFragmentUpNavPressed() {
        onBackPressed();
    }

    @Override
    public void setUpArticleDisplayFragmentActionBar(String title) {
        ActionBar actionBar = getActionBar();

        if (actionBar != null) {
            actionBar.setSubtitle(title);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void onShareButtonPressed() {

    }

    @Override
    public void onCommentsListButtonPressed(Integer postId, String postTitle) {
        CommentListFragment fragment = new CommentListFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(ArticleDisplayFragment.BUNDLE_POST_ID, postId);
        bundle.putString(ArticleDisplayFragment.BUNDLE_POST_TITLE, postTitle);

        fragment.setArguments(bundle);

        getFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, fragment)
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void onCommentListFragmentUpNavPressed() {
        onBackPressed();
    }

    @Override
    public void setCommentListFragmentActionBarTitle(String title) {
        ActionBar actionBar = getActionBar();

        if (actionBar != null) {
            actionBar.setSubtitle(title);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void onNewCommentPressed(Integer postId) {
        CommentEditFragment fragment = new CommentEditFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(ArticleDisplayFragment.BUNDLE_POST_ID, postId);

        fragment.setArguments(bundle);

        getFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onCommentEditFragmentUpNavPressed() {
        onBackPressed();
    }

    @Override
    public void setCommentEditFragmentActionBarTitle() {
        ActionBar ab = getActionBar();

        if (ab != null) {
            ab.setHomeButtonEnabled(true);
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void onSendCommentButtonPressed() {
        onBackPressed();
    }
}
