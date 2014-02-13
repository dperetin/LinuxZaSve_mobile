package com.linuxzasve.mobile;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;

/**
 * Activity displays list of articles (posts) from search results.
 * @author dejan
 *
 */
public class ArticleSearchActivity extends SherlockFragmentActivity {
	
	/**
	 * Setting content view and creating action bar. 
	 */
	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
//		setContentView(R.layout.activity_article_list);
		ArticleListFragment details = new ArticleListFragment();
		Intent intent = getIntent().putExtra("articleListFragmentType", ArticleListFragmentType.SEARCH);
		details.setArguments(getIntent().getExtras());
		getSupportFragmentManager().beginTransaction().add(android.R.id.content, details).commit();
		
		String query = null;
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			query = intent.getStringExtra(SearchManager.QUERY);
		}
		
		// Creating action bar and setting subtitle
		ActionBar ab = getSupportActionBar();
		ab.setSubtitle("Tražim: " + query);
		
		ab.setDisplayHomeAsUpEnabled(true);
	}
}
