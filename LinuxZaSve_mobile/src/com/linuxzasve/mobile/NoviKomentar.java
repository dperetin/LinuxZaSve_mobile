package com.linuxzasve.mobile;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import android.os.Bundle;
import android.widget.EditText;

public class NoviKomentar extends SherlockActivity {
	private String name;
	private String email;
	private String url;
	private String text;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.novi_komentar);
		
		EditText et1 = (EditText) findViewById(R.id.editText1);
		name = et1.getText().toString();
		
		EditText et2 = (EditText) findViewById(R.id.editText2);
		email = et2.getText().toString();
		
		EditText et3 = (EditText) findViewById(R.id.editText3);
		url = et3.getText().toString();
		
		EditText et4 = (EditText) findViewById(R.id.editText4);
		text = et4.getText().toString();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.novi_komentar, menu);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			return true;
			
		case R.id.menu_send:
			
			onBackPressed();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
}

