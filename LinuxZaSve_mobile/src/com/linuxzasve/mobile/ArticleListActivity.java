package com.linuxzasve.mobile;

import android.os.Bundle;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;

/**
 * Activity displays list of articles (posts).
 * @author dejan
 *
 */
public class ArticleListActivity extends SherlockFragmentActivity {
	
	/**
	 * Setting content view and creating action bar. 
	 * Fragment that is being loaded is defined the view.
	 */
	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.dual);
//		ArticleListFragment details = new ArticleListFragment();
//		getIntent().putExtra("articleListFragmentType", ArticleListFragmentType.LIST);
//		details.setArguments(getIntent().getExtras());
//		getSupportFragmentManager().beginTransaction().add(android.R.id.content, details).commit();
//		
		// Creating action bar and setting subtitle
		ActionBar ab = getSupportActionBar();
		ab.setSubtitle(getResources().getString(R.string.action_bar_subtitle_article_list));
	}
}
