package com.linuxzasve.mobile;

import android.os.Bundle;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;

public class ArticleDisplayActivity extends SherlockFragmentActivity {
	
	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
//		setContentView(R.layout.activity_article_list);
		ArticleDisplayFragment details = new ArticleDisplayFragment();
		details.setArguments(getIntent().getExtras());
		getSupportFragmentManager().beginTransaction().add(android.R.id.content, details).commit();
		
		// Creating action bar and setting subtitle
		ActionBar ab = getSupportActionBar();
		String message = getIntent().getStringExtra("articleTitle");
		ab.setSubtitle(message);
	}
}
